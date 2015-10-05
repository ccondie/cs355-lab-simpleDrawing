package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Add your circle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Circle extends Shape 
{

	// The center of this shape.
	private Point2D.Double center;
	private Point2D.Double upperLeft;
	private Point2D.Double firstCorner;

	// The radius.
	private double radius;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param radius the radius of the new shape.
	 */
	public Circle(Color color, Point2D.Double center, double radius) {

		// Initialize the superclass.
		super(color);

		// Set fields.
		this.upperLeft = center;
		this.firstCorner = center;
		this.center = center;
		this.radius = radius;
	}

	/**
	 * Getter for this shape's center.
	 * @return this shape's center as a Java point.
	 */
	public Point2D.Double getCenter() {
		return center;
	}
	
	public Point2D.Double getUpperLeft()
	{	return upperLeft;	}
	
	public void setUpperLeft(Point2D.Double newPoint)
	{	upperLeft = newPoint;	}
	
	public Point2D.Double getFirstCorner()
	{	return firstCorner;	}

	/**
	 * Setter for this shape's center.
	 * @param center the new center as a Java point.
	 */
	public void setCenter(Point2D.Double center) {
		this.center = center;
	}

	/**
	 * Getter for this Circle's radius.
	 * @return the radius of this Circle as a double.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Setter for this Circle's radius.
	 * @param radius the new radius of this Circle.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
