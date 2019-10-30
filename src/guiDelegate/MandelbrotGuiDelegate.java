package guiDelegate;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.BorderLayout;
import java.awt.image.RenderedImage;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import model.MandelbrotSetGenerator;
import model.MandelbrotState;
import javax.swing.JMenuBar;
import javax.swing.JTextField;


import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;


/**
 * This is the MandelbrotGuiDelegate class. This class creates the JFrame and
 * adds the GUI components (toolbar, menubar, and JPanel) to it.
 * The class also contains the controller code, and implements the
 * PropertyChangeListener interface to detect model changes.
 * @author 170018405
 * @version 0.1
 */
public class MandelbrotGuiDelegate implements PropertyChangeListener
{
	/*
	 * The MandelbrotSetGenerator model object.
	 */
	private MandelbrotSetGenerator model;

	private JFrame mainFrame;
	private JMenuBar menuBar;
	private MyToolBar toolBar;
	private MyGraphicalDisplayPanel graphicsPanel;


	/**
	 * Boolean variable used to keep track of the user choice to pan or zoom.
	 * False = pan, True = zoom.
	 */
	private boolean mouseDragOperation;

	/**
	 * Boolean variable used to keep track of the user choice to display
	 * magnification estimate on the JPanel.
	 * False will not display, True will display.
	 */
	private boolean showMagChoice;

	/**
	 * Variable used to keep track of the color choice selected by the user.
	 * Check the ColorMixer class for the colors each number represents.
	 */
	private int colorOption;

	/**
	 * Instantiates a new MandelbrotGuiDelegate object
	 * @param model the Model to observe, render, and update according to user
	 * events
	 */
	public MandelbrotGuiDelegate(MandelbrotSetGenerator model)
	{
		this.model = model;
		setupComponents();
		// add the delegate UI component as an observer of the model
		model.addObserver(this);
	}

