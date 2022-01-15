/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static org.usfirst.frc.team2077.Robot.*;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.usfirst.frc.team2077.DriveStation;


public class BasicStickOutput extends CommandBase {

    private Joystick stick;
    private int localCounter = 0;
    private boolean finnished = false;

    public BasicStickOutput(Joystick stick){
        this.stick = stick;
    }


    @Override
    public void execute() {
        if(localCounter % 5 == 0) {
            System.out.println(stick.getRawAxis(0) + ", " + stick.getRawAxis(1));
        }
        localCounter++;
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
