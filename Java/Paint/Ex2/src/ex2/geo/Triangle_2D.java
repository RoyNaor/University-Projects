package ex2.geo;

/**
 * This class represents a 2D Triangle in the plane.
 * @author Roy Naor
 * ID - 323917104
 */
public class Triangle_2D implements GeoShape{
	private Point_2D p1, p2, p3;
	private final Point_2D originalP1;
	private final Point_2D originalP2;
	private final Point_2D originalP3;
	public int negativeOneScaleCount = 0;
	public double height;

	public Triangle_2D(Point_2D p1, Point_2D p2, Point_2D p3) {
		this.p1 = new Point_2D(p1);
		this.p3 = new Point_2D(p3);
		this.p2 = new Point_2D(p2);
		this.originalP1 = new Point_2D(p1.x(), p1.y());
		this.originalP2 = new Point_2D(p2.x(), p2.y());
		this.originalP3 = new Point_2D(p3.x(), p3.y());

		calculateHeight();
	}

	/** -calculateHeight-
	 * calculate the height using the top and right Points
	 */
	public void calculateHeight(){this.height = Math.abs((p1.y() - p2.y()));}

	/** -Triangle_2D-
	 * Creates a new object of Triangle_2D that is a copy of the specified Triangle_2D object.
	 * creates a new object with the same properties.
	 * @param t1 - the Triangle_2D object to copy
	 */
	public Triangle_2D(Triangle_2D t1) {this(t1.p1, t1.p2, t1.p3);}

	/** -getAllPoints-
	 * @return a new Points array with the Top, right and left points of the triangle
	 */
	public Point_2D[] getAllPoints() {return new Point_2D[] {p1, p2, p3};}

	/** -contains-
	 * checking if a Point2D is in the triangles borders using "The barycentric coordinates" formula
	 * @param ot - a query 2D point
	 * @return boolean True if the Point2D is in and False if not
	 */
	@Override
	public boolean contains(Point_2D ot) {
		double areaTotal, area1, area2, area3;
		areaTotal = this.area();

		area1 = Math.abs((ot.x() - p3.x()) * (p2.y() - p3.y()) -
				(ot.y() - p3.y()) * (p2.x() - p3.x())) / areaTotal;
		area2 = Math.abs((p1.x() - ot.x()) * (p2.y() - ot.y()) -
				(p1.y() - ot.y()) * (p2.x() - ot.x())) / areaTotal;
		area3 = 1 - area1 - area2;

		return area1 >= 0 && area1 <= 1 && area2 >= 0 && area2 <= 1 && area3 >= 0 && area3 <= 1;
	}

	/** -area-
	 * calculates the area of the triangle using the coordinates of its vertices.
	 * @return the value of the mul (the area of the triangle)
	 */
	@Override
	public double area() {return Math.abs((p1.x() - p3.x()) *
			(p2.y() - p3.y()) - (p1.y() - p3.y()) * (p2.x() - p3.x()));}

	/** -perimeter-
	 *  sum the 3 vectors created from the 3 Points
	 * @return the value of the perimeter
	 */
	@Override
	public double perimeter() {
		double distance1 = p1.distance(p2);
		double distance2 = p1.distance(p3);
		double distance3 = p3.distance(p2);
		return distance1 + distance2 + distance3;
	}

	/** -translate-
	 * Move the triangle by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {
		this.p1.move(vec);
		this.p3.move(vec);
		this.p2.move(vec);
	}

	/** -copy-
	 * Computes a new (deep) copy of this triangle
	 * @return a new triangle object with the same properties
	 */
	@Override
	public GeoShape copy() {return new Triangle_2D(this);}

	/** -scale-
	 * Rescales the triangle with respect to the given center point
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
				this.p1 = originalP1;
				this.p2 = originalP2;
				this.p3 = originalP3;
				negativeOneScaleCount = 0;
				return;
			}
		}
		// Reset the counter if -1 is not twice in a row
		else {negativeOneScaleCount = 0;}

		// scaling
		p1.scale(center, ratio);
		p2.scale(center, ratio);
		p3.scale(center, ratio);
		calculateHeight();
	}

	/** -rotate-
	 * Rotates the triangle with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		this.p1.rotate(center, angleDegrees);
		this.p2.rotate(center, angleDegrees);
		this.p3.rotate(center, angleDegrees);
	}

	/** -toString-
	 * This method returns a String representing this shape,
	 * such that one can use this string for saving the shape into a text file.
	 * @return a String representing this shape
	 */
	@Override
	public String toString() {return p1 + ", " + p2 + ", " + p3;}
}
