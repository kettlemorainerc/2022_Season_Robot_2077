package org.usfirst.frc.team2077.commands;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.subsystems.CANLineSubsystem;

public class ShooterSpeeder extends RepeatedCommand {
    protected final CANLineSubsystem shooter;
    protected final PrimeAndShoot primeShooter;
    protected final int speedChangeValue;
    private boolean alreadyRan = false;

    public ShooterSpeeder(RobotHardware hardware, PrimeAndShoot primeShooter, int setpointModificationAmount) {
        addRequirements(hardware.SHOOTER);
        this.shooter = hardware.SHOOTER;
        this.primeShooter = primeShooter;
        this.speedChangeValue = setpointModificationAmount;
    }

    @Override
    public void initialize() {
        alreadyRan = false;
    }

    @Override
    public void execute() {
        if(!alreadyRan){
            primeShooter.changeSetpoint(speedChangeValue);
            alreadyRan = true;
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
