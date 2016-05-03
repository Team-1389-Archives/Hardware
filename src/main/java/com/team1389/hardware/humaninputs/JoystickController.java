package com.team1389.hardware.humaninputs;

import com.team1389.hardware.interfaces.OnOffInput;
import com.team1389.hardware.interfaces.POVInput;
import com.team1389.hardware.interfaces.RangeInput;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class JoystickController {
	Joystick wpiJoystick;

	public JoystickController(int port) {
		wpiJoystick = new Joystick(port);
	}
	
	public OnOffInput getButton(int button){
		return () -> {
			return wpiJoystick.getRawButton(button);
		};
	}
	
	public RangeInput getAxis(int axis){
		return () -> {
			return wpiJoystick.getRawAxis(axis);
		};
	}
	
	public POVInput getPov(){
		return () -> {
			return wpiJoystick.getPOV();
		};
	}
	
	/**
	 * @param left intensity from 0 to 1
	 * @param right intensity from 0 to 1
	 */
	public void setRumble(double left, double right){
		setLeftRumble(left);
		setRightRumble(right);
	}
	
	/**
	 * @param strength intensity of rumble from 0 to 1
	 */
	public void setLeftRumble(double strength){
		wpiJoystick.setRumble(RumbleType.kLeftRumble, (float)strength);
	}
	
	/**
	 * @param strength intensity of rumble from 0 to 1
	 */
	public void setRightRumble(double strength){
		wpiJoystick.setRumble(RumbleType.kRightRumble, (float)strength);
	}
}
