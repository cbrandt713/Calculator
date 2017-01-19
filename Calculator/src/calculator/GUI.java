package calculator;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

//Increase gridx = move left
//Increase gridy = move down
//Starts top left corner (0,0)
public class GUI extends JPanel
{
	//Display Line Number Constants:
	public static final int EXPRESSION = 0;
	public static final int INPUT = 1;
	
	private Calculator calculator;
	private double input;
	private double total;
	
	//Data Objects:
	private String displayText = "\n0";
	
	//GUI Elements:
	private JTextArea display;
	private JButton numbers[];
	private JButton backspace;
	private JButton decimal;
	private JButton clear;
	private JButton addition;
	private JButton subtraction;
	private JButton multiplication;
	private JButton division;
	private NumberAction numberAction;
	private DeleteAction deleteAction;
	private OperatorAction operatorAction;
	private EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
	
	
	public GUI()
	{
		//Call JPanel constructor
		super();
		
		//Set Layout of the body
		setLayout(new GridBagLayout());
		
		//Create elements of the layout
		createDisplay();
		createNumbers();
		createMiscButtons();
		createOperators();
		
		//Create calculator
		calculator = Calculator.getCalculatorInstance();
		
	}
	
	private void createDisplay()
	{
		display = new JTextArea(displayText);
		display.setRows(2);
		display.setEditable(false);
		
		numberAction = new NumberAction("0", null, "Insert number", null);
		deleteAction = new DeleteAction("←", null, "Delete number", null);
		operatorAction = new OperatorAction("+", null, "", null);
		
		display.getActionMap().put("number", numberAction);
		display.getActionMap().put("delete", deleteAction);
		display.getActionMap().put("operation", operatorAction);
		
		for (Integer i = 0; i <= 9; i++)
		{
			
			display.getInputMap().put(KeyStroke.getKeyStroke(i.toString()), "number");
			display.getInputMap().put(KeyStroke.getKeyStroke("NUMPAD" + i.toString()), "number");
		} 
		
		display.getInputMap().put(KeyStroke.getKeyStroke('.'), "number");
		display.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "delete");
		display.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "delete");
		display.getInputMap().put(KeyStroke.getKeyStroke('+'), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke('-'), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke('*'), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke('/'), "operation");
		
		GridBagConstraints displayC = new GridBagConstraints();
		
		displayC.gridx = 0;
		displayC.gridy = 0;
		displayC.gridwidth = 4;
		//displayC.gridheight = 2;
		displayC.weighty = 1.0;
		displayC.weightx = 1.0;
		displayC.fill = GridBagConstraints.BOTH;
		
		add(display, displayC);
	}
	
	private void createNumbers()
	{
		
		numbers = new JButton[10];
		
		GridBagConstraints numC = new GridBagConstraints();
		
		for (Integer i = 0; i <= 9; i++)
		{
			if (i == 0)
			{
				numC.gridwidth = 2;
				numC.weightx = 1;
				numC.fill = GridBagConstraints.HORIZONTAL;
			}
			else 
			{
				numC.gridwidth = 1;
				numC.weightx = 0.5;
				numC.fill = GridBagConstraints.HORIZONTAL;
			}
			//Register the action based on number touched. 0 = 48, 1 = 49, etc.
			numberAction = new NumberAction(i.toString(), null, "Insert number", null);
			numbers[i] = new JButton(numberAction);
			numbers[i].setFocusable(false);
			
			numC.gridy = ((9 - i) / 3) + 2;
			
			if (i == 0) numC.gridx = 0;
			else numC.gridx = (i % 3) - 1;
			
			add(numbers[i], numC);
		}
		
	}
	
	private void createMiscButtons()
	{
		deleteAction = new DeleteAction("←", null, "Delete number", null);
		backspace = new JButton(deleteAction);
		backspace.setFocusable(false);
	
		GridBagConstraints backspaceC = new GridBagConstraints();
		backspaceC.gridx = 0;
		backspaceC.gridy = 1;
		backspaceC.weightx = 0.5;
		
		add(backspace, backspaceC);
		
		numberAction = new NumberAction(".", null, "Insert number", KeyEvent.VK_PERIOD);
		decimal = new JButton(numberAction);
		decimal.setFocusable(false);
		
		GridBagConstraints decC = new GridBagConstraints();
		decC.gridx = 2;
		decC.gridy = 5;
		decC.weightx = 0.5;
		decC.fill = GridBagConstraints.HORIZONTAL;
		
		add(decimal, decC);
		
		deleteAction = new DeleteAction("Clr", null, "Delete number", null);
		clear = new JButton(deleteAction);
		clear.setFocusable(false);
		
		
		GridBagConstraints clearC = new GridBagConstraints();
		clearC.gridx = 2;
		clearC.gridy = 1;
		clearC.weightx = 0.5;
		
		add(clear, clearC);
	}
	
	public void createOperators()
	{
		operatorAction = new OperatorAction("+", null, "Add numbers", null);
		addition = new JButton(operatorAction);
		
		GridBagConstraints operatorC = new GridBagConstraints();
		
		operatorC.gridx = 3;
		operatorC.gridy = 5;
		operatorC.weightx = 0.5;
		operatorC.fill = GridBagConstraints.BOTH;
		
		add(addition, operatorC);
		
		operatorAction = new OperatorAction("-", null, "Subtract numbers", null);
		subtraction = new JButton(operatorAction);
		
		operatorC.gridy = 4;
		
		add(subtraction, operatorC);

		operatorAction = new OperatorAction("*", null, "Multiply numbers", null);
		multiplication = new JButton(operatorAction);
		
		operatorC.gridy = 3;
		
		add(multiplication, operatorC);
		
		operatorAction = new OperatorAction("/", null, "Divide numbers", null);
		division = new JButton(operatorAction);
		
		operatorC.gridy = 2;
		
		add(division, operatorC);
		
		
	}
	
	private void changeDisplay(String message, int lineNum)
	{
		int newLine = 0;
		
		try 
		{
			newLine = display.getLineEndOffset(0);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
		
		
		if (lineNum == 0)
		{
			display.insert(message, 0);
		}
		else
		{
			if (display.getText().substring(newLine).equals("0"))
			{
				display.insert(message, newLine);
				int length = display.getText().length(); 
				display.setText(display.getText().substring(0, length - 1));
			}
			else 
			{
				int length = display.getText().length(); 
				display.insert(message, length);
			}
		}
		displayText = display.getText();
	}
	
	private void changeDisplay(float expression, int lineNum)
	{
		String exp = ((Float) expression).toString();
		
		changeDisplay(exp, lineNum);
	}
	
	private void changeDisplay(int expression, int lineNum)
	{
		String exp = ((Integer) expression).toString();
		
		changeDisplay(exp, lineNum);
	}
	
	public class NumberAction extends AbstractAction
	{
		public NumberAction(String name, ImageIcon icon, String shortDescription, Integer mnemonic)
		{
			super(name, icon);
			putValue(SHORT_DESCRIPTION, shortDescription);
		    putValue(MNEMONIC_KEY, mnemonic);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			changeDisplay(e.getActionCommand(), INPUT);
		}
		
	}
	
	public class DeleteAction extends AbstractAction
	{
		
		public DeleteAction(String name, ImageIcon icon, String shortDescription, Integer mnemonic)
		{
			super(name, icon);
			putValue(SHORT_DESCRIPTION, shortDescription);
		    putValue(MNEMONIC_KEY, mnemonic);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() instanceof JTextField)
			{
			 	KeyEvent ke = (KeyEvent)queue.getCurrentEvent();
		        String keyStroke = ke.getKeyText( ke.getKeyCode() );
		        
		        //Backspace:
				if (keyStroke.equals("Backspace"))
				{
					if (!displayText.equals(""))
					{
						displayText = displayText.substring(0, displayText.length() - 1);
					}
				}
				//Clear:
				else
				{
					displayText = "";
				}
				
			}
			//Clear:
			else if (e.getActionCommand().equals("Clr"))
			{
				displayText = "";
			}
			else
			{
				if (!displayText.equals(""))
				{
					displayText = displayText.substring(0, displayText.length() - 1);
				}
			}
			
			display.setText(displayText);
		}
	}
	
	public class OperatorAction extends AbstractAction
	{
		public OperatorAction(String name, ImageIcon icon, String shortDescription, Integer mnemonic)
		{
			super(name, icon);
			putValue(SHORT_DESCRIPTION, shortDescription);
		    putValue(MNEMONIC_KEY, mnemonic);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			
			String operator = e.getActionCommand();
			
			switch (operator)
			{
				case "+":
				{
					
				}
				case "-":
				{
					
				}
				case "*":
				{
					
				}
				case "/":
				{
					
				}
			}
			
			
			
		}
		
	}
	
}
