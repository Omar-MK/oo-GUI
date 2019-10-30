package guiDelegate;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import model.MandelbrotSetGenerator;

/**
 * This class contains the code that renders the Mandelbrot set.
 * The class extends JPanel and implements both the MouseListener and
 * MouseMotionListener interfaces.
 * resolution
 * @author 170018405
 * @version 0.1
 */
public class MyGraphicalDisplayPanel extends JPanel implements MouseListener,
MouseMotionListener
{
	/*
	 * This field records the starting x coordinate when a mouse button is
	 * pressed.
	 */
	private int startXLoc;

	/*
	 * This field records the starting y coordinate when a mouse button is
	 * pressed.
	 */
	private int startYLoc;

	/*
	 * This field records the final x coordinate when a mouse button is
	 * released.
	 */
	private int currentXLoc;

	/*
	 * This field records the starting y coordinate when a mouse button is
	 * released.
	 */
	private int currentYLoc;

	/*
	 * The delegate object. Used to get access to methods to tell this class
	 * whether the user wants to pan or zoom, and whether the magnification
	 * estimate should be displayed.
	 */
	private MandelbrotGuiDelegate delegate;

	/*
	 * The model object. Used to retireve the Mandelbrot set.
	 */
	private MandelbrotSetGenerator model;

	/**
	 * Constructor instantiates a JPanel object given a delegate and
	 * MandelbrotSetGenerator object.
	 *
	 * @param delegate the delegate object. Used to get access to methods to
	 * tell this class whether the user wants to pan or zoom, and whether the
	 * magnification estimate should be displayed.
	 * @param model the model object. Used to retireve the Mandelbrot set.
	 */
	public MyGraphicalDisplayPanel(MandelbrotGuiDelegate delegate,
	MandelbrotSetGenerator model)
	{
		this.delegate = delegate;
		this.model = model;

		int width = Config.GRAPHIC_WIDTH;
		int height = Config.GRAPHIC_HEIGHT;

		this.setPreferredSize(new Dimension(Config.GRAPHIC_WIDTH,
		Config.GRAPHIC_HEIGHT));
		// register this JPanel as a listener
		addMouseMotionListener(this);
		// register this JPanel as a listener
		addMouseListener(this);
	}

	/**
	 * Paints the rendered image to the JPanel.
	 */
	public void paint(Graphics g)
	{
		// get the Mandelbrot set 2d array
		int[][] set = model.getSet();
		// get the maxIterations
		int maxIterations = model.getState().getMaxIterations();
		// construct a ColorMixer Object to color the set
		ColorMixer color = new ColorMixer(maxIterations);
		// iterate over every pixel
		for(int j = 0; j < set.length; j++)
		{
			for (int i = 0; i < set[j].length; i++)
			{
				/* if the current pixel has an iteration value equal to max
				iterations, draw it in black */
				if (set[i][j] == maxIterations)
				{
					g.setColor(Color.BLACK);
				}
				/* otherwise color it using the getColor method in the
				ColorMixer Object */
				else
				{
					g.setColor(new Color(color.getColor(set[i][j],
					delegate.getColorOption())));
				}
				g.drawLine(j, i, j, i);
			}
		}

		// Check if magnification estimate was requested
		if (delegate.getDisplayMagChoice())
		{
			// set color to gray
			g.setColor(Color.lightGray);
			// make the text bold
			g.setFont(new Font("default", Font.BOLD, 16));
			// write maginification estimate to JPanel
			g.drawString("Magnification x " + model.getMagnification(), 10, 20);
			// write mouse x and y coordinates to JPanel
			g.drawString("x: " + currentXLoc + "  y: " + currentYLoc, 10, 40);
		}

		// Check what shape to draw when a mouse drag is detected
		if (delegate.getMouseDragOperation())
		{
			// drawing zoom selection rectangle
			g.setColor(Color.lightGray);
			g.drawRect(startXLoc, startYLoc, currentXLoc - startXLoc,
			currentXLoc - startXLoc);
		}
		else
		{
			// drawing a line to show pan direction and magnitude
			g.setColor(Color.gray);
			g.drawLine(startXLoc, startYLoc, currentXLoc, currentYLoc);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		currentXLoc = e.getX();
		currentYLoc = e.getY();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		startXLoc = e.getX();
		startYLoc = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		currentXLoc = e.getX();
		currentYLoc = e.getY();
		if (delegate.getMouseDragOperation())
		{
			int diff = e.getX() - startXLoc;
			model.renderBetween(startXLoc, startYLoc, e.getX(), startYLoc +
			diff);
		}
		else
		{
			int xShift = (currentXLoc - startXLoc);
			int yShift = (currentYLoc - startYLoc);
			model.shiftBounds(xShift, yShift, Config.MOUSE_SENS);
		}
		startXLoc = startYLoc = currentXLoc = currentYLoc = 0;
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}
}
