package com.mobiera.lib.etsi102221.model;

public abstract class UICCResponse {
	
	public static final int SW_OK = 0x9000;
	public static final int SW_OK_AFTER_INTERNAL_UPDATE_RETRY = 0x6300;
	public static final int SW_ERR_IN_CONTRADICTION_WITH_ACTIVATION_STATUS = 0x6283;
	public static final int SW_ERR_MEMORY_PROBLEM = 0x6581;
	public static final int SW_ERR_INCORRECT_LENGTH_FIELD = 0x6700;
	public static final int SW_ERR_COMMAND_NOT_ALLOWED = 0x6900;
	public static final int SW_ERR_SECURITY_STATUS_NOT_SATISFIED = 0x6982;
	public static final int SW_ERR_INCORRECT_PARAMETERS_IN_DATA_FIELD = 0x6A80;
	public static final int SW_ERR_FILE_NOT_FOUND = 0x6A82;
	public static final int SW_ERR_NOT_ENOUGH_MEMORY_SPACE = 0x6A84;
	public static final int SW_ERR_FILE_ID_ALREADY_EXISTS = 0x6A89;
	public static final int SW_ERR_DF_NAME_ALREADY_EXISTS = 0x6A8A;
	public static final int SW_ERR_INCORRECT_PARAMETER_P1_OR_2 = 0x6B00;
	public static final int SW_ERR_INSTRUCTION_CODE_NOT_SUPPORTED = 0x6D00;
	public static final int SW_ERR_CLASS_NOT_SUPPORTED = 0x6E00;
	public static final int SW_ERR_TECHNICAL_PROBLEM_NO_DIAGNOSTIC_GIVEN = 0x6F00;
	
	protected int sw1;
	
	protected int sw2;
	
	protected byte [] data;
	
	public int getSw1() {
		return sw1;
	}


	public int getSw2() {
		return sw2;
	}


	public byte[] getData() {
		return data;
	}
	
	public int getSw() {
		return (sw1 & 0xFF) << 8 | (sw2 & 0xFF);
	}
	
	public static String swToDecoratedString(int sw) {
		
		
		switch (sw) {
			case SW_OK:
				return "SW_OK";
			case SW_OK_AFTER_INTERNAL_UPDATE_RETRY:
				return "SW_OK_AFTER_INTERNAL_UPDATE_RETRY";
			case SW_ERR_IN_CONTRADICTION_WITH_ACTIVATION_STATUS:
				return "SW_ERR_IN_CONTRADICTION_WITH_ACTIVATION_STATUS";
			case SW_ERR_MEMORY_PROBLEM:
				return "SW_ERR_MEMORY_PROBLEM";
			case SW_ERR_INCORRECT_LENGTH_FIELD:
				return "SW_ERR_INCORRECT_LENGTH_FIELD";
			case SW_ERR_COMMAND_NOT_ALLOWED:
				return "SW_ERR_COMMAND_NOT_ALLOWED";
			case SW_ERR_SECURITY_STATUS_NOT_SATISFIED:
				return "SW_ERR_SECURITY_STATUS_NOT_SATISFIED";
			case SW_ERR_INCORRECT_PARAMETERS_IN_DATA_FIELD:
				return "SW_ERR_INCORRECT_PARAMETERS_IN_DATA_FIELD";
			case SW_ERR_FILE_NOT_FOUND:
				return "SW_ERR_FILE_NOT_FOUND";
			case SW_ERR_NOT_ENOUGH_MEMORY_SPACE:
				return "SW_ERR_NOT_ENOUGH_MEMORY_SPACE";
			case SW_ERR_FILE_ID_ALREADY_EXISTS:
				return "SW_ERR_FILE_ID_ALREADY_EXISTS";
			case SW_ERR_DF_NAME_ALREADY_EXISTS:
				return "SW_ERR_DF_NAME_ALREADY_EXISTS";
			case SW_ERR_INCORRECT_PARAMETER_P1_OR_2:
				return "SW_ERR_INCORRECT_PARAMETER_P1_OR_2";
			case SW_ERR_INSTRUCTION_CODE_NOT_SUPPORTED:
				return "SW_ERR_INSTRUCTION_CODE_NOT_SUPPORTED";
			case SW_ERR_CLASS_NOT_SUPPORTED:
				return "SW_ERR_CLASS_NOT_SUPPORTED";
			case SW_ERR_TECHNICAL_PROBLEM_NO_DIAGNOSTIC_GIVEN:
				return "SW_ERR_TECHNICAL_PROBLEM_NO_DIAGNOSTIC_GIVEN";
			default:
				return String.format("%04X", sw);
		}


	}


}
