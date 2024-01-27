package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class UpdateBinaryCommand extends UICCCommand {
	
	protected Integer sfi;
	protected Integer offset;
	protected byte [] value;

	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_UPDATE_BINARY;
	}

	public static class Builder<T extends Builder<T>> {

		protected Integer sfi;
		protected Integer offset;
		protected byte [] value;
		
		public Builder() {
		}
		
		public Builder<T> sfi(Integer value) {
			this.sfi = value;
			return this;
		}

		public Builder<T> offset(Integer value) {
			this.offset = new Integer(value);
			return this;
		}

		public Builder<T> data(byte [] value) {
			this.value = value;
			return this;
		}
			
		public UpdateBinaryCommand build() throws Etsi102221Exception {
			UpdateBinaryCommand output = new UpdateBinaryCommand();
			output.sfi = this.sfi ;
			output.offset = this.offset;
			output.value = this.value;
			
			int codedOffset = offset != null ? (offset & 0xFF) : 0;
			output.p1 = sfi == null ? 0x7F & (codedOffset >> 8) : 0x80 | sfi;
			output.p2 = codedOffset;
			output.data = value;
			
			return output;
		}
	}
}
