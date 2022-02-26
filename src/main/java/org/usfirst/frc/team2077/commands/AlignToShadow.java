/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;

import com.revrobotics.*;
import org.usfirst.frc.team2077.*;
import org.usfirst.frc.team2077.drivetrain.*;


public class AlignToShadow extends RepeatedCommand {
	private static final int BLACK_WITHIN = 120;
	private static final double SEARCH_VELOCITY = 5;

	private final ColorSensorV3 sensor;
	private final AbstractChassis chassis;

	private AlignToShadow(RobotHardware hardware) {
		this.sensor = hardware.colorSensor;
		this.chassis = hardware.chassis;
	}

	@Override
	public void execute(){
		if(isOnShadow()) chassis.halt();
		else chassis.setVelocity(SEARCH_VELOCITY, 0, 0);
	}

	public boolean isOnShadow() {
//        int red = sensor.getRed();
//        int blue = sensor.getBlue();
//        int green = sensor.getGreen();
		int proximity = sensor.getProximity();

//        int ir = sensor.getIR();

//        FOR BELOW USE 165 for the cutoff
//        return red + green + blue + proximity < CHECK_AGAINST_VALUE;
//        FOR BELOW USE 115 for the cutoff
		return proximity < BLACK_WITHIN;
	}

	@Override
	public void end(boolean interrupted) {
		chassis.halt();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
