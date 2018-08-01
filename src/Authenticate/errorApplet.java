package Authenticate;

import java.util.*;
import java.applet.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/*public class errorApplet extends Applet{
	 JLabel l1;
	 public void init(){
	        setSize(100,100);
	        l1 = new JLabel ("ERROR!");
	        add(l1);
	        //error = new JLabel ("error");
	 }
}*/
import javax.swing.JOptionPane;

public class errorApplet {
	/**
	 * to show the error message
	 * 
	 * @param infoMessage
	 * @param titleBar
	 */

	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

}
