package com.mobiera.lib.etsi102221.model.fcp.pi;

import com.mobiera.lib.etsi102221.Etsi102221Exception;

public class AppPowerConsumption {
	private int voltageClass;
	
	public int getVoltageClass() {
		return voltageClass;
	}

	public int getAppPowerConsumption() {
		return appPowerConsumption;
	}

	public float getPowerReferenceFrequency() {
		return powerReferenceFrequency;
	}

	/** Power Consumption, in mA **/
	private int appPowerConsumption; 
	
	/** Reference Frequency, in MHz **/
	private float powerReferenceFrequency; 
	
	public AppPowerConsumption() {
		
	}
	public AppPowerConsumption(int voltageClass, int power, float frequency) {
		this.voltageClass = voltageClass;
		this.appPowerConsumption = power;
		this.powerReferenceFrequency = frequency;
	}
	
	public static AppPowerConsumption parse(byte [] data) throws Etsi102221Exception {
		
		AppPowerConsumption apc = new AppPowerConsumption(); 
		if (data.length != 3)
			throw new Etsi102221Exception("Wrong App Power Consumption Length");
		
		apc.voltageClass = data[0];
		apc.appPowerConsumption = data[1];
		apc.powerReferenceFrequency = (float) (0.1*data[2]);
		return apc;
		
	}
	
	public byte [] getBytes() {
		return new byte [] {
				(byte) voltageClass, 
				(byte) appPowerConsumption,
				(byte) (powerReferenceFrequency/0.1) };
	}
}
