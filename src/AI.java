import lejos.hardware.Button;

public class AI {

	Coord position;
	double orientation;
	Robot me;
	PalletsDetector detect;
	Coord [] coords;

	public AI(Robot robot) {
		this.me = robot;
		this.detect = new PalletsDetector();
		this.coords = new Coord[PalletsDetector.MAX_PALLETS];
		this.orientation = 180;
		this.position = new Coord(286,91);
		
	}

	public void test() {
		detect.update(coords);
		for (int i = 0; i<PalletsDetector.MAX_PALLETS; i++)
			if (coords[i] != null) {
				moveto(coords[i]);
			}
		
	}
	
	public void moveto(Coord target) {
		double theta = this.position.theta(target) + this.orientation;
		double angle = theta;
		if (angle >= 180) angle-=360;
		me.rotate(angle);
		this.orientation = (this.orientation - angle) % 360;
		System.out.println(target + "\na: " + angle + "\no:" + this.orientation);
	}
}
