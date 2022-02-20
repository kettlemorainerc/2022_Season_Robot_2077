/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;


import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.Map;
import java.util.TreeMap;

import static org.usfirst.frc.team2077.Robot.robot_;

public class LoadLauncher extends CommandBase{
  Joystick _stick;
  private Map<String,NetworkTableEntry> nte_ = new TreeMap<>();


  public LoadLauncher(Joystick stick_){
    addRequirements(robot_.newLauncher_);
    this._stick = stick_;
  }

  @Override
  public void initialize() {
    System.out.println("  >START OF EXECUTE <");

    robot_.newLauncher_.setRunning(true);
    robot_.newLauncher_.launch();
//    System.out.println("_stick.getY = "+_stick.getY());
    System.out.println("  >END OF EXECUTE FUNCTION<");
  }

  @Override
  public void end(boolean interrupted) {
    robot_.newLauncher_.stopLoader();
    robot_.newLauncher_.setRunning(false);
    System.out.println(">>>END OF EXECUTE END<<<<<<");
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}
