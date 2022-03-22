// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/** Add your docs here. */
public class Arm {

    TalonFX arm;

    public Arm(TalonFX a){
    arm = a;
    arm.setInverted(false);
    arm.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 10,15,1));
    arm.configClearPositionOnLimitF(false, 0);
    arm.configClearPositionOnLimitR(false, 0);
    arm.configClearPositionOnQuadIdx(false, 0);
    arm.setSelectedSensorPosition(0, 0, 0);
    }

    public void armGoDown(){
        arm.set(ControlMode.PercentOutput, -.3);
    }
    public void armStop(){
        arm.set(ControlMode.PercentOutput, 0);
    }
    public void armGoUp(){
        arm.set(ControlMode.PercentOutput, .4);
    }
}
