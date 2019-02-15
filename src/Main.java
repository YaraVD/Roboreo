import lejos.hardware.Button;

public class Main {

	public static void main(String[] args ) throws Exception {

		Robot robot = new Robot();
		PalletsDetector palletsDetector = new PalletsDetector();
		palletsDetector.start();
		
		System.out.println("Wait for any press to exit");
		Button.waitForAnyPress();
		System.out.println("Hum..");
		

		// robot.claws.release();
		// robot.forward();
		// while(!robot.bumper.isPressed() && robot.ultrasonic.distance() >= 0.15);
		// robot.stop();
		// robot.claws.grab();

		palletsDetector.end();
		robot.close();
	}
}
