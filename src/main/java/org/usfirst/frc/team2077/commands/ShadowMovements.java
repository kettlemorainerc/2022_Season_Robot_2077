package org.usfirst.frc.team2077.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.usfirst.frc.team2077.drivetrain.MecanumMath;
import org.usfirst.frc.team2077.math.AccelerationLimits;

import java.util.EnumMap;

import static org.usfirst.frc.team2077.Robot.robot_;

public class ShadowMovements extends SequentialCommandGroup {
    double[] slow;
    boolean isAllowedToMove;

    public ShadowMovements(){
//        addCommands(
//              new Move(100,0)
//        );
        double[] distanceTotal_ = new double[]{5,0,0}; //fudged values for the multipliers

        slow = new double[] {Math.signum(distanceTotal_[0]), Math.signum(distanceTotal_[1]), Math.signum(distanceTotal_[2]) };

        EnumMap<MecanumMath.VelocityDirection, Double> min = robot_.chassis_.getMinimumVelocity();

        double[] scale = {
                Math.abs(distanceTotal_[0]) / min.get(MecanumMath.VelocityDirection.NORTH),
                Math.abs(distanceTotal_[1]) / min.get(MecanumMath.VelocityDirection.EAST),
                Math.abs(distanceTotal_[2]) / min.get(MecanumMath.VelocityDirection.ROTATION)
        };
    }

    public void searching(){
        if(isAllowedToMove)
            robot_.chassis_.setVelocity(slow[0], slow[1], slow[2], robot_.chassis_.getAccelerationLimits());
        else
            stopSearching();
    }

    public void stopSearching(){
        isAllowedToMove = false;
        robot_.chassis_.setVelocity(0,0,0,robot_.chassis_.getAccelerationLimits());
    }
}
