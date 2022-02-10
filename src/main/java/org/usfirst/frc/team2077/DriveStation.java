/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.*;
import org.usfirst.frc.team2077.commands.*;
import org.usfirst.frc.team2077.drivetrain.*;


public class DriveStation {
    public DriveStation(Subsystem position_, DriveChassisIF chassis) {
        DriveJoystick driveStick = getFlysky();
//        DriveStick driveStick = getJoystick(0);

        Joystick technicalStick = getTechnicalJoystick();
//        Joystick technicalStick = getNumpad();

        position_.setDefaultCommand(new PrimaryStickDrive3Axis(position_, driveStick, chassis));

        bindDriverControl(driveStick);
        bindTechnicalControl(technicalStick);
    }

    private static void bindDriverControl(Joystick primary) {
//        JoystickButton primaryTrigger = new JoystickButton(primary, 1);

//        new JoystickButton(primary, 4).whenPressed(new ResetCrosshairs());
//        new JoystickButton(primary, 0).whenPressed(new PrimaryStickDrive3Axis());
    }

    private void bindTechnicalControl(Joystick secondary_) {
        useCommand(new LoadLauncher(secondary_), new JoystickButton(secondary_, 1));
//        new JoystickButton(testing, 2).whileHeld(new AlignToShadow());

//        new JoystickButton(testing, 1).whenPressed(new PrimaryStickDrive3Axis());

//        new JoystickButton(testing, 1).whenPressed(new TurnOffLauncher());
//        new JoystickButton(testing, 1).whenHeld(new BasicStickOutput(testing),true);
//        new JoystickButton(testing, 1).whenHeld(new SimpleDrive(testing),true);
//        new JoystickButton(testing, 1).whenHeld(new NewLauncher(),true);
    }

    private static DriveJoystick getJoystick() {
        return new DriveJoystick(0).setSensitivity(.5, 2.5);
    }

    private static DriveJoystick getFlysky() {
        return new DriveJoystick(2).setDriveSensitivity(.3, 1)
                                   .setRotationSensitivity(.05, 1);
    }

    private static Joystick getTechnicalJoystick() {
        return new Joystick(1);
    }

    private static Joystick getNumpad() {
        return new Joystick(5);
    }

    public static void useCommand(BindableCommand command, JoystickButton button) {
        command.bind(button);
    }
}
