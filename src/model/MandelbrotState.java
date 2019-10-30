package model;

import java.io.Serializable;

/**
 * This is a simple class that defines Objects which extend the
 * MandelbrotCalculator class, and store the parameters (state) used to
 * calculate the Mandelbrot Set.
 *
 * Objects of this class do not store the Mandelbrot Set, but a method is
 * provided to calculate and return the set when needed. This is to reduce the
 * space needed to keep track of many MandelbrotState Objects
 *
 * The class provides three constructors and setter/getter methods for all
 * fields (settings) used to calculate a Mandelbrot Set.
 *
 * The class also implements the Serializable interface to allow for writing
 * instances of this class to file
 *
 * @author 170018405
 * @version 0.1
 */

public class MandelbrotState extends MandelbrotCalculator
implements Serializable
{
	/**
	 * The horizontal resolution in pixels of the mandelbrotSet.
	 */
	private int xRes;

	/**
	 * The vertical resolution in pixels of the mandelbrotSet.
	 */
	private int yRes;

	/**
	 * The maximum number of iterations to iterate the complex formula.
	 */
	private int maxIterations;

	/**
	 * The lower real bound for the complex constant C.
	 */
	private double minReal;

	/**
	 * The upper real bound for the complex constant C.
	 */
	private double maxReal;

	/**
	 * The lower imaginary bound for the complex constant C.
	 */
	private double minImaginary;

	/**
	 * the upper imaginary bound for the complex constant C.
	 */
	private double maxImaginary;

	/**
	 * the squared of the radius to use when determining whether Z escaped the
	 * circle in the complex plain or remained bounded.
	 */
	private double sqRadius;


	/**
	 * This Constructor should be used to create a MandelbrotState Object with
	 * the default initial parameters defined in the MandelbrotCalculator class.
	 *
	 * @param xRes the horizontal resolution of the mandelbrotSet in pixels
	 * @param yRes the vertical resolution of the mandelbrotSet in pixels
	 *
	 */
	public MandelbrotState(int xRes, int yRes) throws IllegalArgumentException
	{
		if (xRes <= 0 || yRes <= 0)
		{
			throw new IllegalArgumentException("Resolution cannot be 0 or smaller");
		}
		this.xRes = xRes;
		this.yRes = yRes;
		this.maxIterations = INITIAL_MAX_ITERATIONS;
		this.minReal = INITIAL_MIN_REAL;
		this.maxReal = INITIAL_MAX_REAL;
		this.minImaginary = INITIAL_MIN_IMAGINARY;
		this.maxImaginary = INITIAL_MAX_IMAGINARY;
		this.sqRadius = DEFAULT_RADIUS_SQUARED;
	}

	/**
	 * This Constructor should be used to create a MandelbrotState Object with
	 * custom parameters.
	 *
	 * @param xRes the horizontal resolution of the mandelbrotSet in pixels
	 * @param yRes the vertical resolution of the mandelbrotSet in pixels
 	 * @param maxIterations the maximum number of iterations to iterate the
	 * complex formula
	 * @param minReal the lower real bound for the complex constant C
	 * @param maxReal the upper real bound for the complex constant C
	 * @param minImaginary the lower imaginary bound for the complex constant C
	 * @param maxImaginary the upper imaginary bound for the complex constant C
	 * @param sqRadius the squared radius to use when determining
	 * whether Z escaped the circle in the complex plain or remained bounded.
	 *
	 */
	public MandelbrotState(int xRes, int yRes, int maxIterations,
	double minReal, double maxReal, double minImaginary, double maxImaginary,
	double sqRadius) throws IllegalArgumentException
	{
		if (xRes <= 0 || yRes <= 0)
		{
			throw new IllegalArgumentException("Resolution cannot be 0 or smaller");
		}
		this.xRes = xRes;
		this.yRes = yRes;
		this.maxIterations = maxIterations;
		this.minReal = minReal;
		this.maxReal = maxReal;
		this.minImaginary = minImaginary;
		this.maxImaginary = maxImaginary;
		this.sqRadius = sqRadius;
	}

	/**
	 * This Constructor should be used to create a MandelbrotState Object with
	 * the same parameters as another MandelbrotState Object.
	 *
	 * @param prevMandelbrot the MandelbrotState Object to be copied
	 *
	 */
	public MandelbrotState(MandelbrotState prevMandelbrot)
	{
		// This could throw a null pointer exception..
		/* Decided not to catch or use an if statement to skip the following
		lines if prevMandelbrot is null */
		this.xRes = prevMandelbrot.xRes;
		this.yRes = prevMandelbrot.yRes;
		this.maxIterations = prevMandelbrot.maxIterations;
		this.minReal = prevMandelbrot.minReal;
		this.maxReal = prevMandelbrot.maxReal;
		this.minImaginary = prevMandelbrot.minImaginary;
		this.maxImaginary = prevMandelbrot.maxImaginary;
		this.sqRadius = prevMandelbrot.sqRadius;
	}

	/**
	 * Returns the horizontal resolution of the Mandelbrot Set.
	 *
	 * @return the horizontal resolution of the Mandelbrot Set
	 */
	public int getXRes()
	{
		return xRes;
	}

	/**
	 * Sets the horizontal resolution of the Mandelbrot Set.
	 *
	 * @param xRes the horizontal resolution of the Mandelbrot Set
	 */
	public void setXRes(int xRes) throws IllegalArgumentException
	{
		if (xRes <= 0)
		{
			throw new IllegalArgumentException("Resolution cannot be 0 or smaller");
		}
		this.xRes = xRes;
	}

	/**
	 * Returns the vertical resolution of the Mandelbrot Set.
	 *
	 * @return the vertical resolution of the Mandelbrot Set
	 */
	public int getYRes()
	{
		return yRes;
	}

	/**
	 * Sets the vertical resolution of the Mandelbrot Set.
	 *
	 * @param yRes the vertical resolution of the Mandelbrot Set
	 */
	public void setYRes(int yRes) throws IllegalArgumentException
	{
		if (yRes <= 0)
		{
			throw new IllegalArgumentException("Resolution cannot be 0 or smaller");
		}
		this.yRes = yRes;
	}

	/**
	 * Returns the max iterations to iterate the complex formula.
	 *
	 * @return the max iterations to iterate the complex formula
	 */
	public int getMaxIterations()
	{
		return maxIterations;
	}

	/**
	 * Sets the max interations to iterate the complex formula.
	 *
	 * @param maxImaginary the max interations to iterate the complex formula
	 */
	public void setMaxIterations(int maxIterations)
	{
		this.maxIterations = maxIterations;
	}

	/**
	 * Returns the lower real boundary for the complex constant C.
	 *
	 * @return the lower real boundary for the complex constant C
	 */
	public double getMinReal()
	{
		return minReal;
	}

	/**
	 * Sets the lower real boundary for the complex constant C.
	 *
	 * @param minReal the lower real boundary for the complex
	 * constant C
	 */
	public void setMinReal(double minReal)
	{
		this.minReal = minReal;
	}

	/**
	 * Returns the upper real boundary for the complex constant C.
	 *
	 * @return the upper real boundary for the complex constant C.
	 */
	public double getMaxReal()
	{
		return maxReal;
	}

	/**
	 * Sets the upper real boundary for the complex constant C.
	 *
	 * @param maxReal the upper real boundary for the complex
	 * constant C
	 */
	public void setMaxReal(double maxReal)
	{
		this.maxReal = maxReal;
	}

	/**
	 * Returns the lower imaginary boundary for the complex constant C.
	 *
	 * @return the lower imaginary boundary for the complex constant C
	 */
	public double getMinimaginary()
	{
		return minImaginary;
	}

	/**
	 * Sets the lower imaginary boundary for the complex constant C.
	 *
	 * @param minImaginary the lower imaginary boundary for the complex
	 * constant C
	 */
	public void setMinimaginary(double minImaginary)
	{
		this.minImaginary = minImaginary;
	}

	/**
	 * Returns the upper imaginary boundary for the complex constant C.
	 *
	 * @return the upper imaginary boundary for the complex constant C
	 */
	public double getMaximaginary()
	{
		return maxImaginary;
	}

	/**
	 * Sets the upper imaginary boundary for the complex constant C.
	 *
	 * @param maxImaginary the upper imaginary boundary for the complex
	 * constant C
	 */
	public void setMaximaginary(double maxImaginary)
	{
		this.maxImaginary = maxImaginary;
	}

	/**
	 * Returns the squared radius to use when determining
	 * whether Z escaped the circle in the complex plain or remained bounded.
	 *
	 * @return the squared radius
	 */
	public double getSqRadius()
	{
		return sqRadius;
	}

	/**
	 * Sets the squared radius to use when determining
	 * whether Z escaped the circle in the complex plain or remained bounded.
	 *
	 * @param sqRadius the squared radius
	 */
	public void setSqRadius(double sqRadius)
	{
		this.sqRadius = sqRadius;
	}

	/**
	 * Returns the Mandelbrot Set.
	 *
	 * @return the Mandelbrot Set as a 2d array
	 */
	public int[][] getMandelbrotSet()
	{
		return calcMandelbrotSet(xRes, yRes, minReal, maxReal, minImaginary,
		maxImaginary, maxIterations, sqRadius);
	}
}
