package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team2077.drivetrain.SparkNeoDriveModule;
import org.usfirst.frc.team2077.subsystems.LauncherIF;

public class NewLauncher extends CommandBase implements LauncherIF {
    public static final double MAX_RPM = 5_000;
    private TalonSRX _launcherTalon = new TalonSRX(2);
    private SparkNeoDriveModule _launcherSpark;

    //More in use            DEFAULTS
    private double _setPoint = 0.0;
    private boolean _running = false;
    private boolean _loaded = false;
    private boolean _loaderReady = false;


    public NewLauncher() {
        _launcherTalon.configFactoryDefault();
    }

    @Override
    public void execute() {
        _launcherTalon.set(ControlMode.PercentOutput, _setPoint);
    }


    @Override
    public boolean setRangeUpper(double range) {
        return false;
    }

    @Override
    public boolean setRangeLower(double range) {
        return false;
    }

    @Override
    public void setRunning(boolean running) {
        _running = running;
        if(_running)
            _launcherTalon.set(ControlMode.PercentOutput, _setPoint);
    }

    @Override
    public boolean isRunning() {
        return _running;
    }

    @Override
    public void load() {
        setRPM(.50);
        if (_running && _setPoint != 0) {
            _launcherTalon.set(ControlMode.PercentOutput,_setPoint);
        } else {
            _launcherTalon.set(ControlMode.PercentOutput,0);
        }
    }

    @Override
    public boolean isLoaded() {
        return _loaded;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void launch() {
        _running = true;
    }



    public void setRPM(double rpm) {
        _setPoint = Math.min(rpm, MAX_RPM);
    }
    public void stopLoader(){
//        _loaderReady = false;
//        _running = false;
        _launcherTalon.set(ControlMode.PercentOutput,0.0);

    }







}