package com.mobiera.lib.etsi102221;

public class EtsiConstants {
	
	/* INS codes according to table 10.5 from 102.221 v14.00*/
	public static final int INS_SELECT					= 0xA4;
	public static final int INS_STATUS						= 0xF2;
	public static final int INS_READ_BINARY					= 0xB0;
	public static final int INS_UPDATE_BINARY				= 0xD6;
	public static final int INS_READ_RECORD					= 0xB2;
	public static final int INS_UPDATE_RECORD				= 0xDC;
	public static final int INS_SEARCH_RECORD				= 0xA2;
	public static final int INS_INCREASE					= 0x32;
	public static final int INS_RETRIEVE_DATA				= 0xCB;
	public static final int INS_SET_DATA					= 0xDB;
	public static final int INS_VERIFY_PIN					= 0x20;
	public static final int INS_CHANGE_PIN					= 0x24;
	public static final int INS_DISABLE_PIN					= 0x26;
	public static final int INS_ENABLE_PIN					= 0x28;
	public static final int INS_UNBLOCK_PIN					= 0x2C;
	public static final int INS_DEACTIVATE_FILE				= 0x04;
	public static final int INS_ACTIVATE_FILE				= 0x44;
	public static final int INS_AUTHENTICATE				= 0x88;
	public static final int INS_GET_CHALLENGE				= 0x84;
	public static final int INS_TERMINAL_CAPABILITY			= 0xAA;
	public static final int INS_TERMINAL_PROFILE			= 0x10;
	public static final int INS_ENVELOPE					= 0xC2;
	public static final int INS_FETCH						= 0x12;
	public static final int INS_TERMINAL_RESPONSE			= 0x14;
	public static final int INS_MANAGE_CHANNEL				= 0x70;
	public static final int INS_MANAGE_SECURE_CHANNEL		= 0x73;
	public static final int INS_TRANSACT_DATA				= 0x75;
	public static final int INS_SUSPEND_UICC				= 0x76;
	public static final int INS_GET_RESPONSE				= 0xC0;
	
	
}
