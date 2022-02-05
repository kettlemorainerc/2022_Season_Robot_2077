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


public class AlignToShadow extends CommandBase {
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
////		System.out.println("TESTING");

//		ShadowMovements smallMovement = new ShadowMovements();


		EnumMap<MecanumMath.VelocityDirection, Double> max = robot_.chassis_.getMaximumVelocity();
		EnumMap<MecanumMath.VelocityDirection, Double> min = robot_.chassis_.getMinimumVelocity();

		double[] distanceTotal_ = new double[]{100, 100 * .68, 100 * 7 / 8}; //fudged values for the multipliers

		double[] sign = {
				Math.signum(distanceTotal_[0]),
				Math.signum(distanceTotal_[1]),
				Math.signum(distanceTotal_[02])
		};

		double[] slow_ = new double[]{
				min.get(MecanumMath.VelocityDirection.NORTH) * sign[0],
				min.get(MecanumMath.VelocityDirection.EAST) * sign[1],
				min.get(MecanumMath.VelocityDirection.ROTATION) * sign[2]
		}; // don't scale below minimum
//		robot_.chassis_.setVelocity(slow_[0], slow_[1], slow_[2], robot_.chassis_.getAccelerationLimits());

	}




	@Override
	public void end(boolean interrupted) {

	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
