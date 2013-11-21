/*
   Copyright 2013 Technical University of Denmark, DTU Compute. 
   All rights reserved.
   
   This file is part of the time-predictable VLIW processor Patmos.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are met:

      1. Redistributions of source code must retain the above copyright notice,
         this list of conditions and the following disclaimer.

      2. Redistributions in binary form must reproduce the above copyright
         notice, this list of conditions and the following disclaimer in the
         documentation and/or other materials provided with the distribution.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER ``AS IS'' AND ANY EXPRESS
   OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
   OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
   NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
   THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

   The views and conclusions contained in the software and documentation are
   those of the authors and should not be interpreted as representing official
   policies, either expressed or implied, of the copyright holder.
 */

/*
 * Stack cache execution stage
 * 
 * Author: Sahar Abbaspour (sabb@dtu.dk)
 * 
 */


package patmos

import Chisel._
import Node._


//import scala.collection.mutable.HashMap
//import scala.util.Random
import scala.math
import Constants._ 

class SC_ex(sc_size: Int, mem_size: Int, scStart: Int) extends Component {
    val io = new Bundle {
    val sc_func_type 	= UFix(INPUT, 2) // 00: reserve, 01: ensure, 10: free
    val imm				= UFix(INPUT, width = ADDR_WIDTH)
    val spill 			= UFix(OUTPUT, 1)
    val fill 			= UFix(OUTPUT, 1)
    val free			= UFix(OUTPUT, 1)
    val stall			= UFix(INPUT, 1)
    val m_top			= UFix(INPUT, width = ADDR_WIDTH)
    val n_spill			= Fix(OUTPUT, log2Up(sc_size))
    val n_fill			= Fix(OUTPUT, log2Up(sc_size))
    val sc_top			= UFix(OUTPUT, width = ADDR_WIDTH) 
  }
  	
    
	val sc_top 			= Reg(resetVal = UFix(scStart, width = ADDR_WIDTH)) // 
	
	
	val immToByte		= io.imm << UFix(2)
	val scTopImmDiff	= sc_top - immToByte// sc_top - n
	val scTopImmAdd		= sc_top + immToByte

	
	io.n_spill 			:= UFix(0) // reset value
	io.n_fill 			:= UFix(0) // reset value
	io.free				:= UFix(0)
	val reserve_size 	= io.m_top - scTopImmDiff - (UFix(sc_size) << UFix(2)) //(UFix(1)  << UFix(log2Up(sc_size))) 
	val ensure_size 	= scTopImmAdd - io.m_top 
	
	
	
	  
	// res_diff 	<= signed(mem_top) - signed(sc_top) - sc_size + signed(decdout.imm);
	//	ens_diff	<= signed(decdout.imm) - signed(mem_top) + signed(sc_top);  
	io.spill 			:= UFix(0)
	io.fill				:= UFix(0)
	io.sc_top			:= sc_top // 
	
	val stall 	= Reg(resetVal = UFix(0, 1))
    stall := io.stall

    when (stall === UFix(0)) {
		  when (io.sc_func_type === UFix(0)){ // reserve
		    sc_top 			:= scTopImmDiff
		    io.sc_top		:= scTopImmDiff
		    when (reserve_size > UFix(0)){
		      io.spill 		:= UFix(1)
		      io.n_spill 	:= reserve_size(ADDR_WIDTH - 1, 2)		   
		    }
		    .otherwise {
		      io.spill 		:= UFix(0)
		    }
		  }
		  when (io.sc_func_type === UFix(1)){ // ensure
		    when (ensure_size > UFix(0)){
		      io.fill 		:= UFix(1)
		      io.n_fill 	:= ensure_size(ADDR_WIDTH - 1, 2)
		    }
		    .otherwise {
		      io.fill 		:= UFix(0)
		    }
		  }
		  when (io.sc_func_type === UFix(2)){ // free
		    io.spill 		:= UFix(0)
		    io.fill			:= UFix(0)
		    io.free			:= UFix(1)
		    sc_top 			:= scTopImmAdd
		    io.sc_top 		:= scTopImmAdd
		  }
		  
		  when (io.sc_func_type === UFix(3)) {
		    io.spill 		:= UFix(0)
		    io.fill			:= UFix(0)
		    io.n_spill 		:= UFix(0)
		    io.n_fill 		:= UFix(0)
		  }
	}

}
  




