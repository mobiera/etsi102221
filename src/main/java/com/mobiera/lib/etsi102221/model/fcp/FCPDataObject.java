package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.mobiera.java.sim.util.tlv.BerTlv;


public abstract class FCPDataObject {

	protected abstract byte getTag();
	
	protected abstract byte [] getValue() throws IOException;
	
	public byte [] getBytes() throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		
		bo.write(getTag());
		byte [] value = getValue();
		bo.write(BerTlv.getLength(value));
		bo.write(value);
		
		return bo.toByteArray();
		
	}
}
