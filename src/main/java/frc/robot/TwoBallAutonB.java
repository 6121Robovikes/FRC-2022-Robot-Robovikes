// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Add your docs here. */
public class TwoBallAutonB {

    public void twoBallAutonBInt() {   
    Constant.m_timer.reset();
    Constant.m_timer.start();
    }
    public static void runAuton() {
        DriveTrain.differentialDrive.arcadeDrive(0,0);
        if (Constant.m_timer.get() <= 2) {
            SmartDashboard.putString("Auton step :  ", "1 Arm going down");
            SmartDashboard.putBoolean("downwardlimitswitch", Robot.downwardlimitswitch.get());
    if ( Robot.downwardlimitswitch.get() == true) {
                Arm.armGoDown();
                SmartDashboard.putString("Auton step : ", "1 Arm going down");
            }
            else{
            SmartDashboard.putString("Auton step : ", "1 Arm stopped");   
            Arm.armStop();
            }
        }
        if (Constant.m_timer.get() > 2 && Constant.m_timer.get() <= 2.4){
            SmartDashboard.putString("Auton step : ", "2 intake on drive forward");
            Arm.armStop();
            Intake.OhmNom();
            DriveTrain.driveForward(.55);
           }  
           if (Constant.m_timer.get() > 2.4 && Constant.m_timer.get() <= 2.9) {
            SmartDashboard.putString("Auton step : ", "3 stop");
               DriveTrain.driveForward(0);
           }
           if (Constant.m_timer.get() > 2.9 && Constant.m_timer.get() <= 4.9) {
            if(Robot.upwardlimitswitch.get() == true) {
                Intake.StopIntake();
                Arm.armGoUp();
            SmartDashboard.putString("Auton step : ", "4 Arm going up");
    
            }
            else{
                SmartDashboard.putString("Auton step : ", "4 Arm stopped");   
                Arm.armStop();
               
            }
    
          }
          if (Constant.m_timer.get() > 4.9 && Constant.m_timer.get() < 6.3) { // we think 1.4 is too short Adjusted by .2
              DriveTrain.differentialDrive.arcadeDrive(0, -.48);
          }
          if(Constant.m_timer.get() > 6.3 && Constant.m_timer.get() < 6.8) {
            DriveTrain.driveForward(0);
          }
         if(Constant.m_timer.get() > 6.8 && Constant.m_timer.get() < 9.0) {
             DriveTrain.driveForward(.55);
             // move longer and turn less
         }
         if(Constant.m_timer.get() > 9.0 && Constant.m_timer.get() < 9.3 ) {
             DriveTrain.driveForward(0);
         }
         if(Constant.m_timer.get() > 9.3 && Constant.m_timer.get() < 9.9 ) {
             Intake.PewPew(Constant.shooterSpeed);
         }
         if(Constant.m_timer.get() > 9.9 && Constant.m_timer.get() < 11.4) {
             Intake.StopIntake();
             DriveTrain.driveForward(-.5);
         }
         if(Constant.m_timer.get() > 11.4) {
             DriveTrain.driveForward(0);
         }
        }
    }
        
        
       
       
    

