package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class TotalFileSize extends FCPDataObject {

	
	private int allocatedDataBytes;
	
	public static TotalFileSize parse(byte [] value) throws Etsi102221Exception {
		TotalFileSize fs = new TotalFileSize();
		
		if (value.length < 2 )
			throw new Etsi102221Exception("Wrong length. It shall be >= 2");
		
		
		for (int i = 0; i < value.length; i++) 
			fs.allocatedDataBytes |= (value[i] & 0xFF) << 8*(value.length-i-1);
		
		return fs;
		
	}
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_TOTAL_FILE_SIZE;
	}
	
	@Override
	public byte [] getValue() throws IOException {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bo);
		dos.writeShort(allocatedDataBytes);
		
		return bo.toByteArray();
	}
	
	@Override
	public String toString() {
		return Integer.toString(allocatedDataBytes) + 
				" bytes (0x" + Integer.toHexString(allocatedDataBytes) + ")";
	}
	
	public static class Builder {

		protected int allocatedDataBytes;
		
		public Builder() {
			allocatedDataBytes = 0;
		}

		public Builder allocatedDataBytes(int value) {
			this.allocatedDataBytes = value;
			return this;
		}
		
		public TotalFileSize build() {
			TotalFileSize output = new TotalFileSize();
			output.allocatedDataBytes = this.allocatedDataBytes;

			return output;
		}
	}
}

