package ex2.testing;

import ex2.geo.Point_2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point_2DTest {

    @Test
    void x() {
        double expectedX = 3.5;
        Point_2D point = new Point_2D(3.5, 0);
        assertEquals(expectedX, point.x(), "The x() method should return the X coordinate of the point.");
    }

    @Test
    void y() {
        double expectedY = -2.5;
        Point_2D point = new Point_2D(0, -2.5);
        assertEquals(expectedY, point.y(), "The y() method should return the Y coordinate of the point.");
    }

    @Test
    void ix() {
        double x = 3.9;
        int expectedIx = 3; // The integer part of 3.9 is 3
        Point_2D point = new Point_2D(x, 0);
        assertEquals(expectedIx, point.ix(), "The ix() method should return" +
                " the integer part of the X coordinate.");
    }

    @Test
    void iy() {
        double y = -2.1;
        int expectedIy = -2; // The integer part of -2.1 is -2
        Point_2D point = new Point_2D(0, y);
        assertEquals(expectedIy, point.iy(), "The iy() method should return" +
                " the integer part of the Y coordinate.");
    }

    @Test
    void set_x() {
        Point_2D point = new Point_2D(1.0, 2.0);
        double newX = 3.0;
        point.set_x(newX);
        assertEquals(newX, point.x(), "The x-coordinate should be updated to the new value.");
    }

    @Test
    void set_y() {
        Point_2D point = new Point_2D(1.0, 2.0);
        double newY = 4.0;
        point.set_y(newY);
        assertEquals(newY, point.y(), "The y-coordinate should be updated to the new value.");
    }

    @Test
    void add() {
        Point_2D point1 = new Point_2D(1.0, 2.0);
        Point_2D point2 = new Point_2D(3.0, 4.0);
        Point_2D result = point1.add(point2);
        Point_2D expected = new Point_2D(4.0, 6.0);
        assertEquals(expected.x(), result.x(), "The x-coordinate of the result" +
                " should be the sum of the x-coordinates of the two points.");
        assertEquals(expected.y(), result.y(), "The y-coordinate of the result" +
                " should be the sum of the y-coordinates of the two points.");
    }

    @Test
    void ToString() {
        Point_2D point = new Point_2D(1.5, 2.5);
        String expected = "1.5,2.5";
        assertEquals(expected, point.toString(), "The toString method should match");
    }

    @Test
    void distanceToOrigin() {
        Point_2D point = new Point_2D(3, 4);
        double expectedDistance = 5.0; // Based on the 3-4-5 Pythagorean triplet
        assertEquals(expectedDistance, point.distance(), 0.001, "The distance to origin is incorrect.");
    }

    @Test
    void distance() {
        Point_2D point1 = new Point_2D(1, 2);
        Point_2D point2 = new Point_2D(4, 6);
        double expectedDistance = 5.0; // The distance between the points is based on the Pythagorean theorem
        assertEquals(expectedDistance, point1.distance(point2), 0.001, "The distance" +
                " between the points is incorrect.");
    }

    @Test
    void testEquals() {
        Point_2D point1 = new Point_2D(1.0, 2.0);
        Point_2D point2 = new Point_2D(1.0, 2.0);
        Point_2D point3 = new Point_2D(2.0, 3.0);

        assertTrue(point1.equals(point2), "Points with the same coordinates should be considered equal.");
        assertFalse(point1.equals(point3), "Points with different coordinates should not be considered equal.");
    }

    @Test
    void close2equals() {
        Point_2D point1 = new Point_2D(1.0, 2.0);
        Point_2D point2 = new Point_2D(1.0, 2.01);
        Point_2D point3 = new Point_2D(1.0, 2.1);

        assertTrue(point1.close2equals(point2, 0.05), "Points within the epsilon" +
                " range should be considered close.");
        assertFalse(point1.close2equals(point3, 0.05), "Points outside the epsilon" +
                " range should not be considered close.");
    }

    @Test
    void vector() {
        Point_2D point1 = new Point_2D(1, 1);
        Point_2D target = new Point_2D(4, 5);
        Point_2D expectedVector = new Point_2D(3, 4);

        Point_2D actualVector = point1.vector(target);
        assertEquals(expectedVector.x(), actualVector.x(), 0.001, "X-component of vector" +
                " should match expected value.");
        assertEquals(expectedVector.y(), actualVector.y(), 0.001, "Y-component of vector" +
                " should match expected value.");
    }

    @Test
    void move() {
        Point_2D point = new Point_2D(1, 1);
        Point_2D vec = new Point_2D(2, 3);
        point.move(vec);
        assertEquals(new Point_2D(3, 4).x(), point.x(), "After moving, x coordinate" +
                " should be updated correctly.");
        assertEquals(new Point_2D(3, 4).y(), point.y(), "After moving, y coordinate" +
                " should be updated correctly.");
    }

    @Test
    void scale() {
        Point_2D point = new Point_2D(2, 2);
        Point_2D cen = new Point_2D(1, 1);
        double ratio = 2.0;
        point.scale(cen, ratio);
        assertEquals(new Point_2D(3, 3).x(), point.x(), "After scaling, x coordinate" +
                " should be updated correctly.");
        assertEquals(new Point_2D(3, 3).y(), point.y(), "After scaling, y coordinate" +
                " should be updated correctly.");
    }

    @Test
    void rotate() {
        Point_2D point = new Point_2D(1, 0);
        Point_2D cen = new Point_2D(0, 0);
        double angleDegrees = 90;
        point.rotate(cen, angleDegrees);
        // Expected to be close to (0,1) after a 90-degree rotation
        assertTrue(Math.abs(point.x() - 0) < 0.001, "After rotation, x coordinate should be close to 0.");
        assertTrue(Math.abs(point.y() - 1) < 0.001, "After rotation, y coordinate should be close to 1.");
    }
}