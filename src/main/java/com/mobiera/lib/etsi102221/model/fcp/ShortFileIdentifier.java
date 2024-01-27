package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class ShortFileIdentifier extends FCPDataObject {

	boolean supported;
	
	private int sfi;
	
	public int getSFI() {
		return sfi;
	}
	
	public boolean isSupported() {
		return supported;
	}
	
	public static ShortFileIdentifier parse(byte [] value) throws Etsi102221Exception {
		ShortFileIdentifier fs = new ShortFileIdentifier();
		
		if (value.length == 0)
			fs.supported = false;
		else if (value.length == 1) {
			fs.supported = true;
			fs.sfi = value[0];
		} else throw new Etsi102221Exception("Wrong Short File Identifier length");

		return fs;
		
	}
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_SHORT_FILE_IDENTIFIER;
	}
	
	@Override
	public byte [] getValue() throws IOException {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bo);
		if (supported) dos.writeByte(sfi);
		
		return bo.toByteArray();
	}
	
	@Override
	public String toString() {
		return Integer.toHexString(sfi) + " (supported: " + supported + ")";
	}
	
	public static class Builder {

		protected int sfi;
		protected boolean supported;
		
		public Builder() {
			sfi = 0;
		}
		
		public Builder supported(boolean value) {
			this.supported = value;
			return this;
		}

		public Builder sfi(int value) {
			this.sfi = value;
			return this;
		}
		
		public ShortFileIdentifier build() {
			ShortFileIdentifier output = new ShortFileIdentifier();
			output.sfi = this.sfi;
			output.supported = this.supported;
			
			return output;
		}
	}
}

