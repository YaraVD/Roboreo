public class Coord {

	private double x;
	private double y;

	public Coord(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public Coord add(Coord a) {
		return new Coord(this.x + a.x(), this.y + a.y());
	}

	public Coord sub(Coord a) {
		return new Coord(this.x - a.x(), this.y - a.y());
	}

	public Coord clone() {
		return new Coord(this.x, this.y);
	}

	@Override
	public boolean equals(Object o) {
		Coord c = (Coord) o;
		return (this.x == c.x) && (this.y == c.y);
	}

	@Override
	public String toString() {
		return "(" + this.x + ";" + this.y + ")";
	}

	public double theta(Coord c) {
	    return Math.toDegrees(Math.atan2(this.y - c.y(), c.x() - this.x));
	}

}
