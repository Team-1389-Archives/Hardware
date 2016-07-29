package com.team1389.hardware.outputs;

import java.util.HashMap;
import java.util.Map;

import com.team1389.hardware.interfaces.inputs.PositionInput;
import com.team1389.hardware.interfaces.outputs.PositionOutput;
import com.team1389.hardware.registry.Constructor;
import com.team1389.hardware.registry.PWMPort;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.Servo;

/**
 * A Servo motor
 * @author Jacob Prinz
 */
public class ServoHardware implements Watchable{
	public static final Constructor<PWMPort, ServoHardware> constructor = (PWMPort port) -> {
		return new ServoHardware(port);
	};
	
	Servo wpiServo;
	
	/**
	 * @param port PWM port that servo is plugged into
	 */
	private ServoHardware(PWMPort port) {
		wpiServo = new Servo(port.number);
	}
	
	public int getPort(){
		return wpiServo.getChannel();
	}
	
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
