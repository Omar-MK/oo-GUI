package main;

import model.MandelbrotState;
import model.MandelbrotSetGenerator;
import guiDelegate.MandelbrotGuiDelegate;
import guiDelegate.Config;

/**
 * The main class used to launch the Mandelbrot Explorer GUI.
 * @author 170018405
 * @version 0.1
 */
public class MandelbrotMain
{
	public static void main(String[] args)
	{
		MandelbrotSetGenerator model = new MandelbrotSetGenerator(new MandelbrotState(Config.GRAPHIC_WIDTH, Config.GRAPHIC_HEIGHT));
		new MandelbrotGuiDelegate(model);
	}
}
