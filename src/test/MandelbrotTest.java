package test;

import model.MandelbrotSetGenerator;
import model.MandelbrotState;

/**
 * This is a test class used to test the functionality of the
 * MandelbrotSetGenerator and MandelbrotState classes.
 * @author 170018405
 * @version 0.1
 */
 public class MandelbrotTest
{
	public static void main(String[] args)
	{
		// Testing undo / redo
		MandelbrotState state = new MandelbrotState(40, 40);
		MandelbrotSetGenerator generator = new MandelbrotSetGenerator(state);
		int[][] initSet = generator.getSet();

		// print first set;
		System.out.println(generator);

		// change res and print sec set
		generator.setResolution(30, 30);
		System.out.println(generator);

		// change rest and print third set
		generator.setResolution(10, 30);
		System.out.println(generator);

		// go back to previous state and print
		generator.undoState();
		System.out.println(generator);

		// redo and print
		generator.redoState();
		System.out.println(generator);

		// try to redo when the redo stack is empty
		generator.redoState();
		generator.redoState();
		generator.redoState();
		System.out.println(generator);

		// do the same for more undo operations
		generator.undoState();
		generator.undoState();
		generator.undoState();
		generator.undoState();
		generator.undoState();
		System.out.println(generator);

		// Testing changeBounds edge cases
		// min is lager than max this can be visualised by flipping the current min and max values
		MandelbrotState state1 = generator.getState();
		generator.setBounds(state1.getMaxReal(), state1.getMinReal(), state1.getMaximaginary(), state1.getMinimaginary());
		System.out.println(generator);
		// when the bounds are the same value
		generator.setBounds(1, 1, -1, 1);
		System.out.println(generator);

		// Testing changing radius sq value
		generator.undoState();
		generator.undoState();
		generator.setSqRadius(10);
		// negative radius expected zeros
		System.out.println(generator);
		generator.setSqRadius(-20);
		System.out.println(generator);

		// Testing changing maxIterations to a negative number expected zeros
		generator.undoState();
		generator.undoState();
		generator.setMaxIterations(-1);
		System.out.println(generator);

		// Testing reset
		System.out.println("Testing reset");
		generator.reset();
		System.out.println(generator);
		boolean pass = true;
		for (int j = 0; j < initSet.length; j++)
		{
			for (int i = 0; i < initSet[j].length; i++)
			{
				if (initSet[j][i] != generator.getSet()[j][i]) pass = false;
			}
		}
		if (pass)

			System.out.println("pass");
		else
		{
			System.out.println("fail");
		}

		// Testing panning by a single pixel horizontally and vertically
		generator.setResolution(10, 10);
		System.out.println("Before horizontal shift");
		System.out.println(generator);
		System.out.println("After horizontal shift");
		generator.shiftBounds(1, 0, 1);
		System.out.println(generator);

		// Testing incorrect changes of resolution (negative value)
		System.out.println("Testing changing resolution to negative value... This should throw an exception");
		generator.undoState();
		generator.undoState();
		try
		{
			generator.setResolution(-1, 3);
		}
		catch (Exception e)
		{
			System.out.println("Exception thrown");
		}

	}
}
