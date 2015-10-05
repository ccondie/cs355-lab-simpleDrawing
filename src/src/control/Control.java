package src.control;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import src.model.Model;
import cs355.GUIFunctions;
import cs355.JsonShape;
import cs355.controller.CS355Controller;
import cs355.model.drawing.*;


public class Control implements CS355Controller
{
	private static final Gson gson = new GsonBuilder().setPrettyPrinting()
			.registerTypeAdapter(Shape.class, new JsonShape()).create();
	
	boolean activeShape = false;

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		System.out.println("Mouse Pressed: x-" + arg0.getX() + " y-" + arg0.getY());
		
		if(activeShape)
		{
			if(Model.get().getRecent() instanceof Line)
			{	activeShape = false;	}
			
			if(Model.get().getRecent() instanceof Square)
			{	activeShape = false;	}
			
			if(Model.get().getRecent() instanceof Rectangle)
			{	activeShape = false;	}
			
			if(Model.get().getRecent() instanceof Circle)
			{	activeShape = false;	}
			
			if(Model.get().getRecent() instanceof Ellipse)
			{	activeShape = false;	}
			
			if(Model.get().getRecent() instanceof Triangle)
			{	
				Triangle focus = (Triangle)Model.get().getRecent();
				if(focus.getCornerCount() == 2)
				{
					focus.setC(new Point2D.Double(arg0.getX(), arg0.getY()));
					focus.incCornerCount();
					GUIFunctions.refresh();
					activeShape = false;
				}
				
				if(focus.getCornerCount() == 1)
				{
					//add second corner
					focus.setB(new Point2D.Double(arg0.getX(), arg0.getY()));
					focus.incCornerCount();
				}
			}
		}
		else
		{
			switch(Model.get().currentMode)
			{
			case 0:
				Model.get().addShape(new Line(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()), new Point2D.Double(arg0.getX(), arg0.getY())));
				activeShape = true;
				break;
			case 1: 
				Model.get().addShape(new Square(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()), 0));
				activeShape = true;
				break;
			case 2: 
				Model.get().addShape(new Rectangle(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()),0, 0));
				activeShape = true;
				break;
			case 3: 
				Model.get().addShape(new Circle(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()),0));
				activeShape = true;
				break;
			case 4: 
				Model.get().addShape(new Ellipse(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()),0, 0));
				activeShape = true;
				break;
			case 5: 
				Model.get().addShape(new Triangle(Model.selectedColor, new Point2D.Double(arg0.getX(), arg0.getY()), null, null));
				activeShape = true;
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{	}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{	
//		System.out.println("Mouse Moved: x-" + arg0.getX() + " y-" + arg0.getY());
		
		if(activeShape)
		{	
			Shape focus = Model.get().getRecent();
			
			if(focus instanceof Line)
			{	handleActiveLine(arg0);	}
			
			if(focus instanceof Square)
			{	handleActiveSquare(arg0);	}
			
			if(focus instanceof Rectangle)
			{	handleActiveRectangle(arg0);	}
			
			if(focus instanceof Circle)
			{	handleActiveCircle(arg0);	}
			
			if(focus instanceof Ellipse)
			{	handleActiveEllipse(arg0);	}
			
			if(focus instanceof Triangle)
			{	}
		
			GUIFunctions.refresh();
		}
	}
	
	public void handleActiveSquare(MouseEvent arg0)
	{
//		System.out.println("instance: Square");
		
		Square focusSquare = (Square) Model.get().getRecent();
		//if the cursor is moving below the upperleft corner
		if(arg0.getY() > focusSquare.getFirstCorner().y)
		{
			//if the cursor is moving to the bottomright quad
			if(arg0.getX() > focusSquare.getFirstCorner().x)
			{
				double lengthX = arg0.getX() - focusSquare.getFirstCorner().x;
				double lengthY = arg0.getY() - focusSquare.getFirstCorner().y;
				double newlength = Math.min(lengthX, lengthY);
				
				focusSquare.setUpperLeft(focusSquare.getFirstCorner());
				focusSquare.setSize(newlength);
			}

			//if the cursor is moving to the bottomleft quad
			if(arg0.getX() < focusSquare.getFirstCorner().x)
			{
				double lengthX = focusSquare.getFirstCorner().x - arg0.getX();
				double lengthY = arg0.getY() - focusSquare.getFirstCorner().y;
				double newlength = Math.min(lengthX, lengthY);
				
				focusSquare.setUpperLeft(new Point2D.Double(focusSquare.getFirstCorner().x - newlength, focusSquare.getFirstCorner().y));
				focusSquare.setSize(newlength);
			}
		}

		//if the cursor is moving above the upperleft corner
		if(arg0.getY() < focusSquare.getFirstCorner().y)
		{
			//if the cursor is moving to the upperright quad
			if(arg0.getX() > focusSquare.getFirstCorner().x)
			{
				double lengthX = arg0.getX() - focusSquare.getFirstCorner().x;
				double lengthY = focusSquare.getFirstCorner().y - arg0.getY();
				double newlength = Math.min(lengthX, lengthY);
				
				focusSquare.setUpperLeft(new Point2D.Double(focusSquare.getFirstCorner().x, focusSquare.getFirstCorner().y  - newlength));
				focusSquare.setSize(newlength);
			}

			//if the cursor is moving to the upperleft quad
			if(arg0.getX() < focusSquare.getFirstCorner().x)
			{
				double lengthX = focusSquare.getFirstCorner().x - arg0.getX();
				double lengthY = focusSquare.getFirstCorner().y - arg0.getY();
				double newlength = Math.min(lengthX, lengthY);
				
				focusSquare.setUpperLeft(new Point2D.Double(focusSquare.getFirstCorner().x - newlength, focusSquare.getFirstCorner().y - newlength));
				focusSquare.setSize(newlength);
			}
		}
		Model.get().setRecent(focusSquare);
	}

	public void handleActiveLine(MouseEvent arg0)
	{
//		System.out.println("instance: Line");
		
		Line focus = (Line) Model.get().getRecent();
		focus.setEnd(new Point2D.Double(arg0.getX(), arg0.getY()));
		
		Model.get().setRecent(focus);
	}
	
	public void handleActiveRectangle(MouseEvent arg0)
	{
//		System.out.println("instance: Rectangle");
		
		Rectangle focusRect = (Rectangle) Model.get().getRecent();
		
		//if the cursor is moving below the upperleft corner
		if(arg0.getY() > focusRect.getFirstCorner().y)
		{
			//if the cursor is moving to the bottomright quad
			if(arg0.getX() > focusRect.getFirstCorner().x)
			{
				double lengthX = arg0.getX() - focusRect.getFirstCorner().x;
				double lengthY = arg0.getY() - focusRect.getFirstCorner().y;
				
				focusRect.setUpperLeft(focusRect.getFirstCorner());
				focusRect.setWidth(lengthX);
				focusRect.setHeight(lengthY);
			}

			//if the cursor is moving to the bottomleft quad
			if(arg0.getX() < focusRect.getFirstCorner().x)
			{
				double lengthX = focusRect.getFirstCorner().x - arg0.getX();
				double lengthY = arg0.getY() - focusRect.getFirstCorner().y;
				
				focusRect.setUpperLeft(new Point2D.Double(arg0.getX(), focusRect.getFirstCorner().y));
				focusRect.setWidth(lengthX);
				focusRect.setHeight(lengthY);
			}
		}

		//if the cursor is moving above the upperleft corner
		if(arg0.getY() < focusRect.getFirstCorner().y)
		{
			//if the cursor is moving to the upperright quad
			if(arg0.getX() > focusRect.getFirstCorner().x)
			{
				double lengthX = arg0.getX() - focusRect.getFirstCorner().x;
				double lengthY = focusRect.getFirstCorner().y - arg0.getY();
				
				focusRect.setUpperLeft(new Point2D.Double(focusRect.getFirstCorner().x, focusRect.getFirstCorner().y  - lengthY));
				focusRect.setWidth(lengthX);
				focusRect.setHeight(lengthY);
			}

			//if the cursor is moving to the upperleft quad
			if(arg0.getX() < focusRect.getFirstCorner().x)
			{
				double lengthX = focusRect.getFirstCorner().x - arg0.getX();
				double lengthY = focusRect.getFirstCorner().y - arg0.getY();
				
				focusRect.setUpperLeft(new Point2D.Double(arg0.getX(), arg0.getY()));
				focusRect.setWidth(lengthX);
				focusRect.setHeight(lengthY);
			}
		}
		Model.get().setRecent(focusRect);
	}
	
	public void handleActiveCircle(MouseEvent arg0)
	{
//		System.out.println("instance: Circle");
		
		Circle focusCircle = (Circle) Model.get().getRecent();
		//if the cursor is moving below the upperleft corner
		if(arg0.getY() > focusCircle.getFirstCorner().y)
		{
			//if the cursor is moving to the bottomright quad
			if(arg0.getX() > focusCircle.getFirstCorner().x)
			{
				double lengthX = arg0.getX() - focusCircle.getFirstCorner().x;
				double lengthY = arg0.getY() - focusCircle.getFirstCorner().y;
				double newlength = Math.min(lengthX, lengthY);
				
				focusCircle.setUpperLeft(focusCircle.getFirstCorner());
				focusCircle.setRadius(newlength / 2);
			}

			//if the cursor is moving to the bottomleft quad
			if(arg0.getX() < focusCircle.getFirstCorner().x)
			{
				double lengthX = focusCircle.getFirstCorner().x - arg0.getX();
				double lengthY = arg0.getY() - focusCircle.getFirstCorner().y;
				double newlength = Math.min(lengthX, lengthY);
				
				focusCircle.setUpperLeft(new Point2D.Double(focusCircle.getFirstCorner().x - newlength, focusCircle.getFirstCorner().y));
				focusCircle.setRadius(newlength / 2);
			}
		}

		//if the cursor is moving above the upperleft corner
		if(arg0.getY() < focusCircle.getFirstCorner().y)
		{
			//if the cursor is moving to the upperright quad
			if(arg0.getX() > focusCircle.getFirstCorner().x)
			{
				double lengthX = arg0.getX() - focusCircle.getFirstCorner().x;
				double lengthY = focusCircle.getFirstCorner().y - arg0.getY();
				double newlength = Math.min(lengthX, lengthY);
				
				focusCircle.setUpperLeft(new Point2D.Double(focusCircle.getFirstCorner().x, focusCircle.getFirstCorner().y  - newlength));
				focusCircle.setRadius(newlength / 2);
			}

			//if the cursor is moving to the upperleft quad
			if(arg0.getX() < focusCircle.getFirstCorner().x)
			{
				double lengthX = focusCircle.getFirstCorner().x - arg0.getX();
				double lengthY = focusCircle.getFirstCorner().y - arg0.getY();
				double newlength = Math.min(lengthX, lengthY);
				
				focusCircle.setUpperLeft(new Point2D.Double(focusCircle.getFirstCorner().x - newlength, focusCircle.getFirstCorner().y - newlength));
				focusCircle.setRadius(newlength / 2);
			}
		}
		Model.get().setRecent(focusCircle);
	}
	
	public void handleActiveEllipse(MouseEvent arg0)
	{
//		System.out.println("instance: Rectangle");
		
		Ellipse focusElli = (Ellipse) Model.get().getRecent();
		
		//if the cursor is moving below the upperleft corner
		if(arg0.getY() > focusElli.getFirstCorner().y)
		{
			//if the cursor is moving to the bottomright quad
			if(arg0.getX() > focusElli.getFirstCorner().x)
			{
				double lengthX = arg0.getX() - focusElli.getFirstCorner().x;
				double lengthY = arg0.getY() - focusElli.getFirstCorner().y;
				
				focusElli.setUpperLeft(focusElli.getFirstCorner());
				focusElli.setWidth(lengthX);
				focusElli.setHeight(lengthY);
			}

			//if the cursor is moving to the bottomleft quad
			if(arg0.getX() < focusElli.getFirstCorner().x)
			{
				double lengthX = focusElli.getFirstCorner().x - arg0.getX();
				double lengthY = arg0.getY() - focusElli.getFirstCorner().y;
				
				focusElli.setUpperLeft(new Point2D.Double(arg0.getX(), focusElli.getFirstCorner().y));
				focusElli.setWidth(lengthX);
				focusElli.setHeight(lengthY);
			}
		}

		//if the cursor is moving above the upperleft corner
		if(arg0.getY() < focusElli.getFirstCorner().y)
		{
			//if the cursor is moving to the upperright quad
			if(arg0.getX() > focusElli.getFirstCorner().x)
			{
				double lengthX = arg0.getX() - focusElli.getFirstCorner().x;
				double lengthY = focusElli.getFirstCorner().y - arg0.getY();
				
				focusElli.setUpperLeft(new Point2D.Double(focusElli.getFirstCorner().x, focusElli.getFirstCorner().y  - lengthY));
				focusElli.setWidth(lengthX);
				focusElli.setHeight(lengthY);
			}

			//if the cursor is moving to the upperleft quad
			if(arg0.getX() < focusElli.getFirstCorner().x)
			{
				double lengthX = focusElli.getFirstCorner().x - arg0.getX();
				double lengthY = focusElli.getFirstCorner().y - arg0.getY();
				
				focusElli.setUpperLeft(new Point2D.Double(arg0.getX(), arg0.getY()));
				focusElli.setWidth(lengthX);
				focusElli.setHeight(lengthY);
			}
		}
		Model.get().setRecent(focusElli);
	}
	
	
	@Override
	public void colorButtonHit(Color c) 
	{	
		System.out.println("Color: r-" + c.getRed() + " g-" + c.getGreen() + " b-" + c.getBlue());
		Model.get().setColor(c);
		GUIFunctions.changeSelectedColor(c);
	}

	@Override
	public void lineButtonHit() 
	{	Model.get().setMode(0);}

	@Override
	public void squareButtonHit() 
	{	Model.get().setMode(1);}

	@Override
	public void rectangleButtonHit() 
	{	Model.get().setMode(2);}

	@Override
	public void circleButtonHit() 
	{	Model.get().setMode(3);}

	@Override
	public void ellipseButtonHit() 
	{	Model.get().setMode(4);}

	@Override
	public void triangleButtonHit() 
	{	
		Model.get().setMode(5);
		

	}

	@Override
	public void selectButtonHit() 
	{	}
	
	@Override
	public void zoomInButtonHit() 
	{	}

	@Override
	public void zoomOutButtonHit() 
	{	}

	@Override
	public void hScrollbarChanged(int value) 
	{	}

	@Override
	public void vScrollbarChanged(int value) 
	{	}

	@Override
	public void openScene(File file) 
	{	}

	@Override
	public void toggle3DModelDisplay() 
	{	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) 
	{	}

	@Override
	public void openImage(File file) 
	{	}

	@Override
	public void saveImage(File file) 
	{	}

	@Override
	public void toggleBackgroundDisplay() 
	{	}

	@Override
	public void saveDrawing(File file) 
	{	
		try (FileOutputStream fos = new FileOutputStream(file + ".json")) 
		{
			// Get the list from the concrete class.
			List<Shape> data = Model.get().getShapes();
	
			// Convert the List to an array.
			Shape[] shapes = new Shape[data.size()];
			data.toArray(shapes);
	
			// Convert to JSON text and write it out.
			String json = gson.toJson(shapes, Shape[].class);
			fos.write(json.getBytes());
		}
		catch (IOException ex) 
		{
		Logger.getLogger(CS355Drawing.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void openDrawing(File file) 
	{	
		// Make a blank list.
		List<Shape> shapes = null;
		
		try 
		{
			// Read the entire file in. I hate partial I/O.
			byte[] b = Files.readAllBytes(file.toPath());
			// Validation.
			if (b == null) 
			{
				throw new IOException("Unable to read drawing");
			}

			// Convert it to text.System.out.println("A");
			String data = new String(b, StandardCharsets.UTF_8);
			// Use Gson to convert the text to a list of Shapes.
			Shape[] list = gson.fromJson(data, Shape[].class);
			shapes = new ArrayList<>(Arrays.asList(list));
		}
		catch (IOException | JsonSyntaxException ex) 
		{
			Logger.getLogger(CS355Drawing.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Set the new shape list.
		Model.get().setShapes(shapes);
		GUIFunctions.refresh();
	}

	@Override
	public void doDeleteShape() 
	{	}

	@Override
	public void doEdgeDetection() 
	{	}

	@Override
	public void doSharpen() 
	{	}

	@Override
	public void doMedianBlur() 
	{	}

	@Override
	public void doUniformBlur() 
	{	}
	
	@Override
	public void doGrayscale() 
	{	}

	@Override
	public void doChangeContrast(int contrastAmountNum) 
	{	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) 
	{	}

	@Override
	public void doMoveForward() 
	{	}

	@Override
	public void doMoveBackward() 
	{	}

	@Override
	public void doSendToFront() 
	{	}

	@Override
	public void doSendtoBack() 
	{	}

}
