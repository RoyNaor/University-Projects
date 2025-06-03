package ex2.gui;

import ex2.ex2.Ex2_Const;
import ex2.ex2.GUI_Shape_Collection;
import ex2.ex2.ShapeCollection;
import ex2.geo.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * This class is a simple "interlayer" connecting (aka simplifying) the
 * StdDraw with the Map class.
 * Written for 101 java course it uses simple static functions to allow a
 * "Singleton-like" implementation.

 * README:
 * The Ex2 class is designed to create a drawing interface to draw graphical geometric shapes
 * such as circles, rectangles, polygons, and segments within a 2D space.
 * enabling a straightforward visualization of geometric objects.
 * The algorithm can handle a collection of geometric shapes, allowing for operations such as
 * adding, removing, and modifying shapes. Each shape is represented as a GUI_Shape object
 * with operations like drawing, coloring, and filling.
 * The user can perform actions such as mouse clicks and button presses to
 * creating new shapes, selecting and deselecting shapes, and applying transformations like move, rotate, and scale.
 * The algorithm provides functionality to save and load the current state of the shape collection to and from files,
 * enabling persistent storage and retrieval of user past content.

 * Sources that have been used in this project:
 * ChatGPT 4 (for ray casting and Barycentric coordinate system and spelling mistakes)
 * Wikipedia -
 * <a href="https://en.wikipedia.org/wiki/Polygon">...</a>,
 * <a href="https://en.wikipedia.org/wiki/Point_in_polygon">...</a>
 * <a href="https://en.wikipedia.org/wiki/Barycentric_coordinate_system">...</a>
 * GeeksForGeeks:
 * <a href="https://www.geeksforgeeks.org/file-class-in-java/">...</a>
 * JUnit5
 * <a href="https://junit.org/junit5/docs/current/user-guide/">...</a>
 *
 * @author boaz.ben-moshe + Roy Naor
 * ID - 323917104
 */
public class Ex2 implements Ex2_GUI {
    private GUI_Shape_Collection _shapes = new ShapeCollection();
    private GUI_Shape _gs;
    private Polygon_2D _pp;
    private Color _color = Color.blue;
    private boolean _fill = false;
    private String _mode = "";
    private Point_2D _p1;

    private static Ex2 _winEx2 = null;

    private Ex2() {
        init(null);
    }

    /**
     * Initializes the GUI shape collection.
     * @param s The initial collection of GUI shapes. If null, a new collection is created.
     */    public void init(GUI_Shape_Collection s) {
        if (s == null) {
            _shapes = new ShapeCollection();
        } else {
            _shapes = s;
        }
        GUI_Shape _gs = null;
        Polygon_2D _pp = null;
        Point_2D _p1 = null;
        _color = Color.blue;
        _fill = false;
        _mode = "";

    }

    /**
     * Displays the canvas and draws all shapes within a specified dimension.
     * @param d The dimension (width and height) for the drawing canvas.
     */    public void show(double d) {
        StdDraw_Ex2.setScale(0, d);
        StdDraw_Ex2.show();
        drawShapes();
    }


    /**
     * Provides a singleton instance of the Ex2 class.
     * @return The singleton instance of Ex2.
     */    public static Ex2 getInstance() {
        if (_winEx2 == null) {
            _winEx2 = new Ex2();
        }
        return _winEx2;
    }

    /* private static void drawGrid(int x, int y, int delta) {
         for(int i=0;i<x;i+=delta) {
             StdDraw_Ex2.line(i, 0, i, y);
         }
         for(int i=0;i<y;i+=delta) {
             StdDraw_Ex2.line(0, i, x, i);
         }
    } */

