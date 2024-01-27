package com.mobiera.lib.etsi102221.model.sim;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.ReadBinaryCommand;

/** 
 * SELECT FILE TS 151.011 command implementation as a subset from TS 102.221 SELECT
 * 
 *   @author Ariel Gentile
 **/

public class SIMReadBinaryCommand extends ReadBinaryCommand {

	@Override
	protected int getCLA() {
		return 0xA0;
	}

	public static class Builder extends ReadBinaryCommand.Builder<Builder> {

		@Override
		public SIMReadBinaryCommand build() throws Etsi102221Exception {
			SIMReadBinaryCommand output = new SIMReadBinaryCommand();
			
			if (sfi != null) 
				throw new Etsi102221Exception(
						"SFI is not supported");
			
			output.p1 = offset != null ? offset >> 8 : 0;
			output.p2 = offset != null ? offset & 0xFF : 0;
			output.data = new byte[0];
			output.le = length != null ? length : 0;			
			
			return output;
		}
	}


}
