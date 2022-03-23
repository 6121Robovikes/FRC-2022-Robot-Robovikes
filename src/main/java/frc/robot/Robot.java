package frc.robot;

//import java.sql.Driver;

//import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
//import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
//import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.ctre.phoenix.motorcontrol.can.VictorSPX;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
// import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.DigitalInput;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMax.IdleMode;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.ADXRS450_Gyro;

//new import
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.interfaces.Gyro;
//import edu.wpi.first.wpilibj.SPI;


public class Robot extends TimedRobot {
  
  
  /*
  *  sensor instantiation
  */
  DigitalInput downwardlimitswitch = new DigitalInput(0);
  DigitalInput upwardlimitswitch = new DigitalInput(1);
  
  Timer m_timer = new Timer();
  //TalonFX a = new TalonFX(6);
  //Arm arm = new Arm(a);
  //double forward = 0;
  //double turn = 0;
  boolean speedClutch = false;
  private final SendableChooser<String> chooser = new SendableChooser<>();
  private String autoSelected;
  
  
  UsbCamera camera1;
  UsbCamera camera2;
  //private static final double kAngleSetpoint = 0.0;
	//private static final double kP = 0.005; // propotional turning constant
  

   


  // private final DifferentialDrive m_robotDrive = new DifferentialDrive(driveLeftA, driveRightA);
  Joystick operator = new Joystick(1);
  XboxController driver = new XboxController(0);

  //Constants for controlling the arm. consider tuning these for your particular robot
 /**  final double armHoldUp = 0.08;
  final double armHoldDown = 0.13;
  final double armTravel = 0.5;

  final double armTimeUp = 0.5;
  final double armTimeDown = 0.35;
  boolean startleft = false;
  boolean startRight = false;
  //Varibles needed for the code
  boolean armUp = true; //Arm initialized to up because that's how it would start a match
  boolean burstMode = false;
  double lastBurstTime = 0;
  DigitalInput downwardlimitswitch = new DigitalInput(0);
  DigitalInput upwardlimitswitch = new DigitalInput(1);
  boolean godown = false;
  boolean goup = false;
  double autoStart = 0;
  boolean goForAuto = false;
*/

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */




  @Override
  public void robotInit() {

    //initialize drivetrain, arm and cameras
    DriveTrain.DriveTrainInit();
    Arm.armInit();
    camera1 = CameraServer.startAutomaticCapture(0);
    camera2 = CameraServer.startAutomaticCapture(1);
    
    //Auton selector in smartdashboard
    chooser.setDefaultOption("Middle Auton", Constant.kMiddleAuton);
    chooser.addOption("Left Auton", Constant.kLeftAuton);
    chooser.addOption("Right Auton", Constant.kRightAuton);
    chooser.addOption("Center Auton", Constant.kCenterAuton);
  }

  @Override
  public void autonomousInit() {
    // reset and start timer
    m_timer.reset();
    m_timer.start();
    
  }
 
  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() { 
   
   /*
    * LeftAunton selected
   */
    if(autoSelected.equals(Constant.kLeftAuton)){
      leftAuton();
    }else if(autoSelected.equals(Constant.kRightAuton)){
      rightAuton();
    }else if(autoSelected.equals(Constant.kCenterAuton)){
      centerAuton();
    }else{
      middleAuton();
    }

  } // end autonomous Periodic

