package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class SetDataCommand extends UICCCommand {
	
	
	public static enum RetrieveMode {
		FIRST_BLOCK, NEXT_BLOCK, RETRANSMIT_PREV_BLOCK, RFU
	}
	
	protected Integer sfi;
	protected RetrieveMode mode;
	
	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_SET_DATA;
	}

	
	public static class Builder {

		protected Integer sfi;
		protected RetrieveMode mode;
		protected byte [] tlv;
		
		public Builder() {
			mode = RetrieveMode.FIRST_BLOCK;
		}
		
		public Builder sfi(int value) {
			this.sfi = value;
			return this;
		}

		public Builder mode(RetrieveMode value) {
			this.mode = value;
			return this;
		}

		
		public Builder data(byte [] value) {
			this.tlv = value;
			return this;
		}
		
		public SetDataCommand build() throws Etsi102221Exception {
			SetDataCommand output = new SetDataCommand();
			output.sfi = this.sfi;
			output.mode = this.mode;
			
			
			output.p2 = sfi == null ? 0 : sfi & 0x1F;
			switch (mode) {
				case FIRST_BLOCK: output.p2 |= 0x80;
					break;
				case NEXT_BLOCK:
					break; // Nothing to do
				case RETRANSMIT_PREV_BLOCK:
					output.p2 |= 0x40;					
					break;
				default: //RFU 
					output.p2 |= 0xC0;
			}
			
			output.data = this.tlv;
			
			return output;
		}
	}
}
