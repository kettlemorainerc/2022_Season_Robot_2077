package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team2077.*;
import org.usfirst.frc.team2077.drivetrain.*;

public class RotationMovement extends CommandBase {
    protected final DriveStick stick;
    protected final DriveChassisIF chassis;

    public RotationMovement(RobotHardware hardware, DriveStick stick) {
        addRequirements(hardware.heading);

        this.stick = stick;
        this.chassis = hardware.chassis;
    }

    @Override public void execute() {
        chassis.setRotation(stick.getRotation());
    }
}
