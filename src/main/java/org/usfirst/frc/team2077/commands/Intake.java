package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj.Joystick;

import static org.usfirst.frc.team2077.Robot.robot_;

public class Intake extends RepeatedCommand {

  Joystick _stick;
  boolean reverse;

  /**
   * if true then intake, if false, ejecting
   * @param ejecting_
   */
  public Intake(boolean ejecting_){
    this.reverse = ejecting_;
    addRequirements(robot_.intakeController_);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    robot_.intakeController_.setRunning();
    robot_.intakeController_.runIntake(this.reverse);
  }

  @Override
  public void end(boolean interrupted) {
    robot_.intakeController_.stopIntake();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}