package ex2.testing;

import ex2.geo.*;
import ex2.gui.GUIShape;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GUIShapeTest {

    // methods that provides a stream of options to test
    static Stream<GeoShape> shapeProvider() {
        return Stream.of(
                new Circle_2D(new Point_2D(1.0, 1.0), 5.0),
                new Rect_2D(new Point_2D(0.0, 0.0), new Point_2D(2.0, 3.0)),
                new Triangle_2D(new Point_2D(0.0, 0.0), new Point_2D(1.0, 0.0), new Point_2D(0.0, 1.0))
        );
    }
    static Stream<Color> colorProvider() {
        return Stream.of(Color.BLACK, Color.RED, Color.BLUE, new Color(255, 255, 255)); // Add more colors as needed
    }
    /////////////////////////////////////////////////////////


    @ParameterizedTest
    @MethodSource("shapeProvider")
    void getShape(GeoShape expectedShape) {
        GUIShape guiShape = new GUIShape(expectedShape, true, Color.RED, 1);
        GeoShape actualShape = guiShape.getShape();

        // Check if the instance of the actual shape is the same as that of the expected shape
        assertEquals(expectedShape.getClass(), actualShape.getClass(), "The types of the expected and actual shapes should match.");
    }

    @ParameterizedTest
    @MethodSource("shapeProvider")
    void setShape(GeoShape newShape) {
        GUIShape guiShape = new GUIShape(null, false, Color.BLUE, 2); // Initialize GUIShape with no shape
        guiShape.setShape(newShape); // Set the new shape
        GeoShape updatedShape = guiShape.getShape(); // Retrieve the updated shape
        assertEquals(newShape, updatedShape, "The shape set by setShape should match the shape retrieved by getShape.");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isFilled(boolean expectedFilledStatus) {
        GUIShape shape = new GUIShape(null, expectedFilledStatus, Color.BLACK, 0);
        assertEquals(expectedFilledStatus, shape.isFilled(), "The filled status should match the expected value.");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void setFilled(boolean newFilledStatus) {
        GUIShape shape = new GUIShape(null, !newFilledStatus, Color.BLACK, 0); // Set to the opposite of newFilledStatus initially
        shape.setFilled(newFilledStatus); // Change to new status
        assertEquals(newFilledStatus, shape.isFilled(), "The shape should" +
                " reflect the new filled status after setFilled is called.");
    }

    @ParameterizedTest
    @MethodSource("colorProvider")
    void getColor(Color expectedColor) {
        GUIShape shape = new GUIShape(null, false, expectedColor, 0);
        assertEquals(expectedColor, shape.getColor(), "The color should match the expected value.");
    }

    @ParameterizedTest
    @MethodSource("colorProvider")
    void setColor(Color newColor) {
        GUIShape shape = new GUIShape(null, false, Color.BLACK, 0);
        shape.setColor(newColor);
        assertEquals(newColor, shape.getColor(), "The shape should reflect the new color.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, 2 , 4})
    void getTag(int expectedTag) {
        GUIShape shape = new GUIShape(null, false, Color.BLACK, expectedTag);
        assertEquals(expectedTag, shape.getTag(), "The tag should match the expected value.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, 2 , 4})
    void setTag(int newTag) {
        GUIShape shape = new GUIShape(null, false, Color.BLACK, 0); // Initial tag is arbitrary
        shape.setTag(newTag);
        assertEquals(newTag, shape.getTag(), "The tag should reflect the new value after setTag is called.");
    }

    @Test
    void copy() {
        GUIShape original = new GUIShape(new Circle_2D(new Point_2D(1, 1), 5), true, Color.BLUE, 1);
        GUIShape copied = (GUIShape) original.copy();

        assertEquals(original.getColor(), copied.getColor(), "Copied GUIShape should have the same color.");
        assertEquals(original.getTag(), copied.getTag(), "Copied GUIShape should have the same tag.");
        assertEquals(original.isFilled(), copied.isFilled(), "Copied GUIShape should have the same fill status.");
        assertNotSame(original, copied, "Copied GUIShape should not be the same instance as the original.");
    }

    @Test
    void testToString() {
        GUIShape shape = new GUIShape(new Circle_2D(new Point_2D(1.0, 2.0), 3.0), true, Color.RED, 2);
        String expected = "GUIShape,-65536,true,2,Circle_2D,1.0,2.0, 3.0";
        assertEquals(expected, shape.toString(), "The toString output should match the expected format.");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isSelected(boolean selectedStatus) {
        GUIShape shape = new GUIShape(null, false, Color.BLACK, 0);
        shape.setSelected(selectedStatus);
        assertEquals(selectedStatus, shape.isSelected(), "The selected status" +
                " should reflect the value set by setSelected.");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void setSelected(boolean newSelectedStatus) {
        GUIShape shape = new GUIShape(null, false, Color.BLACK, 0);
        shape.setSelected(newSelectedStatus); // Set new selected status
        assertEquals(newSelectedStatus, shape.isSelected(), "The shape should reflect" +
                " the new selected status after setSelected is called.");
    }
}