package guiDelegate;

/**
 * A color mixer class for the MandelbrotGui.
 * @author 170018405
 * @version 0.1
 */
public class ColorMixer
{
    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int GREEN = 2;
    public static final int BANDED_GREEN = 3;
    public static final int colorOptions = 5;

    /**
     * The maximum iterations used to render the Mandelbrot Set
     */
    private int maxIterations;

    /**
     * Constructs a ColorMixer object.
     * @param maxIterations the maximum number of iterations used to iterate the
	 * complex formula
     */
    public ColorMixer(int maxIterations)
    {
        this.maxIterations = maxIterations;
    }

    /**
     * This method returns an RGB color using the number of iterations made,
     * and a color scheme choice from one of the static colors defined in the
     * class fields. The integers 0 returns a red color scheme , 1 returns a
     * blue color scheme, 2 returns a green color scheme, 3 represents a banded
     * green scheme and any other number just returns a default color
     * calculated using the formula: 256 * max iterations / iterations made.
     * @param iterationsMade the number of iterations made before z escapes the
     * radius
     * @param colorChoice an iteger representing one of the
     * statically defined colors of this class. i.e. RED, BLUE, GREEN,
     * BANDED_GREEN, or the default color scheme
     * @return an RGB value
     */
    public int getColor(int iterationsMade, int colorChoice)
    {
        // make a default color using the equation 256 * maxIterations / iterations made
        int color = (256 *  maxIterations / iterationsMade);

        // based on colorChoice, return an integer that changes based on the iterations made, or return the default color.
        switch(colorChoice)
        {
            case 0: return (5 * iterationsMade << 17);
            case 1: return (int) (2 * iterationsMade * 256 / 4.0 + 200);
            case 2: return (2 * iterationsMade << 11);
            case 3: return (2 * iterationsMade << 14);
            default: return (color);
        }
    }
}
