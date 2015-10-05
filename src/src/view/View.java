package src.view;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Observable;

import src.model.Model;
import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.view.ViewRefresher;

public class View implements ViewRefresher
{
	public Rectangle2D rect = new Rectangle2D.Double(10, 10, 10, 10);

	@Override
	public void update(Observable arg0, Object arg1) 
	{
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) 
	{
		// TODO Auto-generated method stub
		ArrayList<Shape> currentShapes = (ArrayList<Shape>) Model.get().getShapes();
		
		for(int i = 0; i < currentShapes.size(); i++)
		{
			Shape focus = currentShapes.get(i);
			
			g2d.setColor(focus.getColor());
			g2d.fill(shapeFactory(focus));
			g2d.draw(shapeFactory(focus));
		}
	}
	
	public java.awt.Shape shapeFactory(Shape focus)
	{
		if(focus instanceof Line)
		{
			Line focusLine = (Line)focus;
			Point2D.Double start = new Point2D.Double(focusLine.getStart().x, focusLine.getStart().y);		
			Point2D.Double end = new Point2D.Double(focusLine.getEnd().x, focusLine.getEnd().y);
			
			return new Line2D.Double(start.x, start.y, end.x, end.y);
		}
		
		if(focus instanceof Square)
		{
			//create a Rectangle2D object and return it
			double x = ((Square) focus).getUpperLeft().x;
			double y = ((Square) focus).getUpperLeft().y;
			double width = ((Square) focus).getSize();
			
			return new Rectangle2D.Double(x, y, width, width);
		}
		
		if(focus instanceof Rectangle)
		{
			//create a Rectangle2D object and return it
			double x = ((Rectangle) focus).getUpperLeft().x;
			double y = ((Rectangle) focus).getUpperLeft().y;
			double width = ((Rectangle) focus).getWidth();
			double height = ((Rectangle) focus).getHeight();
			
			return new Rectangle2D.Double(x, y, width, height);
		}
		
		if(focus instanceof Circle)
		{
			//create a Rectangle2D object and return it
			double x = ((Circle) focus).getUpperLeft().x;
			double y = ((Circle) focus).getUpperLeft().y;
			double radius = ((Circle) focus).getRadius();
			
			return new Ellipse2D.Double(x, y, radius * 2, radius * 2);
		}
		
		if(focus instanceof Ellipse)
		{
			//create a Rectangle2D object and return it
			double x = ((Ellipse) focus).getUpperLeft().x;
			double y = ((Ellipse) focus).getUpperLeft().y;
			double width = ((Ellipse) focus).getWidth();
			double height = ((Ellipse) focus).getHeight();
			
			return new Ellipse2D.Double(x, y, width, height);
		}
		
		if(focus instanceof Triangle)
		{
			//create a Rectangle2D object and return it
			if(((Triangle) focus).getCornerCount() == 3)
			{
				int[] x = new int[3];
				int[] y = new int[3];
				
				x[0] = (int) ((Triangle) focus).getA().x;
				x[1] = (int) ((Triangle) focus).getB().x;
				x[2] = (int) ((Triangle) focus).getC().x;
				
				y[0] = (int) ((Triangle) focus).getA().y;
				y[1] = (int) ((Triangle) focus).getB().y;
				y[2] = (int) ((Triangle) focus).getC().y;
				
				Polygon tri = new Polygon();
				tri.addPoint(x[0], y[0]);
				tri.addPoint(x[1], y[1]);
				tri.addPoint(x[2], y[2]);
				return tri;
			}
			else
			{
				return null;
			}
		}
		
		return null;
	}

}
