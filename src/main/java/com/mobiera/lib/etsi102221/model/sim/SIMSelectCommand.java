package com.mobiera.lib.etsi102221.model.sim;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.SelectCommand;

/** 
 * SELECT FILE TS 151.011 command implementation as a subset from TS 102.221 SELECT
 * 
 *   @author Ariel Gentile
 **/

public class SIMSelectCommand extends SelectCommand {

	@Override
	protected int getCLA() {
		return 0xA0;
	}

	public static class Builder extends SelectCommand.Builder<Builder> {

		@Override
		public SIMSelectCommand build() throws Etsi102221Exception {
			SIMSelectCommand output = new SIMSelectCommand();
			
			if (path != null) 
				throw new Etsi102221Exception(
						"Only SELECT BY FID is supported");
			
			if( fid == null)
				throw new Etsi102221Exception(
						"FID cannot be null");
			
			output.fid = fid;	
			output.p1 = 0;
			output.p2 = 0;
			output.data = new byte [] {(byte) (fid >> 8), (byte) (fid & 0xFF)};
			
			return output;
		}
	}


}
