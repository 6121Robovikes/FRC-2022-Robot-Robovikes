// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/** Add your docs here. */
public class DriveTrain {
  static WPI_TalonFX driveLeftA = new WPI_TalonFX(3);
  static WPI_TalonFX driveLeftB = new WPI_TalonFX(4);
  static WPI_TalonFX driveRightA = new WPI_TalonFX(1);
  static WPI_TalonFX driveRightB = new WPI_TalonFX(2);
  static MotorControllerGroup leftMotors = new MotorControllerGroup(driveLeftA, driveLeftB);
  static MotorControllerGroup rightMotors = new MotorControllerGroup(driveRightA, driveRightB);
  static DifferentialDrive differentialDrive = new DifferentialDrive(leftMotors, rightMotors);


    public static void DriveTrainInit(){
        //initialize the motors from the constructor
    // WPI_TalonSRX driveLeftA = la;
    // WPI_TalonSRX driveLeftB = lb;
    // WPI_TalonSRX driveRightA = ra;
    // WPI_TalonSRX driveRightB = rb;
        

    driveLeftA.setInverted(false);
    driveLeftB.setInverted(false);
    driveRightA.setInverted(true);  // ??????????????
    driveRightB.setInverted(true);
   
    //SmartDashboard.putBoolean("right auton", startRight);
    //SmartDashboard.putBoolean("left auton", startleft);
    
    // configuring the b motor to follow the a motor
    driveLeftB.follow(driveLeftA);
    driveRightB.follow(driveRightA);

    //clear position sensors
    driveRightA.configClearPositionOnLimitF(false, 0);
    driveRightA.configClearPositionOnLimitR(false, 0);
    driveRightA.configClearPositionOnQuadIdx(false, 0);
    driveLeftA.configClearPositionOnLimitF(false, 0);
    driveLeftA.configClearPositionOnLimitR(false, 0);
    driveLeftA.configClearPositionOnQuadIdx(false, 0);
    }

    public void driveForward(double power){
        driveLeftA.set(ControlMode.PercentOutput, power);
        driveRightA.set(ControlMode.PercentOutput, power);
      }
      
      public void turnLeft(double power){
      
        driveLeftA.set(ControlMode.PercentOutput, -power);
        driveRightA.set(ControlMode.PercentOutput, power);
      
      }
      public void turnRight(double power){
      
        driveLeftA.set(ControlMode.PercentOutput, power);
        driveRightA.set(ControlMode.PercentOutput, -power);
      }
      public void forwardTurnLeft(double power){
        driveLeftA.set(ControlMode.PercentOutput, -power);
        driveRightA.set(ControlMode.PercentOutput, +power);
      }
      public void forwardTurnRight(double power){
        driveLeftA.set(ControlMode.PercentOutput, +power);
        driveRightA.set(ControlMode.PercentOutput, -power);
      }
      

}
