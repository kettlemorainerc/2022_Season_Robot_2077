/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.usfirst.frc.team2077.commands.*;
import org.usfirst.frc.team2077.drivetrain.*;
import org.usfirst.frc.team2077.sensors.*;

import java.util.*;

public class Robot extends TimedRobot {
	private static final String runAutoKey = "Run Autonomous";

	// Everything "global" hangs off the single instance of Robot,
	// either directly or under one of the above public members.
	public static Robot robot_ = null;


	// Other globally accessible objects...
	// Using a constructed constants object instead of statics.
	// Put globally accessible system constants in the Constants class.
	// Other code can access them through Robot.robot_.constants_.<FIELD_NAME>.
	// Drive station controls.
	public DriveStation driveStation_;
	// Inter-process data exchange.
	public NetworkTableInstance networkTableInstance_;
	// Sensors.
	public AngleSensor angleSensor_;
//	public AnalogSettings analogSettings_;
	// Drive train, including:
	//   Controller/motor/wheel/encoder units for each wheel.
	//   Logic for applying robot level functionality to individual wheels.
	public AbstractChassis chassis_;
	// Subsystems
	//    The position/heading subsystems operate as flags to allow control
	//    of chassis rotation to move between commands independently of positioning.
//	public Subsystem position_;
//	public Subsystem heading_;
//	public Subsystem target_;
	//    Aiming system for elevating ball launcher and pointing the robot. Displayed on DS video.
//	public Crosshairs crosshairs_;
	//    Ball launcher with ajustable elevation and speed based on range to target.
//	public LauncherIF launcher_;
//	public SimpleDriveSubsys simpleDriveSubsys_;
//	public LauncherIF launcher_;


	//public TestLauncher tLauncher_; // Bringing back support for the TestLauncher Class though the old instance name
	// public Telemetry telemetry_;

	public TalonSRX obtainer;


	// Default commands
	//    Autonomous selected via drive station dashboard.
	protected Command autonomous_;
	//    Default teleop robot drive.
	protected Command drive_;
	//    Continuous update of target range and direction based on robot motion.
//	protected Command track_;
	//    Operator input of target position relative to robot using the stick.
//	protected Command aim_;
	//    Continuous update of launcher elevation for target range.
//	protected Command range_;

	// This class will be instantiated exactly once, via frc.robot.Main.
	// The constructor initializes the globally accessible static instance,
	// all other initialization happens in robotInit().
	private RobotHardware hardware;
	public Robot() {
		robot_ = this;
	}

	/**
	 * Run once on startup.
	 */
	@Override
	public void robotInit() {
		if(!SmartDashboard.getEntry(runAutoKey).exists()) {
			SmartDashboard.putBoolean(runAutoKey, true);
			SmartDashboard.setPersistent(runAutoKey);
		}

		hardware = new RobotHardware();

		SmartDashboard.putBoolean("Run Autonomous", false);
		networkTableInstance_ = NetworkTableInstance.getDefault();
		angleSensor_ = new AngleSensor();

		//analogSettings_ = new AnalogSettings(1, 2, 3);


		setupDriveTrain();
		chassis_.setPosition(-180, 0, 0); // TODO: Initialize from Smart Dashboard
//		EnumMap<VelocityDirection, Double> p = robot_.chassis_.getPosition();
		setupController();
	}

	public void setupDriveTrain() {
		chassis_ = new MecanumChassis(hardware);
	}

	public void setupController() {
		// Container for remote control software objects.
		driveStation_ = new DriveStation(hardware);
	}

	/**
	 * Called every robot packet (generally about 50x/second) no matter the mode.
	 * Use this for items like diagnostics that you want run during disabled,
	 * autonomous, teleoperated and test.
	 * <p>
	 * This runs after the mode specific periodic functions, but before
	 * LiveWindow and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
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
			targetVelocity.get(MecanumMath.VelocityDirection.NORTH) == 0 &&
			targetVelocity.get(MecanumMath.VelocityDirection.EAST) == 0 &&
			targetVelocity.get(MecanumMath.VelocityDirection.ROTATION) == 0
		) hardware.chassis.halt();
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
	@Override
	public void disabledInit() {
	}

	/**
	 * Called periodically while robot is disabled.
	 */
	@Override
	public void disabledPeriodic() {
	}

	/**
	 * Called once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		if(SmartDashboard.getBoolean("Run Autonomous", true))  {
			CommandScheduler.getInstance().schedule(
				new Move(hardware, -60, 0)
			);
		}
	}

	/**
	 * Called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * Called once each time the robot enters teleop (operator controlled) mode.
	 */
	@Override
	public void teleopInit() {

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		 if (autonomous_ != null) {
		 	autonomous_.cancel();
		 }
	}

	private static void printWheelInfo(SparkNeoDriveModule.DrivePosition position) {
		System.out.printf(
			"[%s set RPM: %s][%s reported RPM %s]",
			position,
			((SparkNeoDriveModule) robot_.chassis_.driveModule.get(position.WHEEL_POSITION)).getSetPoint(),
			position,
			((SparkNeoDriveModule) robot_.chassis_.driveModule.get(position.WHEEL_POSITION)).getRPM()
		);
	}


	private long debug = 0;
	/**
	 * Called periodically during teleop.
	 */
	@Override
	public void teleopPeriodic() {
	}

	/**
	 * Called once each time the robot enters test mode.
	 */
	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance()
						.cancelAll();

	}

	/**
	 * Called periodically during test.
	 */
	@Override
	public void testPeriodic() {

	}
}
