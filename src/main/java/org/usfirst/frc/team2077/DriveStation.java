/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.*;
import org.usfirst.frc.team2077.commands.*;

import java.util.*;


public class DriveStation {
    public final Joystick primaryStick_ = new Joystick(0);
    public final Joystick secondaryStick_ = new Joystick(1);
    public final Joystick testingStick_ = new Joystick(5);
    public final Joystick Flight = new Joystick(2);
    
    private final List<Joystick> joysticks = new LinkedList<>();
    private final List<JoystickButton> buttons = new LinkedList<>();
    private final List<Subsystem> subsystems = new LinkedList<>();

    public DriveStation(Subsystem position_) {
        CommandScheduler.getInstance()
                        .setDefaultCommand(position_, new PrimaryStickDrive3Axis());
//        bindDriverControl(primaryStick_);
        bindTechnicalControl(primaryStick_/*testingStick_*/);
    }

    private static void bindDriverControl(Joystick primary) {
        JoystickButton primaryTrigger = new JoystickButton(primary, 1);

//        new JoystickButton(primary, 4).whenPressed(new ResetCrosshairs());
//        new JoystickButton(primary, 0).whenPressed(new PrimaryStickDrive3Axis());

    }

    private void bindTechnicalControl(Joystick testing) {
        useCommand(new LoadLauncher(), new JoystickButton(testing, 1));
//        new JoystickButton(testing, 1).whileHeld(new LoadLauncher());

//        new JoystickButton(testing, 1).whenPressed(new TurnOffLauncher());
//        new JoystickButton(testing, 1).whenHeld(new BasicStickOutput(testing),true);
//        new JoystickButton(testing, 1).whenHeld(new SimpleDrive(testing),true);
//        new JoystickButton(testing, 1).whenHeld(new NewLauncher(),true);

    }

    public static void useCommand(BindableCommand command, JoystickButton button) {
        command.bind(button);
    }

    /**
     * Condition control axis input to improve driveability.
     * Each axis has a center dead band in which the output for that axis is always zero.
     * Outside the dead band the output increases exponentially from zero to 1 or -1.
     */
    public static double adjustInputSensitivity(double input, double deadBand, double exponent) {
        return Math.pow(Math.max(0, Math.abs(input) - deadBand) / (1 - deadBand), exponent) * Math.signum(input);
    }

    public void cancel() {
//        CommandScheduler.getInstance().unregisterSubsystem(subsystems.toArray(new Subsystem[0]));
//        joysticks.forEach(stick -> {
//            stick.`
//        });
    }
}
