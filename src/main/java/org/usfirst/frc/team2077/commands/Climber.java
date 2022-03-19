package org.usfirst.frc.team2077.commands;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.subsystems.CANLineSubsystem;

public class Climber extends RepeatedCommand{

    protected final CANLineSubsystem climber;
    private boolean goUp;
    private double percentageSpeed = 1.0;

    public Climber(RobotHardware hardware, boolean rightSide, boolean up){
        if(rightSide){
            addRequirements(hardware.CLIMBER_RIGHT);
            this.climber = hardware.CLIMBER_RIGHT;
        }else{
            addRequirements(hardware.CLIMBER_LEFT);
            this.climber = hardware.CLIMBER_LEFT;
        }
        this.goUp = up;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        climber.setPercent(this.goUp ? -this.percentageSpeed : this.percentageSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        climber.setPercent(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
