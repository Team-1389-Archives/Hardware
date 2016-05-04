package com.team1389.hardware.outputs;

import com.team1389.hardware.configuration.PIDConstants;
import com.team1389.hardware.control.PIDConfiguration;
import com.team1389.hardware.interfaces.inputs.PositionInput;
import com.team1389.hardware.interfaces.inputs.SpeedInput;
import com.team1389.hardware.interfaces.outputs.PositionOutput;
import com.team1389.hardware.interfaces.outputs.SpeedOutput;
import com.team1389.hardware.interfaces.outputs.VoltageOutput;
import com.team1389.hardware.util.state.State;
import com.team1389.hardware.util.state.StateTracker;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class CANTalonHardware {
	StateTracker stateTracker;
	CANTalon wpiTalon;

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

	private void setPidConstants(CANTalon wpiTalon, PIDConstants pidConstants) {
		wpiTalon.setPID(pidConstants.p, pidConstants.i, pidConstants.d);
	}
}
