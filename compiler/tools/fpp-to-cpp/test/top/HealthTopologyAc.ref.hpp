// ======================================================================
// \title  HealthTopologyAc.hpp
// \author Generated by fpp-to-cpp
// \brief  hpp file for Health topology
//
// \copyright
// Copyright (c) 2021 California Institute of Technology.
// U.S. Government sponsorship acknowledged.
// All rights reserved.
// ======================================================================

#ifndef M_HealthTopologyAc_HPP
#define M_HealthTopologyAc_HPP

#include "C.hpp"
#include "HealthImpl.hpp"
#include "HealthTopologyDefs.hpp"

namespace M {

  // ----------------------------------------------------------------------
  // Constants
  // ----------------------------------------------------------------------

  namespace BaseIds {
    enum {
      health = 0x100,
      c1 = 0x200,
      c2 = 0x300,
    };
  }

  namespace InstanceIds {
    enum {
      c1,
      c2,
      health,
    };
  }

  // ----------------------------------------------------------------------
  // Public interface functions
  // ----------------------------------------------------------------------

  //! Set up the topology
  void setup(
      const TopologyState& state //!< The topology state
  );

  //! Tear down the topology
  void teardown(
      const TopologyState& state //!< The topology state
  );

}

#endif