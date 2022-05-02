package org.usfirst.frc.team2077.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.drivetrain.AbstractChassis;

import java.util.Map;
import java.util.TreeMap;

public class AlignToBall extends RepeatedCommand {
    private final static String VISION_DATA_KEY = "all_vision_data";

    private Map<String,NetworkTableEntry> nte_ = new TreeMap<>();
    private final AbstractChassis chassis;
    private double rotationSpeed = 0D;
    private final NetworkTableEntry entry;


    public AlignToBall(RobotHardware hardware){
        addRequirements(hardware.heading);
        this.chassis = hardware.chassis;

        entry = SmartDashboard.getEntry(VISION_DATA_KEY);
        entry.addListener(this::onVisionDataChange, EntryListenerFlags.kUpdate | EntryListenerFlags.kNew | EntryListenerFlags.kImmediate | EntryListenerFlags.kLocal);
    }

    public void onVisionDataChange(EntryNotification notif) {
        int mask = notif.getEntry().getNumber(0).intValue();
        System.out.println("mask = "+mask);
        updateStateFromMask(mask);
    }

    public void updateStateFromMask(int mask){
        // 0b111110 & 0b1 = 0
        boolean rotateRight = (mask & 0b1) > 0;
        mask = mask >> 1; // 0b111110 >> 1 = 0b11111

        int oridinal = (mask & 0b11); // 0b11111 & 0b11 = 0b00011
        mask = mask >> 2; // 0b11111 >> 2 = 0b111

        ObtainBall.Speed rotationSpeed = ObtainBall.Speed.values()[oridinal];

        if(rotationSpeed == ObtainBall.Speed.NONE) {
            this.rotationSpeed = 0;
        }else if(rotationSpeed == ObtainBall.Speed.LOW){
            this.rotationSpeed = 0.3;
        }else if(rotationSpeed == ObtainBall.Speed.MID){
            this.rotationSpeed = 0.5;
        }else if(rotationSpeed == ObtainBall.Speed.HIGH){
            this.rotationSpeed = 0.8;
        }
        this.rotationSpeed *= rotateRight? -1 : 1;
    }


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.println("this.rotationSpeed = "+this.rotationSpeed);
        chassis.setRotation01(this.rotationSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        chassis.setRotation01(0d);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
