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
	private boolean m_miscOperation;
	
	private int m_amtSelected;
	private int m_amtOperands;
	
	//Calculator and Required Data:
	private BasicCalculator m_calculator;
	private BasicTextArea m_display;
	
	public BasicGUIModel()
	{
		//Create calculator
		m_calculator = new BasicCalculator();	
		m_display = BasicTextArea.getBasicTextAreaInstance();
		
		setDefaultValues();
	}
	
	private void setDefaultValues()
	{
		m_display.setText("\n0");
		m_calculator.resetAll();
		
		m_input = 0;
		m_total = 0;
		m_replace = true;
		m_miscOperation = false;
		m_amtSelected = 0;
		m_amtOperands = 0;
	}
	
	private String formatDouble(double a_result)
	{
		String res = ((Double) a_result).toString();
		String s = res.indexOf(".") < 0 ? res : res.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
	}
	
	private void setupCalculation(String a_operator)
	{
		//Get user input and send to calculator:
		String userInput = m_display.getUserEnteredText();
		m_input = Double.parseDouble(userInput);
		m_calculator.setInput(m_input);
		
		//Show the input and operator on the expression line
		m_display.changeDisplay(userInput + " " + a_operator + " ", EXPRESSION, m_replace);
		m_display.changeDisplay("Clear", INPUT, m_replace);
		
		m_amtSelected++;
		
		if (m_amtSelected != m_amtOperands) return;
		
		if (m_amtOperands == 1) m_display.setTextForUnary(a_operator, userInput);
		else m_display.setTextForBinary(a_operator, userInput);
		
		if (!m_miscOperation) 
		{
			m_total = m_calculator.doCalculation();
		}
		else
		{
			m_total = m_calculator.doMiscCalculation();
		}
		
		//Format and display the total to the user:
		String formattedTotal = formatDouble(m_total);
		if (a_operator.equals("%")) m_display.changeDisplay(formattedTotal, EXPRESSION, m_replace);
		m_display.changeDisplay(formattedTotal, INPUT, m_replace);
		
		m_replace = true;
	}
	
	private void setupMiscCalculation(String a_operator, String formattedInput) 
	{
		
		switch (a_operator)
		{
			
			case "%":
			{
				m_display.changeDisplay(formattedInput, EXPRESSION, m_replace);
				break;
			}
			
			//Error:
			default:
			{
				System.out.println("An unknown error has occurred");
			}
		}
		
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
		//Enter key is equivalent to the = operator.
		//Do not change the operator in calculator here
		setupCalculation("=");
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
			setDefaultValues();
		}
		//Clear Entry:
		else if (a_event.getActionCommand().equals("CE"))
		{
			m_display.clearEntry();
			m_input = 0;
			m_replace = true;
		}
		//Backspace:
		else
		{
			m_display.backspace();
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
		
		m_amtOperands = 2;
		
		setupCalculation(operator);
		
		m_calculator.setOperation(operator);
		m_miscOperation = false;
		
		
	}
	
	public void miscOperatorActionPerformed(ActionEvent a_event)
	{
		
		String operator = a_event.getActionCommand();
		
		if (operator.equals("%")) m_amtOperands = 2;
		else m_amtOperands = 1;
		
		setupCalculation(operator);	
		
		m_calculator.setOperation(operator);
		m_miscOperation = true;
		
			
	}

	@Override
	public void letterActionPerformed(ActionEvent a_event) 
	{
		numberActionPerformed(a_event);
	}	
	
}
