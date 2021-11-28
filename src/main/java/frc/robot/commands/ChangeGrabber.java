/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.RomiArm;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChangeGrabber extends InstantCommand {
  private boolean increment;
  private RomiArm arm;
  private final double STEP_SIZE = 0.1;

  public ChangeGrabber(RomiArm arm, boolean increment) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.increment = increment;
    this.arm = arm;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double currentSetting = arm.getGrabber();
    if (increment && (currentSetting + STEP_SIZE) <= 1.0) {
      arm.setGrabber(currentSetting + STEP_SIZE);
    } else if ((currentSetting - STEP_SIZE) >= 0) {
      arm.setGrabber(currentSetting - STEP_SIZE);
    }
  }
}
