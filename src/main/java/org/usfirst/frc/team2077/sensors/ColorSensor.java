package org.usfirst.frc.team2077.sensors;

import static org.usfirst.frc.team2077.Robot.*;
import edu.wpi.first.wpilibj.I2C;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Talon;//For the motor control

import edu.wpi.first.wpilibj.DriverStation;

public class ColorSensor{
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    public ColorSensor(){

    }
    
    public void checkColor(){
        //int threshold = 150;

        Color color = colorSensor.getColor();
        System.out.println("Red: " + color.red + "\nGreen" + color.green + "\nBlue" + color.blue + "\n");

        //return (colorSensor.getColor().red + colorSensor.getColor().green + colorSensor.getColor().blue > threshold);
    }
    
}