package com.mobiera.lib.etsi102221.model.fcp;

import java.io.ByteArrayOutputStream;

import java.util.List;

import com.mobiera.java.sim.util.tlv.BerTlv;
import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.fcp.pi.AppPowerConsumption;
import com.mobiera.lib.etsi102221.model.fcp.pi.UICCCharacteristics;
import com.mobiera.lib.etsi102221.model.fcp.pi.UICCEnvironmentConditions;

public class ProprietaryInformation extends FCPDataObject {

	public static final byte PI_TAG_UICC_CHARACTERISTICS 		= (byte)0x80;
	public static final byte PI_TAG_APP_POWER_CONSUMPTION 		= (byte)0x81;
	public static final byte PI_TAG_MINIMUM_APP_CLOCK_FREQ 		= (byte)0x82;
	public static final byte PI_TAG_AVAILABLE_MEMORY 			= (byte)0x83;
	public static final byte PI_TAG_FILE_DETAILS 				= (byte)0x84;
	public static final byte PI_TAG_RESERVED_FILE_SIZE 			= (byte)0x85;
	public static final byte PI_TAG_MAXIMUM_FILE_SIZE 			= (byte)0x86;
	public static final byte PI_TAG_SUPPORTED_SYSTEM_COMMANDS	= (byte)0x87;
	public static final byte PI_TAG_SPECIFIC_UICC_ENV_COND		= (byte)0x88;
	public static final byte PI_TAG_P2P_CAT_SECURED_APDU		= (byte)0x89;

	private UICCCharacteristics uiccCharacteristics;
	private AppPowerConsumption appPowerConsumption;
	private Float minimumAppClockFreq;
	private Integer availableMemory;
	private Byte fileDetails;
	private Integer reservedFileSize;
	private Integer maximumFileSize;
	private Byte supportedSysCmds;
	private Byte p2pCatSecuredApdu;
	private UICCEnvironmentConditions uiccEnvConditions;
	
	
	public static ProprietaryInformation parse(byte [] value) throws Etsi102221Exception {
		ProprietaryInformation fd = new ProprietaryInformation();
		
		List<BerTlv> tlvs = BerTlv.parseList(value, false);
		
		for (BerTlv t : tlvs) {
			byte tag = t.getTag().getBytes()[0];
			byte [] val = t.getValue();
			switch (tag) {
				case PI_TAG_UICC_CHARACTERISTICS:
					fd.uiccCharacteristics = UICCCharacteristics.parse(val);
					break;
					
				case PI_TAG_APP_POWER_CONSUMPTION:
					fd.appPowerConsumption = AppPowerConsumption.parse(val);
					break;
				case PI_TAG_MINIMUM_APP_CLOCK_FREQ:
					fd.minimumAppClockFreq = new Float(0.1*t.getValue()[0]);
					break;

				case PI_TAG_AVAILABLE_MEMORY:
					if (val.length <= 2 )
						throw new Etsi102221Exception("Wrong length. It shall be >= 2");
					
					int available = 0;
					for (int i = 0; i < val.length; i++) 
						available |= (val[i] & 0xFF) << 8*(val.length-i-1);
					fd.availableMemory = new Integer(available);
					break;
					
				case PI_TAG_FILE_DETAILS:
					fd.fileDetails = new Byte(val[0]);
					break;
					
				case PI_TAG_RESERVED_FILE_SIZE:
					if (val.length <= 2 )
						throw new Etsi102221Exception("Wrong length. It shall be >= 2");
					
					int rfs = 0;
					for (int i = 0; i < val.length; i++) 
						rfs |= (val[i] & 0xFF) << 8*(val.length-i-1);
					fd.reservedFileSize = new Integer(rfs);
					break;
				
				case PI_TAG_MAXIMUM_FILE_SIZE:
					if (val.length <= 2 )
						throw new Etsi102221Exception("Wrong length. It shall be >= 2");
					
					int mfs = 0;
					for (int i = 0; i < val.length; i++) 
						mfs |= (val[i] & 0xFF) << 8*(val.length-i-1);
					fd.maximumFileSize = new Integer(mfs);
					break;

				case PI_TAG_SUPPORTED_SYSTEM_COMMANDS:
					fd.supportedSysCmds = new Byte(val[0]);
					break;
					
				case PI_TAG_SPECIFIC_UICC_ENV_COND:
					fd.uiccEnvConditions = UICCEnvironmentConditions.parse(val);
					break;
					
				case PI_TAG_P2P_CAT_SECURED_APDU:
					fd.p2pCatSecuredApdu = new Byte(val[0]);
					break;
				
			}
		}		
		
		return fd;
	}
	
	@Override
	public byte getTag() {
		return FCPConstants.FCP_TAG_PROPIETARY_INFORMATION;
	}
	
	@Override
	public byte [] getValue() {
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		//TODO
		return bo.toByteArray();
	}
	
	@Override
	public String toString() {
		String output = "";
		if (uiccCharacteristics != null) 
			output += "\n - UICC Characteristics: " + uiccCharacteristics.toString();
		if (appPowerConsumption != null) 
			output += "\n - App Power Consumption: " + appPowerConsumption.toString();
		if (minimumAppClockFreq != null) 
			output += "\n - Minimum App Clock Freq: " + minimumAppClockFreq.toString();
		if (availableMemory != null) 
			output += "\n - Available Memory: " + availableMemory.toString() + " bytes";
		if (fileDetails != null) 
			output += "\n - File Details: " + fileDetails.toString();
		if (reservedFileSize != null) 
			output += "\n - Reserved File Size: " + reservedFileSize.toString() + " bytes";;
		if (maximumFileSize != null) 
			output += "\n - Maximum File Size: " + maximumFileSize.toString() + " bytes";
		if (supportedSysCmds != null) 
			output += "\n - Supported System Commands: " + supportedSysCmds.toString();
		if (uiccEnvConditions != null) 
			output += "\n - Specific UICC Environmental Conditions: " + uiccEnvConditions.toString();
		if (p2pCatSecuredApdu != null) 
			output += "\n - Platform to Platform Secure APDU required: " + (p2pCatSecuredApdu.byteValue() == 1);

		return output;
	}
	
	public static class Builder {

		// TODO: Implement other parameters
		protected UICCCharacteristics uiccCharacteristics;
		
		public Builder() {
			
		}
		
		public Builder uiccCharacteristics(UICCCharacteristics value) {

			return this;
		}
		
		public ProprietaryInformation build() {
			ProprietaryInformation output = new ProprietaryInformation();
			
			output.uiccCharacteristics = this.uiccCharacteristics;

			return output;
		}
	}
}

