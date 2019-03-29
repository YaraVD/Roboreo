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
		this.position = new Coord(278,108);

	}

	public void test() {
		detect.update(coords);
		Coord maxx = coords[0];
		for (int i = 0; i<PalletsDetector.MAX_PALLETS; i++) {
			if (coords[i] != null && coords[i].x() > maxx.x()) {
				maxx = coords[i];
			}
		}
//				moveto(coords[i]);
//				returnto(new Coord(286, 80 - 10 * i));
				moveto(maxx);
				returnto(new Coord(286, 107));
//			}
//		}
	}

	public void moveto(Coord target) {
		double angle = this.position.theta(target) + this.orientation;
		if (angle >= 180) angle -= 360;
		me.rotate(angle);
		this.orientation = (this.orientation - angle);
		if (this.orientation > 180) this.orientation -= 360;
		System.out.println(target + "\na: " + angle + "\no:" + this.orientation);
		me.forward();
		while (!me.bumper.isPressed());
		me.stop();
		this.position = target;
		me.travel(13);
		me.claws.grab();
	}
	
	public void returnto(Coord target) {
		double angle = this.position.theta(target) + this.orientation;
		if (angle >= 180) angle -= 360;
		me.rotate(angle);
		this.orientation = (this.orientation - angle);
		if (this.orientation > 180) this.orientation -= 360;
		System.out.println(target + "\na: " + angle + "\no:" + this.orientation);

		double distance = Math.sqrt(Math.pow(target.x() - this.position.x(), 2) + Math.pow(target.y() - this.position.y(), 2) );
		System.out.println("travel : " + distance);
		//me.travel(distance);
		me.forward();
		while(!me.colorsensor.detectColor().equals("white"));
		me.stop();
		me.travel(13);
		me.claws.release();
		me.travel(-10);
		me.rotate(180);
		me.travel(-10);
		this.position = target;
		this.orientation = (this.orientation + 180);
		if (this.orientation > 180) this.orientation -= 360;
		
	}
}
