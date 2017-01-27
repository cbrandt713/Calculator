package calculator;

import java.awt.Graphics;

import javax.swing.JFrame;


public class Main 
{

	public static void main(String[] args) 
	{
		
		GUI gui = GUI.getGUIInstance();
	
		JFrame frame = new JFrame("Calculator");
		frame.getContentPane().add(gui);
		frame.setJMenuBar(gui.getJMenuBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // Size the frame.
        frame.pack();

        // Show the frame
        frame.setVisible(true);
        
        Graphics g = frame.getGraphics();
        g.drawRect(10, 10, 20, 20);
        
	}

}
