package com.team1389.hardware.outputs;

import java.util.HashMap;
import java.util.Map;

import com.team1389.hardware.configuration.PIDConstants;
import com.team1389.hardware.control.PIDConfiguration;
import com.team1389.hardware.interfaces.inputs.PositionInput;
import com.team1389.hardware.interfaces.inputs.SpeedInput;
import com.team1389.hardware.interfaces.outputs.CANTalonFollower;
import com.team1389.hardware.interfaces.outputs.PositionOutput;
import com.team1389.hardware.interfaces.outputs.SpeedOutput;
import com.team1389.hardware.interfaces.outputs.VoltageOutput;
import com.team1389.hardware.util.state.State;
import com.team1389.hardware.util.state.StateTracker;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class CANTalonHardware implements Watchable{
	final StateTracker stateTracker;
	final CANTalon wpiTalon;

	public CANTalonHardware(int deviceNumber) {
		stateTracker = new StateTracker();
		wpiTalon = new CANTalon(deviceNumber);
	}

	public VoltageOutput getVoltageOutput() {
		State voltageState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.PercentVbus);
		});
		
		return (double voltage) -> {
			voltageState.init();
			wpiTalon.set(voltage);
		};
	}

	public SpeedOutput getSpeedOutput(PIDConfiguration config) {
		State speedState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.Speed);
			setPidConstants(wpiTalon, config.pidConstants);
			wpiTalon.reverseSensor(config.isSensorReversed);
		});

		return (double speed) -> {
			speedState.init();
			wpiTalon.set(speed);
		};
	}

	public PositionOutput getPositionOutput(PIDConfiguration config) {
		State positionState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.Position);
			setPidConstants(wpiTalon, config.pidConstants);
			wpiTalon.reverseSensor(config.isSensorReversed);
		});

		return (double position) -> {
			positionState.init();
			wpiTalon.set(position);
		};
	}
	
	public SpeedInput getSpeedInput(){
		return () -> {
			return wpiTalon.getSpeed();
		};
	}
	
	public PositionInput getPositionInput(){
		return () -> {
			return wpiTalon.getPosition();
		};
	}
	
	public CANTalonFollower getFollower(CANTalonHardware toFollow){
		State followingState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.Follower);
			wpiTalon.set(toFollow.wpiTalon.getDeviceID());
		});
		
		return new CANTalonFollower() {
			@Override
			public void follow() {
				followingState.init();
			}
		};
	}

	private void setPidConstants(CANTalon wpiTalon, PIDConstants pidConstants) {
		wpiTalon.setPID(pidConstants.p, pidConstants.i, pidConstants.d);
	}

	@Override
	public String getName() {
		return "CAN Talon " + wpiTalon.getDeviceID();
	}

	@Override
	public Map<String, String> getInfo() {
		Map<String, String> info = new HashMap<>();
		
		info.put("speed", "" + wpiTalon.getSpeed());
		info.put("position", "" + wpiTalon.getPosition());
		info.put("voltage out", "" + wpiTalon.getOutputVoltage());
		
		return info;
	}
}
