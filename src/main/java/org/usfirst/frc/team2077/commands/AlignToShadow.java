/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team2077.drivetrain.MecanumMath;
import org.usfirst.frc.team2077.math.AccelerationLimits;
import org.usfirst.frc.team2077.sensors.ColorSensor;

import java.util.EnumMap;

import static org.usfirst.frc.team2077.Robot.robot_;


public class AlignToShadow extends RepeatedCommand {
	private ColorSensor _sensor;
	private int counter = 0;
	private double SPACES_TO_ROUND = 10_000;

	@Override
	public void initialize() {
		_sensor = new ColorSensor();
	}

	@Override
	public void execute(){

//		counter++;
//		if(counter % 10 == 0){
////			Color color = _sensor.checkColors();
////			System.out.println("R: " + Math.round(color.red*SPACES_TO_ROUND) + " G: " + Math.round(color.green*SPACES_TO_ROUND) + " B: " + Math.round(color.blue*SPACES_TO_ROUND));
////			for(double reading: _sensor.getColors()){
////				System.out.print(reading+", ");
////			}
////			System.out.println();
//
//			System.out.println("I am "+(_sensor.isOnShadow()?"":"NOT ")+"above the shadow line");
//		}
//
		System.out.println(_sensor.hashCode());

		ShadowMovements smallMovements = new ShadowMovements();
		while(!_sensor.isOnShadow()){
			smallMovements.searching();
		}
		smallMovements.stopSearching();

	}



	@Override
	public void end(boolean interrupted) {
//		smallMoveovements.stopLoader();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
