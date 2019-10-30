package guiDelegate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ImageIcon;

/**
 * This is an extended JMenuBar class which implements the ActionListener
 * interface.
 * This class contains the code for the custom menubar used in the
 * MandelbrotSet explorer gui.
 * @author 170018405
 * @version 0.1
 */
public class MyMenuBar extends JMenuBar implements ActionListener
{
	/*
	 * The delegate object. Used to get access to methods containing the
	 * control code for the buttons in this JMenuBar.
	 */
	private MandelbrotGuiDelegate delegate;

	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem undoMenuItem;
	private JMenuItem redoMenuItem;
	private JMenuItem resetMenuItem;
	private JMenuItem iterationsMenuItem;
	private JMenuItem boundsMenuItem;
	private JMenuItem magViewToggleMenuItem;

	/**
	 * Instantiates a JMenuBar object and populates the menu bar with the
	 * menus and menu items required.
	 *
	 * @param delegate the MandelbrotGuiDelegate object used to get hold of the
	 * control methods called by the buttons in this menu bar
	 */
	public MyMenuBar(MandelbrotGuiDelegate delegate)
	{
		super();
		this.delegate = delegate;
		setupMenubar();
	}

	/**
	 * Initialises the menu bar to contain the menu tabs: file, edit, and view.
	 * Each menu tab is populated with its own menu items through calls to
	 * specific methods
	 */
	private void setupMenubar()
	{
		// Adds the file, edit, and setup menus
		setupFileMenu();
		setupEditMenu();
		setupViewMenu();
	}

	/**
	 * Populates the file menu with the required items, then adds this
	 * submenu to this JMenuBar.
	 */
	private void setupFileMenu()
	{
		// creating file tab
		fileMenu = new JMenu("File");
		// add mnemonic
		fileMenu.setMnemonic(KeyEvent.VK_F);
		// populating file tab
		openMenuItem = new JMenuItem("Open",
		new ImageIcon("../icons/open.png"));
		addMenuItem(fileMenu, openMenuItem, KeyEvent.VK_O);
		saveMenuItem = new JMenuItem("Save",
		new ImageIcon("../icons/save.png"));
		addMenuItem(fileMenu, saveMenuItem, KeyEvent.VK_S);
		this.add(fileMenu);
	}

	/**
	 * Populates the edit menu with the required items, then adds this
	 * submenu to this JMenuBar.
	 */
	private void setupEditMenu()
	{
		// creating the edit tab
		editMenu = new JMenu("Edit");
		// add mnemonic
		editMenu.setMnemonic(KeyEvent.VK_E);
		// populating edit tab
		undoMenuItem = new JMenuItem("Undo",
		new ImageIcon("../icons/undo.png"));
		addMenuItem(editMenu, undoMenuItem, KeyEvent.VK_Z);
		redoMenuItem = new JMenuItem("Redo",
		new ImageIcon("../icons/redo.png"));
		addMenuItem(editMenu, redoMenuItem, KeyEvent.VK_Y);
		resetMenuItem = new JMenuItem("Reset",
		new ImageIcon("../icons/reset.png"));
		addMenuItem(editMenu, resetMenuItem, KeyEvent.VK_Q);
		editMenu.addSeparator();
		iterationsMenuItem = new JMenuItem("Change Max Iterations",
		new ImageIcon("../icons/change.png"));
		addMenuItem(editMenu, iterationsMenuItem, KeyEvent.VK_I);
		boundsMenuItem = new JMenuItem("Change Bounds",
		new ImageIcon("../icons/change.png"));
		addMenuItem(editMenu, boundsMenuItem, KeyEvent.VK_B);
		this.add(editMenu);
	}

	/**
	 * Populates the view menu with the required menu items, then adds this
	 * submenu to this JMenuBar.
	 */
	private void setupViewMenu()
	{
		// creating view tab
		viewMenu = new JMenu("View");
		// add mnemonic
		viewMenu.setMnemonic(KeyEvent.VK_V);
		// populating the view tab
		magViewToggleMenuItem = new JMenuItem("Show/Hide Magnification",
		new ImageIcon("../icons/mag.png"));
		addMenuItem(viewMenu, magViewToggleMenuItem, KeyEvent.VK_M);
		this.add(viewMenu);
	}

	/**
	 * Helper method used to set an accelerator for a JMenuItem, then registers
	 * it to the ActionListener before adding it to menu.
	 *
	 * @param menu the menu the JMenuItem (item) will be added to.
	 * @param item the JMenuItem to add to menu and register this class as an
	 * ActionListener of.
	 * @param btn the accelerator key to set to this menu item.
	 */
	private void addMenuItem(JMenu menu, JMenuItem item, int btn)
	{

		item.setAccelerator(KeyStroke.getKeyStroke(btn, ActionEvent.CTRL_MASK));
		item.addActionListener(this);
		menu.add(item);
	}

	/**
	 * Method used to listen for clicks on the buttons registered on this
	 * menubar.
	 * This method redirects the clicks to the corresponding control methods in
	 * the delegate object.
	 *
	 * @param e the action event
	 */
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem item = (JMenuItem) e.getSource();
		if (item == openMenuItem) delegate.open();
		else if (item == saveMenuItem) delegate.save();
		else if (item == undoMenuItem) delegate.undo();
		else if (item == redoMenuItem) delegate.redo();
		else if (item == resetMenuItem) delegate.reset();
		else if (item == iterationsMenuItem) delegate.changeMaxIterations();
		else if (item == boundsMenuItem) delegate.changeBounds();
		else if (item == magViewToggleMenuItem)
		{
			delegate.setDisplayMagChoice(!delegate.getDisplayMagChoice());
		}
	}
}
