package calculator;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

public class BasicTextArea extends JTextArea {

	//Display Line Number Constants:
	public static final int EXPRESSION = 0;
	public static final int INPUT = 1;
	
	private boolean m_typeOverFlag;
	private boolean m_firstInputFlag;
	
	private double m_input;
	private double m_total;
	
	private EventQueue queue;
	
	//Calculator and Required Data:
	private Calculator calculator;
	
	public BasicTextArea(String a_input)
	{
		super(a_input);
		
		m_typeOverFlag = true;
		m_firstInputFlag = true;
		
		//Create calculator
		calculator = Calculator.getCalculatorInstance();
		
		queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		
	}
	
	private String getUserInput()
	{
		String displayText = getText();
		int newLine = displayText.indexOf('\n');
		
		System.out.println("User input: " + displayText.substring(newLine + 1));
		return displayText.substring(newLine + 1);
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
	
	private void changeDisplay(String message, int lineNum)
	{
		int newLineChar = 0;
		
		try 
		{
			newLineChar = getLineEndOffset(0);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
		
		
		if (lineNum == EXPRESSION)
		{
			changeExpressionLine(message, newLineChar);
		
		}
		else
		{
			changeInputLine(message, newLineChar);
		}
		
	}
	
	private void changeExpressionLine(String a_message, int a_newLineChar)
	{
		if (a_message.equals("Clear"))
		{
			setText(getText().substring(a_newLineChar - 1));
		}
		else
		{
			insert(a_message, a_newLineChar - 1);
		}	
	}
	
	private void changeInputLine(String a_message, int a_newLineChar)
	{
		//Clear the Input Line:
		if (a_message.equals("Clear"))
		{
			setText(getText().substring(0, a_newLineChar));
		}
		
		//Backspace a character:
		else if (a_message.equals("Backspace"))
		{
			//If the text on the input line is not blank, delete the last character
			if (!getText().substring(a_newLineChar).equals(""))
			{
				setText(getText().substring(0, getText().length() - 1));
			}
			
			//If the text is now blank, place a 0 on the input line and set the typeOverFlag
			if (getText().substring(a_newLineChar).equals(""))
			{
				setText(getText() + "0");
				m_typeOverFlag = true;
			}	
		}
		
		//Type over the text on the input line:
		else if (m_typeOverFlag)
		{
			setText(getText().substring(0, a_newLineChar));
			insert(a_message, a_newLineChar);
			m_typeOverFlag = false;
		}
		
		//If no special case, just append a character to the end of the input line:
		else 
		{
			int length = getText().length(); 
			insert(a_message, length);
		}
	}
	
	private String formatDouble(double a_result)
	{
		String res = ((Double) a_result).toString();
		String s = res.indexOf(".") < 0 ? res : res.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
	}
	
	public void numberActionPerformed(ActionEvent a_event)
	{
		System.out.println(a_event.getActionCommand());
		
		//Possible error: If user uses an operation before a number
		if (m_firstInputFlag)
		{
			changeDisplay("Clear", EXPRESSION);
			m_firstInputFlag = false;
		}
		
		changeDisplay(a_event.getActionCommand(), INPUT);
	}
	
	public void enterActionPerformed(ActionEvent a_event)
	{
		
	}
	
	public void deleteActionPerformed(ActionEvent a_event)
	{
		String keyStroke = "";
		
		if (a_event.getSource() instanceof JTextArea)
		{
		 	KeyEvent ke = (KeyEvent) queue.getCurrentEvent();
	        keyStroke = ke.getKeyText( ke.getKeyCode() );
	        System.out.println(keyStroke);
		}
			
		System.out.println(a_event.getActionCommand());
		//Clear:
		if (a_event.getActionCommand().equals("Clr") || keyStroke.equals("Delete"))
		{
			changeDisplay("Clear", EXPRESSION);
			changeDisplay("Clear", INPUT);
			changeDisplay("0", INPUT);
			m_input = 0;
			m_total = 0;
			m_typeOverFlag = true;
		}
		//Clear Entry:
		else if (a_event.getActionCommand().equals("CE"))
		{
			changeDisplay("Clear", INPUT);
			changeDisplay("0", INPUT);
			m_input = 0;
			m_typeOverFlag = true;
		}
		//Backspace:
		else
		{
			changeDisplay("Backspace", INPUT);
		}
	}
	
	public void operatorActionPerformed(ActionEvent a_event)
	{
		//Find the given operator:
		String operator = a_event.getActionCommand();
		
		//Enter key is equivalent to the = operator.
		if (operator.equals("\n")) operator = "=";
		
		calculator.pushOperator(operator);
		System.out.println("Operator: " + operator);
		
		//Get user input and send to calculator:
		String userInput = getUserInput();
		m_input = Double.parseDouble(userInput);
		calculator.pushOperand(m_input);
		
		//Show the input and operator on the expression line
		changeDisplay(userInput + " " + operator + " ", EXPRESSION);
		changeDisplay("Clear", INPUT);
		
		m_total = calculator.doBasicCalculation();
		
		//Format and display the total to the user:
		String formattedTotal = formatDouble(m_total);
		changeDisplay(formattedTotal, INPUT);
		
		m_typeOverFlag = true;
	}
	
	public void miscOperatorActionPerformed(ActionEvent a_event)
	{
		
		String operator = a_event.getActionCommand();
		System.out.println("Operator: " + operator);
		
		String formattedTotal = "";
		
		String userInput = getUserInput();
		m_input = Double.parseDouble(userInput);
		String formattedInput = formatDouble(m_input);
		
		changeDisplay("Clear", INPUT);
		
		switch (operator)
		{
			case "±":
			{
				
				m_total = calculator.multiply(m_input, -1);
				break;
			}
			case "1/x":
			{
				changeDisplay("Clear", EXPRESSION);
				changeDisplay("reciprocal(" + formattedInput + ")", EXPRESSION);
				m_total = calculator.divide(1, m_input);
				break;
			}
			case "%":
			{
				double result = calculator.percent(m_total, m_input);
				formattedTotal = formatDouble(result);
				changeDisplay(formattedTotal, EXPRESSION);
				break;
			}
			case "√":
			{
				changeDisplay("Clear", EXPRESSION);
				changeDisplay("sqrt(" + formattedInput + ")", EXPRESSION);
				m_total = calculator.squareRoot(m_input);
				break;
			}
			//Error:
			default:
			{
				System.out.println("An unknown error has occurred");
			}
		}
		
		if (!operator.equals("%"))	formattedTotal = formatDouble(m_total);

		changeDisplay(formattedTotal, INPUT);
		m_typeOverFlag = true;
	}	
	
}
