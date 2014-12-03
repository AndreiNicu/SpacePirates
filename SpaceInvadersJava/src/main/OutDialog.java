/**
 * Version 0.1
 */

package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OutDialog extends JDialog{
	private JLabel over,highscore;
	private JPanel panel;
	private static String gOver="Game Over!";
	private static String gWon="You Win!";
	private ArrayList<String> list;
	private Color darkGreen=new Color(22,100,22);
	
public OutDialog(String name, long score, boolean flag) throws IOException

{   
	if(flag)
		over=new JLabel("                        "+gOver);
	if(!flag)
		over=new JLabel("                        "+gWon);
	over.setFont(new Font("Segoe Script", Font.ITALIC,15));
	over.setForeground(darkGreen);
	highscore = new JLabel();
    highscore.setFont(new Font("Segoe Script", Font.ITALIC,15));
	highscore.setForeground(darkGreen);
	
	
    
	panel=new JPanel();
	panel.setPreferredSize(new Dimension(400,150));
    panel.add(highscore);
	
	setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	setVisible(true);
	setPreferredSize(new Dimension(400,200));
	setLocation(300,200);
	setLayout(new BorderLayout());
	
	add(over,BorderLayout.NORTH);
	add(panel,BorderLayout.CENTER);
	pack();
	readFromFile(score+"---"+name);
}

public void readFromFile(String current) throws IOException
{
	File file=new File("image\\highscore.txt");
	Scanner scan;
	 String str="";
	 list=new ArrayList<String>();
	 list.add(current);
	try{
		scan=new Scanner(file);
		while(scan.hasNext())
		{
			str=scan.nextLine();
			list.add(str);
		}
		Collections.sort(list);
		FileWriter stream=new FileWriter("image\\highscore.txt");
		BufferedWriter writer=new BufferedWriter(stream);
		System.out.println(list.toString());
		str="<html>";
		String tmp="";
		for(int i=list.size()-1;i>=0;i--)
		{
			tmp=list.get(i);
			writer.write(tmp);
			writer.newLine();
			str+=list.get(i)+"<br>";
		}
		str+="</html>";
		System.out.println(str);
		highscore.setText(str);
		writer.close();
	} catch (FileNotFoundException e){
		e.printStackTrace();
		}
	}
}