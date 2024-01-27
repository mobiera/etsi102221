package com.mobiera.lib.etsi102221.model.sim;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.UpdateRecordCommand;

/** 
 * UPDATE RECORD TS 151.011 command implementation as a subset from TS 102.221 UPDATE RECORD
 * 
 *   @author Ariel Gentile
 **/

public class SIMUpdateRecordCommand extends UpdateRecordCommand {

	@Override
	protected int getCLA() {
		return 0xA0;
	}

	public static class Builder extends UpdateRecordCommand.Builder<Builder> {

		@Override
		public SIMUpdateRecordCommand build() throws Etsi102221Exception {
			SIMUpdateRecordCommand output = new SIMUpdateRecordCommand();
			
			if (sfi != null) 
				throw new Etsi102221Exception(
						"SFI is not supported");

			if (data == null) 
				throw new Etsi102221Exception("Update Record contents cannot be null");

			output.recordNumber = this.recordNumber;
			output.mode = this.mode;
			output.data = this.data;

			switch (mode) {
				case NEXT_RECORD:
					output.p1 = 0x00;
					output.p2 |= 0x02;
					break;
				case PREVIOUS_RECORD:
					output.p1 = 0x00;
					output.p2 |= 0x03;
					break;
				default: // or ABSOLUTE/CURRENT
					output.p1 = recordNumber;
					output.p2 |= 0x04;
			}
			
			if (sfi != null)
				output.p1 |= sfi << 3;
			
			return output;
		}
	}


}
