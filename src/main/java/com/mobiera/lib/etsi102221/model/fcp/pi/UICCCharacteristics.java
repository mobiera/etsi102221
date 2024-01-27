package com.mobiera.lib.etsi102221.model.fcp.pi;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class UICCCharacteristics {
	
	public static enum ClockStopSetting {
		CLOCK_STOP_ALLOWED_NO_PREFERRED_LEVEL,
		CLOCK_STOP_ALLOWED_HIGH_LEVEL_PREFERRED,
		CLOCK_STOP_ALLOWED_LOW_LEVEL_PREFERRED,
		CLOCK_STOP_NOT_ALLOWED_NEVER,
		CLOCK_STOP_NOT_ALLOWED_UNLESS_AT_HIGH_LEVEL,
		CLOCK_STOP_NOT_ALLOWED_UNLESS_AT_LOW_LEVEL,
		RFU
	}

	
	public static enum SupplyVoltageState {
		SUPPORTED, NOT_SUPPORTED
	}


	private ClockStopSetting clockStopSetting;
	
	private SupplyVoltageState classA, classB, classC;
	
	
	public static UICCCharacteristics parse(byte [] data) throws Etsi102221Exception {
		
		UICCCharacteristics apc = new UICCCharacteristics(); 
		if (data.length != 1)
			throw new Etsi102221Exception("Wrong UICC Characteristics Length");

		switch (data[0] & 0x0D) {
			case 1: 
				apc.clockStopSetting = 
					ClockStopSetting.CLOCK_STOP_ALLOWED_NO_PREFERRED_LEVEL; 
				break; 
			case 5: 
				apc.clockStopSetting = 
					ClockStopSetting.CLOCK_STOP_ALLOWED_HIGH_LEVEL_PREFERRED; 
				break; 
			case 9: 
				apc.clockStopSetting = 
					ClockStopSetting.CLOCK_STOP_ALLOWED_LOW_LEVEL_PREFERRED; 
				break; 
			case 0: 
				apc.clockStopSetting = 
					ClockStopSetting.CLOCK_STOP_NOT_ALLOWED_NEVER; 
				break; 
			case 4: 
				apc.clockStopSetting = 
					ClockStopSetting.CLOCK_STOP_NOT_ALLOWED_UNLESS_AT_HIGH_LEVEL; 
				break; 
			case 8: 
				apc.clockStopSetting = 
					ClockStopSetting.CLOCK_STOP_NOT_ALLOWED_UNLESS_AT_LOW_LEVEL; 
				break; 
			default: 
				apc.clockStopSetting = 
					ClockStopSetting.RFU; 
				break; 
		}
		
		apc.classA = (data[0] & 0x10) != 0 ? 
				SupplyVoltageState.SUPPORTED : SupplyVoltageState.NOT_SUPPORTED;

		apc.classB = (data[0] & 0x20) != 0 ? 
				SupplyVoltageState.SUPPORTED : SupplyVoltageState.NOT_SUPPORTED;

		apc.classC = (data[0] & 0x40) != 0 ? 
				SupplyVoltageState.SUPPORTED : SupplyVoltageState.NOT_SUPPORTED;

		return apc;
	}
	
	public byte [] getBytes() {
		
		byte output = 0;
		
		switch (clockStopSetting) {
			case CLOCK_STOP_ALLOWED_NO_PREFERRED_LEVEL: output = 1; break; 
			case CLOCK_STOP_ALLOWED_HIGH_LEVEL_PREFERRED: output = 5; break;
			case CLOCK_STOP_ALLOWED_LOW_LEVEL_PREFERRED: output = 9; break;
			case CLOCK_STOP_NOT_ALLOWED_NEVER: output = 0; break;
			case CLOCK_STOP_NOT_ALLOWED_UNLESS_AT_HIGH_LEVEL: output = 4; break;
			case CLOCK_STOP_NOT_ALLOWED_UNLESS_AT_LOW_LEVEL: output = 8; break;
			default: output = 13; 
		}
		
		if (classA == SupplyVoltageState.SUPPORTED) output |= 0x10;
		if (classB == SupplyVoltageState.SUPPORTED) output |= 0x20;
		if (classC == SupplyVoltageState.SUPPORTED) output |= 0x40;
		
		return new byte [] { (byte) output };
	}
	
	@Override
	public String toString() {
		return "[ " + clockStopSetting.name() + " ] " + 
				"[ Class A Voltage " + classA + " ] " +
				"[ Class B Voltage " + classB + " ] " +
				"[ Class C Voltage " + classC + " ] ";
	}
}
