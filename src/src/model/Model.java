package src.model;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Observer;

import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.Shape;

public class Model extends CS355Drawing
{
	private static Model model = null;
	
	private Model()
	{	model = this;	}
	
	public static Model get()
	{
		if(model == null)
		{
			model = new Model();
			selectedColor = Color.WHITE;
			shapes = new ArrayList<Shape>();
			observers = new ArrayList<Observer>();
		}
		return model;
	}
	
	/*
	 * used to store the current selected mode
	 * -1:default
	 * 0:line
	 * 1:square
	 * 2:rectangle
	 * 3:circle
	 * 4:ellipse
	 * 5:triangle
	 */
	public int currentMode = -1;
	
	public static Color selectedColor;
	public static ArrayList<Shape> shapes;
	public static ArrayList<Observer> observers;
	
	public void setColor(Color c)
	{	
		selectedColor = c;	
		
		setChanged();
		notifyObservers();
	}
	
	public Color getColor()
	{	return selectedColor;	}
	
	public void setMode(int newMode)
	{	
		currentMode = newMode;	
		System.out.print("Model: currentMode set to: ");
		switch(currentMode)
		{
		case 0: System.out.print("line" + "\n");
			break;
		case 1: System.out.print("square" + "\n");
			break;
		case 2: System.out.print("rectangle" + "\n");
			break;
		case 3: System.out.print("circle" + "\n");
			break;
		case 4: System.out.print("ellipse" + "\n");
			break;
		case 5: System.out.print("triangle" + "\n");
			break;
		}
	}
	
	//Notifies the observers (the View) that a change has been made
	public void notifyObservers()
	{
		super.notifyObservers();
		System.out.println("Model Notified Observers");
	}
	
	@Override
	public Shape getShape(int index) 
	{	return shapes.get(index);	}

	public Shape getRecent()
	{	return shapes.get(shapes.size() - 1);	}
	
	public void setRecent(Shape newRecent)
	{	
		shapes.remove(shapes.size() - 1);
		shapes.add(newRecent);
	}
	
	@Override
	public int addShape(Shape s) 
	{
		shapes.add(s);
		return shapes.size();
	}

	@Override
	public void deleteShape(int index) 
	{
		shapes.remove(index);
	}

	@Override
	public void moveToFront(int index) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movetoBack(int index) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveForward(int index) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBackward(int index) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Shape> getShapes() 
	{	return shapes;	}

	@Override
	public List<Shape> getShapesReversed() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShapes(List<Shape> shapes) 
	{
		// TODO Auto-generated method stub
		this.shapes = (ArrayList<Shape>) shapes;
	}

}
