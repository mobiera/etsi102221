package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class RetrieveDataCommand extends UICCCommand {
	
	
	public static enum RetrieveMode {
		FIRST_BLOCK, NEXT_BLOCK, RETRANSMIT_PREV_BLOCK, RFU
	}
	
	protected Integer sfi;
	protected RetrieveMode mode;
	protected int length;
	
	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_RETRIEVE_DATA;
	}

	
	public static class Builder {

		protected Integer sfi;
		protected RetrieveMode mode;
		protected byte [] tag;
		protected int length;
		
		public Builder() {
			// Default: read all contents
			length = 0;
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

		public Builder length(int value) {
			this.length = value;
			return this;
		}
		
		public Builder tag(byte [] value) {
			this.tag = value;
			return this;
		}
		
		public RetrieveDataCommand build() throws Etsi102221Exception {
			RetrieveDataCommand output = new RetrieveDataCommand();
			output.sfi = this.sfi;
			output.mode = this.mode;
			output.length = this.length;
			
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
			
			output.data = (mode == RetrieveMode.FIRST_BLOCK) ? tag : new byte[0];
			output.le = length;
			
			return output;
		}
	}
}
