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
  //Definitions for the hardware. Change this if you change what stuff you have plugged in
  // WPI_TalonSRX driveLeftA = new WPI_TalonSRX(3);
  // WPI_TalonSRX driveLeftB = new WPI_TalonSRX(4);
  // WPI_TalonSRX driveRightA = new WPI_TalonSRX(1);
  // WPI_TalonSRX driveRightB = new WPI_TalonSRX(2);
  // DriveTrain drivetrain = new DriveTrain(driveLeftA, driveLeftB, driveRightA, driveRightB);
  
  
  
  DigitalInput downwardlimitswitch = new DigitalInput(0);
  DigitalInput upwardlimitswitch = new DigitalInput(1);
  // double RightAdjust = .65;
  Timer m_timer = new Timer();
  TalonFX a = new TalonFX(6);
  Arm arm = new Arm(a);
  double forward = 0;
  double turn = 0;
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
    //Configure motors to turn correct direction. You may have to invert some of your motors
    //driveLeftA.setInverted(false);
    //driveLeftB.setInverted(false);
    //driveRightA.setInverted(false);
    //driveRightB.setInverted(true);
    // arm.setInverted(false);
    //SmartDashboard.putBoolean("right auton", startRight);
    //SmartDashboard.putBoolean("left auton", startleft);
    // configuring the b motor to follow the a motor
    //driveLeftB.follow(driveLeftA);
    //driveRightB.follow(driveRightA);
    DriveTrain.DriveTrainInit();

   //not sure if needed, may remove
    // driveRightA.configClearPositionOnLimitF(false, 0);
    // driveRightA.configClearPositionOnLimitR(false, 0);
    // driveRightA.configClearPositionOnQuadIdx(false, 0);
    // driveLeftA.configClearPositionOnLimitF(false, 0);
    // driveLeftA.configClearPositionOnLimitR(false, 0);
    // driveLeftA.configClearPositionOnQuadIdx(false, 0);
    // arm.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 10,15,1));
    // arm.configClearPositionOnLimitF(false, 0);
    // arm.configClearPositionOnLimitR(false, 0);
    // arm.configClearPositionOnQuadIdx(false, 0);
    // arm.setSelectedSensorPosition(0, 0, 0);
    // set to false to track position
    // set to true to stop tracking and reset to 0
    // tedious
    camera1 = CameraServer.startAutomaticCapture(0);
    camera2 = CameraServer.startAutomaticCapture(1);
    
    //add a thing on the dashboard to turn off auto if needed
   // SmartDashboard.putBoolean("Go For Auto", false);
    //goForAuto = SmartDashboard.getBoolean("Go For Auto", false);

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
   
    if(autoSelected.equals(Constant.kLeftAuton)){
      leftAuton();
    }else if(autoSelected.equals(Constant.kRightAuton)){
      rightAuton();
    }else if(autoSelected.equals(Constant.kCenterAuton)){
      centerAuton();
    }else{
      middleAuton();
    }
    //leftAuton();
    //rightAuton();
    //centerAuton();
    // middleAuton();

  }

    /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    //could be a repeat and might be deleted
    // driveLeftB.follow(driveLeftA);
    // driveRightB.follow(driveRightA);

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // SmartDashboard.putNumber("left drive power", driveLeftA.getActiveTrajectoryVelocity());
    // SmartDashboard.putNumber("right drive power", driveRightA.getActiveTrajectoryVelocity());
    //SmartDashboard.putNumber("arm angle", arm.getSelectedSensorPosition());
    forward = -driver.getLeftY();
    turn = driver.getRightX() * Constant.speedMultiplier; // moved from negative to positive
    
    // double driveLeftPower = forward * Constant.speedMultiplier - turn;
    // double driveRightPower = forward * Constant.speedMultiplier + turn;
    SmartDashboard.putBoolean("down switch", downwardlimitswitch.get());
    SmartDashboard.putBoolean("up switch", upwardlimitswitch.get());
    
         //Speed Clutch Control
    if(driver.getRightBumper() == true){
      
    
      // DriveTrain.driveLeftA.set(ControlMode.PercentOutput, driveLeftPower);
      // DriveTrain.driveRightA.set(ControlMode.PercentOutput, driveRightPower);
      DriveTrain.differentialDrive.arcadeDrive((forward * Constant.slowSpeed), (turn * Constant.slowSpeed));
    }else{
      // DriveTrain.driveLeftA.set(ControlMode.PercentOutput, (forward - turn));
      // DriveTrain.driveRightA.set(ControlMode.PercentOutput, (forward + turn));
      DriveTrain.differentialDrive.arcadeDrive((forward * Constant.speedMultiplier), (turn *Constant.turnMultiplier));
    }

    if(downwardlimitswitch.get()==false){
      arm.armStop();
    SmartDashboard.putString("downlimit switch", "stop the arm");
    }
    else{
      SmartDashboard.putString("downlimit switch", "arm free");
      
    }
    if(upwardlimitswitch.get()==false){
      arm.armStop();
    SmartDashboard.putString("uplimit switch", "stop the arm");
    }
    else{
      SmartDashboard.putString("uplimit switch", "arm free");
    
    }
    //Intake controls
    if(operator.getRawButton(16) && downwardlimitswitch.get() == true){ 
       //button 16 pressed arm down start intake
    // armDown();
    // constant.godown = true;
      arm.armGoDown();
      
     SmartDashboard.putString("arm position", "down");

     
     }
     
     if(operator.getRawButton(11) && upwardlimitswitch.get() == true){
       //button 11 pressed arm up stop intake
    //armUp();
    // constant.goup = true;
    arm.armGoUp();
     
    
    }
    if(operator.getRawButton(11) == false && operator.getRawButton(16) == false){
      arm.armStop();
      SmartDashboard.putString("arm motion ", "stop");
    }
    else{
      SmartDashboard.putString("arm motion ", "moving");
    }

    if(operator.getRawButton(12)){
        //button 12 pressed intake ball
        Intake.OhmNom();
    }
    else if(operator.getRawButton(13)){
      //button 13 pressed eject ball
      // fast shooter
        Intake.PewPew(Constant.shooterSpeed);
      }
      else{
        Intake.StopIntake();
      }
    // if(operator.getRawButton(15)){
    //     //button 15 pressed stop rollers
        
    //     intake.StopIntake();
    //   }
    
         //set to 0
      if(operator.getRawButton(14)){
        arm.armStop();
      }
      if(operator.getRawButton(7)){
        Intake.PewPew(Constant.slowShootSpeed);
        // slow shooter
      }

    }

  @Override
  public void disabledInit() {
    //On disable turn off everything
    //done to solve issue with motors "remembering" previous setpoints after reenable
    DriveTrain.driveLeftA.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveLeftB.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveRightA.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveRightB.set(ControlMode.PercentOutput, 0);
    //arm.set(ControlMode.Position,0);
    Intake.StopIntake();
   // SmartDashboard.putBoolean("down switch", downwardlimitswitch.get());
    //SmartDashboard.putBoolean("up switch", upwardlimitswitch.get());
  }
  /*
  *
  *  this was commented out percieved as redundant
  */
