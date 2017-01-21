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
	
	//Calculator and Required Data:
	private Calculator calculator;
	private double input;
	private double total;
	
	//GUI Data Objects:
	private String displayText = "\n0";
	private String prevOperator = "";
	private boolean typeOverFlag;
	private boolean firstInputFlag;
	private EventQueue queue;
	
	//GUI Elements:
	private JTextArea display;
	private JButton numbers[];
	private JButton backspace;
	private JButton decimal;
	private JButton clear;
	private JButton clearEntry;
	private JButton addition;
	private JButton subtraction;
	private JButton multiplication;
	private JButton division;
	private JButton equals;
	private NumberAction numberAction;
	private DeleteAction deleteAction;
	private OperatorAction operatorAction;
	
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
		input = 0;
		total = 0;
		typeOverFlag = true;
		firstInputFlag = true;
		
		queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		
	}
	
	
	/*public GUI getGUIInstance()
	{
		
	}*/
	
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
		display.getInputMap().put(KeyStroke.getKeyStroke('='), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "operation");
		
		GridBagConstraints displayC = new GridBagConstraints();
		
		displayC.gridx = 0;
		displayC.gridy = 0;
		displayC.gridwidth = 5;
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
		
		deleteAction = new DeleteAction("Clr", null, "Clear All Input", null);
		clear = new JButton(deleteAction);
		clear.setFocusable(false);
		
		GridBagConstraints clearC = new GridBagConstraints();
		clearC.gridx = 2;
		clearC.gridy = 1;
		clearC.weightx = 0.5;
		
		add(clear, clearC);
		
		deleteAction = new DeleteAction("CE", null, "Clear Entry", null);
		clearEntry = new JButton(deleteAction);
		clearEntry.setFocusable(false);
		
		clearC.gridx = 1;
		
		add(clearEntry, clearC);
		
		
		
	}
	
	private void createOperators()
	{
		operatorAction = new OperatorAction("+", null, "Add numbers", null);
		addition = new JButton(operatorAction);
		addition.setFocusable(false);
		
		GridBagConstraints operatorC = new GridBagConstraints();
		
		operatorC.gridx = 3;
		operatorC.gridy = 5;
		operatorC.weightx = 0.5;
		operatorC.fill = GridBagConstraints.BOTH;
		
		add(addition, operatorC);
		
		operatorAction = new OperatorAction("-", null, "Subtract numbers", null);
		subtraction = new JButton(operatorAction);
		subtraction.setFocusable(false);
		
		operatorC.gridy = 4;
		
		add(subtraction, operatorC);

		operatorAction = new OperatorAction("*", null, "Multiply numbers", null);
		multiplication = new JButton(operatorAction);
		multiplication.setFocusable(false);
		
		operatorC.gridy = 3;
		
		add(multiplication, operatorC);
		
		operatorAction = new OperatorAction("/", null, "Divide numbers", null);
		division = new JButton(operatorAction);
		division.setFocusable(false);
		
		operatorC.gridy = 2;
		
		add(division, operatorC);
		
		operatorAction = new OperatorAction("=", null, "Find Total", null);
		equals = new JButton(operatorAction);
		equals.setFocusable(false);
		
		operatorC.gridy = 4;
		operatorC.gridx = 4;
		operatorC.gridheight = 2;
		operatorC.fill = GridBagConstraints.BOTH;
		operatorC.weighty = 1.0;
		operatorC.weightx = 1.0;
		
		add(equals, operatorC);
	}
	
	private String getUserInput()
	{
		int newLine = displayText.indexOf('\n');
		System.out.println("User input: " + displayText.substring(newLine + 1));
		return displayText.substring(newLine + 1);
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
		
		
		if (lineNum == EXPRESSION)
		{
			if (message.equals("Clear"))
			{
				display.setText(display.getText().substring(newLine - 1));
			}
			else
			{
				display.insert(message, newLine - 1);
			}	
		}
		else
		{
			if (message.equals("Clear"))
			{
				display.setText(display.getText().substring(0, newLine));
			}
			
			//display.getText().substring(newLine).equals("0")
			else if (typeOverFlag)
			{
				display.setText(display.getText().substring(0, newLine));
				display.insert(message, newLine);
				typeOverFlag = false;
			}
			else 
			{
				int length = display.getText().length(); 
				display.insert(message, length);
			}
		}
		
		displayText = display.getText();
	}
	
	private void changeDisplay(double expression, int lineNum)
	{
		String exp = ((Double) expression).toString();
		
		changeDisplay(exp, lineNum);
	}
	
	private void changeDisplay(int expression, int lineNum)
	{
		String exp = ((Integer) expression).toString();
		
		changeDisplay(exp, lineNum);
	}
	
	private String formatResult(double result)
	{
		String res = ((Double) result).toString();
		String s = res.indexOf(".") < 0 ? res : res.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
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
		public void actionPerformed(ActionEvent e) 
		{
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
		public void actionPerformed(ActionEvent e) 
		{
			String keyStroke = "";
			
			if (e.getSource() instanceof JTextArea)
			{
			 	KeyEvent ke = (KeyEvent)queue.getCurrentEvent();
		        keyStroke = ke.getKeyText( ke.getKeyCode() );
		        System.out.println(keyStroke);
			}
				
			System.out.println(e.getActionCommand());
			//Clear:
			if (e.getActionCommand().equals("Clr") || keyStroke.equals("Delete"))
			{
				changeDisplay("Clear", EXPRESSION);
				changeDisplay("Clear", INPUT);
				displayText = "\n0";
				input = 0;
				total = 0;
				typeOverFlag = true;
			}
			//Clear Entry:
			else if (e.getActionCommand().equals("CE"))
			{
				changeDisplay("Clear", INPUT);
				input = 0;
			}
			//Backspace:
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
		public void actionPerformed(ActionEvent e) 
		{
			
			String operator = e.getActionCommand();
			System.out.println("Operator: " + operator);
			System.out.println("PrevOperator: " + prevOperator);
			
			String formattedTotal = "";
			
			String userInput = getUserInput();
			input = Double.parseDouble(userInput);
			
			if (operator.equals("\n")) operator = "=";
			
			changeDisplay(userInput + " " + operator + " ", EXPRESSION);
			changeDisplay("Clear", INPUT);
			
			if (operator.equals("="))
			{
				changeDisplay("Clear", EXPRESSION);
			}		
			
			switch (prevOperator)
			{
				case "+":
				{
					total = calculator.add(total, input);
					break;
				}
				case "-":
				{
					total = calculator.subtract(total, input);
					break;
				}
				case "*":
				{
					total = calculator.multiply(total, input);
					break;
				}
				case "/":
				{
					total = calculator.divide(total, input);
					break;
				}
				//Prev operator not yet used:
				case "":
				{
					total = input;
					break;
				}
				//Do nothing:
				case "\n":		
				case "=":
				{
					total = input;
					break;
				}
				//Error case:
				default:
				{
					System.out.println("An unknown error has occurred");
					break;
				}
			}
			
			formattedTotal = formatResult(total);
			changeDisplay(formattedTotal, INPUT);
			prevOperator = operator;
			typeOverFlag = true;
			
		}
		
	}
	
}