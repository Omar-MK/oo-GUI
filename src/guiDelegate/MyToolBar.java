package guiDelegate;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * This is an extended JToolBar class which implements the ActionListener
 * interface.
 * This class contains the code for the custom toolbar used in the
 * MandelbrotSet explorer gui.
 * @author 170018405
 * @version 0.1
 */
public class MyToolBar extends JToolBar implements ActionListener
{
	/*
	 * The delegate object. Used to get access to methods containing the
	 * control code for the buttons in this JToolBar.
	 */
	private MandelbrotGuiDelegate delegate;

	private JButton openBtn;
	private JButton saveBtn;
	private JButton undoBtn;
	private JButton redoBtn;
	private JButton resetBtn;
	private JButton settingsBtn;
	private JButton panToggleBtn;
	private JButton zoomToggleBtn;
	private JButton magToggleBtn;
	private JButton colorToggleBtn;


	/**
	 * Instantiates a JToolBar object and populates the toolbar with the
	 * buttons and seperators required.
	 *
	 * @param delegate the MandelbrotGuiDelegate object used to get hold of the
	 * control methods called by the buttons in this toolbar
	 */
	public MyToolBar(MandelbrotGuiDelegate delegate)
	{
		super();
		this.delegate = delegate;
		// populating toolbar
		openBtn = new JButton(new ImageIcon("../icons/open.png"));
		addButton(openBtn, "open");
		saveBtn = new JButton(new ImageIcon("../icons/save.png"));
		addButton(saveBtn, "save");
		addSeparator();
		undoBtn = new JButton(new ImageIcon("../icons/undo.png"));
		addButton(undoBtn, "undo");
		redoBtn = new JButton(new ImageIcon("../icons/redo.png"));
		addButton(redoBtn, "redo");
		resetBtn = new JButton(new ImageIcon("../icons/reset.png"));
		addButton(resetBtn, "reset");
		addSeparator();
		settingsBtn = new JButton(new ImageIcon("../icons/change.png"));
		addButton(settingsBtn, "Change Max Iterations Mandelbrot");
		panToggleBtn = new JButton(new ImageIcon("../icons/pan.png"));
		addButton(panToggleBtn, "Toggle Pan/Drag");
		zoomToggleBtn = new JButton(new ImageIcon("../icons/zoom.png"));
		addButton(zoomToggleBtn, "Toggle Zoom");
		magToggleBtn = new JButton(new ImageIcon("../icons/mag.png"));
		addButton(magToggleBtn, "Toggle Magnification");
		addSeparator();
		colorToggleBtn = new JButton("Toggle Color");
		addButton(colorToggleBtn, "Toggle Color");
	}

	/**
	 * Helper method used to set the tool tip for a given button btn, and add
	 * this object as an action listener of the button.
	 * This method also add the button to this JToolBar object.
	 *
	 * @param btn the button to add the tooltip and register this class as an
	 * ActionListener of
	 * @param the toolTip to add to btn
	 */
	private void addButton(JButton btn, String toolTip)
	{
		btn.setToolTipText(toolTip);
		btn.addActionListener(this);
		add(btn);
	}

	/**
	 * Method used to listen for clicks on the buttons registered on this
	 * toolbar.
	 * This method redirects the clicks to the corresponding control methods in
	 * the delegate object.
	 *
	 * @param e the action event
	 */
	public void actionPerformed(ActionEvent e)
	{
		JButton btn = (JButton) e.getSource();
		if (btn == openBtn) delegate.open();
		else if (btn == saveBtn) delegate.save();
		else if (btn == undoBtn) delegate.undo();
		else if (btn == redoBtn) delegate.redo();
		else if (btn == resetBtn) delegate.reset();
		else if (btn == settingsBtn) delegate.changeMaxIterations();
		else if (btn == panToggleBtn) delegate.setMouseDragOperation(false);
		else if (btn == zoomToggleBtn) 	delegate.setMouseDragOperation(true);
		else if (btn == magToggleBtn)
			delegate.setDisplayMagChoice(!delegate.getDisplayMagChoice());
		else if (btn == colorToggleBtn) delegate.toggleColor();
	}
}
