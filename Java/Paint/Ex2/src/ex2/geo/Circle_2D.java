package ex2.geo;

/**
 * This class represents a 2D circle in the plane.
 * @author Roy Naor
 * ID - 323917104
 */
public class Circle_2D implements GeoShape{
	private Point_2D _center;
	private double _radius;

	private final double originalRadius;
	private final Point_2D originalCenter;
	private int negativeOneScaleCount = 0;

	public Circle_2D(Point_2D center, double radius) {
		this._center = new Point_2D(center);
		this._radius = radius;
		this.originalCenter = center;
		this.originalRadius = radius;
	}
	public Circle_2D(Circle_2D c) {
		this(c.getCenter(), c.getRadius());
	}
	public double getRadius() {return this._radius;}
	public Point_2D getCenter(){ return _center;}


	/** -toString-
	 * This method returns a String representing this shape,
	 * such that one can use this string for saving the shape into a text file.
	 * @return a String representing this shape
	 */
	@Override
	public String toString() {return _center.toString()+", "+_radius;}


	/** -contains-
	 * check if a Point (x,y) is in the area of the circle
	 * @param ot - a query 2D point
	 * @return True if the Point is in the circle and False if not
	 */
	@Override
	public boolean contains(Point_2D ot) {
		if (_center.distance(ot) - _radius > 0)
			return false;
		return true;
	}

	/** -area-
	 * multiply the radius in the power of 2 with PI
	 * @return the value of the mul (the area of the circle)
	 */
	@Override
	public double area() {return Math.PI * Math.pow(_radius, 2);}

	/** -perimeter-
	 *  mul of Pi, 2, radius
	 * @return the value of the perimeter
	 */
	@Override
	public double perimeter() {return 2 * Math.PI * _radius;}


	/** -translate-
	 * Move the circle by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {_center.move(vec);}

	/** -copy-
	 * Computes a new (deep) copy of this circle
	 * @return a new circle object with the same properties
	 */
	@Override
	public GeoShape copy() {return new Circle_2D(this);}


	/** -scale-
	 * Rescales the circle with respect to the given center point
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
				this._radius = originalRadius;
				this._center = originalCenter;
				negativeOneScaleCount = 0;
				return;
			}
		}
		// Reset the counter if -1 is not twice in a row
		else {negativeOneScaleCount = 0;}

		// scaling
		_center.scale(center, ratio);
		_radius *= ratio;
	}


	/** -rotate-
	 * Rotates the circle with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		this._center.rotate(center,angleDegrees);
	}

}
