package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class UpdateRecordCommand extends UICCCommand {
	
	public static enum Mode { NEXT_RECORD, PREVIOUS_RECORD, 
		ABSOLUTE, CURRENT };

	protected Integer sfi;
	protected Mode mode;
	protected int recordNumber;

	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_UPDATE_RECORD;
	}

	public static class Builder<T extends Builder<T>> {

		protected Integer sfi;
		protected Mode mode;
		protected int recordNumber;
		protected byte [] data;
		
		public Builder() {
			this.mode = Mode.ABSOLUTE;
		}
		
		public Builder<T> sfi(int value) {
			this.sfi = value;
			return this;
		}

		public Builder<T> recordNumber(int value) {
			this.recordNumber = value;
			return this;
		}

		public Builder<T> mode(Mode value) {
			this.mode = value;
			return this;
		}

		public Builder<T> data(byte [] value) {
			this.data = value;
			return this;
		}
			
		public UpdateRecordCommand build() throws Etsi102221Exception {
			UpdateRecordCommand output = new UpdateRecordCommand();
			output.sfi = this.sfi;
			output.recordNumber = this.recordNumber;
			output.mode = this.mode;
			output.data = this.data;
			
			if (data == null) 
				throw new Etsi102221Exception("Update Record contents cannot be null");

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
