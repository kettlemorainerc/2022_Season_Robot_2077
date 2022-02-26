/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team2077.*;
import org.usfirst.frc.team2077.drivetrain.*;

import java.util.Map;
import java.util.TreeMap;

import static org.usfirst.frc.team2077.Robot.*;

public class PrimaryStickDrive3Axis extends CommandBase {
	public static final double ACCELERATION_G_LIMIT = .4;
	public static final double DECELERATION_G_LIMIT = ACCELERATION_G_LIMIT; //1e10 //.35 is the value used for the 03-05-21 version
	public final DriveStickChassisRotation rotationCommand;
	protected DriveStick stick;
	protected DriveChassisIF chassis;
	protected Subsystem heading;

	public PrimaryStickDrive3Axis(
		RobotHardware hardware,
		DriveStick stick
	) {
		addRequirements(hardware.position);

		rotationCommand = new DriveStickChassisRotation(hardware, stick);

		this.stick = stick;
		heading = hardware.heading;
		this.chassis = hardware.chassis;
		this.chassis.setGLimits(ACCELERATION_G_LIMIT, DECELERATION_G_LIMIT);
	}

	@Override
	public void execute() {
		// Speed limit as a percentage (0.0-1.0) of maximum wheel speed
		double speedLimit = 1.0;
		// Rotation limit as a percentage (0.0-1.0) of maximum wheel speed
		double rotationLimit = 1.0; // 0.3;
		double throttle = 1;

		// TODO: Who handles rotation updates if another command owns robot_position_?
		// TODO: Check joystick/drive capabilities and merge w/2-axis.
		double north = -stick.getNorth();
		double east = stick.getEast();

		// Tank drive
		north = Math.abs(north) >= Math.abs(east) ? north : 0;
		east = Math.abs(east) > Math.abs(north) ? east : 0;

		if(heading.getCurrentCommand() != null) { // we don't control heading
			System.out.println(" STICK(3): " + north + " \t" + east);
			if(north == 0 && east == 0) {
				chassis.halt();
			} else {
				chassis.setVelocity01(north * speedLimit * throttle, east * speedLimit * throttle);
			}

		} else { // we control heading
			double clockwise = stick.getRotation()*0.4;

			if (north == 0 && east == 0 && clockwise == 0) {//@@@
				chassis.halt();
			} else {
//				System.out.printf("[Non-0 Stick inputs: N%s E%s R%s]%n", north, east, clockwise);//@@@
				chassis.setVelocity01(
						north * speedLimit * throttle,
						east * speedLimit * throttle,
						clockwise * rotationLimit * throttle
				);
			}
		}
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		return false;
	}

    public static class RunIntake extends RepeatedCommand {
		Joystick _stick;
		private Map<String, NetworkTableEntry> nte_ = new TreeMap<>();
	}
}
