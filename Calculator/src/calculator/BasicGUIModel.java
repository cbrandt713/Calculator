package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

public class BasicGUIModel implements ActionEventHandler {

	//Display Line Number Constants:
	public static final int EXPRESSION = 0;
	public static final int INPUT = 1;
	
	private double m_input;
	private double m_total;
	
	private boolean m_replace;
	
	//Calculator and Required Data:
	private BasicCalculator calculator;
	private BasicTextArea m_display;
	
	public BasicGUIModel()
	{
		//Create calculator
		calculator = new BasicCalculator();	
		m_display = BasicTextArea.getBasicTextAreaInstance();
		
		m_replace = true;
	}
	
	private String formatDouble(double a_result)
	{
		String res = ((Double) a_result).toString();
		String s = res.indexOf(".") < 0 ? res : res.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
	}
	
	public void numberActionPerformed(ActionEvent a_event)
	{	
		//Possible error: If user uses an operation before a number
		m_display.changeDisplay(a_event.getActionCommand(), INPUT, m_replace);
		
		if (m_replace)
		{
			m_replace = false;
		}
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
		}

		//Clear:
		if (a_event.getActionCommand().equals("Clr") || keyStroke.equals("Delete"))
		{
			m_display.changeDisplay("Clear", EXPRESSION, m_replace);
			m_display.changeDisplay("Clear", INPUT, m_replace);
			m_display.changeDisplay("0", INPUT, m_replace);
			m_input = 0;
			m_total = 0;
			m_replace = true;
		}
		//Clear Entry:
		else if (a_event.getActionCommand().equals("CE"))
		{
			m_display.changeDisplay("Clear", INPUT, m_replace);
			m_display.changeDisplay("0", INPUT, m_replace);
			m_input = 0;
			m_replace = true;
		}
		//Backspace:
		else
		{
			m_display.changeDisplay("Backspace", INPUT, m_replace);
		}
		
		//If the text is now blank, place a 0 on the input line and set the typeOverFlag
		if (m_display.getUserEnteredText().equals(""))
		{
			m_display.append("0");
			m_replace = true;
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
		String userInput = m_display.getUserEnteredText();
		m_input = Double.parseDouble(userInput);
		calculator.setInput(m_input);
		
		//Show the input and operator on the expression line
		m_display.changeDisplay(userInput + " " + operator + " ", EXPRESSION, m_replace);
		m_display.changeDisplay("Clear", INPUT, m_replace);
		
		m_total = calculator.doCalculation();
		
		//Format and display the total to the user:
		String formattedTotal = formatDouble(m_total);
		m_display.changeDisplay(formattedTotal, INPUT, m_replace);
		
		m_replace = true;
	}
	
	public void miscOperatorActionPerformed(ActionEvent a_event)
	{
		
		String operator = a_event.getActionCommand();
		String formattedTotal = "";
		
		String userInput = m_display.getUserEnteredText();
		m_input = Double.parseDouble(userInput);
		String formattedInput = formatDouble(m_input);
		
		m_display.changeDisplay("Clear", INPUT, m_replace);
		
		switch (operator)
		{
			case "±":
			{
				
				m_total = calculator.multiply(m_input, -1);
				break;
			}
			case "1/x":
			{
				m_display.changeDisplay("Clear", EXPRESSION, m_replace);
				m_display.changeDisplay("reciprocal(" + formattedInput + ")", EXPRESSION, m_replace);
				m_total = calculator.divide(1, m_input);
				break;
			}
			case "%":
			{
				double result = calculator.percent(m_total, m_input);
				formattedTotal = formatDouble(result);
				m_display.changeDisplay(formattedTotal, EXPRESSION, m_replace);
				break;
			}
			case "√":
			{
				m_display.changeDisplay("Clear", EXPRESSION, m_replace);
				m_display.changeDisplay("sqrt(" + formattedInput + ")", EXPRESSION, m_replace);
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

		m_display.changeDisplay(formattedTotal, INPUT, m_replace);
		m_replace = true;
	}

	@Override
	public void letterActionPerformed(ActionEvent a_event) 
	{
		
	}	
	
}
