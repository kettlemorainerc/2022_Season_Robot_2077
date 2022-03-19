package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.subsystems.CANLineSubsystem;

public class PrimeAndShoot extends RepeatedCommand {
    public static final long MAX_SHOOTER_RPM = 5_000;
    public static final long[] SHOOTING_RPM_RANGE = {2_000L, 5_400L};
    private static final String LAUNCHER_RPM_KEY = "launcher_RPM";
    private final CANLineSubsystem.Talon primer;
    private final CANLineSubsystem.SparkNeo shooter;
    private final NetworkTableEntry shooterSpeed;
    private double shooterTargetRPM;

    public PrimeAndShoot(RobotHardware hardware) {
        primer = hardware.PRIMER;
        shooter = hardware.SHOOTER;

        addRequirements(primer, shooter);

        shooterSpeed = SmartDashboard.getEntry(LAUNCHER_RPM_KEY);

        shooterTargetRPM = shooterSpeed.getDouble(0D);
        if(!shooterSpeed.exists()) shooterSpeed.setDouble(0D);
        shooterSpeed.addListener(entry -> {
            shooterTargetRPM = entry.getEntry().getDouble(0D);
        }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew | EntryListenerFlags.kImmediate | EntryListenerFlags.kLocal);
    }

    public void changeSetpoint(int adjustment){
        double target = shooterTargetRPM + adjustment;
        shooterTargetRPM = target;
        if(target < SHOOTING_RPM_RANGE[0]){
            shooterSpeed.setDouble(SHOOTING_RPM_RANGE[0]);
        }else if(target > SHOOTING_RPM_RANGE[1]){
            shooterSpeed.setDouble(SHOOTING_RPM_RANGE[1]);
        }else{
            shooterSpeed.setDouble(target);
        }
    }

    @Override
    public void initialize() {
        primer.setPercent(-0.45);
        shooter.setRPM(shooterTargetRPM);
    }

    @Override
    public void end(boolean interrupted) {
        primer.setPercent(0);
        shooter.setRPM(0D);
    }
}
