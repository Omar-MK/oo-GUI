package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.lang.Math;

import java.util.Stack;

/**
 * This is a Mandelbrot Set generator class which makes it possible for users
 * to pan, zoom, change render bounds, change the Mandelbrot Set settings,  and
 * undo/redo the changes made to the Mandelbrot Sets generated. This class can
 * therefore also be concidered as a collection class of MandelbrotState
 * Objects.
 * It is the model in the MandelbrotGuiDelegate class.
 *
 * The state of instances of this class are altered when the user requests a
 * change in how the mandelbrotSet appears - this typically causes a new
 * MandelbrotState to be created especailly when a new Set is required.
 *
 * The class uses two stacks (prevStates and nextStates) containing
 * MandelbrotState objects to keep track of the changes made. This allows for
 * undo/redo functionality
 *
 * The class supports change listeners to be notified when to change.
 * This form of loose coupling permits the Delegate (View) to be updated when
 * the model has changed.
 *
 * @author 170018405
 * @version 0.1
 */

public class MandelbrotSetGenerator
{
	/**
	 * Stack to keep track of previous states.
	 */
	private Stack<MandelbrotState> prevStates;

	/**
	 * Stack to keep track of next states whenever the undoState method is
	 * used to view old Mandelbrot Sets.
	 */
	private Stack<MandelbrotState> nextStates;

	/**
	 * The original state used to construct this Object.
	 */
	private MandelbrotState origonalState;

	/**
	 * 2D int array containing the Mandelbrot Set of the current
	 * MandelbrotState Object.
	 */
	private int[][] mandelbrotSet;

	/**
	 * The property change support object to use when notifying listeners of
	 * the model
	 */
	private PropertyChangeSupport notifier;


	/**
	 * Constructs a new MandelbrotSetGenerator Object using a MandelbrotState
	 * Object.
	 *
	 * @param mbs the inital MandelbrotState Object to calculate the Mandelbrot
	 * Set with
	 */
	public MandelbrotSetGenerator(MandelbrotState mbs)
	{
		origonalState = mbs;
		nextStates = new Stack<>();
		prevStates = new Stack<>();
		mandelbrotSet = mbs.getMandelbrotSet();
		prevStates.push(mbs);
		notifier = new PropertyChangeSupport(this);
	}

	/**
	 * Utility method to add an observer using this object's encapsulated
	 * property change support object.
	 *
	 * @param listener the listener to add
	 */
	public void addObserver(PropertyChangeListener listener)
	{
		notifier.addPropertyChangeListener("mandelbrotSet", listener);
	}

	/**
	 * This method return the mandelbrotSet of the current state.
	 *
	 * @return the manderbrotSet as a 2d array
	 */
	public int[][] getSet()
	{
		return mandelbrotSet;
	}

	/**
	 * This method returns the current MandelbrotState object containing the
	 * calculation parameters of the current Mandelbrot Set.
	 *
	 * @return the current MandelbrotState object
	 */
	public MandelbrotState getState()
	{
		return prevStates.peek();
	}

	/**
	 * This method sets the state of the Mandelbrot Set to the same state of
	 * the MandelbrotState Object passed in.
	 * Note: this will empty the nextStates stack.
	 *
	 * @param state the MandelbrotState Object who's state will be copied
	 */
	public void setState(MandelbrotState state)
	{
		updateMandelbrotSet(state);
		prevStates.push(state);
		nextStates.removeAllElements();
	}

	/**
	 * This method returns the current magnification.
	 *
	 * @return the current magnification
	 */
	public int getMagnification()
	{
		// the magnification is calculates as 1 / area
		double area = Math.abs(getState().getMaxReal() -
		getState().getMinReal()) * Math.abs(getState().getMaximaginary() -
		getState().getMinimaginary());
		return (int) (1 / area);
	}

