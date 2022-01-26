/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static org.usfirst.frc.team2077.Robot.*;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.usfirst.frc.team2077.DriveStation;
import org.usfirst.frc.team2077.Robot;
import org.usfirst.frc.team2077.subsystems.SimpleDriveSubsys;


public class SimpleDrive extends CommandBase {

    private Joystick stick;
    private int localCounter = 0;
    private boolean finnished = false;


//    TalonSRX s1 = new TalonSRX(1);
//    s1.configFactoryDefault();
//    TalonSRX s2 = new TalonSRX(2);
//    s2.configFactoryDefault();
//    TalonSRX s3 = new TalonSRX(3);
//    s3.configFactoryDefault();
//    TalonSRX s4 = new TalonSRX(4);
//    s4.configFactoryDefault();


    double v1 = 0;
    double v2 = 0;
    double v3 = 0;
    double v4 = 0;


    public SimpleDrive(Joystick stick){
        this.stick = stick;
        addRequirements(robot_.simpleDriveSubsys_);
    }


//    @Override
//    public void initialize(){
////        s1.configFactoryDefault();
////        s2.configFactoryDefault();
////        s3.configFactoryDefault();
//        s4.configFactoryDefault();
//    }

    @Override
    public void initialize(){
//        robot_.simpleDriveSubsys_.setLauncherRPM(50);
    }

    @Override
    public void execute() {
        if(localCounter % 5 == 0) {
            System.out.println(stick.getRawAxis(0) + ", " + stick.getRawAxis(1));
        }
        localCounter++;

//        robot_.simpleDriveSubsys_.setLauncherRPM(50.0);/// setLauncherRPM(50);


//        double r = Math.hypot(stick.getX(), stick.getY());
//        double robotAngle = Math.atan2(stick.getY(), stick.getX()) - Math.PI / 4;
//        double rightX = stick.getZ();

//        v1 = r * Math.cos(robotAngle) + rightX;
//        v2 = r * Math.sin(robotAngle) - rightX;
//        v3 = r * Math.sin(robotAngle) + rightX;
//        v4 = r * Math.cos(robotAngle) - rightX;
//
////        s1.set(ControlMode.PercentOutput, v1);
////        s2.set(ControlMode.PercentOutput, v2);
////        s3.set(ControlMode.PercentOutput, v3);
//        s4.set(ControlMode.PercentOutput, v4);


    }

    @Override
    public void end(boolean interrupted) {
        finnished = true;
    }

    @Override
    public boolean isFinished() {
        return finnished;
    }
}
