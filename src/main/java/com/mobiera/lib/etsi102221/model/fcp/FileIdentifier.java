package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class FileIdentifier extends FCPDataObject {

	private int fid;
	
	public static FileIdentifier parse(byte [] value) throws Etsi102221Exception {
		FileIdentifier fs = new FileIdentifier();
		
		if (value.length != 2 )
			throw new Etsi102221Exception("Only 2-byte long FileIdentifier TLV are supported");
		
		fs.fid = (value[1] & 0xFF | (value[0] << 8)) & 0xFFFF;
		return fs;
		
	}
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_FILE_IDENTIFIER;
	}
	
	@Override
	public byte [] getValue() throws IOException {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bo);
		dos.writeShort(fid);
		
		return bo.toByteArray();
	}
	
	
	public static class Builder {

		protected int fid;
		
		public Builder() {
			fid = 0;
		}

		public Builder fid(int value) {
			this.fid = value;
			return this;
		}
		
		public FileIdentifier build() {
			FileIdentifier output = new FileIdentifier();
			output.fid = this.fid;

			return output;
		}
	}
	
	@Override
	public String toString() {
		return Integer.toHexString(fid);
	}
}

