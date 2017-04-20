package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * The class which controls the TextPane and the Calculator for the "Basic" view.
 * This class should control what text gets displayed and what operations the calculator performs.
 */
public class BasicGUIModel implements ActionEventHandler {

	//Display Line Number Constants:
	
	/** The EXPRESSION line. */
	public static final int EXPRESSION = 0;
	
	/** The INPUT line. */
	public static final int INPUT = 1;
	
	/** The input. */
	private double m_input;
	
	/** The total. */
	private double m_total;
	
	/** Replace previous text. */
	private boolean m_replace;
	
	/** Is misc operation. */
	private boolean m_miscOperation;
	
	/** The amount of operands. */
	private int m_amtOperands;
	
	/** The calculator. */
	private BasicCalculator m_calculator;
	
	/** The display. */
	private BasicTextArea m_display;
	
	/**
	 * Instantiates a new basic GUI model.
	 */
	public BasicGUIModel()
	{
		//Create calculator and display:
		m_calculator = new BasicCalculator();	
		m_display = BasicTextArea.getBasicTextAreaInstance();
		
		setDefaultValues();
	}
	
	/**
	 * Sets the default values.
	 */
	private void setDefaultValues()
	{
		m_display.setText("\n0");
		m_calculator.resetAll();
		
		m_input = 0;
		m_total = 0;
		m_replace = true;
		m_miscOperation = false;
		m_amtOperands = 0;
	}
	
	/**
	 * Format a double for printing to the calculator
	 *
	 * @param a_result the double to format
	 * @return the formatted double
	 */
	private String formatDouble(double a_result)
	{
		String strResult = ((Double) a_result).toString();
		String formatted = strResult.indexOf(".") < 0 ? strResult : strResult.replaceAll("0*$", "").replaceAll("\\.$", "");
		return formatted;
	}
	
	/**
	 * Sets up the calculation.
	 *
	 * @param a_operator the operator to perform the calculation on.
	 */
	private void setupCalculation(String a_operator)
	{
		//Get user input and send to calculator:
		String userInput = m_display.getUserEnteredText();
		m_input = Double.parseDouble(userInput);
		m_calculator.setInput(m_input);
		
		//If we got NaN result, do not proceed:
		if (Double.isNaN(m_input))
		{
			JOptionPane.showMessageDialog(GUI.getGUIInstance(), "Please press clear to continue.");
			return;
		}
		
		//Show the input and operator on the expression line
		m_display.changeDisplay(userInput + " " + a_operator + " ", EXPRESSION, m_replace);
		m_display.changeDisplay("Clear", INPUT, m_replace);
		
		int amtSelected = m_calculator.getAmountInputs();
		
		if (amtSelected < m_amtOperands) return;
		
		if (m_amtOperands == 1) m_display.setTextForUnary(a_operator, userInput);
		//else m_display.setTextForBinary(a_operator, userInput);
		
		if (!m_miscOperation) 
		{
			m_total = m_calculator.doCalculation();
		}
		else
		{
			m_total = m_calculator.doMiscCalculation();
		}
		
		//Format and display the total to the user:
		displayResult(m_total, a_operator);
		
		m_replace = true;
	}
	
	/**
	 * Display the result.
	 *
	 * @param a_result the result
	 * @param a_operator the operator 
	 */
	private void displayResult(double a_result, String a_operator)
	{
		String formattedResult = formatDouble(a_result);
		if (a_operator.equals("%")) m_display.changeDisplay(formattedResult, EXPRESSION, m_replace);
		m_display.changeDisplay(formattedResult, INPUT, false);
	}

	/** 
	 * @see calculator.ActionEventHandler#numberActionPerformed(java.awt.event.ActionEvent)
	 */
	public void numberActionPerformed(ActionEvent a_event)
	{	
		//Possible error: If user uses an operation before a number
		m_display.changeDisplay(a_event.getActionCommand(), INPUT, m_replace);
		
		if (m_replace)
		{
			m_replace = false;
		}
	}
	
	/**
	 * @see calculator.ActionEventHandler#enterActionPerformed(java.awt.event.ActionEvent)
	 */
	public void enterActionPerformed(ActionEvent a_event)
	{
		//Enter key is equivalent to the = operator.
		//Do not change the operator in calculator here
		setupCalculation("=");
		m_display.clearExpression();
	}
	
	/**
	 * @see calculator.ActionEventHandler#deleteActionPerformed(java.awt.event.ActionEvent)
	 */
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
	
	/**
	 * @see calculator.ActionEventHandler#operatorActionPerformed(java.awt.event.ActionEvent)
	 */
	public void operatorActionPerformed(ActionEvent a_event)
	{
		//Find the given operator:
		String operator = a_event.getActionCommand();
		
		m_amtOperands = 2;
		
		setupCalculation(operator);
		
		m_calculator.setOperation(operator);
		m_miscOperation = false;
		
		
	}
	
	/**
	 * An action was fired that is associated with a miscellaneous operation being performed.
	 *
	 * @param a_event the event that fired the action
	 */
	public void miscOperatorActionPerformed(ActionEvent a_event)
	{
		
		String operator = a_event.getActionCommand();
		
		m_miscOperation = true;
		
		if (operator.equals("%")) 
		{
			m_amtOperands = 2;
			setupCalculation(operator);	
			m_calculator.setOperation(operator);
		}
		else 
		{
			m_amtOperands = 1;
			m_calculator.setOperation(operator);
			setupCalculation(operator);	
		}		
	}

	/**
	 * @see calculator.ActionEventHandler#letterActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void letterActionPerformed(ActionEvent a_event) 
	{
		//Same as a number action:
		numberActionPerformed(a_event);
	}	
	
}
