package ex2.testing;

import ex2.geo.Circle_2D;
import ex2.geo.Point_2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class Circle_2DTest {

    // methods that provides a stream of options to test
    static Stream<Double> radiusProvider() {
        return Stream.of(1.0, 5.0, 10.0);
    }
    static Stream<Point_2D> centerProvider() {
        return Stream.of(
                new Point_2D(1.0, 1.0),
                new Point_2D(-1.0, -1.0),
                new Point_2D(0.0, 0.0)
        );
    }
    static Stream<Arguments> toStringProvider() {
        return Stream.of(
                arguments(new Point_2D(2.0, 2.0), 5, "2.0,2.0, 5.0"),
                arguments(new Point_2D(1.0, 1.0), 3, "1.0,1.0, 3.0")
                // Add more cases as needed
        );
    }
    static Stream<Arguments> containsProvider() {
        Point_2D center = new Point_2D(1, 1);
        double radius = 5;
        return Stream.of(
                arguments(center, radius, new Point_2D(3.0, 4.0), true),
                arguments(center, radius, new Point_2D(10.0, 2.0), false)
                // Add more cases as needed
        );
    }
    static Stream<Double> radiusProviderForArea() {
        return Stream.of(1.0, 5.0, 10.0);
    }
    static Stream<Double> radiusProviderForPerimeter() {
        return Stream.of(1.0, 5.0, 10.0);
    }
    static Stream<Arguments> translateProvider() {
        return Stream.of(
                arguments(new Point_2D(1, 1), new Point_2D(3, 4), new Point_2D(4, 5)),
                arguments(new Point_2D(0, 0), new Point_2D(1, 1), new Point_2D(1, 1))
        );
    }
    static Stream<Arguments> scaleProvider() {
        return Stream.of(
                arguments(new Point_2D(1, 1), 5.0, new Point_2D(2, 2), 2.0, 10.0),
                arguments(new Point_2D(1, 1), 5.0, new Point_2D(1, 1), 0.5, 2.5)
        );
    }
    /////////////////////////////////////////////////////////


    @ParameterizedTest
    @MethodSource("radiusProvider")
    void getRadius(double radius) {
        Point_2D center = new Point_2D(1, 1);
        Circle_2D circle = new Circle_2D(center, radius);
        assertEquals(radius, circle.getRadius(), "The radius should match the input value.");
    }

    @ParameterizedTest
    @MethodSource("centerProvider")
    void getCenter(Point_2D center) {
        double radius = 5;
        Circle_2D circle = new Circle_2D(center, radius);
        assertEquals(center, circle.getCenter(), "The center should match!!");
    }

    @ParameterizedTest
    @MethodSource("toStringProvider")
    void ToString(Point_2D center, double radius, String expected) {
        Circle_2D circle = new Circle_2D(center, radius);
        String actual = circle.toString();
        assertEquals(expected, actual, "The info should match!!");
    }

    @ParameterizedTest
    @MethodSource("containsProvider")
    void contains(Point_2D center, double radius, Point_2D testPoint, boolean expected) {
        Circle_2D circle = new Circle_2D(center, radius);
        assertEquals(expected, circle.contains(testPoint),
                expected ? "The point should be inside the circle." : "The point should be outside the circle.");
    }

    @ParameterizedTest
    @MethodSource("radiusProviderForArea")
    void area(double radius) {
        Circle_2D circle = new Circle_2D(new Point_2D(1, 1), radius);
        double expected = Math.PI * Math.pow(radius, 2);
        assertEquals(expected, circle.area(), "The area should match.");
    }

    @ParameterizedTest
    @MethodSource("radiusProviderForPerimeter")
    void perimeter(double radius) {
        Circle_2D circle = new Circle_2D(new Point_2D(1, 1), radius);
        double expected = 2 * Math.PI * radius;
        assertEquals(expected, circle.perimeter(), "The perimeter should match.");
    }

    @ParameterizedTest
    @MethodSource("translateProvider")
    void translate(Point_2D center, Point_2D vector, Point_2D expected) {
        Circle_2D circle = new Circle_2D(center, 5);
        circle.translate(vector);
        assertEquals(expected, circle.getCenter(), "After translation, the center should match expected.");
    }

    @Test
    void copy() {
        Point_2D Center = new Point_2D(1, 1);
        double radius = 5;
        Circle_2D circle = new Circle_2D(Center, radius);
        Circle_2D copiedCircle = (Circle_2D) circle.copy();

        assertEquals(circle.getCenter().x(), copiedCircle.getCenter().x(), "Copied circle " +
                "X coordinate mismatch");
        assertEquals(circle.getCenter().y(), copiedCircle.getCenter().y(), "Copied circle " +
                "Y coordinate mismatch");
        assertEquals(circle.getRadius(), copiedCircle.getRadius(), "Copied circle radius mismatch");
        assertNotSame(circle, copiedCircle, "The copied circle should be a different object");
    }

    @ParameterizedTest
    @MethodSource("scaleProvider")
    void scale(Point_2D center, double initialRadius, Point_2D scaleCenter, double ratio, double expectedRadius) {
        Circle_2D circle = new Circle_2D(center, initialRadius);
        circle.scale(scaleCenter, ratio);
        assertEquals(expectedRadius, circle.getRadius(), "After scaling, the radius should match expected.");
    }

    @Test
    void rotate() {
        Point_2D Center = new Point_2D(1, 1);
        double radius = 5;
        Circle_2D circle = new Circle_2D(Center, radius);
        Point_2D rotationCenter = new Point_2D(2, 2);
        double angleDegrees = 90;
        Point_2D expectedCenterAfterRotation = new Point_2D(3.0, 1.0);

        circle.rotate(rotationCenter, angleDegrees);

        assertEquals(expectedCenterAfterRotation.x(), circle.getCenter().x(), "Circle center X coordinate " +
                "after rotation is incorrect");
        assertEquals(expectedCenterAfterRotation.y(), circle.getCenter().y(), "Circle center Y coordinate " +
                "after rotation is incorrect");
    }

}