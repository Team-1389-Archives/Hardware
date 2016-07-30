package com.team1389.hardware.control;

import com.team1389.hardware.interfaces.outputs.VoltageOutput;

import edu.wpi.first.wpilibj.PIDOutput;

public class PIDVoltageWrapper implements PIDOutput{
	
	VoltageOutput voltageOutput;
	
	public PIDVoltageWrapper(VoltageOutput voltageOutput) {
		this.voltageOutput = voltageOutput;
	}

	@Override
	public void pidWrite(double output) {
		voltageOutput.setVoltage(output);
	}

}
