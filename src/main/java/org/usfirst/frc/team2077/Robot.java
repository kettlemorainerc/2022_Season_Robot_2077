/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.usfirst.frc.team2077.commands.*;
import org.usfirst.frc.team2077.drivetrain.*;

import java.util.*;

public class Robot extends TimedRobot {
	private static final String runAutoKey = "Run Autonomous";

	private Map<String,NetworkTableEntry> nte_ = new TreeMap<>();

	public static final String IS_RED_KEY = "Alliance";

	// Everything "global" hangs off the single instance of Robot,
	// either directly or under one of the above public members.
	public static Robot robot = null;


	// Drive station controls.
	public DriveStation driveStation;

	// Inter-process data exchange.
	public NetworkTableInstance networkTableInstance;

	//    Autonomous selected via drive station dashboard.
	protected Command autonomous;

	// This class will be instantiated exactly once, via frc.robot.Main.
	// The constructor initializes the globally accessible static instance,
	// all other initialization happens in robotInit().
	private RobotHardware hardware;

	public Robot() {
		robot = this;
	}

	/** Run once on startup. */
	@Override public void robotInit() {

		if(!SmartDashboard.getEntry(runAutoKey).exists()) {
			SmartDashboard.putBoolean(runAutoKey, false);
			SmartDashboard.setPersistent(runAutoKey);
		}

		hardware = new RobotHardware();

		networkTableInstance = NetworkTableInstance.getDefault();
		networkTableInstance.getEntry("game_time").setDouble(-1d);


//		hardware.chassis.setPosition(-180, 0, 0); // TODO: Initialize from Smart Dashboard
		driveStation = new DriveStation(hardware);

//		DriverStation.Alliance.valueOf(); TODO: FIX
//		SmartDashboard.getEntry("Alliance").setNumber(alliance);

		var alliance = DriverStation.getAlliance();
		SmartDashboard.getEntry(IS_RED_KEY).setBoolean(alliance == DriverStation.Alliance.Red);
	}

	/**
	 * Called every robot packet (generally about 50x/second) no matter the mode.
	 * Use this for items like diagnostics that you want run during disabled,
	 * autonomous, teleoperated and test.
	 * <p>
	 * This runs after the mode specific periodic functions, but before
	 * LiveWindow and SmartDashboard integrated updating.
	 */
	@Override public void robotPeriodic() {
		// Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
		// commands, running already-scheduled commands, removing finished or interrupted commands,
		// and running subsystem periodic() methods.  This must be called from the robot's periodic
		// block in order for anything in the Command-based framework to work.
		CommandScheduler.getInstance().run();

		/* Splitting up North/East (N/E) and rotation (R) velocity updates into multiple commands
		 * makes sense as it makes it simpler to stop calculation rotation without a bunch of hullabaloo.
		 *
		 * However, splitting up N/E and R almost forces us to do it here, or to double-check on both R and N/E
		 * since we don't control which runs first without tying them together and losing the functionality
		 * provided by them being separate. */
		EnumMap<MecanumMath.VelocityDirection, Double> targetVelocity = hardware.chassis.getVelocitySet();
		if(
			targetVelocity.get(MecanumMath.VelocityDirection.NORTH) == 0D &&
			targetVelocity.get(MecanumMath.VelocityDirection.EAST) == 0D &&
			targetVelocity.get(MecanumMath.VelocityDirection.ROTATION) == 0D
		){
			hardware.chassis.halt();
		}
	}

	// The robot and the drive station exchange data packets around 50x/second so long
	// as they are connected and the robot program is running (hasn't crashed or exited).
	// This packet exchange is what keeps the DS related software objects, i.e. Joysticks,
	// in the robot code updated with their position, etc on the the actual DS, and what
	// keeps "Robot Code" indicator on the DS green.
	//
	// Each time a DS packet is received, the underlying WPILIB code calls one or more
	// xxxPeriodic() methods in this class, first a mode-specific one and then robotPeriodic().
	//
	// Each time the robot mode (disabled, autonomous, teleop, test) changes the appropriate
	// xxxInit() method is called. The robotInit() method is called only once at startup.

	/**
	 * Called once each time the robot enters disabled mode.
	 * Note that in competition the robot may (or may not?) be
	 * disabled briefly between autonomous and teleop.
	 */
	@Override public void disabledInit() {
		hardware.pdh.setSwitchableChannel(false);
	}

	/** Called periodically while robot is disabled. */
	@Override public void disabledPeriodic() {}

	/** Called once each time the robot enters autonomous mode. */
	@Override public void autonomousInit() {
		//Start timer on overlay
		if(networkTableInstance.getEntry("game_time").exists()){//TODO: Both do the same thing right now
//			networkTableInstance.getEntry("game_time").setDouble(DriverStation.getMatchTime());
			networkTableInstance.getEntry("game_time").setDouble(0);
		}else{
//			networkTableInstance.getEntry("game_time").setDouble(DriverStation.getMatchTime());
			networkTableInstance.getEntry("game_time").setDouble(0);
//			networkTableInstance.getEntry("game_time").setPersistent();
		}

		if(autonomous == null){
			autonomous = new SequentialCommandGroup(
			new Move(hardware, -15, 0),
			new TimedPrimeAndShoot(hardware, 2),
			new Move(hardware, -30, 0)
//				new ObtainBall(hardware)
			);
		}

		if(SmartDashboard.getBoolean("Run Autonomous", false)) {
			CommandScheduler.getInstance().schedule(autonomous);
		}
		autonomous.schedule();
	}

	/** Called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
	}

	/** Called once each time the robot enters teleop (operator controlled) mode. */
	@Override
	public void teleopInit() {
		hardware.pdh.setSwitchableChannel(true);

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		 if (autonomous != null) {
		 	autonomous.cancel();
		 }
	}

	/** Called periodically during teleop. */
	@Override public void teleopPeriodic() {}

	/** Called once each time the robot enters test mode. */
	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
	}

	/** Called periodically during test. */
	@Override public void testPeriodic() {}


}
