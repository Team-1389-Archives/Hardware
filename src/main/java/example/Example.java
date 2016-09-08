package example;

import java.util.List;

import com.team1389.hardware.configuration.PIDConstants;
import com.team1389.hardware.control.PIDConfiguration;
import com.team1389.hardware.inputs.SwitchHardware;
import com.team1389.hardware.interfaces.outputs.PositionOutput;
import com.team1389.hardware.interfaces.outputs.VoltageOutput;
import com.team1389.hardware.outputs.CANTalonHardware;
import com.team1389.hardware.outputs.ServoHardware;
import com.team1389.hardware.outputs.VictorHardware;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.watch.Watchable;

import edu.wpi.first.wpilibj.Victor;

/**
 * This file has a example to show the power of using the Registry to watch everything
 */
public class Example {
	public static void example(){
		//registering hardware
		Registry registry = new Registry();

		CANTalonHardware talon = new CANTalonHardware(0, registry);
		VictorHardware victor = new VictorHardware(0, registry);
		ServoHardware servo = new ServoHardware(1, registry);
		SwitchHardware limitSwitch = new SwitchHardware(0, registry);
		
		//thus it is not possible to instantiate talons or any hardware without the registry
		
		
		PIDConfiguration pidConfig = new PIDConfiguration(new PIDConstants(1, 1, 1),
				false, false);
		PositionOutput talonPosition = talon.getPositionOutput(pidConfig);
		talonPosition.setPosition(56.2);
		
		
		//getting info about all registered hardware
		List<Watchable> allHardware = registry.getHardwareInfo();
		for (Watchable watchable : allHardware){//loop through watchables
			System.out.println(watchable.getName());
			watchable.getInfo().forEach((String name, String value) -> {//loop through info
				System.out.println("\t" + name + ":\t" + value);
			});
		}
		/*
		 * will print out something like this:
		 * 
		 * CANTalon 0
		 *  	speed: 	1.2
		 *   	position: 	34534.23
		 *  	voltage out: 4.5
		 *  	mode: Position
		 * Victor 0
		 *  	last set output: 	0.67
		 * Servo 1
		 *  	position: 	.4
		 * Switch 0
		 *  	state: 	true
		 */
		
		
		
		
		VoltageOutput talonVoltage = talon.getVoltageOutput();
		
		while(true){
			if (limitSwitch.getOnOffOutput().get()){
				talonVoltage.setVoltage(.6);
			} else {
				talonPosition.setPosition(10);
			}
		}
	}
}
