package com.mobiera.lib.etsi102221.model.sim;

import com.mobiera.lib.etsi102221.model.GetResponseCommand;

/** 
 * SELECT FILE TS 151.011 command implementation as a subset from TS 102.221 SELECT
 * 
 *   @author Ariel Gentile
 **/

public class SIMGetResponseCommand extends GetResponseCommand {

	@Override
	protected int getCLA() {
		return 0xA0;
	}

	public static class Builder extends GetResponseCommand.Builder<Builder> {

		@Override
		public SIMGetResponseCommand build() {
			SIMGetResponseCommand output = new SIMGetResponseCommand();
			
			output.returnByteCount = this.returnByteCount;
			output.le = this.returnByteCount;

			return output;			
		}
	}


}
