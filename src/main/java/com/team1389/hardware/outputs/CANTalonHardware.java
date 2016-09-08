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
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.util.state.State;
import com.team1389.hardware.util.state.StateTracker;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/**
 * An object that can control a CAN Talon
 * 
 * This class will automatically keep track of what state the talon is in, and configure it
 * Makes it easy to e.g. switch between voltage and PID control
 * @author jacob
 *
 */
public class CANTalonHardware implements Watchable{
	private final StateTracker stateTracker;
	private final CANTalon wpiTalon;
	private String currentMode;

	public CANTalonHardware(int canPort, Registry registry) {
		registry.claimCANPort(canPort);
		registry.registerWatcher(this);
		wpiTalon = new CANTalon(canPort);

		stateTracker = new StateTracker();
		currentMode = "None";
	}

	/**
	 * @return a {@link VoltageOutput} that can control the CAN Talon in voltage mode
	 */
	public VoltageOutput getVoltageOutput() {
		State voltageState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.PercentVbus);
			currentMode = "Voltage Control";
		});
		
		return (double voltage) -> {
			voltageState.init();
			wpiTalon.set(voltage);
		};
	}

	/**
	 * 
	 * @param config The PID configuration that the talon will use
	 * @return A {@link SpeedOutput} that can control the talon
	 */
	public SpeedOutput getSpeedOutput(PIDConfiguration config) {
		State speedState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.Speed);
			setPidConstants(wpiTalon, config.pidConstants);
			wpiTalon.reverseSensor(config.isSensorReversed);
			currentMode = "Speed";
		});

		return (double speed) -> {
			speedState.init();
			wpiTalon.set(speed);
		};
	}

	/**
	 * 
	 * @param config The PID configuration that the talon will use
	 * @return A {@link PositionOutput} that controls the talon
	 */
	public PositionOutput getPositionOutput(PIDConfiguration config) {
		State positionState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.Position);
			setPidConstants(wpiTalon, config.pidConstants);
			wpiTalon.reverseSensor(config.isSensorReversed);
			currentMode = "Position";
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
	
	/**
	 * 
	 * @param toFollow The other talon that this talon should follow
	 * @return A {@link CANTalonFollower} that when called will make this talon follow toFollow
	 */
	public CANTalonFollower getFollower(CANTalonHardware toFollow){
		State followingState = stateTracker.newState(() -> {
			wpiTalon.changeControlMode(TalonControlMode.Follower);
			wpiTalon.set(toFollow.wpiTalon.getDeviceID());
			currentMode = "Follower";
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
		info.put("mode", currentMode);
		
		return info;
	}
}