	/**
	 * This method should be used to change the bounds of the Mandelbrot Set
	 * calculated. Forexample if panning or zooming is required.
	 * The method creates a new MandelbrotState instance using the new inputs,
	 * then calls the updateMandelbrotSet function.
 	 * Note: this will empty the nextStates stack.
	 *
	 * @param minReal the lower real bound for the complex constant C
 	 * @param maxReal the upper real bound for the complex constant C
 	 * @param minImaginary the lower imaginary bound for the complex constant C
 	 * @param maxImaginary the upper imaginary bound for the complex constant C
	 */
	public void setBounds(double minReal, double maxReal,
	double minImaginary, double maxImaginary)
	{
		MandelbrotState mbs = new MandelbrotState(prevStates.peek());
		mbs.setMinReal(minReal);
		mbs.setMaxReal(maxReal);
		mbs.setMinimaginary(minImaginary);
		mbs.setMaximaginary(maxImaginary);
		updateMandelbrotSet(mbs);
		prevStates.push(mbs);
		nextStates.removeAllElements();
	}

	/**
	 * This method should be used to shift the bounds of the Mandelbrot Set.
	 * This is useful for panning.
	 * The method takes the amount the set should be shifted by as pixel
	 * values in both the real and imaginary axis.
	 * Note: this will empty the nextStates stack.
	 *
	 * @param shiftReal the real number to add or substract from both the upper
	 * and lower real boundary.
	 * direction
	 * @param shiftimaginary the real number to add or substract from both the
	 * upper and lower imaginary boundary.
	 * @param shiftMultiplier a multiplier to increase / decrease the shift
	 * made by moving each pixel. Especially useful if fractions of pixel
	 * movements are required
	 * are required. For-example, half pixel shifts.
	 */
	public void shiftBounds(int realShift, int imaginaryShift,
	double shiftMultiplier)
	{
		/* calculate how much shift each pixel pan shifts the real and
		imaginerary min/max values */
		// calculating real value per pixel
		double hPixel = Math.abs(getState().getMaxReal()
		- getState().getMinReal()) / getState().getXRes();

		// calculating imaginary value per pixel
		double vPixel = Math.abs(getState().getMaximaginary()
		- getState().getMinimaginary()) / getState().getYRes();

		// calculating horizontal and vertical shift
		double hShift = realShift * hPixel * shiftMultiplier;
		double vShift = imaginaryShift * vPixel * shiftMultiplier;

		// create a new state and set the new bounds then update
		MandelbrotState mbs = new MandelbrotState(prevStates.peek());
		mbs.setMinReal(mbs.getMinReal() + hShift);
		mbs.setMaxReal(mbs.getMaxReal() + hShift);
		mbs.setMinimaginary(mbs.getMinimaginary() + vShift);
		mbs.setMaximaginary(mbs.getMaximaginary() + vShift);
		updateMandelbrotSet(mbs);
		prevStates.push(mbs);
		nextStates.removeAllElements();
	}

	/**
	 * This method should be used to zoom into the MandelbrotSet currently
	 * being displayed by choosing two pixel coordinates.
	 *
	 * The method takes four integer parameters representing the real and
	 * imaginary lower and upper pixel boundaries.
	 * Note: this will empty the nextStates stack.
	 *
	 * @param minXPixel the new lower real boundary in pixels from the current
	 * lower real boundary
	 * @param minYPixel the new lower imaginary boundary in pixels from the
	 * current lower imaginary boundary
	 * @param maxXPixel the new upper real boundary in pixels from the current
	 * lower real boundary
	 * @param maxYPixel the new upper imaginary boundary in pixels from the
	 * current lower imaginary boundary
	 */
	public void renderBetween(int minXPixel, int minYPixel, int maxXPixel,
	int maxYPixel)
	{
		// get the real number per pixel
		double realDiv = Math.abs(getState().getMaxReal()
		- getState().getMinReal()) / getState().getXRes();
		// get the imaginary number per pixel
		double imaginaryDiv = Math.abs(getState().getMaximaginary()
		- getState().getMinimaginary()) / getState().getYRes();

		// calculate the new boundaries
		double minReal = minXPixel * realDiv + getState().getMinReal();

		double maxReal = getState().getMaxReal()
		- (Math.abs(getState().getXRes() - maxXPixel) * realDiv);

		double minImaginary = minYPixel * imaginaryDiv
		+ getState().getMinimaginary();

		double maxImaginary = getState().getMaximaginary()
		- (Math.abs(getState().getYRes() - maxYPixel) * imaginaryDiv);

		// change the bounds using the new boundaries
		setBounds(minReal, maxReal, minImaginary, maxImaginary);
	}

