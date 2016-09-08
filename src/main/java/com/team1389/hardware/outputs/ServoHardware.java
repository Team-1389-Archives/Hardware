package com.team1389.hardware.outputs;

import java.util.HashMap;
import java.util.Map;

import com.team1389.hardware.interfaces.inputs.PositionInput;
import com.team1389.hardware.interfaces.outputs.PositionOutput;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.Servo;

/**
 * A Servo motor
 * @author Jacob Prinz
 */
public class ServoHardware implements Watchable{
	Servo wpiServo;
	
	/**
	 * @param port PWM port that servo is plugged into
	 */
	public ServoHardware(int pwmPort, Registry registry) {
		registry.claimPWMPort(pwmPort);
		registry.registerWatcher(this);

		wpiServo = new Servo(pwmPort);
	}
	
	/**
	 * @return The PWM port that the servo is on
	 */
	public int getPort(){
		return wpiServo.getChannel();
	}
	
	/**
	 * @return A {@link PositionOutput} that controls the servo
	 */
	public PositionOutput getPositionOutput(){
		return (double position) -> {
			wpiServo.set(position);
		};
	}

	@Override
	public String getName() {
		return "Servo " + getPort();
	}

	@Override
	public Map<String, String> getInfo() {
		Map<String, String> info = new HashMap<>();
		info.put("position", "" + wpiServo.getPosition());
		return info;
	}
	
	public PositionInput getPositionInput(){
		return () -> {
			return wpiServo.getPosition();
		};
	}
}
