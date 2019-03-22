import lejos.hardware.Button;

public class Main {

	public static void main(String[] args ) throws Exception {

		Robot robot = new Robot();
		AI ai = new AI(robot);

		System.out.println("Start");

		ai.test();
		System.out.println("End");
		Button.waitForAnyPress();


//		robot.claws.release();
//		robot.forward();
//		while(!robot.bumper.isPressed() && robot.ultrasonic.distance() >= 0.15);
//		robot.stop();
//		robot.claws.grab();

		robot.close();
	}
}
