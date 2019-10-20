(* ----------------------------------------------------------------------
 * File.sml
 * An FPP file location
 * ----------------------------------------------------------------------*)

structure File =
struct

  datatype t = File of {
    (* The file name *)
    name: string,
    (* The directory path, represented as a list of directories
       The directory containing the file is at the head of the list. *)
    dir_path : string list
  }

  val stdin = File { name = "stdin", dir_path = [] }

end