	/**
	 * Method to setup the menu, toolbar, and JPanel graphics components
	 */
	private void setupComponents()
	{
		// set up the main frame for this GUI
		this.mainFrame = new JFrame("Mandelbrot-Set Explorer");
		// change layout to BorderLayout
		mainFrame.getContentPane().setLayout(new BorderLayout());
		// set up the menu bar
		this.menuBar = new MyMenuBar(this);
		// attaching menu to mainFrame
		mainFrame.setJMenuBar(menuBar);
		// set up tool bar
		this.toolBar = new MyToolBar(this);
		// add toolbar to frame
		mainFrame.add(toolBar, BorderLayout.SOUTH);
		// set up the graphics panel
		this.graphicsPanel = new MyGraphicalDisplayPanel(this, model);
		mainFrame.add(graphicsPanel, BorderLayout.CENTER);
		// set default close operation, pack, center, and display gui
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	/**
	 * Returns the mouseDragOperation field.
	 * True means zoom, and false means pan.
	 * @return the mouseDragOperation field
	 */
	public boolean getMouseDragOperation()
	{
		return mouseDragOperation;
	}

	/**
	 * Sets the mouseDragOperation field.
	 *
	 * @param mouseDragOperation the new mouse drag operation.
	 * True means zoom, and false means pan.
	 */
	public void setMouseDragOperation(boolean mouseDragOperation)
	{
		this.mouseDragOperation = mouseDragOperation;
	}

	/**
	 * Returns the showMagChoice field.
	 * True will display the zoom extimate superimposed on the Mandelbrot set
	 * render.
	 * @return the mouseDragOperation field
	 */
	public boolean getDisplayMagChoice()
	{
		return showMagChoice;
	}

	/**
	 * Sets the showMagChoice field.
	 * True will display the zoom extimate superimposed on the Mandelbrot set
	 * render.
	 * @param choice the new value of the mouseDragOperation field
	 */
	public void setDisplayMagChoice(boolean choice)
	{
		showMagChoice = choice;
		graphicsPanel.repaint();
	}

	/**
	 * Returns the main JFrame of the GUI.
	 * @return the main JFrame of the GUI
	 */
	public JFrame getMainFrame()
	{
		return mainFrame;
	}

	/**
	 * Changes the maximum iterations field in the model, thus causing a
	 * re-render of the Mandelbrot set.
	 * The method pops up a JOptionPane on the screen which takes the user
	 * input for the new maximum iterations count. The method then calls the
	 * setMaxIterations method of the model.
	 */
	public void changeMaxIterations()
	{
		// Text field contains the current iterations value
		JTextField input = new JTextField("" +
		model.getState().getMaxIterations());
		// Text field and label are added to fields array
		Object[] fields = {"Max Iterations:", input};
		// JOptionPane is launched and the option clicked is returned
		int option = JOptionPane.showConfirmDialog(null, fields,
		"Change max iteration count", JOptionPane.OK_CANCEL_OPTION);
		// if the user clicked ok, then change the model based on new iterations
		if (option == JOptionPane.OK_OPTION)
		{
			try
			{
				int iterations = Integer.parseInt(input.getText());
				model.setMaxIterations(iterations);
			}
			catch (NumberFormatException e)
			{
				// Catch is needed for NumberFormatException and IO exceptions
				JOptionPane.showMessageDialog(null,
				"Please enter a valid integer");
			}
		}
	}

	/**
	 * This method allows a user to change the bounds used to calculate the
	 * Mandelbrot set.
	 * The method brings up a JOptionPane containing 5 text entry fields
	 * corresponding to each of the calculation bounds.
	 * Appropriate calls to the model methods are then made.
	 */
	public void changeBounds()
	{
		// create fields for min and max real and imaginary entries
		JTextField minRealField = new JTextField("" +
		model.getState().getMinReal());
		JTextField maxRealField = new JTextField("" +
		model.getState().getMaxReal());
		JTextField minImaginaryField = new JTextField("" +
		model.getState().getMinimaginary());
		JTextField maxImaginaryField = new JTextField("" +
		model.getState().getMaximaginary());
		JTextField sqRadiusField = new JTextField("" +
		model.getState().getSqRadius());
		// put them into an array with their respective labels
		Object[] fields =
		{
			"Min x (real num): ", minRealField,
			"Max x (real num): ", maxRealField,
			"Min y (imaginary num): ", minImaginaryField,
			"Max y (imaginary num): ", maxImaginaryField,
			"Square radius: ", sqRadiusField
		};
		// launch option pane
		int option = JOptionPane.showConfirmDialog(null, fields,
		"Change bounds", JOptionPane.OK_CANCEL_OPTION);
		// if user clicked ok, change the model based on values changed
		if (option == JOptionPane.OK_OPTION)
		{
			try
			{
				double minReal, maxReal, minImag, maxImag, sqRadius;
				minReal = Double.parseDouble(minRealField.getText());
				maxReal = Double.parseDouble(maxRealField.getText());
				minImag = Double.parseDouble(minImaginaryField.getText());
				maxImag = Double.parseDouble(maxImaginaryField.getText());
				sqRadius = Double.parseDouble(sqRadiusField.getText());
				model.setBounds(minReal, maxReal, minImag, maxImag);
				model.setSqRadius(sqRadius);
			}
			catch (NumberFormatException e)
			{
				// Catch is needed for NumberFormatException and IO exceptions
				JOptionPane.showMessageDialog(null,
				"Please enter valid numbers");
			}
		}
	}

	/**
	 * Causes the model to revert back to the previous Mandelbrot Set.
	 */
	public void undo()
	{
		model.undoState();
		graphicsPanel.repaint();
	}

	/**
	 * Causes the model to redo an undo to show the next Mandelbrot Set.
	 */
	public void redo()
	{
		model.redoState();
		graphicsPanel.repaint();
	}

	/**
	 * Causes the model to reset back to the inital Mandelbrot Set.
	 */
	public void reset()
	{
		model.reset();
		graphicsPanel.repaint();
	}

	/**
	 * Attempts to open a saved Mandelbrot Set.
	 * This method calls the getFile helper method to bring up the JFileChooser
	 * used to get the file the user wants to open.
	 */
	public void open()
	{
		// get the file chosen by the user
		File file = getFile("Open File");
		// if the user cancels opening, file will be null
		if (file != null)
		{
			// now we can attempt to read the state Object stored in the file
			try (ObjectInputStream is = new ObjectInputStream(new
			FileInputStream(file));)
			{
				MandelbrotState state = (MandelbrotState) is.readObject();
				model.setState(state);
			}
			catch (Exception e)
			{
				/* many exceptions may occur, from IO to wrong Object type, all
				are caught and a error dialog message is shown to the user */
				JOptionPane.showMessageDialog(null, "Oops.. " + e.getMessage());
			}
		}
	}

	/**
	 * Attempts to save the current Mandelbrot Set to file.
	 * This method calls the getFile helper method to bring up the JFileChooser
	 * used to get the file name and path the user wants to save.
	 */
	public void save()
	{
		// get the file chosen by the user
		File file = getFile("Save File");
		// if the user cancels saving, file will be null
		if (file != null)
		{
			// now we can attempt to write the state Object to file
			try (ObjectOutputStream os = new ObjectOutputStream(new
			FileOutputStream(file));)
			{
				os.writeObject(model.getState());
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Oops.. " + e.getMessage());
			}
		}
	}

	/**
	 * Returns the colorOption value.
	 *
	 * @return the colorOption value
	 */
	public int getColorOption()
	{
		return colorOption;
	}

	/**
	 * Increases the colorOption field until it becomes no longer smaller than
	 * ColorMixer.colorOptions - 1, at which point it will reset colorOption to
	 * 0.
	 */
	public void toggleColor()
	{
		if (colorOption < ColorMixer.colorOptions - 1) colorOption++;
		else colorOption = 0;
		graphicsPanel.repaint();
	}

	/**
	 * Brings up a JOptionPane used to save or get a file based on the action
	 * parameter.
	 *
	 * @param action "Save File" to open a SaveDialog, otherwise an openDialog
	 * is opened.
	 */
	private File getFile(String action)
	{
		// Creating a file chooser
		JFileChooser fc = new JFileChooser();
		// Setting the opening directory
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		// Setting the title
		fc.setDialogTitle(action);
		// Enforcing the type of file that can be chosen
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// depending on action required show either save or open dialog
		if (action.equals("Save File"))
		{
			fc.showSaveDialog(mainFrame);
		}
		else
		{
			fc.showOpenDialog(mainFrame);
		}
		return fc.getSelectedFile();
	}

	/**
	 * This method contains code to update the GUI view when the model changes
	 * The method is called when the model changes (i.e. when the model
	 * executes setChanged() and notifyObservers())
	 * Any parameters passed to notifyObservers
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * are passed to update.
	 *
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent event)
	{
		// Tell the SwingUtilities thread to update the GUI components.
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				graphicsPanel.repaint();
			}
		});
	}
}
