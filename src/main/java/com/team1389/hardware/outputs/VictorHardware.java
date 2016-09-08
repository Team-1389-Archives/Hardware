package com.team1389.hardware.outputs;

import java.util.HashMap;
import java.util.Map;

import com.team1389.hardware.interfaces.outputs.VoltageOutput;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.Victor;

/**
 * A victor motor controller
 * @author Jacob Prinz
 */
public class VictorHardware implements Watchable{
	Victor wpiVictor;
	
	public VictorHardware(int pwmPort, Registry registry) {
		registry.claimPWMPort(pwmPort);
		registry.registerWatcher(this);
		
		wpiVictor = new Victor(pwmPort);
	}

	public VoltageOutput getVoltageOutput() {
		return new VoltageOutput() {
			
			@Override
			public void setVoltage(double voltage) {
				wpiVictor.set(voltage);
			}
		};
	}

	@Override
	public String getName() {
		return "Victor " + wpiVictor.getChannel();
	}

	@Override
	public Map<String, String> getInfo() {
		Map<String, String> info = new HashMap<>();
		
		info.put("last set output", "" + wpiVictor.getSpeed());
		
		return info;
	}
}
