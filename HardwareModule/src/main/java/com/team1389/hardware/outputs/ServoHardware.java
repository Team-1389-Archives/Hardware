package com.team1389.hardware.outputs;

import java.util.HashMap;
import java.util.Map;

import com.team1389.hardware.interfaces.inputs.PositionInput;
import com.team1389.hardware.interfaces.outputs.PositionOutput;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.Servo;

/**
 * A Servo motor
 * @author Jacob Prinz
 */
public class ServoHardware implements PositionOutput, PositionInput, Watchable{
	Servo wpiServo;
	
	/**
	 * @param port PWM port that servo is plugged into
	 */
	public ServoHardware(int port) {
		wpiServo = new Servo(port);
	}
	
	public int getPort(){
		return wpiServo.getChannel();
	}

	@Override
	public void setPosition(double position) {
		wpiServo.set(position);
	}

	@Override
	public String getName() {
		return "Servo " + getPort();
	}

	@Override
	public Map<String, String> getInfo() {
		Map<String, String> info = new HashMap<>();
		info.put("position", "" + getPosition());
		return info;
	}

	@Override
	public double getPosition() {
		return wpiServo.getPosition();
	}
}
