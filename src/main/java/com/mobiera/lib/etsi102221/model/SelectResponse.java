package com.mobiera.lib.etsi102221.model;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class SelectResponse extends UICCResponse {

	protected FCPTemplate fcp;
	
	public FCPTemplate getFCP() {
		return fcp;
	}
	
	public static SelectResponse parse(byte [] data) throws Etsi102221Exception {
		
		SelectResponse sr = new SelectResponse();
		sr.fcp = FCPTemplate.parse(data);
		return sr;
	}
	
	
	@Override
	public String toString() {
		return "FCP: " + fcp.toString();
	}

}
