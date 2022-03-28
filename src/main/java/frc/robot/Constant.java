// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Timer;

/** Add your docs here. */
public class Constant {

    public static final double speedMultiplier = 0.63; // max speed multiplier of drivetrain
    public static final double slowSpeed = 0.55; // speed of drivetrain when trigger held
    public static final double slowSpeedTurn = 0.7; // speed of drivetrain when trigger held
    public static final double turnMultiplier = 0.9; 
    public static final double shooterSpeed = 0.9; // speed for intake while shooting
    public static final double slowShootSpeed = 0.7; // speed for intake while defense throwing
    public static final double intakeSpeed = 0.5; // speed for intake of balls
    public static final String kMiddleAuton = "Middle Auton";
    public static final String kLeftAuton = "Left Auton";
    public static final String kRightAuton = "Right Auton";
    public static final String kCenterAuton = "Center Auton";
    public static Timer m_timer = new Timer();
  
  
} // end class constant
