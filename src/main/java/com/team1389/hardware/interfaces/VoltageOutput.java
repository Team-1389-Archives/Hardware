package com.team1389.hardware.interfaces;

/**
 * Represents a component that can be controlled by voltage[
 * @author Jacob Prinz
 */
public interface VoltageOutput {
	/**
	 * @param voltage the proportion of full battery voltage to send a component, from -1 to 1
	 */
	public void setVoltage(double voltage);
}
