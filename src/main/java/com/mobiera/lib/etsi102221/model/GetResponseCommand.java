package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.EtsiConstants;

public class GetResponseCommand extends UICCCommand {

	protected int returnByteCount;
	
	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_GET_RESPONSE;
	}

	public static class Builder<T extends Builder<T>> {

		protected int returnByteCount;
		
		public Builder() {
			returnByteCount = 0;
		}
		
		public Builder<T> returnBytecount(int value) {
			this.returnByteCount = value;
			return this;
		}

		public GetResponseCommand build() {
			GetResponseCommand output = new GetResponseCommand();
			output.returnByteCount = this.returnByteCount;

			output.le = this.returnByteCount;
			return output;
		}
	}
}
