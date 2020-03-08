package src.com.commitpro.apps.inverter;

public class Inverter {

    public static void main(String[] args) throws InterruptedException {
        boolean maxVoltageReached = HFTrafo.startHFTrafo();
        if (maxVoltageReached) {
            Wechselrichter.startWechselrichter();
        }
    }
}
