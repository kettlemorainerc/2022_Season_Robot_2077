package org.usfirst.frc.team2077;

import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team2077.drivetrain.*;
import org.usfirst.frc.team2077.sensors.*;
import org.usfirst.frc.team2077.subsystems.*;

public class RobotHardware {
    public final CANLineSubsystem.SparkNeo FRONT_LEFT_WHEEL = new CANLineSubsystem.SparkNeo(SparkNeoDriveModule.DrivePosition.FRONT_LEFT);
    public final CANLineSubsystem.SparkNeo FRONT_RIGHT_WHEEL = new CANLineSubsystem.SparkNeo(SparkNeoDriveModule.DrivePosition.FRONT_RIGHT);
    public final CANLineSubsystem.SparkNeo BACK_LEFT_WHEEL = new CANLineSubsystem.SparkNeo(SparkNeoDriveModule.DrivePosition.BACK_LEFT);
    public final CANLineSubsystem.SparkNeo BACK_RIGHT_WHEEL = new CANLineSubsystem.SparkNeo(SparkNeoDriveModule.DrivePosition.BACK_RIGHT);
    public final CANLineSubsystem.SparkNeo[] WHEELS = new CANLineSubsystem.SparkNeo[] {
        FRONT_LEFT_WHEEL, FRONT_RIGHT_WHEEL, BACK_LEFT_WHEEL, BACK_RIGHT_WHEEL
    };

    public final CANLineSubsystem.SparkNeo SHOOTER = new CANLineSubsystem.SparkNeo(SparkNeoDriveModule.DrivePosition.SHOOTER);
    public final CANLineSubsystem.Talon PRIMER = new CANLineSubsystem.Talon(6);
    public final CANLineSubsystem.Talon OBTAINER = new CANLineSubsystem.Talon(7);

    public final Subsystem heading = new Subsystem() {};
    public final Subsystem position = new Subsystem() {};

    public final AngleSensor angleSensor = new AngleSensor();
    public final DriveChassisIF chassis;

    public RobotHardware() {
        chassis = new MecanumChassis(this);
    }

    public CANLineSubsystem.SparkNeo getSparkNeoWheel(MecanumMath.WheelPosition position) {
        for(CANLineSubsystem.SparkNeo wheel : WHEELS) if(wheel.motor.getWheelPosition() == position) return wheel;

        return null;
    }
}
