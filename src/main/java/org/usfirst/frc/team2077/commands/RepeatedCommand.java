/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.commands;


import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Full speed translation for a fixed time.
 */
public abstract class RepeatedCommand extends BindableCommand {

    @Override
    public void bind(JoystickButton button){
        button.whileHeld(this);
    }

}
