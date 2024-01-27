package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class ReferencedSecurityAttributes extends SecurityAttributes {

	private static class SeidRecordPair {
		byte seid;
		byte recordNumber;
		
		public SeidRecordPair(byte seid, byte arr) {
			this.seid = seid; 
			this.recordNumber = arr;
		}
	}
	
	private Integer fid;
	private byte recordNumber;
	
	public Integer getFID() {
		return fid;
	}
	
	public byte getRecordNumber() {
		return recordNumber;
	}
	
	private List<SeidRecordPair> pairs;
	
	public static ReferencedSecurityAttributes parse(byte [] value) throws Etsi102221Exception {
		ReferencedSecurityAttributes fd = new ReferencedSecurityAttributes();

		if (value.length == 1) {
			// Simplest case (only ARR byte)
			fd.recordNumber = value[0];
		} else if (value.length == 3) {
			// FID + ARR byte
			fd.fid = new Integer((value[0] << 8) | (value[1] & 0xFF));
			fd.recordNumber = value[2];
		} else {
			// FID + SEID + ARR
			fd.fid = new Integer((value[0] << 8) | (value[1] & 0xFF));
			
			fd.pairs = new ArrayList<SeidRecordPair>();
			
			for (int i = 2; i < value.length; i+= 2)
				fd.pairs.add(new SeidRecordPair(value[i], value[i+1]));
				
		}

		return fd;
	}
	
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_SECURITY_ATTRIBUTES_REFERENCED;
	}
	
	@Override
	public byte [] getValue() throws IOException {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bo);
		
		// ARR byte only
		if (fid == null) {
			dos.writeByte(recordNumber);
		} else {

			dos.writeShort(fid);
			
			// FID + ARR byte			
			if (pairs == null) {
				dos.writeByte(recordNumber);
			} else {
				for (SeidRecordPair pair : pairs) {
					dos.writeByte(pair.seid);
					dos.writeByte(pair.recordNumber);
				}
			}
		}
		
		return bo.toByteArray();
	}
	
	@Override
	public String toString() {
		String output = "";
		
		if (fid != null)
			output += "File Identifier: " + Integer.toHexString(fid);
		
		if (pairs == null) {
			output += " ARR byte: " + recordNumber;
		} else {
			for (SeidRecordPair pair : pairs)
				output += " SEID: " + pair.seid + "- ARR byte: " + pair.recordNumber;
		}
		return output;
	}
	
	@Override
	public Format getFormat() {
		return Format.REFERENCED;
	}
	
	public static class Builder {

		/* SC for both EF and DF */
		protected Integer fid;
		protected byte recordNumber;
		
		protected List<SeidRecordPair> pairs;
		
		public Builder() {
		}

		public Builder fid(int value) {
			this.fid = new Integer(value);
			return this;
		}

		public Builder recordNumber(byte value) {
			this.recordNumber = value;
			return this;
		}

		public Builder pairs(List<SeidRecordPair> value) {
			this.pairs = new ArrayList<SeidRecordPair>();
			this.pairs.addAll(value);
			return this;
		}
		
		public ReferencedSecurityAttributes build() {
			ReferencedSecurityAttributes output = new ReferencedSecurityAttributes();
			output.fid = this.fid;
			output.recordNumber = this.recordNumber;
			output.pairs= this.pairs;

			return output;
		}
	}
}

