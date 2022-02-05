package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team2077.drivetrain.SparkNeoDriveModule;
import org.usfirst.frc.team2077.subsystems.LauncherIF;

public class NewLauncher extends CommandBase implements LauncherIF {
    public static final double MAX_RPM = 5_000;
    private TalonSRX _launcherTalon = new TalonSRX(6);
    private SparkNeoDriveModule _shooterNeo = new SparkNeoDriveModule(SparkNeoDriveModule.DrivePosition.SHOOTER);
    private double SHOOTER_UNITS_TO_RPM = 1;//(600 / 2048); //TODO: Fix this
    private double DEFAULT_LAUNCHING_SPEED = 600;

    //More in use            DEFAULTS
    private double _setPoint = 0.0;
    private boolean _loaderRunning = false;
    private boolean _shooterRunning = false;
    private boolean _loaded = false;
    private boolean _launcherReady = false;

    private int printingi = 0;



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
        _loaderRunning = running;
        if(_loaderRunning)
            _launcherTalon.set(ControlMode.PercentOutput, _setPoint);
    }

    @Override
    public boolean isRunning() {
        return _loaderRunning;
    }

    @Override
    public void load() {}


    public void load(double value_) {
        setLoaderPercentage(-.3);
        _shooterRunning = true;

        if(_shooterRunning && true){//TODO: Make take a _setPoint
//            _shooterNeo.setRPM(DEFAULT_LAUNCHING_SPEED);
            _shooterNeo.setInverted(true);
            _shooterNeo.setRPM(value_*100000);
        }

        if (_loaderRunning && _setPoint != 0) {
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
//        _running = true;
        _shooterRunning = true;
        fireWhenReady(500);
        if(_launcherReady){
            load();
        }else{
            stopLoader();
        }
    }

    public void fireWhenReady(double targetVelocity_){
        runLauncher(targetVelocity_);
        if(_shooterNeo.getRPM()>=targetVelocity_){ _launcherReady = true; }else{ _launcherReady = false; }
    }

    private void runLauncher(double shooterRPM_) {
        _shooterRunning = true;
        if (shooterRPM_ != 0) {
            shooterRPM_ = shooterRPM_ / SHOOTER_UNITS_TO_RPM;
            _shooterNeo.setRPM(shooterRPM_);
        }
        _shooterNeo.setRPM(0.0);
    }

    public void setLoaderPercentage(double percentage_) {
        _setPoint = Math.min(percentage_, MAX_RPM);
    }
    public void stopLoader(){
//        _loaderReady = false;
//        _running = false;
        _launcherTalon.set(ControlMode.PercentOutput,0.0);
        _shooterRunning = false;
        _shooterNeo.setRPM(0.0);

    }







}