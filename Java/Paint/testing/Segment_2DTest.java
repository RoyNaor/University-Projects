package ex2.testing;

import ex2.geo.Circle_2D;
import ex2.geo.Point_2D;
import ex2.geo.Segment_2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Segment_2DTest {

    @Test
    void get_p1() {
        Point_2D a = new Point_2D(4, 8);
        Point_2D b = new Point_2D(8, 8);
        Segment_2D segment = new Segment_2D(a, b);

        Point_2D expected_P1 = new Point_2D(4, 8);
        Point_2D actual_P1 = segment.get_p1();
        assertEquals(expected_P1, actual_P1, "P1 should match!!.");
    }

    @Test
    void get_p2() {
        Point_2D a = new Point_2D(4, 8);
        Point_2D b = new Point_2D(8, 8);
        Segment_2D segment = new Segment_2D(a, b);

        Point_2D expected_P2 = new Point_2D(8, 8);
        Point_2D actual = segment.get_p2();
        assertEquals(expected_P2, actual, "P2 should match!!.");
    }

    @Test
    void contains() {
        Point_2D a = new Point_2D(0, 0);
        Point_2D b = new Point_2D(4, 0);
        Segment_2D segment = new Segment_2D(a, b);

        Point_2D insidePoint = new Point_2D(2, 0);
        Point_2D outsidePoint = new Point_2D(5, 0);

        assertTrue(segment.contains(insidePoint), "The point expected to be inside the segment is not.");
        assertFalse(segment.contains(outsidePoint), "The point expected to be outside the segment is wrongly");
    }


    @Test
    void area() {
        Point_2D a = new Point_2D(4, 8);
        Point_2D b = new Point_2D(8, 8);
        Segment_2D segment = new Segment_2D(a, b);

        double expectedArea = 0;
        double actualArea = segment.area();

        assertEquals(expectedArea, actualArea, "the areas should match and be 0!!.");
    }

    @Test
    void perimeter() {
        Point_2D a = new Point_2D(4, 8);
        Point_2D b = new Point_2D(8, 8);
        Segment_2D segment = new Segment_2D(a, b);

        double expectedPerimeter = 8.0;
        double actualPerimeter = segment.perimeter();

        assertEquals(expectedPerimeter, actualPerimeter, "the perimeters should match!!.");
    }

    @Test
    void translate() {
        Point_2D a = new Point_2D(4, 8);
        Point_2D b = new Point_2D(8, 8);
        Segment_2D segment = new Segment_2D(a, b);
        Point_2D vector = new Point_2D(3, 4);
        segment.translate(vector);

        Point_2D expectedA = new Point_2D(7, 12);
        Point_2D expectedB = new Point_2D(11, 12);

        assertEquals(expectedA.x(), segment.get_p1().x(), "X coordinate after translation is incorrect");
        assertEquals(expectedB.y(), segment.get_p2().y(), "Y coordinate after translation is incorrect");
    }

    @Test
    void copy() {
        Point_2D a = new Point_2D(4, 8);
        Point_2D b = new Point_2D(8, 8);
        Segment_2D segment = new Segment_2D(a, b);
        Segment_2D copiedSegment = (Segment_2D) segment.copy();

        assertEquals(segment.get_p1().x(), copiedSegment.get_p1().x(), "Copied circle " +
                "X coordinate mismatch");
        assertEquals(segment.get_p1().y(), copiedSegment.get_p1().y(), "Copied circle " +
                "Y coordinate mismatch");
        assertEquals(segment.get_p2().x(), copiedSegment.get_p2().x(), "Copied circle " +
                "X coordinate mismatch");
        assertEquals(segment.get_p2().y(), copiedSegment.get_p2().y(), "Copied circle " +
                "Y coordinate mismatch");
        assertNotSame(segment, copiedSegment, "The copied circle should be a different object");
    }

    @Test
    void scale() {
        Point_2D a = new Point_2D(14, 18);
        Point_2D b = new Point_2D(8, 8);
        Segment_2D segment = new Segment_2D(a, b);

        Point_2D scaleCenter = new Point_2D(12, 12);
        double ratio = 2;

        segment.scale(scaleCenter, ratio);
        Point_2D expectedA = new Point_2D(16, 24);
        Point_2D expectedB = new Point_2D(4, 4);

        assertEquals(expectedA, segment.get_p1(), "PointsA after scaling is incorrect");
        assertEquals(segment.get_p2(), expectedB, "PointsB after scaling is incorrect");
    }

    @Test
    void rotate() {
        Point_2D a = new Point_2D(1, 0);
        Point_2D b  = new Point_2D(2, 0);
        Segment_2D segment = new Segment_2D(a, b);
        Point_2D center = new Point_2D(1, 1);
        double angleDegrees = 90;

        segment.rotate(center, angleDegrees);

        assertTrue(segment.get_p1().x() != 1 || segment.get_p1().y() != 0, "Point a " +
                "did not rotate correctly.");
        assertTrue(segment.get_p2().x() != 2 || segment.get_p2().y() != 0, "Point b " +
                "did not rotate correctly.");
    }

    @Test
    void testToString() {
        Point_2D a = new Point_2D(1, 2);
        Point_2D b  = new Point_2D(3, 4);
        Segment_2D segment = new Segment_2D(a, b);

        String expected = "1.0,2.0, 3.0,4.0";
        String result = segment.toString();

        assertEquals(expected, result, "The toString method does not return the expected format.");
    }

}