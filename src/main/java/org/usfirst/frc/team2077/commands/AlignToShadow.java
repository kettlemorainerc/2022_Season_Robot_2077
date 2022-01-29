/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team2077.sensors.ColorSensor;


public class AlignToShadow extends CommandBase {
	private ColorSensor _sensor;
	private int counter = 0;
	private double SPACES_TO_ROUND = 1000;

	@Override
	public void initialize() {
		_sensor = new ColorSensor();
	}

	@Override
	public void execute(){

		counter++;
		if(counter % 20 == 0){
			Color color = _sensor.checkColors();
//			System.out.println("R: " + Math.round(color.red*SPACES_TO_ROUND) + " G: " + Math.round(color.green*SPACES_TO_ROUND) + " B: " + Math.round(color.blue*SPACES_TO_ROUND));
//			for(int reading: _sensor.getColors()){
//				System.out.print(reading+", ");
//			}
			System.out.println(_sensor.isOnShadow());
		}

//		System.out.println("TESTING");
	}




	@Override
	public void end(boolean interrupted) {

	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
