package com.wicpar.wicparbase.utils.error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Frederic on 28/09/2015 at 14:04.
 */
public class ErrorDialog extends JDialog implements ActionListener
{
	public ErrorDialog(JFrame parent, String title, String message)
	{
		super(parent, title, true);
		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(message));
		getContentPane().add(messagePane);
		JPanel buttonPane = new JPanel();
		JButton button = new JButton("OK");
		buttonPane.add(button);
		button.addActionListener(this);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	public void actionPerformed(ActionEvent e)
	{
		setVisible(false);
		dispose();
		System.exit(1);
	}

	public static void Send(String message)
	{
		new ErrorDialog(new JFrame(), "Sinking Simulator's Brand new Error Message, now for 0.00$ only!", message);
	}

}
