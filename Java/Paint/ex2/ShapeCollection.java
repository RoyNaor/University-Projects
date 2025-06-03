package ex2.ex2;

import ex2.geo.*;
import ex2.gui.GUIShape;
import ex2.gui.GUI_Shape;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a collection of GUI_Shape.
 * Ex2: you should implement this class!
 * @author Roy Naor
 * ID - 323917104
 */
public class ShapeCollection implements GUI_Shape_Collection {
	private ArrayList<GUI_Shape> _shapes;
	
	public ShapeCollection() {
		_shapes = new ArrayList<GUI_Shape>();
	}

	@Override
	public GUI_Shape get(int i) {
		return _shapes.get(i);
	}

	@Override
	public int size() {
		return _shapes.size();
	}

	@Override
	public GUI_Shape removeElementAt(int i) {return this._shapes.remove(i);}

	@Override
	public void addAt(GUI_Shape s, int i) {
		if(s!=null && s.getShape()!=null) {
			this._shapes.add(i, s);
		}
	}

	@Override
	public void add(GUI_Shape s) {
		if(s!=null && s.getShape()!=null) {
			_shapes.add(s);
		}
	}

	@Override
	public ShapeCollection copy() {
		ShapeCollection copyCollection = new ShapeCollection();
		for (GUI_Shape shape : this._shapes) {
			copyCollection.add(shape.copy());
		}
		return copyCollection;
	}

	@Override
	public void sort(Comparator<GUI_Shape> comp) {
		this._shapes.sort(comp);
	}

	@Override
	public void removeAll() {this._shapes.clear();}

	@Override
	public void save(String file_name) {
		String content = this._shapes.toString().replace("[", "").replace("]", "");
		String modifiedContent = content.replaceAll("(?<!^)(GUIShape)", "\n$1");
		String path = file_name + ".txt";
		try {
			Files.writeString(Paths.get(path), modifiedContent.trim());
			System.out.println("File saved successfully.");
		} catch (Exception e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}


	@Override
	public void load(String file) {
		try {
			String path = file;
			String content = new String(Files.readAllBytes(Paths.get(path)));
			String[] string_shapes = content.split("\n"); // split into the shapes
			for (String shape : string_shapes) {
				String[] prop = shape.split(","); // split into the properties of every shape
				System.out.println(Arrays.toString(prop));
				if ("Rect_2D".equals(prop[4])) {
					double xTop = Double.parseDouble(prop[5]);
					double yTop = Double.parseDouble(prop[6]);
					double xRight = Double.parseDouble(prop[9]);
					double yRight = Double.parseDouble(prop[10]);
					boolean isFilled = Boolean.parseBoolean(prop[2]);
					int tag = Integer.parseInt(prop[3]);
					Point_2D Top = new Point_2D(xTop, yTop);
					System.out.println(Top);
					Point_2D RightBottom = new Point_2D(xRight, yRight);
					System.out.println(RightBottom);
					Rect_2D r = new Rect_2D(Top, RightBottom);
					GUI_Shape gs = new GUIShape(r, isFilled, Color.decode(prop[1]), tag);
					this._shapes.add(gs);
				}
				if ("Circle_2D".equals(prop[4])) {
					double x = Double.parseDouble(prop[5]);
					double y = Double.parseDouble(prop[6]);
					double radius = Double.parseDouble(prop[7]);
					boolean isFilled = Boolean.parseBoolean(prop[2]);
					int tag = Integer.parseInt(prop[3]);
					Point_2D cen = new Point_2D(x, y);
					Circle_2D c = new Circle_2D(cen, radius);
					GUI_Shape gs = new GUIShape(c, isFilled, Color.decode(prop[1]), tag);
					this._shapes.add(gs);
				}
				if ("Triangle_2D".equals(prop[4])) {
					ArrayList<Point_2D> vertex = new ArrayList<>();
					for (int i = 5; i < prop.length; i += 2) {
						double x = Double.parseDouble(prop[i]);
						double y = Double.parseDouble(prop[i + 1]);
						vertex.add(new Point_2D(x, y));
					}

					Triangle_2D t = new Triangle_2D(vertex.get(0), vertex.get(1), vertex.get(2));

					boolean isFilled = Boolean.parseBoolean(prop[2]);
					int tag = Integer.parseInt(prop[3]);
					Color color = Color.decode(prop[1]);

					GUI_Shape gs = new GUIShape(t, isFilled, color, tag);
					this._shapes.add(gs);
				}
				if ("Segment_2D".equals(prop[4])) {
					double xA = Double.parseDouble(prop[5]);
					double yA = Double.parseDouble(prop[6]);
					double xB = Double.parseDouble(prop[7]);
					double yB = Double.parseDouble(prop[8]);

					Point_2D A = new Point_2D(xA, yA);
					Point_2D B = new Point_2D(xB, yB);

					boolean isFilled = Boolean.parseBoolean(prop[2]);
					int tag = Integer.parseInt(prop[3]);
					Color color = Color.decode(prop[1]);

					Segment_2D t = new Segment_2D(A, B);

					GUI_Shape gs = new GUIShape(t, isFilled, color, tag);
					this._shapes.add(gs);
				}
				if ("Polygon_2D".equals(prop[4])) {
					Polygon_2D p = new Polygon_2D();
					for (int i = 5; i + 1 < prop.length; i += 2) {
						double x = Double.parseDouble(prop[i]);
						double y = Double.parseDouble(prop[i + 1]);
						p.add(new Point_2D(x, y));
					}

					boolean isFilled = Boolean.parseBoolean(prop[2]);
					int tag = Integer.parseInt(prop[3]);
					Color color = Color.decode(prop[1]);

					GUI_Shape gs = new GUIShape(p, isFilled, color, tag);
					this._shapes.add(gs);
				}
			}

		} catch (IOException e) {
			System.err.println("Cannot read the file not in the correct format: " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		String ans = "";
		for(int i=0;i<size();i=i+1) {
			ans += this.get(i) + "\n";
		}
		return ans;
	}
	

}
