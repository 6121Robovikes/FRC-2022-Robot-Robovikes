// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class CenterAuton {

    public static void centerAutonInit(){
        Constant.m_timer.reset();
        Constant.m_timer.start();
    }
    public static void centerAuton(){
        // turn center
        if (Constant.m_timer.get() < 0){
          DriveTrain.driveForward(0);
        }
          else if(Constant.m_timer.get() > 0 & Constant.m_timer.get() < 2){
            Intake.PewPew(Constant.shooterSpeed);
        }
          else if(Constant.m_timer.get() > 2 & Constant.m_timer.get() < 3.5){
            DriveTrain.driveForward(-.5);
            Intake.StopIntake();
        }
          else if(Constant.m_timer.get() > 3.5){
            DriveTrain.driveForward(0);
          }
          
        
        }
}
