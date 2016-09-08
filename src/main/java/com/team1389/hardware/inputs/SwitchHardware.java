package com.team1389.hardware.inputs;

import java.util.HashMap;
import java.util.Map;

import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Represents any binary input such as a limit switch that connects to a DIO port
 */
public class SwitchHardware implements Watchable{
	DigitalInput wpiSwitch;
	
	public SwitchHardware(int dioPort, Registry registry) {
		registry.claimDIOPort(dioPort);
		registry.registerWatcher(this);
		wpiSwitch = new DigitalInput(dioPort);
	}
	
	/**
	 * @return a {@link DigitalInput} that represents the switch
	 */
	public com.team1389.hardware.interfaces.inputs.DigitalInput getOnOffOutput(){
		return () -> {
			return wpiSwitch.get();
		};
	}

	@Override
	public String getName() {
		return "Switch " + wpiSwitch.getChannel();
	}

	@Override
	public Map<String, String> getInfo() {
		Map<String, String> info = new HashMap<>();
		
		info.put("state", "" + wpiSwitch.get());
		
		return info;
	}

}
