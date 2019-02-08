import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Ultrasonic extends EV3UltrasonicSensor {

	public Ultrasonic() {
		super(SensorPort.S3);
	}
	
	public float distance( ) {
		float [] sample = new float[this.sampleSize()];
		this.fetchSample(sample, 0);
		return sample[0];
	}
}
