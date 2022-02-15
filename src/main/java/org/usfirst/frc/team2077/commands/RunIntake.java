/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;

import static org.usfirst.frc.team2077.Robot.robot_;

public class RunIntake extends RepeatedCommand {
  public RunIntake(){
    addRequirements(robot_.newIntake_);
  }

  @Override
  public void initialize() {
    robot_.newLauncher_.setRunning(true);
  }

  @Override
  public void execute() {
    robot_.newLauncher_.load();
  }

  @Override
  public void end(boolean interrupted) {
    robot_.newLauncher_.stopLoader();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
