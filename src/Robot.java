import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class Robot extends MovePilot {
	
	Bumper bumper;
	Claws claws;
	Ultrasonic ultrasonic;

	public Robot() { 
		super(new WheeledChassis(	new Wheel[] { WheeledChassis.modelWheel(Motor.B, 5.3).offset(-5.8), 
												  WheeledChassis.modelWheel(Motor.C, 5.3).offset( 5.8) }, 
									WheeledChassis.TYPE_DIFFERENTIAL));
		this.bumper = new Bumper();
		this.claws = new Claws();
		this.ultrasonic = new Ultrasonic();
		
		this.setLinearAcceleration(100);
		this.setLinearSpeed(10);  // cm/sec
		this.setAngularSpeed(90); // deg/sec
	}
	
	public void close() {
		ultrasonic.close();
		bumper.close();
	}
}
