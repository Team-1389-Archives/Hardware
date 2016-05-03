package com.team1389.hardware.outputs;

import com.team1389.hardware.configuration.PIDConstants;
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

	public SpeedOutput getSpeedOutput(PIDConstants pidConstants, boolean sensorIsReversed) {
		State speedState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.Speed);
			setPidConstants(wpiTalon, pidConstants);
			wpiTalon.reverseSensor(sensorIsReversed);
		});

		return (double speed) -> {
			speedState.init();
			wpiTalon.set(speed);
		};
	}

	public PositionOutput getPositionOutput(PIDConstants pidConstants, boolean sensorIsReversed) {
		State positionState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.Position);
			setPidConstants(wpiTalon, pidConstants);
			wpiTalon.reverseSensor(sensorIsReversed);
		});

		return (double position) -> {
			positionState.init();
			wpiTalon.set(position);
		};
	}

	private void setPidConstants(CANTalon wpiTalon, PIDConstants pidConstants) {
		wpiTalon.setPID(pidConstants.p, pidConstants.i, pidConstants.d);
	}
}
