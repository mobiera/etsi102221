package com.mobiera.lib.etsi102221.model.fcp.pi;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class UICCEnvironmentConditions {
	public static enum TemperatureClass {
		STANDARD_TEMPERATURE_RANGE, TEMPERATURE_CLASS_A,
		TEMPERATURE_CLASS_B, TEMPERATURE_CLASS_C, RFU
	}
	
	public static enum HighHumidity {
		SUPPORTED, NOT_SUPPORTED;
	}
	
	
	public HighHumidity getHighHumiditySupported() {
		return highHumiditySupported;
	}

	public TemperatureClass getTemperatureClass() {
		return temperatureClass;
	}

	private TemperatureClass temperatureClass; 
	
	/** Reference Frequency, in MHz **/
	private HighHumidity highHumiditySupported; 
	
	public UICCEnvironmentConditions() {
		
	}
	
	public static UICCEnvironmentConditions parse(byte [] data) throws Etsi102221Exception {
		
		UICCEnvironmentConditions apc = new UICCEnvironmentConditions(); 
		if (data.length != 1)
			throw new Etsi102221Exception("Wrong UICC Environmental Conditions Length");

		switch (data[0] & 0x07) {
			case 0: 
				apc.temperatureClass = TemperatureClass.STANDARD_TEMPERATURE_RANGE; 
				break; 
			case 1: 
				apc.temperatureClass = TemperatureClass.TEMPERATURE_CLASS_A; 
				break; 
			case 2: 
				apc.temperatureClass = TemperatureClass.TEMPERATURE_CLASS_B; 
				break; 
			case 3: 
				apc.temperatureClass = TemperatureClass.TEMPERATURE_CLASS_C; 
				break;
			default:
				apc.temperatureClass = TemperatureClass.RFU; 
		}
		
		apc.highHumiditySupported = (data[0] & 0x80) != 0 ? 
				HighHumidity.SUPPORTED : HighHumidity.NOT_SUPPORTED;
		
		return apc;
	}
	
	public byte [] getBytes() {
		
		byte output = 0;
		
		switch (temperatureClass) {
		case STANDARD_TEMPERATURE_RANGE: output = 0; break; 
		case TEMPERATURE_CLASS_A: output = 1; break;
		case TEMPERATURE_CLASS_B: output = 2; break;
		case TEMPERATURE_CLASS_C: output = 3; break;
		default: output = 4;
	}
		
		output |= highHumiditySupported == HighHumidity.SUPPORTED ? 0x80 : 0x00;
		
		return new byte [] { (byte) output };
	}
	
	@Override
	public String toString() {
		return "Temperature Class: " + temperatureClass.name() + 
				" High Humidity: " + highHumiditySupported.name();
	}
}
