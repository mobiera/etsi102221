package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class DFName extends FCPDataObject {

	private byte [] aid;
	
	public byte [] getAID() {
		return aid;
	}
	
	public static DFName parse(byte [] value) throws Etsi102221Exception {
		DFName fs = new DFName();
		
		
		fs.aid = value.clone();
		
		return fs;
		
	}
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_DF_NAME;
	}
	
	@Override
	public byte [] getValue() throws IOException {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		bo.write(aid);
		
		return bo.toByteArray();
	}
	
	
	public static class Builder {

		protected byte [] aid;
		
		public Builder() {
			
		}

		public Builder aid(byte [] value) {
			this.aid = value;
			return this;
		}
		
		public DFName build() {
			DFName output = new DFName();
			output.aid = this.aid;

			return output;
		}
	}
}

