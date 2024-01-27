package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class IncreaseCommand extends UICCCommand {
	
	protected Integer sfi;
	protected byte [] length;
	protected byte[] value;

	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_INCREASE;
	}

	public static class Builder {

		protected Integer sfi;
		protected Integer length;
		protected byte [] value;
				
		public Builder() {
			// Default: read all contents until end of file
			length = 0;
		}
		
		public Builder sfi(int value) {
			this.sfi = value;
			return this;
		}

		public Builder value(byte [] value) {
			this.value = value;
			return this;
		}
		
			
		public IncreaseCommand build() throws Etsi102221Exception {
			IncreaseCommand output = new IncreaseCommand();
			output.sfi = this.sfi;

			output.value = this.value;
			output.le = this.length;
			
			if (sfi != null)	
			{
				output.p1 = (byte) 0x80;
				output.p1 |= sfi;
			}
				
			output.data = this.value;
			
			return output;
		}
	}
}
