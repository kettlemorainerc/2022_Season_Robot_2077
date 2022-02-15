package org.usfirst.frc.team2077.sensors;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.*;

public class ColorSensor{
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 _colorSensor = new ColorSensorV3(i2cPort);
    private static final int CHECK_AGAINST_VALUE = 120;

    public ColorSensor(){

    }
    
//    public Color checkColors(){
//
//        Color color = _colorSensor.getColor();
//        return color;
////        "Red: " + color.red + "\nGreen" + color.green + "\nBlue" + color.blue + "\n");
//
//        //return (colorSensor.getColor().red + colorSensor.getColor().green + colorSensor.getColor().blue > threshold);
//    }

    public int[] getColors(){ return new int[] {_colorSensor.getRed(),_colorSensor.getGreen(),_colorSensor.getBlue(),_colorSensor.getIR(),_colorSensor.getProximity()}; }

    public int getIR(){
        return _colorSensor.getIR();
    }


    public boolean isOnShadow(){
//        Color color = _colorSensor.getColors();
        int[] colors = getColors();


//        FOR BELOW USE 165 for the cutoff
//        return colors[0]+colors[1]+colors[2]+colors[4] < CHECK_AGAINST_VALUE;
//        FOR BELOW USE 115 for the cutoff
        return colors[4] < CHECK_AGAINST_VALUE;
    }


}