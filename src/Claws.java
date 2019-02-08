import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class Claws {

	public Claws() {
		Motor.D.setSpeed(500);
	}
	
	public void release() {
		Motor.D.forward();
		Delay.msDelay(3500);
		Motor.D.stop();
	}
	
	public void grab() {
		Motor.D.backward();
		Delay.msDelay(3500);
		Motor.D.stop();
	}
	
	

}
