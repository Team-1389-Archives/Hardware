package com.team1389.hardware.outputs;

import com.team1389.hardware.interfaces.outputs.VoltageOutput;

import edu.wpi.first.wpilibj.Victor;

/**
 * A victor motor controller
 * @author Jacob Prinz
 */
public class VictorHardware implements VoltageOutput{
	
	Victor wpiVictor;
	
	public VictorHardware(int port) {
		wpiVictor = new Victor(port);
	}

	@Override
	public void setVoltage(double voltage) {
		wpiVictor.set(voltage);
	}
}
