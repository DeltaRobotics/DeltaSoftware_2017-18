package org.firstinspires.ftc.teamcode.Off_Season_Testing;

import android.content.Context;
import android.media.AudioManager;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by User on 6/6/2017.
 */
@TeleOp (name = "LED_Test_2", group = "")
public class LED_Test_2 extends LinearOpMode {

    PacmanHardware beast = new PacmanHardware();

    private int loop = 100000;

    private boolean led_orange = false;
    private boolean led_blue= false;
    private boolean led_green= false;
    private boolean led_rainbow = false;
    private boolean led_white = false;
    private boolean orange_state = false;
    private boolean blue_state = false;
    private boolean green_state = false;
    private boolean white_state = false;
    private boolean rainbow_state = false;
    private double rainbow_red = 0;
    private double rainbow_green = 0;
    private double rainbow_blue = 0;
    boolean rainbowPhaseOne = false;
    boolean rainbowPhaseTwo = false;
    boolean rainbowPhaseThree = false;
    boolean start = true;

    public void runOpMode() {

        beast.init(hardwareMap);

        //AudioManager audioManager = (AudioManager)

        waitForStart();

        while (opModeIsActive())
        {

            if(gamepad2.y && !orange_state)
            {
                led_orange = true;
                orange_state = true;
                led_blue = false;
                led_green = false;
                led_rainbow = false;
                green_state= false;
                blue_state = false;
                rainbow_state = false;
                white_state = false;
                led_white = false;
                sleep(250);
            }
            else if(gamepad2.y)
            {
                orange_state = false;
                led_orange = false;
                sleep(250);
            }

            if(gamepad2.x && !blue_state)
            {
                led_blue = true;
                blue_state = true;
                led_orange = false;
                orange_state = false;
                led_green = false;
                green_state= false;
                led_rainbow = false;
                rainbow_state = false;
                white_state = false;
                led_white = false;
                sleep(250);
            }
            else if(gamepad2.x)
            {
                blue_state = false;
                led_blue = false;
                sleep(250);
            }

            if(gamepad2.a && !green_state)
            {
                led_green= true;
                green_state= true;
                led_blue = false;
                led_rainbow = false;
                blue_state = false;
                led_orange = false;
                orange_state = false;
                rainbow_state = false;
                white_state = false;
                led_white = false;
                sleep(250);
            }
            else if (gamepad2.a)
            {
                green_state = false;
                led_green = false;
                sleep(250);
            }

            if(gamepad2.b && !rainbow_state)
            {
                led_rainbow = true;
                rainbow_state = true;
                led_green= false;
                green_state= false;
                led_blue = false;
                blue_state = false;
                led_orange = false;
                orange_state = false;
                white_state = false;
                led_white = false;
                sleep(250);
            }
            else if (gamepad2.b)
            {
                led_rainbow = false;
                rainbow_state = false;
                start = true;
                sleep(250);
            }

            if(gamepad2.right_stick_button && !white_state)
            {
                led_orange = false;
                orange_state = false;
                led_blue = false;
                led_green = false;
                led_rainbow = false;
                green_state= false;
                blue_state = false;
                rainbow_state = false;
                white_state = true;
                led_white = true;
                sleep(250);
            }

            else if(gamepad2.right_stick_button)
            {
                white_state = false;
                led_white = false;
                sleep(250);
            }


            if(led_orange)
            {

                beast.LEDRed.setPower(1.0);
                beast.LEDGreen.setPower(0.03);
                beast.LEDBlue.setPower(0.0);

                telemetry.addData("LED", "orange");
                telemetry.update();
            }
            else if(led_blue)
            {
                beast.LEDRed.setPower(.01);
                beast.LEDGreen.setPower(0.0);
                beast.LEDBlue.setPower(1.0);

                telemetry.addData("LED", "blue");
                telemetry.update();
            }
            else if(led_green)
            {
                beast.LEDRed.setPower(.01);
                beast.LEDGreen.setPower(1.0);
                beast.LEDBlue.setPower(0.0);

                telemetry.addData("LED", "green");
                telemetry.update();
            }
            else if(led_white)
            {
                beast.LEDRed.setPower(1.0);
                beast.LEDGreen.setPower(1.0);
                beast.LEDBlue.setPower(1.0);

                telemetry.addData("LED", "white");
                telemetry.update();
            }
            else if(led_rainbow)
            {
                sleep(100);
                Range.clip(rainbow_red, 0.02, 1.0);
                Range.clip(rainbow_green, 0.02, 1.0);
                Range.clip(rainbow_blue, 0.02, 1.0);

                if(start)
                {
                    //sleep(2000);
                    rainbow_red = 1.0;
                    rainbow_green = 0.0;
                    rainbow_blue = 0.0;
                    beast.LEDRed.setPower(1.0);
                    beast.LEDGreen.setPower(0.0);
                    beast.LEDBlue.setPower(0.0);
                    start = false;
                    rainbowPhaseOne = true;
                    rainbowPhaseTwo = false;
                    rainbowPhaseThree = false;
                }

                if(rainbowPhaseOne && rainbow_red > 0.01)
                {
                    rainbow_red -= 0.01;
                    rainbow_green += 0.01;
                }
                else if(rainbowPhaseOne)
                {
                    rainbowPhaseOne = false;
                    rainbowPhaseTwo = true;
                }

                if(rainbowPhaseTwo && rainbow_green > 0.01)
                {
                    rainbow_green -= 0.01;
                    rainbow_blue += 0.01;
                }
                else if(rainbowPhaseTwo)
                {
                    rainbowPhaseTwo = false;
                    rainbowPhaseThree = true;
                }

                if(rainbowPhaseThree && rainbow_blue > 0.01)
                {
                    rainbow_blue -= 0.01;
                    rainbow_red += 0.01;
                }
                else if(rainbowPhaseThree)
                {
                    rainbowPhaseThree = false;
                    rainbowPhaseOne = true;
                }

                if(rainbow_red < .01)
                {
                    rainbow_red =+ .01;
                }
                if(rainbow_green < .01)
                {
                    rainbow_green =+ .01;
                }
                if(rainbow_blue < .01)
                {
                    rainbow_blue =+ .01;
                }

                beast.LEDRed.setPower(rainbow_red);
                beast.LEDGreen.setPower(rainbow_green);
                beast.LEDBlue.setPower(rainbow_blue);

                telemetry.addData("LED", "Rainbow");
                telemetry.addData("Rainbow Red", rainbow_red);
                telemetry.addData("Rainbow Green", rainbow_green);
                telemetry.addData("Rainbow Blue", rainbow_blue);
                telemetry.update();
            }
            else
            {
                beast.LEDRed.setPower(0.0);
                beast.LEDGreen.setPower(0.0);
                beast.LEDBlue.setPower(0.0);

                telemetry.addData("LED", "Dark");
                telemetry.update();
            }
        }

    }
}
