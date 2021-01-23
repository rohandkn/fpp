package fpp.compiler.codegen

import fpp.compiler.analysis._
import fpp.compiler.ast._
import fpp.compiler.util._

/** Write out F Prime XML for array definitions */
object ArrayXmlWriter extends AstVisitor with LineUtils {

  override def default(s: XmlWriterState) = Nil

  override def defArrayAnnotatedNode(s: XmlWriterState, aNode: Ast.Annotated[AstNode[Ast.DefArray]]) = {
    val node = aNode._2
    val data = node.data
    val tags = {
      val pairs = s.getNamespaceAndName(Symbol.Array(aNode))
      XmlTags.tags("array", pairs)
    }
    val body = {
      val Right(a1) = UsedSymbols.defArrayAnnotatedNode(s.a, aNode)
      val s1 = s.copy(a = a1)
      val comment = AnnotationXmlWriter.multilineComment(aNode)
      val imports = s1.writeImportDirectives
      val arrayType @ Type.Array(_, _, _, _) = s.a.typeMap(node.id) 

      val t = {
        val typeName = TypeXmlWriter.getName(s, arrayType.anonArray.eltType)
        val stringSize = TypeXmlWriter.getSize(s, arrayType.anonArray.eltType)
        val openTag = stringSize match {
          case Some(openTag)=> XmlTags.openTag("type", List( ("size", openTag) ))
          case None => XmlTags.openTag("type", Nil)
        }
        val closeTag = XmlTags.closeTag("type")
        val tags = openTag ++ typeName ++ closeTag
        List(line(tags))
      }
      val size = {
        val tags = XmlTags.tags("size")
        val mappedSize = arrayType.getArraySize match {
          case Some(mappedSize) => mappedSize.toString
          case None => "0"
        }
        List(line(XmlTags.taggedString(tags)(mappedSize)))
      }
      val format = {
        val tags = XmlTags.tags("format")
        val format = arrayType.format match {
          case Some(format) => format
          case None => Format("", List((Format.Field.Default,"")))
        }
        val s = FormatXmlWriter.formatToString(format, List(data.eltType))
        List(line(XmlTags.taggedString(tags)(s)))
      }
      val default = {
        val defaultTags = XmlTags.tags("default")
        val defaultValue = arrayType.getDefaultValue.get
        val ls = writeDefaultValue(s, defaultValue)
        XmlTags.taggedLines(defaultTags)(ls.map(indentIn))
      }
      List(
        imports,
        comment,
        t,
        size,
        format,
        default
      ).flatten
    } 
    XmlTags.taggedLines(tags)(body.map(indentIn))
  }

  /** Writes the default value corresponding to an array value */
  def writeDefaultValue(
    s: XmlWriterState,
    arrayValue: Value.Array
  ): List[Line] = {
    val tags = XmlTags.tags("value")
    val elements = arrayValue.anonArray.elements
    val values = elements.map(ValueXmlWriter.getValue(s, _))
    values.map(XmlTags.taggedString(tags)(_)).map(line)
  }

  type In = XmlWriterState

  type Out = List[Line]

}
