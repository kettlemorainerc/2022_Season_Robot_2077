package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.subsystems.CANLineSubsystem;

import java.time.Duration;

public class ClimbAssamblys extends RepeatedCommand {
    protected final CANLineSubsystem climberFirst, climberSecond;
    private boolean allowedToMove = false;

//    private final Duration simulateFor;
    private long startedAtMillis = 0;
    private Duration timeElapsed;

    public ClimbAssamblys(RobotHardware hardware){
        addRequirements(hardware.CLIMBER_ALPHA);
        addRequirements(hardware.CLIMBER_ALPHA);
        addRequirements(hardware.OBTAINER);
        this.climberFirst = hardware.OBTAINER;
        this.climberSecond = hardware.CLIMBER_BETA;
    }

//    public void goToStage1(Duration timeToElapse){
    public void goToStage1(){
        allowedToMove = true;
        if (startedAtMillis == 0) startedAtMillis = System.currentTimeMillis();
        timeElapsed = Duration.ofMillis(System.currentTimeMillis() - startedAtMillis);
        if (timeElapsed.getSeconds()>5) stopAllMovement();
        System.out.println("timeElapsed.getSeconds() = "+timeElapsed.getSeconds());

        climberFirst.setPercent(.5);

    }

    @Override
    public void execute() {
    }

    public void stopAllMovement(){
        allowedToMove = false;
        climberFirst.setPercent(0);
    }

}
