package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.Arrays;

import com.mobiera.java.sim.util.tlv.BerTlv;
import com.mobiera.java.sim.util.tlv.Tag;
import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class PINStatusTemplateDO extends FCPDataObject {

	public static final byte TAG_PS_DO 				= (byte) 0x90;
	public static final byte TAG_USAGE_QUALIFIER 	= (byte) 0x95;
	public static final byte TAG_KEY_REFERENCE 		= (byte) 0x83;
	
	public static enum Pin {
		PIN1, PIN2, PIN3, PIN4, PIN5, PIN6, PIN7, PIN8, RFU, ADM1, ADM2, ADM3,
		ADM4, UNIVERSAL_PIN, SEC_PIN1, SEC_PIN2, SEC_PIN3, SEC_PIN4, SEC_PIN5,
		SEC_PIN6, SEC_PIN7, SEC_PIN8, ADM5, ADM6, ADM7, ADM8, ADM9, ADM10
	}
	
	public static enum State { DISABLED, ENABLED };
	
	public static class PinStatus {
		
		private State state;
		private KeyReference keyReference;
		
		private Byte qualifier; // TODO: Encode / Decode
		
		public PinStatus() {
			
		}
		
		public PinStatus(State state, KeyReference keyReference, byte qualifier) {
			this.qualifier = qualifier;
			this.state = state;
			this.keyReference = keyReference;
		}
		
		public PinStatus(State state, KeyReference keyReference) {
			this.state = state;
			this.keyReference = keyReference;
		}
		
		public Byte qualifier() {
			return qualifier;
		}
		public boolean isEnabled() {
			return state == State.ENABLED;
		}
		
		public void enabled(boolean value) {
			state = value ? State.ENABLED : State.DISABLED; 
		}
		@Override
		public String toString() {
			return " [ " + keyReference.toString()+ " (" + state + ") ]";
		}
	}
	
	public static class KeyReference {
		
		private Pin pin;
		
		public static Pin decodePin(byte value) {
			Pin p;
			int val = value & 0xFF;
			
			// Particular cases
			switch(val){
				case 0x01: p = Pin.PIN1; break;
				case 0x02: p = Pin.PIN2; break;
				case 0x03: p = Pin.PIN3; break;
				case 0x04: p = Pin.PIN4; break;
				case 0x05: p = Pin.PIN5; break;
				case 0x06: p = Pin.PIN6; break;
				case 0x07: p = Pin.PIN7; break;
				case 0x08: p = Pin.PIN8; break;
				case 0x0A: p = Pin.ADM1; break;				
				case 0x0B: p = Pin.ADM2; break;
				case 0x0C: p = Pin.ADM3; break;
				case 0x0D: p = Pin.ADM4; break;
				case 0x0E: p = Pin.ADM5; break;
				case 0x11: p = Pin.UNIVERSAL_PIN; break;
				case 0x81: p = Pin.SEC_PIN1; break;
				case 0x82: p = Pin.SEC_PIN2; break;
				case 0x83: p = Pin.SEC_PIN3; break;
				case 0x84: p = Pin.SEC_PIN4; break;
				case 0x85: p = Pin.SEC_PIN5; break;
				case 0x86: p = Pin.SEC_PIN6; break;
				case 0x87: p = Pin.SEC_PIN7; break;
				case 0x88: p = Pin.SEC_PIN8; break;
				case 0x8A: p = Pin.ADM6; break;				
				case 0x8B: p = Pin.ADM7; break;
				case 0x8C: p = Pin.ADM8; break;
				case 0x8D: p = Pin.ADM9; break;
				case 0x8E: p = Pin.ADM10; break;
				default: p = Pin.RFU; break;
			}
			
			return p;
		}
		
		public static byte encodePin(Pin value) {
			byte p;
			
			// Particular cases
			switch(value){
				case PIN1: p = (byte)0x01; break;
				case PIN2: p = (byte)0x02; break;
				case PIN3: p = (byte)0x03; break;
				case PIN4: p = (byte)0x04; break;
				case PIN5: p = (byte)0x05; break;
				case PIN6: p = (byte)0x06; break;
				case PIN7: p = (byte)0x07; break;
				case PIN8: p = (byte)0x08; break;
				case ADM1: p = (byte)0x0A; break;
				case ADM2: p = (byte)0x0B; break;
				case ADM3: p = (byte)0x0C; break;
				case ADM4: p = (byte)0x0D; break;
				case ADM5: p = (byte)0x0E; break;
				case UNIVERSAL_PIN: p = (byte)0x11; break;
				case SEC_PIN1: p = (byte)0x81; break;
				case SEC_PIN2: p = (byte)0x82; break;
				case SEC_PIN3: p = (byte)0x83; break;
				case SEC_PIN4: p = (byte)0x84; break;
				case SEC_PIN5: p = (byte)0x85; break;
				case SEC_PIN6: p = (byte)0x86; break;
				case SEC_PIN7: p = (byte)0x87; break;
				case SEC_PIN8: p = (byte)0x88; break;
				case ADM6: p = (byte)0x8A; break;
				case ADM7: p = (byte)0x8B; break;
				case ADM8: p = (byte)0x8C; break;
				case ADM9: p = (byte)0x8D; break;
				case ADM10: p = (byte)0x8E; break;
				default: p = (byte)0x09; break; // RFU
			}
			
			return p;
		}
		
		public KeyReference(byte pin) {
			this.pin = decodePin(pin);
		}
		
		public KeyReference(Pin pin) {
			this.pin = pin;
		}

		public Pin getPin() {
			return pin;
		}

		public void setPin(Pin pin) {
			this.pin = pin;
		}

		public byte [] getBytes() {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			bo.write(TAG_KEY_REFERENCE); // Tag
			bo.write(1); // Length
			bo.write(encodePin(pin));
			return bo.toByteArray();
		}
		
		@Override
		public String toString() {
			return pin.name();
		}
	}
		
	private List<PinStatus> pins;
	
	public List<PinStatus> getPins() {
		return pins;
	}
	
	public static PINStatusTemplateDO parse(byte [] value) throws Etsi102221Exception {
		PINStatusTemplateDO fd = new PINStatusTemplateDO();

		int currentOffset = 0;
		
		// First TLV shall be PS_DO
		if (value[currentOffset++] != TAG_PS_DO)
			throw new Etsi102221Exception("PS_DO TLV not found");
		
		if (value[currentOffset++] != 1) 
			throw new Etsi102221Exception("Only 1-byte long PS_DO are supported");
		byte psdoValue = value[currentOffset++];
		
		fd.pins = new ArrayList<PinStatus>();
		// Populate list to maximum PS_DO value capacity (then non-used pins will be removed)
		for (int i = 0; i < 8; i++) {
			PinStatus ps = new PinStatus();
			ps.state = (psdoValue & (1 << (8-i-1))) != 0 ? State.ENABLED : State.DISABLED;
			fd.pins.add(ps);
			
		}
		
		List<BerTlv> tlvs = BerTlv.parseList(value, false);
		
		int presentPinCount = 0;
		for (BerTlv t : tlvs) {
			byte tag = t.getTag().getBytes()[0];
			
			switch (tag) {
				case TAG_USAGE_QUALIFIER:
					fd.pins.get(presentPinCount).qualifier = t.getValue()[0];
					break;
				case TAG_KEY_REFERENCE:
					fd.pins.get(presentPinCount).keyReference = new KeyReference(t.getValue()[0]);
					presentPinCount++;
					break;
			}
		}
		
		// Remove all unused PINs
		fd.pins = fd.pins.subList(0, presentPinCount);
		
		return fd;
	}
	
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_PIN_STATUS_TEMPLATE_DO;
	}
	
	@Override
	public byte [] getValue() throws IOException {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		
		bo.write(TAG_PS_DO);
		bo.write(1);
		
		//PS_DO value
		byte psdoValue = 0;
		for (int i = 0; i < pins.size(); i++) {
			psdoValue |= ((pins.get(i).state == State.ENABLED ? 1:0) << (8-i-1));
		}
		bo.write(psdoValue);
		for (PinStatus pin : pins)
			bo.write(pin.keyReference.getBytes());
		
		return bo.toByteArray();
	}
	
	@Override
	public String toString() {
		String output = "";
		for (PinStatus pin : pins)
			output += " " + pin.toString();
		
		return output;
	}
	
	public static class Builder {

		protected List<PinStatus> pins;
		
		public Builder() {
			pins = new ArrayList<PinStatus>();
		}

		
		public Builder pins(List<PinStatus> pins) {
			this.pins = pins;
			return this;
		}
		
		public Builder addPin(PinStatus pin) {
			this.pins.add(pin);
			return this;
		}
		
		public PINStatusTemplateDO build() {
			PINStatusTemplateDO output = new PINStatusTemplateDO();
			output.pins = this.pins;
	
			return output;
		}
	}
}

