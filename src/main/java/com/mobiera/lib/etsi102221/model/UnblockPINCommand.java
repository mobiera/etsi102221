package com.mobiera.lib.etsi102221.model;

import org.bouncycastle.util.Arrays;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;
import com.mobiera.lib.etsi102221.model.fcp.PINStatusTemplateDO.KeyReference;
import com.mobiera.lib.etsi102221.model.fcp.PINStatusTemplateDO.Pin;

public class UnblockPINCommand extends UICCCommand {
	
	protected byte [] oldValue;
	protected byte [] newValue;
	protected Pin pin;
	
	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_UNBLOCK_PIN;
	}

	public static class Builder {

		protected byte [] oldValue;
		protected byte [] newValue;
		protected Pin pin;
		
		public Builder() {
		}
		
		public Builder pin(Pin value) {
			this.pin = value;
			return this;
		}

		public Builder oldValue(byte [] value) {
			this.oldValue = value;
			return this;
		}

		public Builder newValue(byte [] value) {
			this.newValue = value;
			return this;
		}

		public UnblockPINCommand build() throws Etsi102221Exception {
			UnblockPINCommand output = new UnblockPINCommand();
			
			output.pin = this.pin;
			output.oldValue = this.oldValue;
			output.newValue = this.newValue;
			
			if (output.oldValue == null || output.newValue == null)
				throw new Etsi102221Exception("Old and New PIN values shall be present");

			if (output.oldValue.length != 8 || output.newValue.length != 8)
				throw new Etsi102221Exception("PINs shall be 8 bytes long");
			
			output.p2 = KeyReference.encodePin(pin);
			output.data = Arrays.concatenate(oldValue, newValue);
			
			return output;
		}
	}
}