    /**
     * Draws all shapes contained in the shape collection onto the canvas.
     */    public void drawShapes() {
        StdDraw_Ex2.clear();
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape sh = _shapes.get(i);
            System.out.println(sh);
            drawShape(sh);
        }
        if (_gs != null) {
            drawShape(_gs);
        }
        StdDraw_Ex2.show();
    }

    /**
     * Draws a single GUI shape on the canvas based on its properties.
     * @param g The GUI shape to be drawn.
     */    private static void drawShape(GUI_Shape g) {
        StdDraw_Ex2.setPenColor(g.getColor());
        if (g.isSelected()) {
            StdDraw_Ex2.setPenColor(Color.gray);
        }
        GeoShape gs = g.getShape();
        boolean isFill = g.isFilled();
        if (gs instanceof Circle_2D) {
            Circle_2D c = (Circle_2D) gs;
            Point_2D cen = c.getCenter();
            double rad = c.getRadius();
            if (isFill) {
                StdDraw_Ex2.filledCircle(cen.x(), cen.y(), rad);
            } else {
                StdDraw_Ex2.circle(cen.x(), cen.y(), rad);
            }
        }
        if (gs instanceof Segment_2D) {
            Segment_2D c = (Segment_2D) gs;
            Point_2D m0 = c.get_p1();
            Point_2D m1 = c.get_p2();
            StdDraw_Ex2.line(m0.x(), m0.y(), m1.x(), m1.y());
        }
        Point_2D[] ps = null;
        if (gs instanceof Polygon_2D) {
            ps = ((Polygon_2D) gs).getAllPoints();
        }
        if (gs instanceof Triangle_2D) {
            ps = ((Triangle_2D) gs).getAllPoints();
        }
        if(gs instanceof Rect_2D){
            Rect_2D r= (Rect_2D) gs;
            ps=new Point_2D[]{r.getP1(),r.getP3(),r.getP2(),r.getP4()};
        }
        if (ps != null) {
            double[] xx = new double[ps.length];
            double[] yy = new double[ps.length];
            for (int i = 0; i < xx.length; i++) {
                xx[i] = ps[i].x();
                yy[i] = ps[i].y();
            }
            if (isFill) {
                StdDraw_Ex2.filledPolygon(xx, yy);
            } else {
                StdDraw_Ex2.polygon(xx, yy);
            }
        }
    }

    /**
     * Sets the color for all selected shapes in the collection.
     * @param c The color to set for the selected shapes.
     */    private void setColor(Color c) {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                s.setColor(c);
            }
        }
    }

    /**
     * Sets the fill property for all selected shapes in the collection.
     */    private void setFill() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                s.setFilled(_fill);
            }
        }
    }

    /**
     * Removes all selected shapes from the collection.
     */    private void remove() {
        for (int i = _shapes.size() - 1; i >= 0; i--) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                _shapes.removeElementAt(i);
            }
        }
    }

    /**
     * Executes actions based on the provided string command, such as color changes and shape manipulations.
     * @param p The command that determines the action to be performed.
     */    public void actionPerformed(String p) {
        _mode = p;
        if (p.equals("Blue")) {
            _color = Color.BLUE;
            setColor(_color);
        }
        if (p.equals("Red")) {
            _color = Color.RED;
            setColor(_color);
        }
        if (p.equals("Green")) {
            _color = Color.GREEN;
            setColor(_color);
        }
        if (p.equals("White")) {
            _color = Color.WHITE;
            setColor(_color);
        }
        if (p.equals("Black")) {
            _color = Color.BLACK;
            setColor(_color);
        }
        if (p.equals("Yellow")) {
            _color = Color.YELLOW;
            setColor(_color);
        }
        if (p.equals("Fill")) {
            _fill = true;
            setFill();
        }
        if (p.equals("Empty")) {
            _fill = false;
            setFill();
        }
        if (p.equals("Remove")) {
            remove();
        }
        if (p.equals("Save")) {
            save();
        }
        if (p.equals("Load")) {
            load();
        }
        if (p.equals("ByArea")) {
            _shapes.sort(ShapeComp.CompByArea);
        }
        if (p.equals("ByAntiArea")) {
            _shapes.sort(ShapeComp.CompByAntiArea);
        }
        if (p.equals("ByPerimeter")) {
            _shapes.sort(ShapeComp.CompByPerimeter);
        }
        if (p.equals("ByAntiPerimeter")) {
            _shapes.sort(ShapeComp.CompByAntiPerimeter);
        }
        if (p.equals("ByTag")) {
            _shapes.sort(ShapeComp.CompByTag);
        }
        if (p.equals("ByAntiTag")) {
            _shapes.sort(ShapeComp.CompByAntiTag);
        }
        if (p.equals("ByToString")) {
            _shapes.sort(ShapeComp.CompByToString);
        }
        if (p.equals("ByAntiToString")) {
            _shapes.sort(ShapeComp.CompByAntiToString);
        }
        if (p.equals("Clear")) {
            _shapes.removeAll();
        }
        if (p.equals("None")) {
            selectNone();
        }
        if (p.equals("All")) {
            selectAll();
        }
        if (p.equals("Anti")) {
            selectAnti();
        }
        if (p.equals("Info")) {
            System.out.println(getInfo());
        }

        drawShapes();

    }

    /**
     * Saves the current shape collection to a file chosen by the user.
     */    private void save() {
        FileDialog chooser = new FileDialog(StdDraw_Ex2.getFrame(), "Save to Text file", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            _shapes.save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    /**
     * Loads a shape collection from a file chosen by the user.
     */
    private void load() {
        FileDialog chooser = new FileDialog(StdDraw_Ex2.getFrame(), "Load from Text file", FileDialog.LOAD);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            _shapes.load(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    /**
     * Handles mouse click events, creating or manipulating shapes based on the current mode.
     * @param p The point where the mouse was clicked.
     */
    public void mouseClicked(Point_2D p) {
        System.out.println("Mode: " + _mode + "  " + p);
        if (_mode.equals("Rect") || _mode.equals("Circle") || _mode.equals("Segment")) {
            if (_gs == null) {
                _p1 = new Point_2D(p);
            } else {
                _gs.setColor(_color);
                _gs.setFilled(_fill);
                _shapes.add(_gs);
                _gs = null;
                _p1 = null;
            }
        }
        if (_mode.equals("Move")) {
            if (_p1 == null) {
                _p1 = new Point_2D(p);
            } else {
                _p1 = new Point_2D(p.x() - _p1.x(), p.y() - _p1.y());
                move();
                _p1 = null;
            }
        }
        if (_mode.equals("Copy")) {
            if (_p1 == null) {
                _p1 = new Point_2D(p);
            } else {
                _p1 = new Point_2D(p.x() - _p1.x(), p.y() - _p1.y());
                copy();
                _p1 = null;
            }
        }
        if (_mode.equals("Rotate")) {
            if (_p1 == null) {
                _p1 = new Point_2D(p);
            } else {
                rotate(p);
                _p1 = null;
            }
        }

        if (_mode.equals("Polygon")) {
            if (_pp == null) {
                _pp = new Polygon_2D();
            }
            _p1 = new Point_2D(p);
            _pp.add(p);
        }

        if (_mode.equals("Triangle")) {
            if (_pp == null) {
                _pp = new Polygon_2D();
            }
            _p1 = new Point_2D(p);
            _pp.add(p);
            if (_pp.getAllPoints().length == 3) {
                Point_2D[] pp = _pp.getAllPoints();
                Triangle_2D tt = new Triangle_2D(pp[0], pp[1], pp[2]);
                GUI_Shape s = new GUIShape(tt, _fill, _color, 0);
                _shapes.add(s);
                _pp = null;
                _p1 = null;
                _gs = null;
            }
        }
        if (_mode.equals("Point")) {
            select(p);
        }
        //
        if (_mode.equals("Scale_90%")) {
            scale(p, 0.9);
        }
        if (_mode.equals("Scale_110%")) {
            scale(p, 1.10);
        }
        drawShapes();
    }

    /**
     * Scales the selected shapes around a given point by the specified ratio.
     * @param p     The point around which the shapes are scaled.
     * @param ratio The ratio by which the shapes are scaled.
     */
    private void scale(Point_2D p, double ratio) {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            GeoShape g = s.getShape();
            if (s.isSelected() && g != null) {
                g.scale(p, ratio);
            }
        }
    }

    /**
     * Selects or deselects shapes based on their overlap with a given point.
     * @param p The point used to select or deselect shapes.
     */
    private void select(Point_2D p) {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            GeoShape g = s.getShape();
            if (g != null && g.contains(p)) {
                s.setSelected(!s.isSelected());
            }
        }
    }

    /**
     * Moves the selected shapes by a specified offset.
     */
    private void move() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            GeoShape g = s.getShape();
            if (s.isSelected() && g != null) {
                g.translate(_p1);
            }
        }
    }


    /**
     * Copies the selected shapes and moves the copies by a specified offset.
     */
    private void copy() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                GUI_Shape s1 = s.copy();
                GeoShape g = s1.getShape();
                g.translate(_p1);
                _shapes.add(s1);
            }
        }
    }

    /**
     * Rotates the selected shapes around a given point by a calculated angle.
     * @param ang The point around which the shapes are rotated.
     */
    private void rotate(Point_2D ang) {
        double dx = ang.x() - _p1.x();
        double dy = ang.y() - _p1.y();
        double da = Math.atan2(dy, dx);
        da = Math.toDegrees(da);
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            GeoShape g = s.getShape();
            if (s.isSelected()) {
                g.rotate(_p1, da);
            }
        }
    }

    /**
     * Selects all shapes in the shape collection.
     */
    private void selectAll() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            s.setSelected(true);
        }
    }

    /**
     * Prints information for each selected shape in the collection.
     */    private void printInfo() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                System.out.println(i + ") " + s.toString());
            }
        }
    }

    /**
     * Deselects all shapes in the shape collection.
     */
    private void selectNone() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            s.setSelected(false);
        }
    }


    /**
     * Inverts the selection status for all shapes in the collection.
     */
    private void selectAnti() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            s.setSelected(!s.isSelected());
        }
    }

    /**
     * Handles right-click mouse events for completing polygon shapes.
     * @param p The point where the right mouse button was clicked.
     */
    public void mouseRightClicked(Point_2D p) {
        if (_mode.equals("Polygon") && _pp != null) {
            GUIShape s = new GUIShape(_pp, _fill, _color, 0);
            _shapes.add(s);
        }
        _pp = null;
        _gs = null;
        _p1 = null;
        drawShapes();


    }

    /**
     * Handles mouse movement for dynamic shape creation and editing.
     * @param e The mouse event that contains the new mouse position.
     */
    public void mouseMoved(MouseEvent e) {
        if (_p1 != null) {
            double x1 = StdDraw_Ex2.mouseX();
            double y1 = StdDraw_Ex2.mouseY();
            GeoShape gs = null;
            //	System.out.println("M: "+x1+","+y1);
            Point_2D p = new Point_2D(x1, y1);
            if (_mode.equals("Circle")) {
                double r = _p1.distance(p);
                gs = new Circle_2D(_p1, r);
            }
            if (_mode.equals("Rect")) {
                gs = new Rect_2D(_p1, p);
            }
            if (_mode.equals("Segment")) {
                gs = new Segment_2D(_p1, p);
            }

            if (_mode.equals("Polygon") || _mode.equals("Triangle")) {
                if (_pp == null) {
                    _pp = new Polygon_2D();
                    _pp.add(_p1);
                }
                Polygon_2D gg = new Polygon_2D(_pp);
                gg.add(p);
                gs = gg;
            }
            _gs = new GUIShape(gs, false, Color.pink, 0);
            drawShapes();
        }
    }

    /**
     * Returns the current collection of GUI shapes.
     * @return The current GUI_Shape_Collection.
     */
    @Override
    public GUI_Shape_Collection getShape_Collection() {
        // TODO Auto-generated method stub
        return this._shapes;
    }

    /**
     * Shows the graphical user interface using default dimensions.
     */
    @Override
    public void show() {
        show(Ex2_Const.DIM_SIZE);
    }

    /**
     * returns information about all shapes in the current collection.
     * @return A string containing information for all shapes in the collection.
     */
    @Override
    public String getInfo() {
        // TODO Auto-generated method stub
        String ans = "";
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            ans += s.toString() + "\n";
            //	ans +=s.toString()+"  area: "+s.getShape().area()+"  per: "+s.getShape().perimeter()+"\n";
        }
        return ans;
    }
}

