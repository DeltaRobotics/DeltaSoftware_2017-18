package org.firstinspires.ftc.teamcode.Off_Season_Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by User on 4/29/2017.
 */

@TeleOp (name = "TeleLinear", group = "")
public class TeleOpLinear extends LinearOpMode
{
    // Object of the robot's hardware
    PacmanHardware beast = new PacmanHardware();

    // Keeps the elapsed time
    ElapsedTime runtime = new ElapsedTime();

    // Powers for the left and right wheels
    double leftPower = 0.0;
    double rightPower = 0.0;

    // Power for the launcher
    double launcherPower = 0.0;

    // Positions popper will be set at
    double popperUp = 0.69;
    double popperDown = 0.94;
    double popperPosition = 0.94;

    // Power the collector will run at
    double collectorPower = 0.0;

    // Power the lift will run at
    double liftPower = 0.0;

    // Last known power the launcher had before it stopped
    double lastLauncherPower = 0.7;

    // State variables that get the current state of the d-pad down and up buttons
    boolean dpadUpState = false;
    boolean dpadDownState = false;

    // State variables that get the current state of the left and right bumpers
    boolean leftBumperState = false;
    boolean rightBumperState = false;

    // State of collector
    boolean collector = false;

    // Variable that keeps track of the collectors direction
    boolean collectorDirection = false;

    // State of the launcher
    boolean launcher = false;

    // Variable that determines if the program should display the telemetry relating to the color sensor
    boolean colorTelemetry = false;

    // Value the launcherPower variable increments/decrements by
    double incrementValue = 0.005;

    // False = regular increment
    // True = fast increment
    boolean incrementValueState = false;

    // State for the back button
    boolean backState = false;

    @Override
    // Runs opMode
    public void runOpMode()
    {
        // Inits the hardware on the robot
        beast.init(hardwareMap);

        //beast.rightColor.setI2cAddress(I2cAddr.create8bit(0x3c));
        beast.rightColor.setI2cAddress(I2cAddr.create8bit(0x2c));

        // Waits for user to press the start button
        waitForStart();

        // Verifys that the opMode is still active
        while(opModeIsActive())
        {
            // Sets the leftPower and rightPower variables to the current value of the joysticks y value corresponding to their appropriate sides
            leftPower = -gamepad1.left_stick_y;
            rightPower = gamepad1.right_stick_y;

            if(gamepad1.dpad_up && !dpadUpState && launcher)
            {
                dpadUpState = true;
                launcherPower += incrementValue;
                lastLauncherPower = launcherPower;

            }
            else if(!gamepad1.dpad_up)
            {
                dpadUpState = false;
            }

            if(gamepad1.dpad_down && !dpadDownState && launcher)
            {
                dpadDownState = true;
                launcherPower -= incrementValue;
                lastLauncherPower = launcherPower;
            }
            else if(!gamepad1.dpad_down)
            {
                dpadDownState = false;
            }

            if(gamepad1.right_trigger > .8)
            {
                popperPosition = popperUp;
            }
            else
            {
                popperPosition = popperDown;
            }

            if(gamepad1.b)
            {
                launcher = false;
                launcherPower = 0.0;
            }

            if(gamepad1.a)
            {
                launcher = true;
                launcherPower = lastLauncherPower;
            }

            if(incrementValueState)
            {
                incrementValue = 0.005;
            }
            else
            {
                incrementValue= 0.05;
            }

            if(gamepad1.back && backState == false)
            {
                backState = true;
                incrementValueState = !incrementValueState;
            }
            else if (!gamepad1.back)
            {
                backState = false;
            }

            if(gamepad1.left_bumper && !leftBumperState)
            {
                leftBumperState = true;
                collector = !collector;
            }
            else if(!gamepad1.left_bumper)
            {
                leftBumperState = false;
            }

            if (collector)
            {
                beast.collector.setPower(collectorPower);
                beast.lift.setPower(liftPower);
            }
            else
            {
                collectorPower = 0.0;
                liftPower = 0.0;
                beast.collector.setPower(collectorPower);
                beast.lift.setPower(liftPower);
            }

            if(gamepad1.right_bumper && !rightBumperState)
            {
                rightBumperState = true;
                collectorDirection = !collectorDirection;
            }
            else if(!gamepad1.right_bumper)
            {
                rightBumperState = false;
            }

            if(collectorDirection && collector)
            {
                collectorPower = 0.3;
                liftPower = 0.3;
            }
            else if(!collectorDirection && collector)
            {
                collectorPower = -0.3;
                liftPower = -0.3;
            }

            Range.clip(launcherPower, 0.005, 1.0);


            beast.motorL.setPower(leftPower);
            beast.motorLF.setPower(leftPower);
            beast.motorR.setPower(rightPower);
            beast.motorRF.setPower(rightPower);
            beast.launcher.setPower(-launcherPower);
            beast.popper.setPosition(popperPosition);

            telemetry.addData("Launcher Power", launcherPower);
            telemetry.addData("Collector Power", collectorPower);
            telemetry.addData("Last Launcher Power", lastLauncherPower);
            telemetry.addData("incrementValueState", incrementValueState);

            if(colorTelemetry)
            {
                telemetry.addData("Left Red", beast.leftColor.red());
                telemetry.addData("Left Green", beast.leftColor.green());
                telemetry.addData("Left Blue", beast.leftColor.blue());
                telemetry.addData("Right Red", beast.rightColor.red());
                telemetry.addData("Right Green", beast.rightColor.green());
                telemetry.addData("Right Blue", beast.rightColor.blue());
            }

            telemetry.update();
        }
    }


}
