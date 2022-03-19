package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2077.Clock;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.drivetrain.SparkNeoDriveModule;

public class TimedPrimeAndShoot extends PrimeAndShoot {
    private double initTime;
    private double seconds;

    @Override
    public boolean isFinished() {
        return (Clock.getSeconds() - initTime >= seconds);
    }

    public TimedPrimeAndShoot(RobotHardware hardware, double seconds) {
        super(hardware);
        this.seconds = seconds;
    }

    @Override
    public void initialize() {
        super.initialize();
        initTime = Clock.getSeconds();
    }

    @Override
    public void execute() {
        super.execute();
    }
}
