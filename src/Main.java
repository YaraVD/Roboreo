import lejos.hardware.motor.Motor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class Main {
		
	public static void main(String[] args ) throws Exception {
				
		Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 5.3).offset(-5.8);
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 5.3).offset(5.8);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		
		MovePilot robot = new MovePilot(chassis);		
		robot.setLinearAcceleration(2000);
		robot.setLinearSpeed(100);  // cm/sec
		robot.setAngularSpeed(90); // deg/sec
		
		EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S3);
		sensor.enable();
		
//		Button.waitForAnyPress();
	   
//		robot.forward();
//		while (getDist(sensor) > 0.2);
//		robot.stop();
//		while (getDist(sensor) < 0.40) {
		for (int i=0;i<4;i++)	
			robot.rotate(-360);
//		}
//		robot.stop();
		
		
		sensor.close();
	}
	
	static float getDist(EV3UltrasonicSensor sensor) {
		SampleProvider res = sensor.getDistanceMode();
		float [] sample = new float[res.sampleSize()];
		res.fetchSample(sample, 0);
		return sample[0];
	}
}