	/**
	 * This method should be used to change the squared-radius used when
	 * determining whether Z escaped the circle in the complex plain or
	 * remained bounded during the Mandelbrot Set calculation.
	 * The method creates a new MandelbrotState instance using the new input,
	 * then calls the updateMandelbrotSet function.
 	 * Note: this will empty the nextStates stack.
	 *
	 * @param sqRadius the squared radius to use when determining
	 */
	public void setSqRadius(double sqRadius)
	{
		MandelbrotState mbs = new MandelbrotState(prevStates.peek());
		mbs.setSqRadius(sqRadius);
		updateMandelbrotSet(mbs);
		prevStates.push(mbs);
		nextStates.removeAllElements();
	}

	/**
	 * This method should be used to change the maximum number of iterations to
	 * iterate the complex formula during calculation of the Mandelbrot Set.
	 * The method creates a new MandelbrotState instance using the new input,
	 * then calls the updateMandelbrotSet function.
 	 * Note: this will empty the nextStates stack.
	 *
	 * @param maxIterations the maximum number of iterations to iterate the
	 * complex formula
	 */
	public void setMaxIterations(int maxIterations)
	{
		MandelbrotState mbs = new MandelbrotState(prevStates.peek());
		mbs.setMaxIterations(maxIterations);
		updateMandelbrotSet(mbs);
		prevStates.push(mbs);
		nextStates.removeAllElements();
	}

	/**
	 * This method should be called when the resolution values need to be
	 * changed.
	 * The method creates a new MandelbrotState instance using the new input,
	 * then calls the updateMandelbrotSet function.
 	 * Note: this will empty the nextStates stack.
	 *
	 * @param xRes the new horizontal resolution
	 * @param yRes the new vertical resolution
	 */
	public void setResolution(int xRes, int yRes)
	throws IllegalArgumentException
	{
		if (xRes <= 0 || yRes <= 0)
		{
			throw new IllegalArgumentException("Resolution cannot be 0 or smaller");
		}
		MandelbrotState mbs = new MandelbrotState(prevStates.peek());
		mbs.setXRes(xRes);
		mbs.setYRes(yRes);
		updateMandelbrotSet(mbs);
		prevStates.push(mbs);
		nextStates.removeAllElements();
	}

	/**
	 * This method should be called to revert back to the previous Mandelbrot
	 * Set before the last change was made.
	 */
	public void undoState()
	{
		if(prevStates.size() > 1)
		{
			/* The idea is to move the last state in prevStates to nextStates
			then return the last item in prevStates */
			nextStates.push(prevStates.pop());
			/* now update mandelbrotSet to the state of the last item in
			prevStates */
			updateMandelbrotSet(getState());
		}
	}

	/**
	 * This method should be called to go back to the Mandelbrot Set before the
	 * last undo call was made.
	 */
	public void redoState()
	{
		if(!nextStates.empty())
		{
			/* The idea is to move the last item in nextStates to prevStates
			then return the last item in prevStates */
			prevStates.push(nextStates.pop());
			updateMandelbrotSet(getState());
		}
	}

	/**
	 * This method should be called to reset the Mandelbrot Set to the state
	 * given when the this Object was constructed.
	 * Note: this method will clear nextStates.
	 */
	public void reset()
	{
		updateMandelbrotSet(origonalState);
		prevStates.push(origonalState);
		nextStates.removeAllElements();
	}

	/**
	 * This is a helper method which is called by change methods to affect
	 * their changes.
	 * The method updates the mandelbrotSet using the getMandelbrotSet method
	 * of a MandelbrotState instance. Then, calls the firePropertyChange
	 * method of the notifier and finally adds the new MandelbrotState
	 * instance to the prevStates.
	 *
	 * @param mbs the new MandelbrotState instance to render
	 */
	private void updateMandelbrotSet(MandelbrotState mbs)
	{
		mandelbrotSet = mbs.getMandelbrotSet();
		notifier.firePropertyChange("mandelbrotSet", prevStates.peek(), mbs);
	}

	/**
	 * Returns a String of the Mandelbrot Set as a table of integers.
	 *
	 * @return  a String of the Mandelbrot Set as a table of integers
	 */
	@Override
	public String toString()
	{
		String set = "";
		for (int j = 0; j < mandelbrotSet.length; j++)
		{
			set += "[";
			for (int i = 0; i < mandelbrotSet[j].length; i++)
			{
				set += mandelbrotSet[j][i] + " ";
			}
			set += "]\n";
		}
		return set;
	}
}
