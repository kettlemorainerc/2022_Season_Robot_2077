package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc.team2077.drivetrain.SparkNeoDriveModule;
import org.usfirst.frc.team2077.subsystems.LauncherIF;

import static org.usfirst.frc.team2077.Robot.robot_;

public class NewLauncher extends CommandBase implements LauncherIF {
    public static final double MAX_RPM = 5_000;
    private TalonSRX _launcherTalon = new TalonSRX(6);
    private SparkNeoDriveModule _shooterNeo = new SparkNeoDriveModule(SparkNeoDriveModule.DrivePosition.SHOOTER);
    private double SHOOTER_UNITS_TO_RPM = 1;//(600 / 2048); //TODO: Fix this
    private double DEFAULT_LAUNCHING_SPEED = 600;

    //More in use            DEFAULTS
    private double _setPoint = 0.0;
    private double shooterSetRpm = 0D;
    private boolean _loaderRunning = false;
    private boolean _shooterRunning = false;
    private boolean _loaded = false;
    private boolean _launcherReady = false;

    private int printingi = 0;



    public NewLauncher() {
        _launcherTalon.configFactoryDefault();
        _shooterNeo.setInverted(true);

        NetworkTableEntry launcherInstance = SmartDashboard.getEntry("launcher_RPM");
        launcherInstance.setPersistent();

        shooterSetRpm = launcherInstance.getDouble(0D);
        if(!launcherInstance.exists()) launcherInstance.setDouble(0.0);



        launcherInstance.addListener(entryNotification -> {
            double providedLauncherRPM = entryNotification.getEntry().getDouble(0D);
            this.shooterSetRpm = providedLauncherRPM;
            if(_loaderRunning) { // TODO: Separate launch & shooter
                _shooterNeo.setRPM(providedLauncherRPM);
            }
        }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew | EntryListenerFlags.kImmediate | EntryListenerFlags.kLocal);

    }

    @Override
    public void execute() {
        _launcherTalon.set(ControlMode.PercentOutput, _setPoint);
    }

    @Override
    public void initialize() {

        this._shooterNeo.setRPM(SmartDashboard.getEntry("launcher_RPM").getDouble(0));
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
        if(_loaderRunning) {
            _launcherTalon.set(ControlMode.PercentOutput, _setPoint);
            _shooterNeo.setRPM(shooterSetRpm);
        }
    }

    @Override
    public boolean isRunning() {
        return _loaderRunning;
    }

    @Override
    public void load() {
        load(_shooterNeo.getMaximumSpeed());
    }


    public void load(double value_) {
        setLoaderPercentage(-.5);
        _shooterRunning = true;

//        if(_shooterRunning /*&& value_ > 0.1*/){//TODO: Make take a _setPoint
////            _shooterNeo.setRPM(DEFAULT_LAUNCHING_SPEED);
////            double launchSpeed = -Math.pow(10000,value_*10);
////            double launchSpeed = -Math.pow(10000,(1+value_)*10);
//            double launchSpeed = value_;
//            _shooterNeo.setRPM(launchSpeed);
////            System.out.println(launchSpeed);
//        }

        if (_loaderRunning && _setPoint != 0.0) {
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
        fireWhenReady(shooterSetRpm);
//        if(_launcherReady){
            load();
//        }else{
//            stopLoader();
//        }
    }

    public void fireWhenReady(double targetVelocity_){
        runLauncher(targetVelocity_);
        if(_shooterNeo.getRPM() >= targetVelocity_){
            _launcherReady = true;
        }else{
            _launcherReady = false;
        }
    }

    private void runLauncher(double shooterRPM_) {
        _shooterRunning = true;
        if (shooterRPM_ != 0) {
            shooterRPM_ = shooterRPM_ / SHOOTER_UNITS_TO_RPM;
//            _shooterNeo.setRPM(shooterRPM_);
        }else{
            _shooterNeo.setRPM(0.0);
        }
    }

    public void setLoaderPercentage(double percentage_) {
        _setPoint = Math.min(percentage_, MAX_RPM);
    }
    public void stopLoader(){
        _loaderRunning = false;
        _launcherTalon.set(ControlMode.PercentOutput,0.0);
        _shooterRunning = false;
        _shooterNeo.setRPM(0.0);

    }







}