    /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    
  } // end telopInit

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    forward = -driver.getLeftY();
    turn = driver.getRightX() * Constant.speedMultiplier; // moved from negative to positive
    
    SmartDashboard.putBoolean("down switch", downwardlimitswitch.get());
    SmartDashboard.putBoolean("up switch", upwardlimitswitch.get());
    
         //Speed Clutch Control
    if(driver.getRightBumper() == true){
      DriveTrain.differentialDrive.arcadeDrive((forward * Constant.slowSpeed), (turn * Constant.slowSpeed));
    } // end if driver holding right bumper
    else{
      DriveTrain.differentialDrive.arcadeDrive((forward * Constant.speedMultiplier), (turn *Constant.turnMultiplier));
    } // end if user not holding right bumper

    if(downwardlimitswitch.get()==false){
      arm.armStop();
    SmartDashboard.putString("downlimit switch", "stop the arm");
    } // end if downwardlimitswitch pressed
    else{
      SmartDashboard.putString("downlimit switch", "arm free");
      
    } // end else downwardlimitswitch free
    if(upwardlimitswitch.get()==false){
      arm.armStop();
    SmartDashboard.putString("uplimit switch", "stop the arm");
    } // end if upwardlimitswitch pressed
    else{
      SmartDashboard.putString("uplimit switch", "arm free");
    
    } // end else upwardlimitswitch free

  /*
  * arm movement controls
  */
    if(operator.getRawButton(16) && downwardlimitswitch.get() == true){ 
      arm.armGoDown();
      SmartDashboard.putString("arm position", "down");
     } // end if button 16 pressed and downwardlimitswitch is free
     
     if(operator.getRawButton(11) && upwardlimitswitch.get() == true){
      arm.armGoUp();
    } // end if button 11 pressed and downwardlimitswitch is free

    if(operator.getRawButton(11) == false && operator.getRawButton(16) == false){
      arm.armStop();
      SmartDashboard.putString("arm motion ", "stop");
    } // end if neither 11 or 16 are pressed
    else{
      SmartDashboard.putString("arm motion ", "moving");
    } // end else 11 or 16 are pressed

  /*
  * intake controls
  */
    if(operator.getRawButton(12)){
        Intake.OhmNom();
    }//button 12 pressed intake ball
    else if(operator.getRawButton(13)){
        Intake.PewPew(Constant.shooterSpeed);
    } // else if button 13 pressed shoot ball high speed
    else if(operator.getRawButton(7)){
        Intake.PewPew(Constant.slowShootSpeed);
      }// slow shooter
    else{
        Intake.StopIntake();
      } // else not button 12 or 13 turn off shooter
      
      
    }

  @Override
  public void disabledInit() {
    //On disable turn off everything
    DriveTrain.driveLeftA.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveLeftB.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveRightA.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveRightB.set(ControlMode.PercentOutput, 0);
    
    Intake.StopIntake();
   
  }

 
  public void leftAuton(){
  //starting on the left side
   
  if (m_timer.get() < 0){
   DriveTrain.driveForward(0);
  } else if(m_timer.get() > 0 & m_timer.get() < 1.5){
    DriveTrain.driveForward(.3);
  }
  else if(m_timer.get() > 1.5 & m_timer.get() < 1.9){
    DriveTrain.turnLeft(.3);
  }
  else if(m_timer.get() > 1.9 & m_timer.get() < 4.4){
   Intake.PewPew(Constant.shooterSpeed);
  }
  else if(m_timer.get() > 4.4 & m_timer.get() < 8.4){
    DriveTrain.driveForward(-.4);
   }
  else if(m_timer.get() > 8.4);
  DriveTrain.driveForward(0);
  }

  public void rightAuton(){
  //starting on right side
  if (m_timer.get() < 0){
    DriveTrain.driveForward(0);
} else if(m_timer.get() > 0 & m_timer.get() < 1.5){
  DriveTrain.driveForward(.3);
 }
 else if(m_timer.get() > 1.5 & m_timer.get() < 1.9){
  DriveTrain.turnRight(.3);
 } 
 else if(m_timer.get() > 1.9 & m_timer.get() < 4.4){
   Intake.PewPew(Constant.shooterSpeed);
 }
 else if(m_timer.get() > 4.4 & m_timer.get() < 8.4){
  DriveTrain.driveForward(-.4);
 }
 else if(m_timer.get() > 8.4);
 DriveTrain.driveForward(0);
//else if(m_timer.get() > 5 & m_timer.get() < 9){
 //  turnLeft(.5);
////}
// else if(m_timer.get() > 9 & m_timer.get() < 11){
  // driveForward(.3);
 //}
}
  public void centerAuton(){
// turn center
if (m_timer.get() < 0){
  DriveTrain.driveForward(0);
}
  else if(m_timer.get() > 0 & m_timer.get() < 2){
    Intake.PewPew(Constant.shooterSpeed);
}
  else if(m_timer.get() > 2 & m_timer.get() < 8){
    DriveTrain.driveForward(-.3);
}
  else if(m_timer.get() > 8){
    DriveTrain.driveForward(0);
  }
  

}
  public void middleAuton(){
    if (m_timer.get() < 0){
      DriveTrain.driveForward(0);
    }
      else if(m_timer.get() > 0 & m_timer.get() < 2){
        Intake.PewPew(Constant.shooterSpeed);;
    }
      else if(m_timer.get() > 2 & m_timer.get() < 6){
        DriveTrain.driveForward(0);
    }
    else if(m_timer.get() > 6 & m_timer.get() < 12){
      DriveTrain.driveForward(-.3);

    }
      else if(m_timer.get() > 12){
        DriveTrain.driveForward(0);
  }

  }
}
