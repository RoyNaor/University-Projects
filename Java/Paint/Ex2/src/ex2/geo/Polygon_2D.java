package ex2.geo;

import java.util.ArrayList;

public class Polygon_2D implements GeoShape{
	private ArrayList<Point_2D>	 vertex;
	//private final ArrayList<Point_2D> originalVertex;
	private double negativeOneScaleCount;

	public Polygon_2D() {this.vertex = new ArrayList<>();}

	/** -Polygon_2D-
	 * Creates a new object of Polygon_2D that is a copy of the specified Polygon_2D object.
	 * creates a new object with the same properties.
	 * @param po - the Polygon object to copy
	 */
	public Polygon_2D(Polygon_2D po) {this.vertex = new ArrayList<>(po.vertex);}

	/** -getAllPoints-
	 * @return a new Points array with all the vertex points of the polygon
	 */
	public Point_2D[] getAllPoints() {
		Point_2D[] pointArray = new Point_2D[this.vertex.size()];
		for (int i = 0; i < this.vertex.size(); i++) {
			pointArray[i] = this.vertex.get(i);
		}
		return pointArray;
	}

	/** -add-
	 * Add new vertex to the List
	 * @param p a Point_2D point that becoming a vertex in the current polygon
	 */
	public void add(Point_2D p) {this.vertex.add(p);} //this.originalVertex.add(p);}

	/** -toString-
	 * This method returns a String representing this shape,
	 * such that one can use this string for saving the shape into a text file.
	 * @return a String representing this shape
	 */
	@Override
	public String toString() {return vertex.toString();}

	/** -contains-
	 * checking if a Point2D is in the polygon borders using "The Ray Casting" formula
	 * @param ot - a query 2D point
	 * @return boolean True if the Point2D is in and False if not
	 */
	@Override
	public boolean contains(Point_2D ot) {
		double x = ot.x();
		double y = ot.y();
		boolean isCrossed = false;
		for (int i = 0; i < vertex.size(); i++) {
			// Get the x and y coordinates of the current vertex
			double ax = vertex.get(i).x();
			double ay = vertex.get(i).y();
			// Get the x and y coordinates of the next vertex
			double bx = vertex.get((i + 1) % vertex.size()).x();
			double by = vertex.get((i + 1) % vertex.size()).y();
			// Check if the point coincides with a vertex (corner) of the polygon
			if (x == ax && y == ay) {
				return true; // Point is a corner
			}
			// Check if the y-coordinate of the point is between the y-coordinates of the segment's endpoints
			if ((ay > y) != (by > y)) {
				double slope = (x - ax) * (by - ay) - (bx - ax) * (y - ay);
				if (slope == 0) {
					return true; // Point is on boundary
				}
				if ((slope < 0) != (by < ay)) {
					isCrossed = !isCrossed;
				}
			}
		}
		return isCrossed;
	}

	/** -area-
	 * calculates the area of the polygon using the Shoelace formula.
	 * @return the value of area
	 */
	@Override
	public double area() {
		double area = 0.0;
		// Calculate the area using the Shoelace formula
		for (int i = 0; i < vertex.size(); i++) {
			// Current vertex
			double x1 = vertex.get(i).x();
			double y1 = vertex.get(i).y();

			// Next vertex
			double x2 = vertex.get((i + 1) % vertex.size()).x();
			double y2 = vertex.get((i + 1) % vertex.size()).y();

			area += (x1 * y2) - (x2 * y1);
		}
		return Math.abs(area / 2.0);
	}

	/** -perimeter-
	 *  sum the vectors created from all the vertex in the polygon
	 * @return the value of the perimeter
	 */
	@Override
	public double perimeter() {
		double totalP = 0;
		for (int i = 0; i < vertex.size() - 1; i++) {
			totalP += vertex.get(i + 1).distance(vertex.get(i));
		}

		return totalP;
	}

	/** -translate-
	 * Move the polygon by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {
		for (int i = 0; i < vertex.size(); i++) {
			vertex.get(i).move(vec);
		}
	}


	/** -copy-
	 * Computes a new (deep) copy of this polygon
	 * @return a new polygon object with the same properties
	 */
	@Override
	public GeoShape copy() {
		Polygon_2D copyPolygon = new Polygon_2D();
		for (Point_2D vertex : this.vertex) {
			copyPolygon.add(new Point_2D(vertex));
		}
		return copyPolygon;
	}


	/** -scale-
	 * Rescales the polygon with respect to the given center point
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
				//this.vertex = originalVertex;
				negativeOneScaleCount = 0;
				return;
			}
		}
		// Reset the counter if -1 is not twice in a row
		else {negativeOneScaleCount = 0;}

		// scaling
		for (int i = 0; i < vertex.size(); i++) {
			vertex.get(i).scale(center, ratio);
		}
	}

	/** -rotate-
	 * Rotates the polygon with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		for (int i = 0; i < vertex.size(); i++) {
			vertex.get(i).rotate(center, angleDegrees);
		}
	}

}
