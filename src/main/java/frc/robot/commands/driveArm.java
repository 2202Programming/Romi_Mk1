// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.sql.Driver;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiArm;
import frc.robot.subsystems.ifx.DriverControls;

public class driveArm extends CommandBase {
  /** Creates a new driveArm. */

  private RomiArm arm;
  private XboxController controls;

  public driveArm(RomiArm arm, XboxController controls) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.arm = arm;
    this.controls = controls;

    addRequirements(arm);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //arm.setServo(Math.abs(controls.getRawAxis(0))); //left stick X axis
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
