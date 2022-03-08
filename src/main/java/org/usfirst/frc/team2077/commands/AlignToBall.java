package org.usfirst.frc.team2077.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.usfirst.frc.team2077.RobotHardware;

import java.util.Map;
import java.util.TreeMap;

import static org.usfirst.frc.team2077.Robot.robot;

public class AlignToBall extends RepeatedCommand {
    private Map<String,NetworkTableEntry> nte_ = new TreeMap<>();
    RobotHardware hardware;

    public AlignToBall(RobotHardware hardware){
        this.hardware = hardware;
    }


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        NetworkTableEntry nte = null;

        if ( (nte = getNTE("ball1")) != null) {
            Double valueNeedingNewName = NetworkTableInstance.getDefault().getEntry("ball1").getDouble(0);
            System.out.println("888888888888888888888888888888888 "+-valueNeedingNewName);//.getDoubleArray(new double[0])[3]);
//            double[] ball1 = nte.getDoubleArray(new double[0]);
//            if(ball1[3] != 0.0)
            if(valueNeedingNewName == 0D)
                return;
            if(valueNeedingNewName > 50)
                valueNeedingNewName = 50D;


            hardware.chassis.setRotation(valueNeedingNewName*0.75);
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }


    private NetworkTableEntry getNTE( String key ) {
        NetworkTableEntry nte;
        if (robot.networkTableInstance != null
                && ( (nte = nte_.get(key)) != null
                || ( (nte = robot.networkTableInstance.getEntry(key)) != null
                && nte_.put(key, nte) == null ) ) ) {
            return nte;
        }
        return null;
    }
}
