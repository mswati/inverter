package com.commitpro.apps.inverter;

public class Inverter {

    public static void main(String[] args) throws InterruptedException {
        final boolean maxVoltageReached = HFTrafo.startHFTrafo();
        if (maxVoltageReached) {
            Wechselrichter.startWechselrichter();
        }
    }
}
