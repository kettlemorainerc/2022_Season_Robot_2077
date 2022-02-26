package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team2077.*;
import org.usfirst.frc.team2077.drivetrain.*;

public class DriveStickChassisRotation extends CommandBase {
    protected final DriveStick stick;
    protected final DriveChassisIF chassis;

    public DriveStickChassisRotation(RobotHardware hardware, DriveStick stick) {
        this.stick = stick;
        this.chassis = hardware.chassis;
        addRequirements(hardware.heading);
    }

    @Override public void execute() {
        double rotation = stick.getRotation();


    }
}
