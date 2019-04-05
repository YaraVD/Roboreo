public class AI {

	Coord position;
	double orientation; // [-180 .. 180]
	Robot me;
	PalletsDetector detect;
	Coord [] coords;

	public AI(Robot robot) {
		this.me = robot;
		this.detect = new PalletsDetector();
		this.coords = new Coord[PalletsDetector.MAX_PALLETS];
		this.orientation = 180;
		this.position = new Coord(280,103);
	}

	public void test() {
		detect.update(coords);
		Coord maxx = coords[0];
		for (int i = 1; i<PalletsDetector.MAX_PALLETS; i++) {
			if (coords[i] != null && coords[i].x() > maxx.x()) {
				maxx = coords[i];
			}
		}
//				moveto(coords[i]);
//				returnto(new Coord(286, 80 - 10 * i));
		moveto(maxx);
		returnto();
//			}
//		}
	}

	void rotateto(double angle) {
		double new_angle = angle - this.orientation;
		if (new_angle > 180) new_angle -= 360;
		if (new_angle < -180) new_angle += 360;
		me.rotate(new_angle);
		this.orientation = angle;
		System.out.println("rotate(" + (int)new_angle + "); o=" + (int)this.orientation);
	}

	public void moveto(Coord target) {
		System.out.println("theta = " + this.position.theta(target));
		rotateto(this.position.theta(target));
		me.forward();
		while (!me.bumper.isPressed());
		me.stop();
		me.claws.grab();
		double thales = 13 / Math.sqrt(Math.pow(target.x() - this.position.x(), 2) + Math.pow(target.y() - this.position.y(), 2));
		this.position = new Coord(target.x() + (position.x() - target.x()) * thales, target.y() + (position.y() - target.y()) * thales);
	}

	public void returnto() {
		rotateto(180);
		me.backward();
		while (!me.colorsensor.detectColor().equals("white"));
		me.stop();
		rotateto(90);
		me.claws.release();
		me.travel(-10);
		this.position = new Coord(270 + 9, this.position.y() - 10);

	}
}
