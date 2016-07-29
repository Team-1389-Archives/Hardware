package com.team1389.hardware.inputs;

import java.util.HashMap;
import java.util.Map;

import com.team1389.hardware.registry.Constructor;
import com.team1389.hardware.registry.DIOPort;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.DigitalInput;

public class SwitchHardware implements Watchable{
	public static final Constructor<DIOPort, SwitchHardware> constructor = (DIOPort port) -> {
		return new SwitchHardware(port);
	};
	
	DigitalInput wpiSwitch;
	
	private SwitchHardware(DIOPort port) {
		wpiSwitch = new DigitalInput(port.number);
	}
	
	public com.team1389.hardware.interfaces.inputs.DigitalInput getOnOffOutput(){
		return () -> {
			return wpiSwitch.get();
		};
	}

	@Override
	public String getName() {
		return "Switch" + wpiSwitch.getChannel();
	}

	@Override
	public Map<String, String> getInfo() {
		Map<String, String> info = new HashMap<>();
		
		info.put("state", "" + wpiSwitch.get());
		
		return info;
	}

}
