package ex2.testing;

import ex2.ex2.Ex2_Const;
import ex2.ex2.ShapeCollection;
import ex2.geo.Circle_2D;
import ex2.geo.Point_2D;
import ex2.geo.ShapeComp;
import ex2.gui.GUIShape;
import ex2.gui.GUI_Shape;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ShapeCollectionTest {

    Point_2D p1 = new Point_2D(1.0, 2.0);
    Point_2D p2 = new Point_2D(2.0, 2.0);
    double radius = 5.0, radius2 = 7.0;
    Color color = Color.black;
    int tag = 1;
    private final String testFileName = "testShapeCollectionSave.txt";
    private final Path testFilePathLoad = Paths.get("testShapeCollectionLoad.txt");
    private Path testFilePath;
    public static final ShapeComp CompByArea = new ShapeComp(Ex2_Const.Sort_By_Area);

    @Test
    void get() {
        int i = 0;
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        GUI_Shape gs = new GUIShape(c1, false, color, tag);
        shapes.add(gs);
        assertTrue(shapes.get(i).getShape() instanceof Circle_2D, "the shapes should match.");
    }

    @Test
    void size() {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        GUI_Shape gs = new GUIShape(c1, false, color, tag);
        shapes.add(gs);
        assertEquals(1, shapes.size(), "Size should match.");
    }

    @Test
    void sizeAfterRemoving() {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        Circle_2D c2 = new Circle_2D(p1, radius);
        GUI_Shape gs = new GUIShape(c1, false, color, tag);
        GUI_Shape gs2 = new GUIShape(c2, false, color, tag);
        shapes.add(gs);
        shapes.add(gs2);
        shapes.removeElementAt(0);
        assertEquals(1, shapes.size(), "Size should match.");
    }

    @Test
    void removeElementAt() {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        Circle_2D c2 = new Circle_2D(p1, radius);
        Circle_2D c3 = new Circle_2D(p1, radius);
        GUI_Shape gs = new GUIShape(c1, false, color, tag);
        GUI_Shape gs2 = new GUIShape(c2, false, color, tag);
        GUI_Shape gs3 = new GUIShape(c3, false, color, tag);
        shapes.add(gs);
        shapes.add(gs2);
        shapes.add(gs3);
        shapes.removeElementAt(1);
        assertEquals(gs3, shapes.get(1), "the shape in the index after the removed shape should" +
                "be index - 1.");
    }

    @Test
    void addAt() {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        Circle_2D c2 = new Circle_2D(p1, radius);
        Circle_2D c3 = new Circle_2D(p1, radius);
        GUI_Shape gs1 = new GUIShape(c1, false, color, tag);
        GUI_Shape gs2 = new GUIShape(c2, false, color, tag);
        GUI_Shape gs3 = new GUIShape(c3, false, color, tag);
        shapes.add(gs1); // [gs1]
        shapes.add(gs2); // [gs1, gs2]
        shapes.addAt(gs3, 1); // Expected: [gs1, gs3, gs2]

        assertEquals(gs3, shapes.get(1), "gs3 should be at index 1 after insertion.");
        assertEquals(gs2, shapes.get(2), "gs2 should be shifted to index + 1.");
        assertEquals(3, shapes.size(), "Collection should contain the correct amount of\" +\n" +
                "                \" elements after adding.");
    }

    @Test
    void add() {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        GUI_Shape gs1 = new GUIShape(c1, false, color, tag);
        shapes.add(gs1); // [gs1]

        assertEquals(1, shapes.size(), "Collection should contain the correct amount of" +
                " elements after adding.");
    }

    @Test
    void copy() {
        ShapeCollection original = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        Circle_2D c2 = new Circle_2D(p1, radius);
        GUI_Shape gs1 = new GUIShape(c1, false, color, tag);
        GUI_Shape gs2 = new GUIShape(c2, false, color, tag);
        original.add(gs1); // [gs1]
        original.add(gs2); // [gs1, gs2]

        ShapeCollection copy = original.copy();
        // Check if the copy has the same size as the original
        assertEquals(original.size(), copy.size(), "Copy should have the same size as the original collection.");

        // Verify that the objects are not the same (deep copy)
        for (int i = 0; i < original.size(); i++) {
            assertNotSame(original.get(i), copy.get(i), "Elements at index " + i +
                    " should not be the same in the original and the copy.");
        }
    }

    @Test
    void sort() {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius); // smaller
        Circle_2D c2 = new Circle_2D(p1, radius2); // bigger
        GUI_Shape gs1 = new GUIShape(c1, false, color, tag);
        GUI_Shape gs2 = new GUIShape(c2, false, color, tag);
        shapes.add(gs1); // [gs1]
        shapes.add(gs2); // [gs1, gs2]

        ShapeCollection sortedShapes = new ShapeCollection();
        sortedShapes.add(gs2);
        sortedShapes.add(gs1);

        shapes.sort(CompByArea); // Sort the collection

        boolean isCorrectOrder = shapes.get(0).getShape().area() <= shapes.get(1).getShape().area();
        assertTrue(isCorrectOrder, "Shapes should be sorted in ascending order by area.");
    }

    @Test
    void removeAll() {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        Circle_2D c2 = new Circle_2D(p1, radius2);
        GUI_Shape gs1 = new GUIShape(c1, false, color, tag);
        GUI_Shape gs2 = new GUIShape(c2, false, color, tag);
        shapes.add(gs1); // [gs1]
        shapes.add(gs2); // [gs1, gs2]

        assertNotEquals(0, shapes.size(), "Collection should not be empty before removeAll is called.");

        shapes.removeAll();

        // verify the collection is empty
        assertEquals(0, shapes.size(), "Collection should be empty after removeAll is called.");
    }


    @Test
    void save() throws IOException {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        Circle_2D c2 = new Circle_2D(p1, radius2);
        GUI_Shape gs1 = new GUIShape(c1, false, color, tag);
        GUI_Shape gs2 = new GUIShape(c2, false, color, tag);
        shapes.add(gs1); // [gs1]
        shapes.add(gs2); // [gs1, gs2]

        // Save the collection to a file
        shapes.save(testFileName);

        testFilePath = Paths.get(testFileName);

        // Read the file's content
        String path = testFileName + ".txt";
        String content = new String(Files.readAllBytes(Paths.get(path)));
        String expectedContent = "GUIShape,-16777216,false,1,Circle_2D,2.0,2.0, 5.0, \n" +
                "GUIShape,-16777216,false,1,Circle_2D,1.0,2.0, 7.0";

        assertEquals(expectedContent, content.trim(), "The content of the saved file" +
                " should match the expected content.");
    }

    @Test
    void load() throws IOException {
        ShapeCollection shapes = new ShapeCollection();

        String testContent = "GUIShape,-16777216,false,1,Circle_2D,2.0,2.0,5.0\n" +
                "GUIShape,-16777216,false,1,Circle_2D,1.0,2.0,7.0";
        Files.writeString(testFilePathLoad, testContent);

        // Load the shapes from the test file
        shapes.load(testFilePathLoad.toString());

        // Verify that the shapes were loaded correctly
        assertEquals(2, shapes.size(), "The collection should contain the shapes after loading.");
    }

        @Test
    void testToString() {
        ShapeCollection shapes = new ShapeCollection();
        Circle_2D c1 = new Circle_2D(p2, radius);
        Circle_2D c2 = new Circle_2D(p1, radius2);
        GUI_Shape gs1 = new GUIShape(c1, false, color, tag);
        GUI_Shape gs2 = new GUIShape(c2, false, color, tag);

        shapes.add(gs1);
        shapes.add(gs2);

        String expected = gs1 + "\n" + gs2 + "\n";
        String actual = shapes.toString();

        assertEquals(expected, actual, "The toString method should return a newline-separated list of shapes.");
    }
}