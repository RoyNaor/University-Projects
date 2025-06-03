package ex2.testing;

import ex2.geo.Point_2D;
import ex2.geo.Polygon_2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Polygon_2DTest {

    @Test
    void getAllPoints() {
        Polygon_2D polygon = new Polygon_2D();
        Point_2D p1 = new Point_2D(1, 2);
        Point_2D p2 = new Point_2D(3, 4);
        Point_2D p3 = new Point_2D(5, 6);

        polygon.add(p1);
        polygon.add(p2);
        polygon.add(p3);

        Point_2D[] expected = {p1, p2, p3};
        Point_2D[] actual = polygon.getAllPoints();

        assertArrayEquals(expected, actual, "The method should return all points added to the polygon");
    }

    @Test
    void add() {
        Polygon_2D polygon = new Polygon_2D();
        Point_2D p1 = new Point_2D(1, 2);

        polygon.add(p1);

        assertEquals(1, polygon.getAllPoints().length, "Polygon should contain 1 vertex after adding a point");
        assertEquals(p1, polygon.getAllPoints()[0], "The added point should be the same as the one in the polygon");
    }

    @Test
    void testToString() {
        Polygon_2D polygon = new Polygon_2D();
        polygon.add(new Point_2D(0, 0));
        polygon.add(new Point_2D(1, 0));
        polygon.add(new Point_2D(1, 1));
        polygon.add(new Point_2D(0, 1));

        String expected = "[0.0,0.0, 1.0,0.0, 1.0,1.0, 0.0,1.0]";
        String actual = polygon.toString();
        assertEquals(expected, actual, "The toString do not match.");
    }

    @Test
    void contains() {
        Polygon_2D polygon = new Polygon_2D();
        polygon.add(new Point_2D(0, 0));
        polygon.add(new Point_2D(4, 0));
        polygon.add(new Point_2D(4, 4));
        polygon.add(new Point_2D(0, 4));

        Point_2D insidePoint = new Point_2D(2, 2);
        Point_2D outsidePoint = new Point_2D(5, 5);
        Point_2D edgePoint = new Point_2D(4, 2);
        Point_2D vertexPoint = new Point_2D(0, 0);

        assertTrue(polygon.contains(insidePoint), "The point inside the polygon should be identified as inside.");
        assertFalse(polygon.contains(outsidePoint), "The point outside the polygon should be identified as outside.");
        assertTrue(polygon.contains(edgePoint), "The point on the edge of the polygon should be identified as inside.");
        assertTrue(polygon.contains(vertexPoint), "The point at the vertex of the polygon should be identified as inside.");
    }

    @Test
    void area() {
        Polygon_2D polygon = new Polygon_2D();
        polygon.add(new Point_2D(0, 0));
        polygon.add(new Point_2D(2, 0));
        polygon.add(new Point_2D(3, 2));
        polygon.add(new Point_2D(1, 4));
        polygon.add(new Point_2D(-1, 2));

        // Manually calculated area of the polygon
        double expectedArea = 10.0;

        assertEquals(expectedArea, polygon.area(), 0.001, "The calculated area should match the expected value.");
    }

    @Test
    void perimeter() {
        Polygon_2D polygon = new Polygon_2D();
        // Add vertices to the polygon
        polygon.add(new Point_2D(1, 0)); // Vertex 1
        polygon.add(new Point_2D(0.5, Math.sqrt(3) / 2)); // Vertex 2
        polygon.add(new Point_2D(-0.5, Math.sqrt(3) / 2)); // Vertex 3
        polygon.add(new Point_2D(-1, 0)); // Vertex 4
        polygon.add(new Point_2D(-0.5, -Math.sqrt(3) / 2)); // Vertex 5
        polygon.add(new Point_2D(0.5, -Math.sqrt(3) / 2)); // Vertex 6

        double expectedPerimeter = 5.0;

        assertEquals(expectedPerimeter, polygon.perimeter(), 0.001, "The calculated perimeter" +
                " should match the expected value.");
    }

    @Test
    void translateContains() {
        Polygon_2D polygon = new Polygon_2D();
        polygon.add(new Point_2D(0, 0));
        polygon.add(new Point_2D(2, 0));
        polygon.add(new Point_2D(2, 2));
        polygon.add(new Point_2D(0, 2));

        // Translation vector (moving right by 3 and up by 4)
        Point_2D translationVector = new Point_2D(3, 4);
        polygon.translate(translationVector);

        // Check each vertex to ensure it has been translated correctly
        assertTrue(polygon.contains(new Point_2D(3, 4)), "The polygon should now contain the point (3, 4).");
        assertTrue(polygon.contains(new Point_2D(5, 4)), "The polygon should now contain the point (5, 4).");
        assertTrue(polygon.contains(new Point_2D(5, 6)), "The polygon should now contain the point (5, 6).");
        assertTrue(polygon.contains(new Point_2D(3, 6)), "The polygon should now contain the point (3, 6).");

    }

    @Test
    void translateCheck() {
        Polygon_2D polygon = new Polygon_2D();
        // Define original vertices
        polygon.add(new Point_2D(1, 0)); // Vertex 1
        polygon.add(new Point_2D(0.5, Math.sqrt(3) / 2)); // Vertex 2
        polygon.add(new Point_2D(-0.5, Math.sqrt(3) / 2)); // Vertex 3
        polygon.add(new Point_2D(-1, 0)); // Vertex 4
        polygon.add(new Point_2D(-0.5, -Math.sqrt(3) / 2)); // Vertex 5
        polygon.add(new Point_2D(0.5, -Math.sqrt(3) / 2)); // Vertex 6

        // Translate the polygon by the vector (2, 3)
        Point_2D translationVector = new Point_2D(2, 3);
        polygon.translate(translationVector);

        // Expected positions after translation
        Point_2D[] expectedPositions = {
                new Point_2D(3, 3),
                new Point_2D(2.5, 3 + Math.sqrt(3) / 2),
                new Point_2D(1.5, 3 + Math.sqrt(3) / 2),
                new Point_2D(1, 3),
                new Point_2D(1.5, 3 - Math.sqrt(3) / 2),
                new Point_2D(2.5, 3 - Math.sqrt(3) / 2)
        };

        // Verify each vertex has been translated correctly
        Point_2D[] actualPositions = polygon.getAllPoints(); // Assuming this method exists and is accurate
        for (int i = 0; i < expectedPositions.length; i++) {
            assertEquals(expectedPositions[i].x(), actualPositions[i].x(), 0.001,
                    "X-coordinate mismatch at vertex " + (i + 1));
            assertEquals(expectedPositions[i].y(), actualPositions[i].y(), 0.001,
                    "Y-coordinate mismatch at vertex " + (i + 1));
        }
    }

    @Test
    void copy() {
        Polygon_2D originalPolygon = new Polygon_2D();
        // Add vertices
        originalPolygon.add(new Point_2D(1, 1));
        originalPolygon.add(new Point_2D(2, 2));
        originalPolygon.add(new Point_2D(3, 3));

        // Copy the polygon
        Polygon_2D copiedPolygon = (Polygon_2D) originalPolygon.copy();

        // Verify that the copied polygon is not the same object
        assertNotSame(originalPolygon, copiedPolygon, "The copied polygon should be a separate object.");

        // Verify that the vertices are equal but not the same objects (deep copy)
        Point_2D[] originalVertices = originalPolygon.getAllPoints();
        Point_2D[] copiedVertices = copiedPolygon.getAllPoints();
        assertEquals(originalVertices.length, copiedVertices.length, "The copied polygon should have the same number of vertices.");
        for (int i = 0; i < originalVertices.length; i++) {
            assertEquals(originalVertices[i], copiedVertices[i], "Vertices should be equal.");
            assertNotSame(originalVertices[i], copiedVertices[i], "Vertices should not be the same object (deep copy).");
        }
    }


    @Test
    void scale() {
        Polygon_2D polygon = new Polygon_2D();
        // Add vertices
        polygon.add(new Point_2D(1, 1));
        polygon.add(new Point_2D(1, -1));
        polygon.add(new Point_2D(-1, -1));
        polygon.add(new Point_2D(-1, 1));

        // Center of scaling (0,0) and ratio of 2
        Point_2D center = new Point_2D(0, 0);
        double ratio = 2;

        // Expected positions after scaling
        Point_2D[] expectedPositions = {
                new Point_2D(2, 2),
                new Point_2D(2, -2),
                new Point_2D(-2, -2),
                new Point_2D(-2, 2)
        };

        // Scale the polygon
        polygon.scale(center, ratio);

        // Verify each vertex has been scaled correctly
        Point_2D[] actualPositions = polygon.getAllPoints(); // Assuming this method exists and is accurate
        for (int i = 0; i < expectedPositions.length; i++) {
            assertEquals(expectedPositions[i].x(), actualPositions[i].x(), 0.001,
                    "X-coordinate mismatch at vertex " + (i + 1));
            assertEquals(expectedPositions[i].y(), actualPositions[i].y(), 0.001,
                    "Y-coordinate mismatch at vertex " + (i + 1));
        }
    }

    @Test
    void rotate() {
        Polygon_2D polygon = new Polygon_2D();
        polygon.add(new Point_2D(1, 0));
        polygon.add(new Point_2D(0, 1));
        polygon.add(new Point_2D(-1, 0));

        Point_2D center = new Point_2D(0, 0);
        double angleDegrees = 90.0;
        polygon.rotate(center, angleDegrees);

        // Expected positions after a 90-degree rotation
        Point_2D[] expectedPositions = {
                new Point_2D(0, 1),
                new Point_2D(-1, 0),
                new Point_2D(0, -1)
        };

        // Check if the vertices have been rotated correctly
        Point_2D[] actualPositions = polygon.getAllPoints();
        for (int i = 0; i < expectedPositions.length; i++) {
            assertEquals(expectedPositions[i].x(), actualPositions[i].x(), 0.001,
                    "X-coordinate mismatch after rotation at vertex " + (i + 1));
            assertEquals(expectedPositions[i].y(), actualPositions[i].y(), 0.001,
                    "Y-coordinate mismatch after rotation at vertex " + (i + 1));
        }
    }

}