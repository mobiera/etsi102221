package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class ReadBinaryCommand extends UICCCommand {
	
	
	protected Integer sfi;
	protected int offset;
	protected int length;
	
	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_READ_BINARY;
	}

	
	public static class Builder<T extends Builder<T>> {

		protected Integer sfi;
		protected Integer offset;
		protected Integer length;
		
		public Builder() {
			// Default: read all contents until end of file
			length = 0;
			offset = new Integer(0);
		}
		
		public Builder<T> sfi(Integer value) {
			this.sfi = value;
			return this;
		}

		public Builder<T> offset(Integer value) {
			this.offset = new Integer(value);
			return this;
		}

		public Builder<T> length(Integer value) {
			this.length = new Integer(value);
			return this;
		}
		
		public ReadBinaryCommand build() throws Etsi102221Exception {
			ReadBinaryCommand output = new ReadBinaryCommand();
			output.sfi = this.sfi;
			output.offset = this.offset;
			output.length = this.length;
			int codedOffset = (offset != null ? offset : 0);
			output.p1 = sfi == null ? 0x7F & (codedOffset >> 8) : 0x80 | sfi;;
			output.p2 = codedOffset;
			output.data = new byte[0];
			output.le = length != null ? length : 0;
			
			return output;
		}
	}
}
