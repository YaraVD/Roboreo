import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.MotorPort;
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
		robot.setLinearAcceleration(100);
		robot.setLinearSpeed(10);  // cm/sec
		robot.setAngularSpeed(90); // deg/sec
		
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
				
		EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S3);
		sensor.enable();

//		EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.D);
		
//		Button.waitForAnyPress();
	   
//		robot.forward();
//		while (getDist(sensor) > 0.2);
//		robot.stop();
//		while (getDist(sensor) < 0.40) {
//		for (int i=0;i<4;i++)	
//			robot.rotate(-360);
//		}
//		robot.stop();
		
		// OUVRE
		Motor.D.setSpeed(500);
		Motor.D.forward();
		Delay.msDelay(4000);
		Motor.D.stop();
		
		//AVANCE JUSQU'A BUMPER
		robot.forward();
		while(isPressed(touchSensor) != 1);
		robot.stop();
		
		//FERME
		Motor.D.setSpeed(500);
		Motor.D.backward();
		Delay.msDelay(4000);
		Motor.D.stop();
		System.out.println(Motor.D.getPosition());
		
				
		sensor.close();
	}
	
	static int isPressed(EV3TouchSensor sensor) {
		float [] sample = new float[sensor.sampleSize()];
		sensor.fetchSample(sample, 0);
		return (int) sample[0];
	}
	
	static float getDist(EV3UltrasonicSensor sensor) {
		SampleProvider res = sensor.getDistanceMode();
		float [] sample = new float[res.sampleSize()];
		res.fetchSample(sample, 0);
		return sample[0];
	}
}