//   @Override
//   public void disabledPeriodic(){
//     //DO NOT PUT ANYTHING ELSE BESIDES AUTO SELECTOR IN HERE - DANGEROUS
//     SmartDashboard.putData("Auto Choices", chooser);
//     autoSelected = chooser.getSelected();
//   }

//    public void driveForward(double power){
//     // DriveTrain.driveLeftA.set(ControlMode.PercentOutput, power);
//     // DriveTrain.driveRightA.set(ControlMode.PercentOutput, power);
//     DriveTrain.differentialDrive.arcadeDrive(power, 0);
// }
  
//   public void turnLeft(double power){

//     // DriveTrain.driveLeftA.set(ControlMode.PercentOutput, -power);
//     // DriveTrain.driveRightA.set(ControlMode.PercentOutput, power);
//     DriveTrain.differentialDrive.arcadeDrive(0, power);

//   }
//   /**
//    * DEPRECEATED
//    * @param power sets power to left drive, and inverted power to right drive
//    */
//   public void turnRight(double power){
//     //THIS IS THE SAME AS TURN LEFT, JUST INVERTED? REDUNDANT, DO NOT USE
//     DriveTrain.driveLeftA.set(ControlMode.PercentOutput, power);
//     DriveTrain.driveRightA.set(ControlMode.PercentOutput, -power);
//     DriveTrain.differentialDrive.arcadeDrive(0, -power);
//   }
  

 
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
