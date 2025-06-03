package ex2.ex2;

import ex2.geo.Circle_2D;
import ex2.geo.Point_2D;
import ex2.geo.Rect_2D;
import ex2.geo.Triangle_2D;
import ex2.gui.Ex2;
import ex2.gui.GUIShape;
import ex2.gui.GUI_Shape;

import java.awt.*;

/**
 * This class is a very simple main for starting with Ex2
 * You may update this file, but do noteL your Ex2 will be run using our own "main" class
 * Make sure you test the loadDemo(); function once you have implemented it (do test the "save" as well)
 */
public class Ex2_main {

	public static void main(String[] args) {
		simpleShapes();
		//loadDemo();
	}

	// Two simple GeoShapes
	public static void simpleShapes() {
		Ex2 ex2 = Ex2.getInstance();
		GUI_Shape_Collection shapes = ex2.getShape_Collection();

		Point_2D p1 = new Point_2D(2.88768,8.32435); // the center for the Circle 1
		Point_2D top = new Point_2D(4.4335,4.35466);
		Point_2D right = new Point_2D(6,2);
		Point_2D left = new Point_2D(2, 2);


		Circle_2D c1 = new Circle_2D(p1,2);
		//Circle_2D c2 = new Circle_2D(p3,3);
		Triangle_2D t1 = new Triangle_2D(top, right, left);
		Rect_2D r1 = new Rect_2D(p1, top);
		Rect_2D r2 = new Rect_2D(right, left);


		GUI_Shape gs1 = new GUIShape(r1, true, Color.blue, 1);
		GUI_Shape gs2 = new GUIShape(c1, true, Color.red, 2);
		GUI_Shape gs3 = new GUIShape(r2, true, Color.red, 2);

		shapes.add(gs1);
		shapes.add(gs2);
		ex2.init(shapes);
		ex2.show();
	}



	// Loads a file from file a1.txt.
	public static void loadDemo() {
		Ex2 ex2 = Ex2.getInstance();
		GUI_Shape_Collection shapes = ex2.getShape_Collection();
		String file = "a1.txt"; //make sure the file is your working directory.
		shapes.load(file);
		ex2.init(shapes);
		ex2.show();
	}

}
