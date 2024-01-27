package com.mobiera.lib.etsi102221.model.sim;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.ReadRecordCommand;

/** 
 * READ RECORD ILE TS 151.011 command implementation as a subset from TS 102.221 READ RECORD
 * 
 *   @author Ariel Gentile
 **/

public class SIMReadRecordCommand extends ReadRecordCommand {

	@Override
	protected int getCLA() {
		return 0xA0;
	}

	public static class Builder extends ReadRecordCommand.Builder<Builder> {

		@Override
		public SIMReadRecordCommand build() throws Etsi102221Exception {
			SIMReadRecordCommand output = new SIMReadRecordCommand();
			
			if (sfi != null) 
				throw new Etsi102221Exception(
						"SFI is not supported");
			
			output.recordNumber = this.recordNumber;
			output.mode = this.mode;
			output.le = this.length;
			
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
			
			return output;
		}
	}


}
