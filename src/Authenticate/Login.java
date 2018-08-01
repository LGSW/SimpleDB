package Authenticate;

import java.util.*;
import java.applet.*;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import simpledb.remote.RemoteDriver;
import simpledb.remote.RemoteDriverImpl;
import simpledb.server.*;

public class Login extends Applet implements ActionListener {

	JTextField textUser;
	JPasswordField textPass;
	JButton b1, b2, b3;
	JLabel l1, l2, error;
	Window window;
	Dimension screenSize;
	JPanel gui;
	Random r = new Random();

	/**
	 * init the main window by Applet,and the main window includes buttons, text
	 * fields and labels.
	 */

	public void init() {

		textUser = new JTextField("", 10);
		textUser.setBackground(Color.white);

		textPass = new JPasswordField("", 10);
		textPass.setBackground(Color.white);

		l1 = new JLabel("username");
		l2 = new JLabel("password");
		b1 = new JButton("confirm");
		b2 = new JButton("clear");
		b3 = new JButton("New User");
		b1.addActionListener(this);
		b2.addActionListener(this);

		add(l1);
		add(textUser);
		add(l2);
		add(textPass);
		add(b1);
		add(b2);
	}

	/**
	 * this is why the class implemented actionListener, we should get what the
	 * users have input in the text field and compare them whether what we have
	 * stored in the "pair" class. Also, if the user has passed the validation
	 * process, we can initiate the database and bounce out a successful
	 * information box. Else, the error information box would be pop up.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			String username = textUser.getText();
			String myPass = String.valueOf(textPass.getPassword());
			System.out.println(username);
			System.out.println(myPass);
			System.out.println(username.getClass().getName());
			System.out.println(myPass.getClass().getName());
			boolean re = true;
			re = pairs.searchPairs(username, myPass);
			if (!re) {

				errorApplet.infoBox("Invalid Username or password !", "Error Message");
				textUser.setText(null);
				textPass.setText(null);

			} else {
				errorApplet.infoBox("Success Message", "Database has been initiated for you!");
				String[] args = { "studentdb" };
				try {

					Startup.main(args);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}

		else if (e.getSource() == b2) {
			textUser.setText(null);
			textPass.setText(null);
		}

	}
}
