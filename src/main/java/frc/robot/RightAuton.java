// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

/** Add your docs here. */
public class RightAuton {
    
  static DigitalInput downwardlimitswitch = new DigitalInput(0);
  static DigitalInput upwardlimitswitch = new DigitalInput(1);
    public static void rightAutonInit(){
        Constant.m_timer.reset();
        Constant.m_timer.start();
    }

    public static void rightAuton(){
        //starting on right side
        if (Constant.m_timer.get() < 0){
          DriveTrain.driveForward(0);
      } else if(Constant.m_timer.get() > 0 & Constant.m_timer.get() < 1.5){
        DriveTrain.driveForward(.5);
       }
       else if(Constant.m_timer.get() > 1.5 & Constant.m_timer.get() < 1.9){
        Arm.armGoDown();
        
       } 
       else if(Constant.m_timer.get() > 1.9 & Constant.m_timer.get() < 4.4){
        Intake.OhmNom();
       }
       else if(Constant.m_timer.get() > 4.4 & Constant.m_timer.get() < 8.4){
        Arm.armGoUp();
        if(upwardlimitswitch.get()==false){
            Arm.armStop();
        }
       }
       else if(Constant.m_timer.get() > 8.4 & Constant.m_timer.get() < 10.4){
       //DriveTrain.turnLeft(.5);
       }
       else if(Constant.m_timer.get() > 10.4 & Constant.m_timer.get() < 12.4){
       DriveTrain.driveForward(.5);
       }
       else if(Constant.m_timer.get() > 12.4 & Constant.m_timer.get() < 13.4){
       Intake.PewPew(.9);
       }
      //else if(Constant.m_timer.get() > 5 & Constant.m_timer.get() < 9){
       //  turnLeft(.5);
      ////}
      // else if(Constant.m_timer.get() > 9 & Constant.m_timer.get() < 11){
        // driveForward(.3);
       //}
       if(downwardlimitswitch.get()==false){
        Arm.armStop();
  }
 }
}
//hello