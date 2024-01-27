package com.mobiera.lib.etsi102221.model.fcp;

public class FCPConstants {
	
	public static final byte FCP_TAG_FCP_TEMPLATE 					= (byte)0x62;

	public static final byte FCP_TAG_FILE_SIZE 						= (byte)0x80;
	public static final byte FCP_TAG_TOTAL_FILE_SIZE 				= (byte)0x81;
	public static final byte FCP_TAG_FILE_DESCRIPTOR 				= (byte)0x82;
	public static final byte FCP_TAG_FILE_IDENTIFIER 				= (byte)0x83;
	public static final byte FCP_TAG_PROPIETARY_INFORMATION 		= (byte)0xA5;
	public static final byte FCP_TAG_LIFE_CYCLE_STATUS_INTEGER 		= (byte)0x8A;
	public static final byte FCP_TAG_SECURITY_ATTRIBUTES_COMPACT 	= (byte)0x8C; 
	public static final byte FCP_TAG_SECURITY_ATTRIBUTES_EXPANDED 	= (byte)0xAB; 
	public static final byte FCP_TAG_SECURITY_ATTRIBUTES_REFERENCED = (byte)0x8B;
	public static final byte FCP_TAG_SHORT_FILE_IDENTIFIER 			= (byte)0x88;
	public static final byte FCP_TAG_PIN_STATUS_TEMPLATE_DO 		= (byte)0xC6;
	public static final byte FCP_TAG_DF_NAME 						= (byte)0x84;

/*	
	62 2F 
		82 02 7821
		83 02 3F00
		A5 09 8001618304000167F1
		8A 01 05 
		8B 03 2F0602
		C6 0C 
			90 01 60 
			83 01 01 
			83 01 81 
			83 01 0A 
		81 04 00037C76 
		80 000000000000000000000000
	*/
}
