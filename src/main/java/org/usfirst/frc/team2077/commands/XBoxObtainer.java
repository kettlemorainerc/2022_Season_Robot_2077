package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team2077.RobotHardware;

public class XBoxObtainer extends Obtainer{
    private final XboxController controller;

    public XBoxObtainer(RobotHardware hardware, boolean ejecting, XboxController controller){
        super(hardware, ejecting);
        this.controller = controller;
    }

    @Override
    public void execute() {
        if(controller.getRightTriggerAxis() >= .25){
            super.execute();
        }else{
            super.end(true);
        }
    }

}
