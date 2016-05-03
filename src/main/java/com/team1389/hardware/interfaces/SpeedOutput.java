package com.team1389.hardware.interfaces;

/**
 * Represents a component whose speed can be directly controlled
 * @author Jacob Prinz
 */
public interface SpeedOutput {
	/**
	 * @param speed the speed for the component to go in rotations per second
	 */
	public void setSpeed(double speed);
}
