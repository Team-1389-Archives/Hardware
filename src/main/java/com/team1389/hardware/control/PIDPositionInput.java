package com.team1389.hardware.control;

import com.team1389.hardware.interfaces.inputs.PositionInput;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDPositionInput implements PIDSource{
	PositionInput sensor;
	
	public PIDPositionInput(PositionInput sensor) {
		this.sensor = sensor;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		if (!pidSource.equals(PIDSourceType.kDisplacement)){
			throw new UnsupportedOperationException("PIDPosition Input is not able to become anything besides a position input");
		}
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return sensor.getPosition();
	}

}
