package org.usfirst.frc.team2077.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2077.*;
import org.usfirst.frc.team2077.subsystems.*;

import java.util.function.Consumer;

public class Obtainer extends RepeatedCommand {
  protected boolean ejecting;
  protected final CANLineSubsystem obtainer;
  private static final String OBTAINER_EJECT_KEY = "obtainer_eject_power", OBTAINER_INTAKE_KEY = "obtainer_intake_power";
  private double DEFAULT_EJECT_POWER = 0.35, DEFAULT_INTAKE_POWER = .5; // Revere is handled later, just put the percentage here (0.0 - 1.0)
  private double ejectPower = DEFAULT_EJECT_POWER, intakePower = DEFAULT_INTAKE_POWER;
  private NetworkTableEntry ejectPowerNTE, intakePowerNTE;

  /** @param ejecting */
  public Obtainer(RobotHardware hardware, boolean ejecting){
    addRequirements(hardware.OBTAINER);
    this.obtainer = hardware.OBTAINER;
    this.ejecting = ejecting;

    this.ejectPowerNTE = SmartDashboard.getEntry(OBTAINER_EJECT_KEY);
    this.ejectPower = ejectPowerNTE.getDouble(this.ejectPower);
    if(!ejectPowerNTE.exists()) ejectPowerNTE.setDouble(this.DEFAULT_EJECT_POWER);
    ejectPowerNTE.addListener(entry -> {
      this.ejectPower = entry.getEntry().getDouble(DEFAULT_EJECT_POWER);
    }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew | EntryListenerFlags.kImmediate | EntryListenerFlags.kLocal);

    this.intakePowerNTE = SmartDashboard.getEntry(OBTAINER_INTAKE_KEY);
    this.intakePower = intakePowerNTE.getDouble(this.intakePower);
    if(!intakePowerNTE.exists()) intakePowerNTE.setDouble(this.DEFAULT_INTAKE_POWER);
    intakePowerNTE.addListener(entry -> {
      this.intakePower = entry.getEntry().getDouble(DEFAULT_INTAKE_POWER);
    }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew | EntryListenerFlags.kImmediate | EntryListenerFlags.kLocal);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    obtainer.setPercent(this.ejecting ? -this.ejectPower : this.intakePower);
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