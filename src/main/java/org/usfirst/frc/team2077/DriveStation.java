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
    public DriveStation(Subsystem position_, DriveChassisIF chassis, Robot robot) {
//        DriveJoystick driveStick = getFlysky();
        DriveJoystick driveStick = getJoystick();

        Joystick technicalStick = getTechnicalJoystick();
//        Joystick technicalStick = getNumpad();

        position_.setDefaultCommand(new PrimaryStickDrive3Axis(position_, driveStick, chassis));

        bindDriverControl(driveStick);
        bindTechnicalControl(technicalStick, robot);
    }

    private static void bindDriverControl(Joystick primary) {
    }

    private void bindTechnicalControl(Joystick secondary, Robot robot) {
        new JoystickButton(secondary, 1).whileHeld(new PrimeAndShoot(), true);
        useCommand(new Obtainer(robot.obtainer, false), new JoystickButton(secondary, 2));
        useCommand(new Obtainer(robot.obtainer, true), new JoystickButton(secondary, 3));

//        useCommand(new AlignToShadow(), new JoystickButton(secondary,4));
    }

    private static DriveJoystick getJoystick() {
        return new DriveJoystick(0).setSensitivity(.2, 2.5);
    }

    private static DriveJoystick getFlysky() {
        return new DriveJoystick(2, 4).setDriveSensitivity(.3, 1)
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
