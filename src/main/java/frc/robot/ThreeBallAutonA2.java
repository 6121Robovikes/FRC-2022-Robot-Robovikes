// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Add your docs here. */
public class ThreeBallAutonA2 {
    
    
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
        if (Constant.m_timer.get() > 2 && Constant.m_timer.get() <= 2.6){
            SmartDashboard.putString("Auton step : ", "2 intake on drive forward");
            Arm.armStop();
            Intake.OhmNom();
            DriveTrain.driveForward(.5);
           }  
           if (Constant.m_timer.get() > 2.6 && Constant.m_timer.get() <= 3.1) {
            SmartDashboard.putString("Auton step : ", "3 stop");
               DriveTrain.driveForward(0);
           }
           if (Constant.m_timer.get() > 3.1 && Constant.m_timer.get() <= 5.1) {
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
          if (Constant.m_timer.get() > 5.1 && Constant.m_timer.get() < 6.7) {
              DriveTrain.differentialDrive.arcadeDrive(0, .48);
              //ball picked up and turning
          }
          if(Constant.m_timer.get() > 6.7 && Constant.m_timer.get() < 6.95) {
            DriveTrain.driveForward(0);
            //stop turning
          }
         if(Constant.m_timer.get() > 6.95 && Constant.m_timer.get() < 7.75) {
             DriveTrain.driveForward(.5);
             //move forward after ball pickup turn
         }
         if(Constant.m_timer.get() > 7.75 && Constant.m_timer.get() < 8.00) {
             DriveTrain.driveForward(0);
             //stop
         }
        
         if(Constant.m_timer.get() > 8.00 && Constant.m_timer.get() < 8.5) {
             DriveTrain.differentialDrive.arcadeDrive(0, -.48);
             //align to goal turn
         }
         
         if(Constant.m_timer.get() > 8.5 && Constant.m_timer.get() < 8.75) {
             DriveTrain.driveForward(.5);
             //stop
         }
         if(Constant.m_timer.get() > 8.75 && Constant.m_timer.get() < 9) {
           Intake.PewPew(Constant.shooterSpeed);
            //final drive to goal
         }

         if(Constant.m_timer.get() > 9 && Constant.m_timer.get() < 10.5) {
             Intake.StopIntake();
             DriveTrain.driveForward(-.5);
         }
        }
       }
        
        
       
       
    



