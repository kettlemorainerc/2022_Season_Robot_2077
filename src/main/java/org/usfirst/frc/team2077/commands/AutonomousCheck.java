// This file, as far as I can tell is only being worked on by me, therefore, currently, this is the file that you want to replace the old versions
package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class AutonomousCheck extends SequentialCommandGroup{

    private boolean runAuto;
    private boolean galacticSearch;
    private boolean a;
    private boolean red;
    private boolean barrelRacing = false;
    private boolean slalom = false;
    private boolean bounce = false;

    public void initialize(){
    }


    @Override
    public void execute() {
        runAuto = SmartDashboard.getBoolean("Run Autonomous", false);

        if (runAuto || true) {
            new SimulatedStickInput(1, 0, 0).schedule(true);
            done = true;
        }
    }

    private boolean done = false;
    
    public void end(boolean interrupted) {
        super.end(interrupted);
        done = true;
    }
    public boolean isFinished() {
        return done;
    }

    public static void main(String[] args) {
        (new AutonomousCheck()).initialize();
    }
}