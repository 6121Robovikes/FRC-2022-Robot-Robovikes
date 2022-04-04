// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Add your docs here. */
public class TwoBallAutonA {

    // static DigitalInput downwardlimitswitch = new DigitalInput(3);
    // static DigitalInput upwardlimitswitch = new DigitalInput(4);

    public static void init() {
        Constant.m_timer.reset();
        Constant.m_timer.start();
        SmartDashboard.putNumber("TwoBallAutonTimer", Constant.m_timer.get());

    }

    public static void runAuton() {
        DriveTrain.differentialDrive.arcadeDrive(0, 0);
        if (Constant.m_timer.get() <= 2) {
            SmartDashboard.putString("Auton step :  ", "1 Arm going down");
            SmartDashboard.putBoolean("downwardlimitswitch", Robot.downwardlimitswitch.get());
            if (Robot.downwardlimitswitch.get() == true) {
                Arm.armGoDown();
                SmartDashboard.putString("Auton step : ", "1 Arm going down");
            } else {
                SmartDashboard.putString("Auton step : ", "1 Arm stopped");
                Arm.armStop();
            }
        }
        if (Constant.m_timer.get() > 2 && Constant.m_timer.get() <= 2.5) {
            SmartDashboard.putString("Auton step : ", "2 intake on drive forward");
            Arm.armStop();
            Intake.OhmNom();
            DriveTrain.driveForward(.5);
        }
        if (Constant.m_timer.get() > 2.5 && Constant.m_timer.get() <= 3.5) {
            SmartDashboard.putString("Auton step : ", "3 stop");
            DriveTrain.driveForward(0);
        }
        if (Constant.m_timer.get() > 3.5 && Constant.m_timer.get() <= 5.5) {
            if (Robot.upwardlimitswitch.get() == true) {
                Intake.StopIntake();
                Arm.armGoUp();
                SmartDashboard.putString("Auton step : ", "4 Arm going up");

            } else {
                SmartDashboard.putString("Auton step : ", "4 Arm stopped");
                Arm.armStop();

            }

        }
        if (Constant.m_timer.get() > 5.5 && Constant.m_timer.get() < 7.2) {
            DriveTrain.differentialDrive.arcadeDrive(0, .48);
        }
        if (Constant.m_timer.get() > 7.2 && Constant.m_timer.get() < 7.7) {
            DriveTrain.driveForward(0);
        }
        if (Constant.m_timer.get() > 7.7 && Constant.m_timer.get() < 9.7) {
            DriveTrain.driveForward(.5);
            // move longer and turn less
        }
        if (Constant.m_timer.get() > 9.7 && Constant.m_timer.get() < 10.2) {
            DriveTrain.driveForward(0);
        }
        if (Constant.m_timer.get() > 10.2 && Constant.m_timer.get() < 10.7) {
            Intake.PewPew(Constant.shooterSpeed);
        }
        if (Constant.m_timer.get() > 10.7 && Constant.m_timer.get() < 12.7) {
            DriveTrain.driveForward(-.5);
            Intake.StopIntake();
        }
        if (Constant.m_timer.get() > 12.7) {
            DriveTrain.driveForward(0);
        }
    }
}
