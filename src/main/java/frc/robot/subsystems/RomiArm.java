// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RomiArm extends SubsystemBase {
  /** Creates a new RomiArm. */

  private Servo armServo;
  private double pwmTargetPW = 1.5; //in ms
  private int rawTarget;

  public RomiArm(int armPWMChannel) {
    armServo = new Servo(armPWMChannel);
  }

  public void setServo(double target) { //from 0.0 to 1.0 full left to full right
    armServo.set(target); 
  }

  public void setTargetPW(double newTarget) { 
    //takes a ms pulse width and converts to 0 to 255
    //as a ration of max to min PW
    pwmTargetPW = newTarget;
    double max = armServo.getRawBounds().max;
    double min = armServo.getRawBounds().min;
    if(newTarget<min) newTarget = min;
    if(newTarget>max) newTarget = max;
    rawTarget = (int) (255*((newTarget - min) / (max - min)));
    armServo.setRaw(rawTarget);
  }

  public void incrementTarget(){
    pwmTargetPW = 0.1 + pwmTargetPW;
    setTargetPW(pwmTargetPW);
    System.out.println("New target = " + pwmTargetPW);
  }

  public void decrementTarget(){
    pwmTargetPW = pwmTargetPW - 0.1;
    setTargetPW(pwmTargetPW);
    System.out.println("New target = " + pwmTargetPW);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
