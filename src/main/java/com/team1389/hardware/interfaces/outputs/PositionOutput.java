package com.team1389.hardware.interfaces.outputs;

/**
 * Represents a component whose position can be controlled directly
 * @author Jacob Prinz
 */
public interface PositionOutput {
	/**
	 * @param position the position of the component in rotations
	 */
	public void setPosition(double position);
}
