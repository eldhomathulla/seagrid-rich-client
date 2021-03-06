package nanocad.util;

/*
	A basic extension of the java.awt.Dialog class
 */

import java.awt.*;

public class AboutDialog extends Dialog {

	//{{DECLARE_CONTROLS
	Label label1;
	Button okButton;
	//}}

	// Used for addNotify redundency check.
	boolean fComponentsAdjusted = false;

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == AboutDialog.this)
				AboutDialog_WindowClosing(event);
		}
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == okButton)
				okButton_Clicked(event);
		}
	}

	public AboutDialog(Frame parent, String title, boolean modal) 
	{
		this(parent, modal);
		setTitle(title);
	}
	public AboutDialog(Frame parent, boolean modal)
	{
		super(parent, modal);

		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.

		//{{INIT_CONTROLS
		setLayout(null);
		setVisible(false);
		setSize(insets().left + insets().right + 249,insets().top + insets().bottom + 150);
		label1 = new Label("Michael Hewner, NCSA");
		label1.setBounds(insets().left + 36,insets().top + 12,166,72);
		add(label1);
		okButton = new Button();
		okButton.setLabel("OK");
		okButton.setBounds(insets().left + 84,insets().top + 108,66,27);
		add(okButton);
		setTitle("About");
		//}}

		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		okButton.addActionListener(lSymAction);
		//}}

	}
	void AboutDialog_WindowClosing(java.awt.event.WindowEvent event)
	{
				dispose();
	}
	public void addNotify()
	{
	    // Record the size of the window prior to calling parents addNotify.
	    Dimension d = getSize();
	    
		super.addNotify();

	    // Only do this once.
		if (fComponentsAdjusted)
			return;

		// Adjust components according to the insets
		setSize(insets().left + insets().right + getSize().width, insets().top + insets().bottom + getSize().height);
		Component components[] = getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Point p = components[i].getLocation();
			p.translate(insets().left, insets().top);
			components[i].setLocation(p);
		}
		
		// Used for addNotify check.
		fComponentsAdjusted = true;
	}
	void okButton_Clicked(java.awt.event.ActionEvent event)
	{
		//{{CONNECTION
		// Clicked from okButton Hide the Dialog
				dispose();
		//}}
	}
	public synchronized void show()
	{
		Rectangle bounds = getParent().bounds();
		Rectangle abounds = bounds();

		move(bounds.x + (bounds.width - abounds.width)/ 2,
			 bounds.y + (bounds.height - abounds.height)/2);

		super.show();
	}
}
