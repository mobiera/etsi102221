package com.mobiera.lib.etsi102221.model.sim;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.UpdateBinaryCommand;

/** 
 * UPDATE BINARY TS 151.011 command implementation as a subset from TS 102.221 UPDATE BINARY
 * 
 *   @author Ariel Gentile
 **/

public class SIMUpdateBinaryCommand extends UpdateBinaryCommand {

	@Override
	protected int getCLA() {
		return 0xA0;
	}

	public static class Builder extends UpdateBinaryCommand.Builder<Builder> {

		@Override
		public SIMUpdateBinaryCommand build() throws Etsi102221Exception {
			SIMUpdateBinaryCommand output = new SIMUpdateBinaryCommand();
			
			if (sfi != null) 
				throw new Etsi102221Exception(
						"SFI is not supported");

			if (value == null) 
				throw new Etsi102221Exception(
						"File contents cannot be null");

			output.offset = this.offset;
			output.value = this.value;

			output.p1 = offset != null ? offset >> 8 : 0;
			output.p2 = offset != null ? offset & 0xFF : 0;
			output.data = this.value;			

			
			return output;
		}
	}


}
