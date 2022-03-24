// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class LeftAuton {

    public static void leftAutonInit(){
        Constant.m_timer.reset();
        Constant.m_timer.start();
    }

    public static void leftAuton(){
        //starting on the left side
         
        if (Constant.m_timer.get() < 0){
         DriveTrain.driveForward(0);
        } else if(Constant.m_timer.get() > 0 & Constant.m_timer.get() < 1.5){
          DriveTrain.driveForward(.3);
        }
        else if(Constant.m_timer.get() > 1.5 & Constant.m_timer.get() < 1.9){
          DriveTrain.turnLeft(.3);
        }
        else if(Constant.m_timer.get() > 1.9 & Constant.m_timer.get() < 4.4){
         Intake.PewPew(Constant.shooterSpeed);
        }
        else if(Constant.m_timer.get() > 4.4 & Constant.m_timer.get() < 8.4){
          DriveTrain.driveForward(-.4);
         }
        else if(Constant.m_timer.get() > 8.4);
        DriveTrain.driveForward(0);
        }
      
        

}
