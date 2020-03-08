package com.commitpro.apps.inverter;

import java.util.concurrent.TimeUnit;

public class HFTrafo {

    private static float MAX_VOLTAGE = 1.4f;
    private static int GPIO_HIGH = 1;
    private static int GPIO_LOW = 0;
    private static int PIN_24 = 24;
    private static int PIN_25 = 25;
    private static int CHANEL_NR_7 = 7;
    private static long TIME_TO_WAIT_25 = 25;
    private static long TIME_TO_WAIT_2 = 2;


    public static boolean startHFTrafo() throws InterruptedException {
        float voltage = 0; //just for tests !!!
        while (getVoltage(CHANEL_NR_7, voltage) <= MAX_VOLTAGE) {
            runCircle(PIN_24);
            runCircle(PIN_25);
            voltage += 0.1;
        }
        return true;
    }

    private static void runCircle(int pin) throws InterruptedException {
        switchFet(pin, GPIO_HIGH);
        TimeUnit.MICROSECONDS.sleep(TIME_TO_WAIT_25);

        switchFet(pin, GPIO_LOW);
        TimeUnit.MICROSECONDS.sleep(TIME_TO_WAIT_2);
    }

    private static void switchFet(int pinNumber, int gpio) {

        if (gpio == GPIO_HIGH) {
            //Rasperry commands to switch on I/O
            System.out.println("Switch on pin " + pinNumber + ".");
        } else if (gpio == GPIO_LOW) {
            //Rasperry commands to switch off I/O
            System.out.println("Switch off pin " + pinNumber + ".");
        } else {
            System.out.println("Unvalid GPIO signal " + gpio + "!");
        }

    }

    /*
    float voltage parameter is only for tests !!! Remove it for production.
     */
    private static float getVoltage(int chanelNumber, float voltage) {
        //Rasperry commands to get Voltage
        //float voltage = 1.126f;//this comes from rasperry - just for tests
        System.out.println("Current Voltage of chanelNumber " + chanelNumber + " is " + voltage);
        return voltage;
    }
}
