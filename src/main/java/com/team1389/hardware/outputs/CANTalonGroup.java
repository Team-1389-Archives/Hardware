package com.team1389.hardware.outputs;

import java.util.ArrayList;
import java.util.List;

import com.ni.vision.NIVision.FocalLength;
import com.team1389.hardware.control.PIDConfiguration;
import com.team1389.hardware.interfaces.outputs.CANTalonFollower;
import com.team1389.hardware.interfaces.outputs.PositionOutput;
import com.team1389.hardware.interfaces.outputs.SpeedOutput;
import com.team1389.hardware.interfaces.outputs.VoltageOutput;

public class CANTalonGroup {
	
	private final CANTalonHardware main;
	private final List<CANTalonFollower> followers;

	public CANTalonGroup(CANTalonHardware main, CANTalonHardware ... followers) {
		this.main = main;
		
		this.followers = new ArrayList<CANTalonFollower>();
		for (CANTalonHardware talon : followers){
			this.followers.add(talon.getFollower(main));
		}
	}
	
	private void setFollowers(){
		for (CANTalonFollower follower : followers){
			follower.follow();
		}
	}

	public VoltageOutput getVoltageOutput(){
		VoltageOutput mainOutput = main.getVoltageOutput();
		return (double voltage) -> {
			setFollowers();
			mainOutput.setVoltage(voltage);
		};
	}
	
	public PositionOutput getPositionOutput(PIDConfiguration config){
		PositionOutput mainOutput = main.getPositionOutput(config);
		return (double position) -> {
			setFollowers();
			mainOutput.setPosition(position);
		};
	}

	public SpeedOutput getSpeedOutput(PIDConfiguration config){
		SpeedOutput mainOutput = main.getSpeedOutput(config);
		return (double speed) -> {
			setFollowers();
			mainOutput.setSpeed(speed);
		};
	}
}
