// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RomiArm extends SubsystemBase {
  /** Creates a new RomiArm. */

  private Servo grabberServo;
  private Servo wristServo;
  private Servo shoulderServo;
  
  private double pwmTargetPW = 1.5; //in ms
  private double target;

  public final String NT_Name = "Arm"; // expose data under arm table
  private NetworkTable table;
  private NetworkTableEntry nt_target;
  private NetworkTableEntry nt_current;
  private NetworkTableEntry nt_PWtarget;

  public RomiArm(int grabberPWMChannel, int wristPWMChannel, int shoulderPWMChannel) {
    grabberServo = new Servo(grabberPWMChannel);
    wristServo = new Servo(wristPWMChannel);
    shoulderServo = new Servo(shoulderPWMChannel);

    //Set servo bounds 2.4ms to 0.5ms per documentation
    grabberServo.setBounds(2.4, 2.4, (2.4-0.5)/2, 0.5, 0.5);
    wristServo.setBounds(2.4, 2.4, (2.4-0.5)/2, 0.5, 0.5);
    shoulderServo.setBounds(2.4, 2.4, (2.4-0.5)/2, 0.5, 0.5);

      // for updating CAN status in periodic
      table = NetworkTableInstance.getDefault().getTable(NT_Name);
      nt_target = table.getEntry("/nt_target");
      nt_current = table.getEntry("/nt_current");
      nt_PWtarget = table.getEntry("/nt_PWTarget");
  }

  public void setServo(double temp) { //from 0.0 to 1.0 full left to full right
    target = temp;
    armServo.set(target); 
    nt_target.setDouble(target);
  }

  public void setServo() { //from 0.0 to 1.0 full left to full right
    armServo.set(target);
    nt_target.setDouble(target);
    System.out.println("SetServo target = " + target);
  }

  public void setTargetPW() { 
    //takes a ms pulse width and converts to 0 to 1
    //as a ration of max to min PW
    double max = 2.4;
    double min = 0.5;
    if(pwmTargetPW < min) pwmTargetPW = min;
    if(pwmTargetPW > max) pwmTargetPW = max;
    target = (pwmTargetPW - min) / (max - min);
    setServo();
    System.out.println("PWM Target = " + pwmTargetPW + ", max = " + max
                        + ", min = " + min + ", target = " + target);
  }

  public void incrementTarget(){
    pwmTargetPW = 0.1 + pwmTargetPW;
    setTargetPW();
    nt_PWtarget.setDouble(pwmTargetPW);
  }

  public void decrementTarget(){
    pwmTargetPW = pwmTargetPW - 0.1;
    setTargetPW();
    nt_PWtarget.setDouble(pwmTargetPW);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    nt_current.setDouble(armServo.getPosition());
    
  }

  void sleep(long ms) {
    try {
      Thread.sleep(ms);
    } catch (Exception e) {
    }
  }
}
