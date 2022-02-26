package org.usfirst.frc.team2077.commands;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2077.drivetrain.SparkNeoDriveModule;

public class PrimeAndShoot extends RepeatedCommand {
    public static final long MAX_RPM = 5_000;
    private static final String LAUNCHER_RPM_KEY = "launcher_RPM";
    private final TalonSRX primer = new TalonSRX(6);
    private final SparkNeoDriveModule shooter = new SparkNeoDriveModule(SparkNeoDriveModule.DrivePosition.SHOOTER);
    private final NetworkTableEntry shooterSpeed;
    private double shooterTargetRPM;

    public PrimeAndShoot() {
        shooterSpeed = SmartDashboard.getEntry(LAUNCHER_RPM_KEY);

        shooterTargetRPM = shooterSpeed.getDouble(0D);
        if(!shooterSpeed.exists()) shooterSpeed.setDouble(0D);
        shooterSpeed.addListener(entry -> {
            shooterTargetRPM = entry.getEntry().getDouble(0D);
        }, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew | EntryListenerFlags.kImmediate | EntryListenerFlags.kLocal);
    }

    @Override
    public void initialize() {
        primer.set(TalonSRXControlMode.PercentOutput, -.45);
        shooter.setRPM(shooterTargetRPM);
    }

    @Override
    public void end(boolean interrupted) {
        primer.set(TalonSRXControlMode.PercentOutput, 0);
        shooter.setRPM(0D);
    }
}
