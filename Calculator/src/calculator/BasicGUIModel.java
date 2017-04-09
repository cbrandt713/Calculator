package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

public class BasicGUIModel {

	//Display Line Number Constants:
	public static final int EXPRESSION = 0;
	public static final int INPUT = 1;
	
	private double m_input;
	private double m_total;
	
	//Calculator and Required Data:
	private BasicCalculator calculator;
	private BasicTextArea m_displayText;
	
	public BasicGUIModel()
	{
		//Create calculator
		calculator = new BasicCalculator();	
		m_displayText = BasicTextArea.getBasicTextAreaInstance();
	}
	
	public void numberActionPerformed(ActionEvent a_event)
	{	
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
		 	KeyEvent ke = (KeyEvent) EventQueue.getCurrentEvent();
	        keyStroke = KeyEvent.getKeyText( ke.getKeyCode() );
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
		
		calculator.setOperation(operator);
		
		//Get user input and send to calculator:
		String userInput = getUserInput();
		m_input = Double.parseDouble(userInput);
		calculator.setInput(m_input);
		
		//Show the input and operator on the expression line
		changeDisplay(userInput + " " + operator + " ", EXPRESSION);
		changeDisplay("Clear", INPUT);
		
		m_total = calculator.doCalculation();
		
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
			case "Â±":
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
