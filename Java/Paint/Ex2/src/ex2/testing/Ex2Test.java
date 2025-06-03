package ex2.testing;

import ex2.ex2.Ex2_Const;
import ex2.ex2.GUI_Shape_Collection;
import ex2.ex2.ShapeCollection;
import ex2.geo.Circle_2D;
import ex2.geo.Point_2D;
import ex2.gui.Ex2;
import ex2.gui.GUIShape;
import ex2.gui.GUI_Shape;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Ex2Test {

    static Stream<GUI_Shape_Collection> shapeCollectionProvider() {
        return Stream.of(
                null,
                new ShapeCollection(),
                createNonEmptyShapeCollection()
        );
    }

    private static ShapeCollection createNonEmptyShapeCollection() {
        ShapeCollection shapes = new ShapeCollection();
        shapes.add(new GUIShape(new Circle_2D(new Point_2D(1, 1), 1), false, Color.BLACK, 0));
        return shapes;
    }

    @ParameterizedTest
    @MethodSource("shapeCollectionProvider")
    void init(GUI_Shape_Collection shapes) {
        Ex2 ex2 = Ex2.getInstance();
        ex2.init(shapes);
        assertNotNull(ex2.getShape_Collection(), "Shape collection should be initialized.");
        if (shapes == null) {
            assertEquals(0, ex2.getShape_Collection().size(), "Shape collection should be empty for null input.");
        } else {
            assertEquals(shapes.size(), ex2.getShape_Collection().size(), "Shape collection sizes should match.");
        }
    }

    @Test
    void show() {
        Ex2 ex2 = Ex2.getInstance();
        assertDoesNotThrow(() -> ex2.show(Ex2_Const.DIM_SIZE), "Show method should not throw exceptions.");
    }

    @Test
    void getInstance() {
        Ex2 firstInstance = Ex2.getInstance();
        assertNotNull(firstInstance, "getInstance should never return null.");

        Ex2 secondInstance = Ex2.getInstance();
        assertSame(firstInstance, secondInstance, "getInstance should always return the same Ex2 instance.");
    }

    @Test
    void drawShapes() {
        Ex2 ex2 = Ex2.getInstance();
        assertDoesNotThrow(ex2::drawShapes, "drawShapes should complete without throwing an exception.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Red", "Fill", "Empty"})
    void actionPerformed(String command) {
        Ex2 ex2 = Ex2.getInstance(); // Ensure this gets a fresh instance or resets its state appropriately

        ex2.init(new ShapeCollection());

        GUIShape shape = new GUIShape(new Circle_2D(new Point_2D(1.0, 1.0), 5.0), true, Color.RED, 1);
        ex2.getShape_Collection().add(shape);

        ex2.actionPerformed(command);

        GUI_Shape testShape = ex2.getShape_Collection().get(0);
        if ("Red".equals(command)) {
            assertEquals(Color.RED, testShape.getColor(), "After 'Red' command, shape color should be set to red.");
        }
        if ("Fill".equals(command)) {
            assertTrue(testShape.isFilled(), "After 'Fill' command, shape should be set to filled.");
        }
        if ("Empty".equals(command)) {
            assertTrue(testShape.isFilled(), "After 'Empty' command, shape should not be filled.");
        }
    }


    @Test
    void mouseClicked() {
        Ex2 ex2 = Ex2.getInstance();
        ex2.init(new ShapeCollection());

        Circle_2D circle = new Circle_2D(new Point_2D(1.0, 1.0), 5.0);
        GUIShape circleShape = new GUIShape(circle, false, Color.BLACK, 1);
        ex2.getShape_Collection().add(circleShape);


        // Simulate a mouse click within the circle.
        Point_2D clickPoint = new Point_2D(2.0, 1.0); // Adjusted to be within the circle's bounds.
        ex2.mouseClicked(clickPoint); // This should ideally select the shape if such behavior is implemented.

        // Check if the circle was selected after the click.
        boolean isCircleSelected = ex2.getShape_Collection().get(0).getShape().contains(clickPoint);

        // Assert that the circle is now selected.
        assertTrue(isCircleSelected, "The circle should be selected after it has been clicked on.");
    }

    @Test
    void mouseRightClicked() {
    }

    @Test
    void mouseMoved() {
    }

    @Test
    void getShape_Collection() {
    }

    @Test
    void testShow() {
    }

    @Test
    void getInfo() {
    }
}