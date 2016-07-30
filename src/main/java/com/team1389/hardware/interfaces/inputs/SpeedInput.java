package com.team1389.hardware.interfaces.inputs;

/**
 * Represents a component that can detect its speed
 * @author Jacob Prinz
 */
public interface SpeedInput {
	/**
	 * @return speed of the component in rotations per second
	 */
	public double getSpeed();
	
	public static SpeedInput invert(SpeedInput speedInput){
		return () -> {
			return -speedInput.getSpeed();
		};
	}
}
