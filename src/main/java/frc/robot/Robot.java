package frc.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;


public class Robot extends TimedRobot {
  /*
  *  sensor instantiation
  */
  static DigitalInput downwardlimitswitch = new DigitalInput(3);
  static DigitalInput upwardlimitswitch = new DigitalInput(4);
  AnalogInput ultrasonicLeft= new AnalogInput(0);
  AnalogInput ultrasonicRight = new AnalogInput(1);
  DigitalOutput ultrasonicLeftTrigPin = new DigitalOutput(0);
  DigitalOutput ultrasonicRightTrigPin = new DigitalOutput(1);
  
  double forward = 0;
  double turn = 0;
  boolean speedClutch = false;
  private final SendableChooser<String> chooser = new SendableChooser<>();
  //private String autoSelected;
  
  double leftRawValue = ultrasonicLeft.getValue();
  double rightRawValue = ultrasonicRight.getValue();
  double voltageScaleFactor= 5/RobotController.getVoltage5V();

  
  UsbCamera camera1;
  UsbCamera camera2;
  Joystick operator = new Joystick(1);
  XboxController driver = new XboxController(0);

  /*
  *  this function is called when the robot first initializes
  */
  @Override
  public void robotInit() {

    //initialize drivetrain, arm and cameras
    DriveTrain.DriveTrainInit();
    Arm.armInit();
    camera1 = CameraServer.startAutomaticCapture(0);
    camera2 = CameraServer.startAutomaticCapture(1);
    
    //Auton selector in smartdashboard
    chooser.setDefaultOption("two ball auton", Constant.ktwoballAutona1);
    chooser.addOption("Left Auton", Constant.kLeftAuton);
    chooser.addOption("Right Auton", Constant.kRightAuton);
    chooser.addOption("Center Auton", Constant.kCenterAuton);
    chooser.addOption("two ball auton a1", Constant.ktwoballAutona1);
    chooser.addOption("two ball auton a2", Constant.ktwoballAutona2);
    chooser.addOption("two ball auton b3", Constant.ktwoballAutonb3);
    chooser.addOption("three ball auton a2", Constant.kthreeballAutona2);
    SmartDashboard.putData(chooser);
    SmartDashboard.putString("Auton selected: ", chooser.getSelected());
  }

  @Override
  public void autonomousInit() {
    // reset and start timer
    Constant.m_timer.reset();
    Constant.m_timer.start();
    TwoBallAutonA.init();
    
  }
 
  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() { 
   
   /*
    * LeftAunton selected
   */
    SmartDashboard.putNumber("M_timer: ", Constant.m_timer.get());
    if(chooser.getSelected().equals(Constant.kLeftAuton)){
      LeftAuton.leftAuton();
    }else if(chooser.getSelected().equals(Constant.kRightAuton)){
      RightAuton.rightAuton();
    }else if(chooser.getSelected().equals(Constant.kCenterAuton)){
      CenterAuton.centerAuton();
    }
    else if(chooser.getSelected().equals(Constant.ktwoballAutona1)){
      
      TwoBallAutonA.runAuton();
    }
    else if(chooser.getSelected().equals(Constant.ktwoballAutona2)){
      TwoBallAutonA2.runAuton();
    }
    else if(chooser.getSelected().equals(Constant.ktwoballAutonb3)){
      TwoBallAutonB.runAuton();
    }
    else if(chooser.getSelected().equals(Constant.kthreeballAutona2)){
      ThreeBallAutonA2.runAuton();
    }
       
   // else if(chooser.getSelected().equals(Constant.ktwoballAutonb2)){
    //  TwoBallAutonB2.runAuton();
  //  }
    else{
      MiddleAuton.middleAuton();
    }

  } // end autonomous Periodic

