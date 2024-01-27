package com.mobiera.lib.etsi102221.model;


import com.mobiera.java.sim.util.tlv.ISOUtil;
import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.EtsiConstants;

public class SelectCommand extends UICCCommand {

	
	/* Supported P1 settings */
	private static final int P1_BY_FID = 0x00;
	private static final int P1_BY_AID = 0x04;
	private static final int P1_BY_PATH_FROM_MF = 0x08;
	
	/* Supported P2 settings */
	private static final int P2_RETURN_FCP = 0x04;
	private static final int P2_NO_DATA_RETURNED = 0x0C;

	
	protected Integer fid;
	protected String path;
	protected byte [] aid;
	protected boolean returnFileInformation;
	
	@Override
	protected int getInstructionCode() {
		return EtsiConstants.INS_SELECT;
	}
	
	public static class Builder<T extends Builder<T>> {

		protected Integer fid;
		protected String path;
		protected byte [] aid;
		protected boolean returnFileInformation;
		
		public Builder() {
			returnFileInformation = false;
		}
		
		public Builder<T> returnFileInformation(boolean value) {
			this.returnFileInformation = value;
			return this;
		}

		public Builder<T> fid(int value) {
			this.fid = value;
			return this;
		}

		public Builder<T> path(String value) {
			this.path = value;
			return this;
		}
		
		public Builder<T> aid(byte [] value) {
			this.aid = value;
			return this;
		}
		
		public SelectCommand build() throws Etsi102221Exception {
			SelectCommand output = new SelectCommand();
			output.fid = this.fid;
			output.path = this.path;
			output.aid = this.aid;
			output.returnFileInformation = this.returnFileInformation;
			
			if ((fid == null) && (path == null) && (aid == null))
				throw new Etsi102221Exception("Either FID, Path or AID must be present");
			
			if (fid != null) {
				// Select by FID case
				if ((aid != null) || (path != null)) 
					throw new Etsi102221Exception("FID and Path or AID cannot be present at the same time");
				output.p1 = P1_BY_FID;
				output.data  = new byte [] {(byte) (fid >> 8), (byte) (fid & 0xFF)};
			} else if (output.aid != null){
				// Select by AID case
				if ((aid != null) || (fid != null)) 
					throw new Etsi102221Exception("Path and FID or AID cannot be present at the same time");
				output.p1 = P1_BY_AID;
				output.data  = aid;
			} else {
				// Select by path case
				output.p1 = P1_BY_PATH_FROM_MF;
				output.data = ISOUtil.hex2byte(output.path);
			}
			
			output.p2 = returnFileInformation ? P2_RETURN_FCP : P2_NO_DATA_RETURNED;
						
			return output;
		}
	}


}
