/**
 * Version 0.1
 */

package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InDialog extends JDialog {
	private JTextField text;
	private JLabel NAME;
	private JButton play;
	private JPanel panel;
	private String theName;
	private boolean start=false;
	private Color darkGreen=new Color(22,100,22);
	public  InDialog()
	{   Font ft=new Font("Segoe Script", Font.ITALIC,15);
		
		text=new JTextField(20);
		text.setPreferredSize(new Dimension(100,30));
		
		
		NAME=new JLabel("Name: ");
		NAME.setFont(ft);
		NAME.setForeground(darkGreen);
		
		
		play=new JButton("Play");
		play.setFont(ft);
		play.setForeground(darkGreen);
		ButtonListener listener=new ButtonListener();
		play.addActionListener(listener);
		
		panel=new JPanel();
		panel.setPreferredSize(new Dimension(200,40));
		panel.add(NAME,BorderLayout.WEST);
		panel.add(text, BorderLayout.EAST);
		
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setVisible(true);
		setPreferredSize(new Dimension(400,128));
		setLocation(150,200);
		setLayout(new BorderLayout());
	
		add(panel, BorderLayout.NORTH);
		add(play,BorderLayout.SOUTH);
		pack();
		
	}
	
	public String getName()
	{
	  return theName;
	}
	public boolean getStart()
	{
		return start;
	}
	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==play){
				start=true;
			theName=text.getText();
			}
		}	
	}
}