    /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    
  } // end telopInit

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  
  ultrasonicLeftTrigPin.set(true);
  ultrasonicRightTrigPin.set(false);
   leftRawValue = ultrasonicLeft.getValue();

  ultrasonicLeftTrigPin.set(false);
  ultrasonicRightTrigPin.set(true);
  rightRawValue = ultrasonicRight.getValue();

  ultrasonicLeftTrigPin.set(false);
  ultrasonicRightTrigPin.set(false);
  voltageScaleFactor= 5/RobotController.getVoltage5V();
  SmartDashboard.putNumber("voltageScaleFactor 5/: ", voltageScaleFactor);
  SmartDashboard.putNumber("rightRawValue: ", rightRawValue);
  SmartDashboard.putNumber("leftRawValue: ", leftRawValue);
  double leftDistanceCentimeters = leftRawValue * voltageScaleFactor * 0.125;
  double rightDistanceCentimeters = rightRawValue * voltageScaleFactor * 0.125;
  SmartDashboard.putNumber("left distance", (int)leftDistanceCentimeters);
  SmartDashboard.putNumber("right distance", (int)rightDistanceCentimeters);
  if((leftDistanceCentimeters -rightDistanceCentimeters) < 1 && (leftDistanceCentimeters -rightDistanceCentimeters) >0 
  || (leftDistanceCentimeters -rightDistanceCentimeters) >-1 && (leftDistanceCentimeters -rightDistanceCentimeters) <0  ){
    Constant.robotAligned = true;
  }
  else{
    Constant.robotAligned = false;
  }
  SmartDashboard.putBoolean( "Robot square +- 1.5cm" ,Constant.robotAligned);
    forward = -driver.getLeftY();
    turn = driver.getRightX() * Constant.speedMultiplier; // moved from negative to positive
    
    SmartDashboard.putBoolean("down switch", downwardlimitswitch.get());
    SmartDashboard.putBoolean("up switch", upwardlimitswitch.get());
    
         //Speed Clutch Control
    if(driver.getRightBumper() == true){
      DriveTrain.differentialDrive.arcadeDrive((forward * Constant.slowSpeed), (turn * Constant.slowSpeedTurn));
    } // end if driver holding right bumper
    else{
      DriveTrain.differentialDrive.arcadeDrive((forward * Constant.speedMultiplier), (turn *Constant.turnMultiplier));
    } // end if user not holding right bumper

    if(downwardlimitswitch.get()==false){
      Arm.armStop();
    SmartDashboard.putString("downlimit switch", "stop the arm");
    } // end if downwardlimitswitch pressed
    else{
      SmartDashboard.putString("downlimit switch", "arm free");
      
    } // end else downwardlimitswitch free
    if(upwardlimitswitch.get()==false){
      Arm.armStop();
    SmartDashboard.putString("uplimit switch", "stop the arm");
    } // end if upwardlimitswitch pressed
    else{
      SmartDashboard.putString("uplimit switch", "arm free");
    
    } // end else upwardlimitswitch free

  /*
  * arm movement controls
  */
    if(operator.getRawButton(16) && downwardlimitswitch.get() == true){ 
      Arm.armGoDown();
      SmartDashboard.putString("arm position", "down");
     } // end if button 16 pressed and downwardlimitswitch is free
     
     if(operator.getRawButton(11) && upwardlimitswitch.get() == true){
      Arm.armGoUp();
    } // end if button 11 pressed and downwardlimitswitch is free

    if(operator.getRawButton(11) == false && operator.getRawButton(16) == false){
      Arm.armStop();
      SmartDashboard.putString("arm motion ", "stop");
    } // end if neither 11 or 16 are pressed
    else{
      SmartDashboard.putString("arm motion ", "moving");
    } // end else 11 or 16 are pressed

  /*
  * intake controls
  */
    if(operator.getRawButton(14)){
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
      
      
    } // end teleop periodic

  @Override
  public void disabledInit() {
    //On disable turn off everything
    DriveTrain.driveLeftA.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveLeftB.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveRightA.set(ControlMode.PercentOutput, 0);
    DriveTrain.driveRightB.set(ControlMode.PercentOutput, 0);
    
    Intake.StopIntake();
   
  } // end disabled init
} // end robot
