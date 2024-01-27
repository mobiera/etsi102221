package com.mobiera.lib.etsi102221.model;

import javax.smartcardio.CommandAPDU;

import org.bouncycastle.util.Arrays;



public abstract class UICCCommand {
	protected int cla;
	protected int p1;
	protected int p2;
	protected byte [] data;
	protected Integer le; // 0 and null have a different meaning

	protected abstract int getInstructionCode();
	
	protected int getCLA() {
		return 0x00;
	}
	
	public CommandAPDU getCommandAPDU() {
		
		// Did it in the hard way in order to force the framework to
		// Send the Le byte even if it is set to 00
		
		byte [] apduBytes = new byte[] { (byte) getCLA(), 
				(byte) getInstructionCode(), (byte) p1, (byte) p2};
		
		if (data != null) {
			byte lc = (byte) data.length;
			if (lc > 0) {
				apduBytes = Arrays.append(apduBytes, lc);
				apduBytes = Arrays.concatenate(apduBytes, data);	
			}
		}
		
		if (le != null)
			apduBytes = Arrays.append(apduBytes, le.byteValue());
		
		return new CommandAPDU(apduBytes);
	}
}
