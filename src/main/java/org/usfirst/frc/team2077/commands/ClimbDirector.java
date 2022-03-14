package org.usfirst.frc.team2077.commands;

import org.usfirst.frc.team2077.RobotHardware;

import java.time.Duration;

public class ClimbDirector extends RepeatedCommand {
    protected final ClimbAssamblys climberController;
    private boolean isDown, shouldMove;

    public ClimbDirector(RobotHardware hardware, ClimbAssamblys climberController){
        addRequirements(hardware.CLIMBER_ALPHA);
        addRequirements(hardware.CLIMBER_BETA);
        this.climberController = climberController;
    }

    @Override
    public void initialize(){
        isDown = true;//REDUNDANT
        shouldMove = true;
    }

    @Override
    public void execute(){
        if(shouldMove){
            climberController.goToStage1();
        }else{
            climberController.stopAllMovement();
        }
    }

    @Override
    public boolean isFinished(){return false;}

}
