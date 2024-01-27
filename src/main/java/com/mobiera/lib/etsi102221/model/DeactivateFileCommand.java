package com.mobiera.lib.etsi102221.model;

import com.mobiera.java.sim.util.tlv.ISOUtil;
import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class DeactivateFileCommand extends UICCCommand {

	public static enum SelectMode {BY_FID, BY_PATH_FROM_MF,
		BY_PATH_FROM_CURRENT_DF};
		
	/* Supported P1 settings */
	private static final int P1_BY_FID = 0x00;
	private static final int P1_BY_PATH_FROM_MF = 0x08;
	private static final int P1_BY_PATH_FROM_CURRENT_DF = 0x09;
	
	protected Integer fid;
	protected String path;
	protected SelectMode selectMode;
		
	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_DEACTIVATE_FILE;
	}
	
	public static class Builder {

		protected Integer fid;
		protected String path;
		protected SelectMode selectMode;
		
		public Builder() {
			selectMode = SelectMode.BY_FID;
		}
		

		public Builder fid(int value) {
			this.selectMode = SelectMode.BY_FID;
			this.fid = value;
			return this;
		}

		public Builder selectMode(SelectMode value) {
			this.selectMode = value;
			return this;
		}

		public Builder path(String value) {
			this.selectMode = SelectMode.BY_PATH_FROM_MF;
			this.path = value;
			return this;
		}
		
		public DeactivateFileCommand build() throws Etsi102221Exception {
			DeactivateFileCommand output = new DeactivateFileCommand();
			output.fid = this.fid;
			output.path = this.path;
			
			if (output.path != null && output.fid != null) 
				throw new Etsi102221Exception(
						"FID and Path cannot be present at the same time");
			
			if (output.path != null) {
				// Select by path case
				output.p1 = selectMode == SelectMode.BY_PATH_FROM_MF ? 
						P1_BY_PATH_FROM_MF : P1_BY_PATH_FROM_CURRENT_DF;
				output.data = ISOUtil.hex2byte(output.path);
			}
			else if (output.fid != null) {
				// Select by FID case
				output.p1 = P1_BY_FID;
				output.data  = new byte [] {
						(byte) (output.fid >> 8), (byte) (output.fid & 0xFF)};
			} else { // Current file
				output.p1 = P1_BY_FID;
				output.data  = new byte [0];
			}
			
			
			return output;
		}
	}


}
