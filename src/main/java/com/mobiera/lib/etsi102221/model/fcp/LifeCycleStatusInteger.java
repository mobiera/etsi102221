package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.fcp.FileDescriptor.FileStructure;
import com.mobiera.lib.etsi102221.model.fcp.FileDescriptor.FileType;

public class LifeCycleStatusInteger extends FCPDataObject {

	
	public static enum State { NO_INFORMATION_GIVEN, CREATION, INITIALIZATION, 
		OPERATIONAL_ACTIVATED, OPERATIONAL_DEACTIVATED, TERMINATION, 
		PROPRIETARY, RFU };
	
	private State state;
	
	public State getState() {
		return state;
	}
	
	public static LifeCycleStatusInteger parse(byte [] value) throws Etsi102221Exception {
		LifeCycleStatusInteger fs = new LifeCycleStatusInteger();
		
		if (value.length != 1 )
			throw new Etsi102221Exception("Wrong length: it shall be 1 byte");

		fs.state = decodeState(value[0]);
		
		return fs;
		
	}
	
	private static State decodeState(byte value) {
		State output;
		
		switch (value) {
			case 0x00: output = State.NO_INFORMATION_GIVEN; break;
			case 0x01: output = State.CREATION; break;
			case 0x03: output = State.INITIALIZATION; break;
			case 0x04: case 0x06: output = State.OPERATIONAL_DEACTIVATED; break;
			case 0x05: case 0x07: output = State.OPERATIONAL_ACTIVATED; break;
			case 0x0C: case 0x0D: case 0x0E: case 0x0F: output = State.TERMINATION; break;
			default: output = State.RFU;
		}
		
		return output;
	}
	
	private static byte encodeFileDescriptorByte(State state) {

		byte output = 0;
		
		switch(state) {
			case NO_INFORMATION_GIVEN: output = 0x00; break;
			case CREATION: output = 0x01; break;
			case INITIALIZATION: output = 0x03; break;
			case OPERATIONAL_DEACTIVATED: output = 0x04; break;
			case OPERATIONAL_ACTIVATED: output = 0x05; break;
			case TERMINATION: output = 0x0C; break;
			default: output = 0x10; // RFU
			
		}
		return output;
	}
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_LIFE_CYCLE_STATUS_INTEGER;
	}
	
	@Override
	public byte [] getValue() throws IOException {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		bo.write(encodeFileDescriptorByte(state));
		
		return bo.toByteArray();
	}
	
	@Override
	public String toString() {
		return state.toString();
	}
	
	public static class Builder {

		protected State state;
		
		public Builder() {
			state = State.NO_INFORMATION_GIVEN;
		}

		public Builder state(State value) {
			this.state = value;
			return this;
		}
		
		public LifeCycleStatusInteger build() {
			LifeCycleStatusInteger output = new LifeCycleStatusInteger();
			output.state = this.state;

			return output;
		}
	}
}

