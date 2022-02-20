package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class NewIntake extends CommandBase implements Subsystem {
    public static final double MAX_POWER = 1;
    private boolean _running = false;
    private double _power = 0.5;

    private TalonSRX _launcherTalon = new TalonSRX(7);


    public NewIntake() {
        _launcherTalon.configFactoryDefault();
    }

    public void execute() {
        _launcherTalon.set(ControlMode.PercentOutput, _power);
    }


    public boolean isRunning() {
        return _running;
    }

    public void runIntake(boolean reversed_) {
        setPercent(reversed_? 0.35 : -0.5);
        if (_running && _power != 0) {
            _launcherTalon.set(ControlMode.PercentOutput, _power);
        } else {
            _launcherTalon.set(ControlMode.PercentOutput, 0);
        }
    }

    public boolean isReady() {
        return false;
    }

    public void setPercent(double percent) {
        _power = Math.min(percent, MAX_POWER);
    }

    public void setRunning(){ _running = true; }

    public void stopIntake() {
        _running = false;
        _launcherTalon.set(ControlMode.PercentOutput, 0.0);
    }


}
