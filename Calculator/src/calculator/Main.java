package calculator;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GUI gui = GUI.getGUIInstance();
		
		JFrame frame = new JFrame("Calculator");
		frame.getContentPane().add(gui);

        // Size the frame.
        frame.pack();

        // Show the frame
        frame.setVisible(true);
        
	}

}
