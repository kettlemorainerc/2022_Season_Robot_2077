package org.usfirst.frc.team2077.commands;

import org.usfirst.frc.team2077.*;
import org.usfirst.frc.team2077.subsystems.*;

public class Obtainer extends RepeatedCommand {
  protected boolean reverse;
  protected final CANLineSubsystem obtainer;

  /** @param ejecting */
  public Obtainer(RobotHardware hardware, boolean ejecting){
    addRequirements(hardware.OBTAINER);
    this.obtainer = hardware.OBTAINER;
    this.reverse = !ejecting;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    obtainer.setPercent(this.reverse ? -.5 : 0.35);
  }

  @Override
  public void end(boolean interrupted) {
    obtainer.setPercent(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}