package ex2.geo;

/**
 * This class represents a 2D segment on the plane.
 * @author Roy Naor
 * ID - 323917104
 */
public class Segment_2D implements GeoShape{
	private Point_2D a, b;
	private final Point_2D originalA;
	private final Point_2D originalB;
	private int negativeOneScaleCount = 0;
	public Segment_2D(Point_2D a, Point_2D b) {
		this.a = new Point_2D(a);
		this.b = new Point_2D(b);
		this.originalA = new Point_2D(a);
		this.originalB = new Point_2D(b);
	}
	public Segment_2D(Segment_2D t1) {this(t1.a, t1.b);}


	public Point_2D get_p1() {return this.a;}
	public Point_2D get_p2() {return this.b;}

	/** -contains-
	 * check if a Point (x,y) is on the segment
	 * @param ot - a query 2D point
	 * @return True if the Point is on and False if not
	 */
	@Override
	public boolean contains(Point_2D ot) {
		if (this.a.distance(ot) + this.b.distance(ot) - this.a.distance(this.b) < 0.01)
			return true;
		return false;
	}

	/** -area-
	 * has no area
	 * @return 0
	 */
	@Override
	public double area() {
		return 0;
	}

	/** -perimeter-
	 *   twice the distance from a to b
	 * @return the value of the perimeter
	 */
	@Override
	public double perimeter() {
		return a.distance(b) * 2;
	}

	/** -translate-
	 * Move the segment by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {a.move(vec); b.move(vec);}

	/** -copy-
	 * Computes a new (deep) copy of this segment
	 * @return a new segment object with the same properties
	 */
	@Override
	public GeoShape copy() {
		return new Segment_2D(new Point_2D(this.a), new Point_2D(this.b));
	}

	/** -scale-
	 * Rescales the segment with respect to the given center point
	 * @param center - center point from which the rescaling is being done.
	 * @param ratio - the ratio of rescaling.
	 */
	@Override
	public void scale(Point_2D center, double ratio) {
		if (ratio == 1) {return;} // exit the method

		if (ratio == -1) {
			negativeOneScaleCount++;
			if (negativeOneScaleCount == 2) {
				// Restore to original scale and reset counter
				this.a = originalA;
				this.b = originalB;
				negativeOneScaleCount = 0;
				return;
			}
		}
		// Reset the counter if -1 is not twice in a row
		else {negativeOneScaleCount = 0;}

		// scaling
		a.scale(center, ratio);
		b.scale(center, ratio);
	}

	/** -rotate-
	 * Rotates the segment with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		a.rotate(center, angleDegrees);
		b.rotate(center, angleDegrees);
	}

	/** -toString-
	 * This method returns a String representing this shape,
	 * such that one can use this string for saving the shape into a text file.
	 * @return a String representing this shape
	 */
	@Override
	public String toString() {return a + ", " + b;}
}