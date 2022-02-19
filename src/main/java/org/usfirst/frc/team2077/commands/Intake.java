package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj.Joystick;

import static org.usfirst.frc.team2077.Robot.robot_;

public class Intake extends RepeatedCommand {

  Joystick _stick;
  public Intake(){
    addRequirements(robot_.intakeController_);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    robot_.intakeController_.setRunning();
    robot_.intakeController_.runIntake(false);
  }


  @Override
  public boolean isFinished() {
    return false;
  }

}