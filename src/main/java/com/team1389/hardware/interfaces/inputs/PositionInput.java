package com.team1389.hardware.interfaces.inputs;

/**
 * Represents a component that can detect its position
 * @author Jacob Prinz
 */
public interface PositionInput {
	/**
	 * @return The position of the component in rotations
	 */
	public double getPosition();
	
	public static PositionInput invert(PositionInput positionInput){
		return () -> {
			return -positionInput.getPosition();
		};
	}
}
