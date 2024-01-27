package com.mobiera.lib.etsi102221.model.fcp;


public abstract class SecurityAttributes extends FCPDataObject {
	
	public static enum Format { COMPACT, EXPANDED, REFERENCED };
	
	public abstract Format getFormat();

}

