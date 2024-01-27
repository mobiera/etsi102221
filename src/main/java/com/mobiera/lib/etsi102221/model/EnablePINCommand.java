package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;
import com.mobiera.lib.etsi102221.model.fcp.PINStatusTemplateDO.KeyReference;
import com.mobiera.lib.etsi102221.model.fcp.PINStatusTemplateDO.Pin;

public class EnablePINCommand extends UICCCommand {
	
	protected byte [] value;
	protected Pin pin;
	
	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_ENABLE_PIN;
	}

	public static class Builder {

		protected byte [] value;
		protected Pin pin;
		
		public Builder() {
		}
		
		public Builder pin(Pin value) {
			this.pin = value;
			return this;
		}

		public Builder value(byte [] value) {
			this.value = value;
			return this;
		}
			
		public EnablePINCommand build() throws Etsi102221Exception {
			EnablePINCommand output = new EnablePINCommand();
			
			output.pin = this.pin;
			output.value = this.value;
			
			if (output.value == null)
				throw new Etsi102221Exception("PIN value must be present");
			
			if (output.value.length != 8)
				throw new Etsi102221Exception("Wrong PIN length");
			
			output.p2 = KeyReference.encodePin(pin);
			output.data = value;
			
			return output;
		}
	}
}
