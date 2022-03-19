package org.usfirst.frc.team2077.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.drivetrain.DriveChassisIF;
import org.usfirst.frc.team2077.subsystems.CANLineSubsystem;

public class ObtainBall extends RepeatedCommand {

    private final static String VISION_DATA_KEY = "all_vision_data";
    private final NetworkTableEntry entry;

    private final DriveChassisIF chassis;
    protected final CANLineSubsystem obtainer;

    private double rotationSpeed;
    private double movementSpeed;

    private double obtainerSpeed = 0;

    public ObtainBall(RobotHardware hardware) {
        chassis = hardware.chassis;
        addRequirements(chassis);
        obtainer = hardware.OBTAINER;

        entry = SmartDashboard.getEntry(VISION_DATA_KEY);
        entry.addListener(this::onVisionDataChange, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew | EntryListenerFlags.kImmediate | EntryListenerFlags.kLocal);
    }

    @Override
    public void execute() {
        chassis.setVelocity01(movementSpeed, 0, rotationSpeed);
        obtainer.setPercent(obtainerSpeed);
    }

    public void onVisionDataChange(EntryNotification notif) {
        int mask = notif.getEntry().getNumber(0).intValue();
        updateStateFromMask(mask);
    }

    public void updateStateFromMask(int mask){
        // 0b111110 & 0b1 = 0
        boolean rotateRight = (mask & 0b1) > 0;
        mask = mask >> 1; // 0b111110 >> 1 = 0b11111

        int oridinal = (mask & 0b11); // 0b11111 & 0b11 = 0b00011
        mask = mask >> 2; // 0b11111 >> 2 = 0b111

        Speed rotationSpeed = Speed.values()[oridinal];

        if(rotationSpeed == Speed.NONE) {
            this.rotationSpeed = 0;
        }else if(rotationSpeed == Speed.LOW){
            this.rotationSpeed = 0.3;
        }else if(rotationSpeed == Speed.MID){
            this.rotationSpeed = 0.5;
        }else if(rotationSpeed == Speed.HIGH){
            this.rotationSpeed = 0.8;
        }
        this.rotationSpeed *= rotateRight? 1 : -1;

        oridinal = (mask & 0b11);
        mask = mask >> 2;
        Speed movementSpeed = Speed.values()[oridinal];

        if(movementSpeed == Speed.NONE) {
            this.movementSpeed = 0;
        }else if(movementSpeed == Speed.LOW){
            this.movementSpeed = 0.3;
        }else if(movementSpeed == Speed.MID){
            this.movementSpeed = 0.5;
        }else if(movementSpeed == Speed.HIGH){
            this.movementSpeed = 0.8;
        }

        boolean runObtainer = (mask & 0b1) > 0;
        this.obtainerSpeed = (runObtainer? 0.5 : 0);
    }

    @Override
    public void initialize() {
        updateStateFromMask(entry.getNumber(0D).intValue());
    }

    @Override
    public void end(boolean interrupted) {
        chassis.halt();
        obtainer.setPercent(0D);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public enum Speed{
        NONE, LOW, MID, HIGH;
    }
}
