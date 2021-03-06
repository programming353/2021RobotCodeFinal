/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.RobotContainer;

public class BallTransitSubsystem extends SubsystemBase {
  /**
   * Creates a new BallTransitSystem.
   */
  //public static AnalogInput input = new AnalogInput(0);

  public CANSparkMax intakeMotor = new CANSparkMax(Constants.intakeMotorDeviceID,MotorType.kBrushless);
  public CANSparkMax conveyorMotor = new CANSparkMax(Constants.conveyorMotorDeviceID,MotorType.kBrushless);
  public CANSparkMax shooterMotor = new CANSparkMax(Constants.shooterMotorDeviceID,MotorType.kBrushless);
  public CANEncoder ShooterMotorEnconder = shooterMotor.getEncoder();
  /**What happens if we take out preshooter?**/
  
  public DigitalInput shooterSensor = new DigitalInput(Constants.shooterLimitSwitch);
  public DigitalInput intakeSensor = new DigitalInput(Constants.intakeSensorNumber);

  //public DigitalInput conveyorSensor = new DigitalInput(Constants.conveyorSensorNumber); removed conveyorSensor, not sure if it will work

  public boolean intakeIn;
  public boolean intakeOut;
  public boolean shooterRunning;
  public int countTime;
  //If the ball gets stuck or something
  public boolean shooterReverse;
  public static int shoot = 0;
  public BallTransitSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
     boolean intakeBtn = RobotContainer.operatorStick.getRawButton(Constants.intakeButtonNumber); // was driverStick
     boolean intakeBtnReleased = RobotContainer.operatorStick.getRawButtonReleased(Constants.intakeButtonNumber);
     boolean outtakeBtn = RobotContainer.operatorStick.getRawButton(Constants.outtakeButtonNumber); // was driverStick
     boolean outtakeBtnReleased = RobotContainer.operatorStick.getRawButtonReleased(Constants.outtakeButtonNumber);
     //boolean conveyorUpBtn = RobotContainer.operatorStick.getRawButton(Constants.conveyorUpButtonNumber); //getRawButton
     //boolean conveyorDownBtn = RobotContainer.operatorStick.getRawButton(Constants.conveyorUpButtonNumber); //getRawButton
     boolean shootBtn = RobotContainer.operatorStick.getRawButton(Constants.shootButtonNumber); //3/16/2021 Changed to getRawButton to see if this was the issue ~NS
     //code written at 2 in the morning by NS so please review this
    
     //double rawValue = input.getValue();
     //double currentDistance = rawValue * 0.125; //unit is currently scaled to cm
     //SmartDashboard.putNumber("Not Pot", currentDistance);

    if (shootBtn){
      shooterMotor.set(Constants.shooterMotorSpeed);
    }
    else{
      shooterMotor.set(0);
    }
    //Comment this out
    /*if(intakeBtn||outtakeBtn){
      runIntake(intakeBtn,outtakeBtn);
    }*/
    //to here
    //Uncomment
    if (intakeBtnReleased){
      conveyorMotor.set(0);
      intakeMotor.set(0);
    }else if (outtakeBtnReleased){
      conveyorMotor.set(0);
      intakeMotor.set(0);
    }
    else if (intakeBtn){
      conveyorMotor.set(Constants.conveyorMotorSpeed);
      intakeMotor.set(Constants.intakeMotorSpeed);
    }else if (outtakeBtn){
      conveyorMotor.set(Constants.conveyorMotorSpeed*-1);
      intakeMotor.set(-Constants.conveyorMotorSpeed);
    }else{
      conveyorMotor.set(0);
      intakeMotor.set(0);
    }
    //comment this out
    /*if (intakeIn && intakeOut){
      conveyorMotor.set(0);
    }
    else if(intakeIn){ // these next three respond to global querries to run conveyor, could be ors but style
      conveyorMotor.set(Constants.conveyorMotorSpeed);
    }
    else if(intakeOut){
      conveyorMotor.set(Constants.conveyorMotorSpeed*-1);
    }
    /*else if(shooterReverse){
      conveyorMotor.set(Constants.conveyorMotorSpeed*-1);
    }
    else if(shooterRunning){
      conveyorMotor.set(Constants.conveyorMotorSpeed);
    }
    else{
        conveyorMotor.set(0);
    }*/

  }
  /*public double setShooterSpeed(double distance){
    double velocityNeed = Math.sqrt((9.81 * Math.pow(.6, 2)) / (Math.cos(60 * 60) * ((Constants.goalHeight1 - Constants.robotHeight) + .6 * Math.tan(60))));
    return velocityNeed/39.898;
  }
  //RunIntake won't run anymore
  //comment this out
  public void runIntake(boolean intakeBtn,  boolean outtakeBtn){
    if (intakeBtn && outtakeBtn){
      intakeMotor.set(0);
      intakeIn = false;
      intakeOut = false;
    }
    else if (intakeBtn == true){
      intakeMotor.set(Constants.intakeMotorSpeed);
      if(true){  //if(intakeSensor.get() == true){ // Removed for limit switch concerns on 2/29 ~CR
        intakeIn = true;
        intakeOut = false;
      }
    }
    else if (outtakeBtn == true){
      intakeMotor.set(-Constants.conveyorMotorSpeed);
      intakeIn = false;
      intakeOut = true;
    }
    else{
      intakeMotor.set(0);
      intakeIn = false;
      intakeOut = false;
    }
}*/

  /*public void runShooter (boolean shootBtn){
    if (shootBtn == true){
      countTime++;
      if (countTime > 50){
        shooterMotor.set(Constants.shooterMotorSpeed);
        //preShooterMotor.set(Constants.preShooterMotorSpeed); 
        //use redlight to see if code even gets here or limelight
        if (shooterSensor.get() == true){
          shooterRunning = false;
        }
        else{
          shooterRunning = true;
        }
      }
      
      else{
        shooterReverse = true;
      }
      
    }
    else{
      shooterRunning = false;
      shooterMotor.set(0);
      //preShooterMotor.set(0);
      countTime = 0;
      shooterReverse = false;
    }
  
*/
}