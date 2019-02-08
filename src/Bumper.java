import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;

public class Bumper extends EV3TouchSensor {

	public Bumper() {
		super(SensorPort.S2);
	}
	
	public boolean isPressed() {
		float [] sample = new float[this.sampleSize()];
		this.fetchSample(sample, 0);
		return sample[0] == 1;
	}
}
