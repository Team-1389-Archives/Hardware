package com.team1389.hardware.watch;

import java.util.Map;

/**
 * A device that a programmer might want to view information on while the program is running.
 * This is meant to help with debugging.
 */
public interface Watchable {
	/**
	 * @return A name that describes this device
	 */
	public String getName();
	/**
	 * @return Map containing all information about the device that might be useful for debugging
	 */
	public Map<String, String> getInfo();
}
