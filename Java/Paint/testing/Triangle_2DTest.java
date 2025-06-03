package ex2.testing;

import ex2.geo.Point_2D;
import ex2.geo.Triangle_2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Triangle_2DTest {

    @Test
    void getAllPoints() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(1, 0);
        Point_2D p3 = new Point_2D(0, 1);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);

        Point_2D[] points = triangle.getAllPoints();

        assertArrayEquals(new Point_2D[]{p1, p2, p3}, points, "getAllPoints() method failed" +
                " to return the correct points.");
    }

    @Test
    void contains() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(2, 0);
        Point_2D p3 = new Point_2D(1, 2);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);
        Point_2D insidePoint = new Point_2D(1, 1); // Inside the triangle
        Point_2D outsidePoint = new Point_2D(2, 2); // Outside the triangle

        assertTrue(triangle.contains(insidePoint), "contains() method failed for a point inside the triangle.");
        assertFalse(triangle.contains(outsidePoint), "contains() method failed for a point outside the triangle.");
    }

    @Test
    void area() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(2, 0);
        Point_2D p3 = new Point_2D(1, 2);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);
        double expectedArea = 4.0;
        double actualArea = triangle.area();

        assertEquals(expectedArea, actualArea, "area() method failed to calculate the correct area.");
    }

    @Test
    void perimeter() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(4, 0);
        Point_2D p3 = new Point_2D(0, 3);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);
        double expectedPerimeter = 12;

        double actualPerimeter = triangle.perimeter();

        assertEquals(expectedPerimeter, actualPerimeter, 0.01, "perimeter() method failed" +
                " to calculate the correct perimeter.");
    }

    @Test
    void translate() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(1, 0);
        Point_2D p3 = new Point_2D(0, 1);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);
        Point_2D translationVector = new Point_2D(1, 1); // Move the triangle 1 right and 1 up
        Point_2D[] expectedPoints = {
                new Point_2D(1, 1),
                new Point_2D(2, 1),
                new Point_2D(1, 2)
        };
        triangle.translate(translationVector);

        Point_2D[] translatedPoints = triangle.getAllPoints();
        assertArrayEquals(expectedPoints, translatedPoints, "The translated points" +
                " do not match the expected positions.");
    }

    @Test
    void copy() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(1, 0);
        Point_2D p3 = new Point_2D(0, 1);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);

        Triangle_2D copiedTriangle = (Triangle_2D) triangle.copy();
        Point_2D[] originalPoints = triangle.getAllPoints();
        Point_2D[] copiedPoints = copiedTriangle.getAllPoints();

        assertNotSame(triangle, copiedTriangle, "The copied triangle" +
                " should not be the same instance as the original.");
        assertArrayEquals(originalPoints, copiedPoints, "The points of the copied triangle" +
                " do not match the original.");
    }


    @Test
    void scale() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(2, 0);
        Point_2D p3 = new Point_2D(1, 1);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);
        Point_2D center = new Point_2D(1, 0); // Center of scaling
        double ratio = 2;

        Point_2D expectedP1 = new Point_2D(-1, 0);
        Point_2D expectedP2 = new Point_2D(3, 0);
        Point_2D expectedP3 = new Point_2D(1, 2);

        triangle.scale(center, ratio);

        Point_2D[] scaledPoints = triangle.getAllPoints();
        assertArrayEquals(new Point_2D[]{expectedP1, expectedP2, expectedP3}, scaledPoints,
                "The scaled points do not match the expected positions.");
    }

    @Test
    void scaleRatioNegativeOne() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(2, 0);
        Point_2D p3 = new Point_2D(1, 1);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);
        Triangle_2D copiedTriangle = (Triangle_2D) triangle.copy();
        Point_2D center = new Point_2D(1, 0);
        double ratio = -1;

        triangle.scale(center, ratio);
        triangle.scale(center, ratio);

        Point_2D[] finalPoints = triangle.getAllPoints();
        Point_2D[] copiedPoints = copiedTriangle.getAllPoints();

        for (int i = 0; i < finalPoints.length; i++) {
            assertEquals(copiedPoints[i], finalPoints[i], "Point " + (i + 1) +
                    " did not return to its original value after scaling twice with -1.");
            assertNotSame(copiedPoints[i], finalPoints[i], "Point " + (i + 1) +
                    " is still the same reference after scaling, indicating not a deep copy.");
        }
    }

    @Test
    void rotate() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(1, 0);
        Point_2D p3 = new Point_2D(0, 1);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);
        Point_2D center = new Point_2D(0.5, 0.5); // Center point for rotation
        double angleDegrees = 90; // Rotate 90 degrees

        // Expected vertices after a 90-degree rotation around the center (0.5, 0.5)
        Point_2D expectedP1 = new Point_2D(1.0, 0.0); // Original point (0,0) rotated
        Point_2D expectedP2 = new Point_2D(1.0, 1.0); // Original point (1,0) rotated
        Point_2D expectedP3 = new Point_2D(-1.1102230246251565E-16,0.0);  // Original point (0,1) rotated

        triangle.rotate(center, angleDegrees);

        // Assert
        Point_2D[] rotatedPoints = triangle.getAllPoints();
        // Adjust expected values based on your rotation logic and origin
        assertArrayEquals(new Point_2D[]{expectedP1, expectedP2, expectedP3}, rotatedPoints,
                "The rotated points do not match the expected positions.");
    }


    @Test
    void testToString() {
        Point_2D p1 = new Point_2D(0, 0);
        Point_2D p2 = new Point_2D(1, 0);
        Point_2D p3 = new Point_2D(0, 1);
        Triangle_2D triangle = new Triangle_2D(p1, p2, p3);

        // Expected string representation of the triangle
        String expected = "0.0,0.0, 1.0,0.0, 0.0,1.0";

        String result = triangle.toString();

        // Assert
        assertEquals(expected, result, "toString() method does not return the expected format.");
    }

}