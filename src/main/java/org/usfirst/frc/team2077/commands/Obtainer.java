package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

import static org.usfirst.frc.team2077.Robot.robot_;

public class Obtainer extends RepeatedCommand {

  Joystick _stick;
  boolean reverse;
  private final TalonSRX _obtainer;


  /**
   * if true then intake, if false, ejecting
   * @param ejecting_
   */
  public Obtainer(TalonSRX obtainerTalon_, boolean ejecting_){
    this._obtainer = obtainerTalon_;
    this.reverse = ejecting_;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    _obtainer.set(ControlMode.PercentOutput, this.reverse ? 0.35 : -.5);
  }

  @Override
  public void end(boolean interrupted) {
    _obtainer.set(ControlMode.PercentOutput,0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}