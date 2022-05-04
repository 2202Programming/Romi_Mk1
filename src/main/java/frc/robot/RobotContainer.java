// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.DriverPrefs;
import frc.robot.commands.AutoArm;
import frc.robot.commands.ChangeGrabber;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.TankDriveCommand;
import frc.robot.commands.driveArm;
import frc.robot.subsystems.RomiArm;
import frc.robot.subsystems.RomiDrivetrain;
import frc.robot.subsystems.hid.HID_Xbox_Subsystem;
import frc.robot.subsystems.hid.XboxPOV;
import frc.robot.subsystems.ifx.DriverControls.Id;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final RomiDrivetrain m_romiDrivetrain = new RomiDrivetrain();
  private final RomiArm m_romiArm = new RomiArm(Constants.romiGrabberChannel, Constants.romiWristChannel,
      Constants.romiShoulderChannel);
  private final ExampleCommand m_teleCommand = new ExampleCommand(m_romiDrivetrain);
  private final TankDriveCommand m_autoCommand = new TankDriveCommand(m_romiDrivetrain);
  private final XboxController driverController = new XboxController(0);
  public final HID_Xbox_Subsystem driverControls = new HID_Xbox_Subsystem(DriverPrefs.VelExpo, DriverPrefs.RotationExpo,
      DriverPrefs.StickDeadzone);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_romiArm.setDefaultCommand(new driveArm(m_romiArm, driverController));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    driverControls.bind(Id.Driver, XboxPOV.POV_UP).whenPressed(new ChangeGrabber(m_romiArm, true));
    driverControls.bind(Id.Driver, XboxPOV.POV_DOWN).whenPressed(new ChangeGrabber(m_romiArm, false));

    // driverControls.bind(Id.Driver, XboxPOV.POV_UP).whenPressed(new
    // InstantCommand( m_romiArm::incrementTarget ));
    // driverControls.bind(Id.Driver, XboxPOV.POV_DOWN).whenPressed(new
    // InstantCommand( m_romiArm::decrementTarget ));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new AutoArm(m_romiArm);
  }

  public Command getTeleCommand() {
    return m_teleCommand;
  }
}
