package ex2.testing;

import ex2.geo.Point_2D;
import ex2.geo.Rect_2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Rect_2DTest {

    @Test
    void getP1() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        Point_2D expected = new Point_2D(0, 2);

        assertEquals(rect.getP1(),expected, "getP1() method failed to return the correct point.");
    }

    @Test
    void getP2() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        Point_2D expected = new Point_2D(2, 0);

        assertEquals(rect.getP2(),expected, "getP2() method failed to return the correct point.");
    }

    @Test
    void getP3() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        Point_2D expected = new Point_2D(2, 2);

        assertEquals(expected, rect.getP3(), "getP3() method failed to return the correct point.");
    }

    @Test
    void getP4() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        Point_2D expected = new Point_2D(0, 0);

        assertEquals(expected, rect.getP4(), "getP4() method failed to return the correct point.");
    }

    @Test
    void contains() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        Point_2D insidePoint = new Point_2D(1, 1);
        Point_2D outSide = new Point_2D(3, 3);

        assertTrue(rect.contains(insidePoint), "contains() method failed " +
                "for a point inside the rectangle.");
        assertFalse(rect.contains(outSide), "contains() method failed " +
                "for a point outside the rectangle.");
    }

    @Test
    void area() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);    // should be 4
        assertEquals(4, rect.area(), "area() method failed to calculate the correct area.");
    }

    @Test
    void perimeter() {
        Point_2D p1 = new Point_2D(0, 4);
        Point_2D p2 = new Point_2D(4, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        // The perimeter of this rectangle should be 2*(width + height) = 2*(4+4) = 16
        double expectedPerimeter = 16;
        double actualPerimeter = rect.perimeter();

        assertEquals(expectedPerimeter, actualPerimeter, "perimeter() method failed" +
                " to calculate the correct perimeter.");
    }

    @Test
    void translate() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        Point_2D translationVector = new Point_2D(1, 1); // Move the rectangle 1 right and 1  up
        Point_2D expectedP1 = new Point_2D(1, 3);
        Point_2D expectedP2 = new Point_2D(3, 1);

        rect.translate(translationVector);

        assertEquals(expectedP1, rect.getP1(), "translate() method failed for point P1.");
        assertEquals(expectedP2, rect.getP2(), "translate() method failed for point P2.");
    }


    @Test
    void copy() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        Rect_2D copiedRect = (Rect_2D) rect.copy();

        assertNotSame(rect, copiedRect, "copy() method returned the same object reference.");
        assertEquals(rect.getP1(), copiedRect.getP1(), "Copied rectangle's P1 point does not match.");
        assertEquals(rect.getP2(), copiedRect.getP2(), "Copied rectangle's P2 point does not match.");
        assertEquals(rect.getP3(), copiedRect.getP3(), "Copied rectangle's P3 point does not match.");
        assertEquals(rect.getP4(), copiedRect.getP4(), "Copied rectangle's P4 point does not match.");
    }


    @Test
    void scale() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        Point_2D center = new Point_2D(1, 1); // Center point for scaling
        double scaleFactor = 2;

        rect.scale(center, scaleFactor);

        assertEquals(new Point_2D(-1, 3), rect.getP1(), "Scaling p1 failed.");
        assertEquals(new Point_2D(3, -1), rect.getP2(), "Scaling p2 failed.");
        assertEquals(new Point_2D(3, 3), rect.getP3(), "Scaling p3 failed.");
        assertEquals(new Point_2D(-1, -1), rect.getP4(), "Scaling p4 failed.");
    }


    @Test
    void rotate() {
        Rect_2D rect = new Rect_2D(new Point_2D(0, 2), new Point_2D(2, 0));
        Point_2D center = new Point_2D(1, 1); // center of the rectangle
        double angleDegrees = 90;
        rect.rotate(center, angleDegrees);

        Point_2D expectedP1 = new Point_2D(-2.220446049250313E-16, 0);
        Point_2D expectedP2 = new Point_2D(0.0,2.0);
        Point_2D expectedP3 = new Point_2D(2.0,2.0);
        Point_2D expectedP4 = new Point_2D(2.0,0.0);

        assertEquals(expectedP1, rect.getP1(), "Point P1 did not rotate correctly.");
        assertEquals(expectedP2, rect.getP3(), "Point P3 did not rotate correctly.");
        assertEquals(expectedP3, rect.getP2(), "Point P2 did not rotate correctly.");
        assertEquals(expectedP4, rect.getP4(), "Point P4 did not rotate correctly.");
    }


    @Test
    void testToString() {
        Point_2D p1 = new Point_2D(0, 2);
        Point_2D p2 = new Point_2D(2, 0);
        Rect_2D rect = new Rect_2D(p1, p2);
        String result = rect.toString();

        String expected = "0.0,2.0,2.0,2.0,2.0,0.0,0.0,0.0";

        assertEquals(expected, result, "toString() method does not match.");
    }

}