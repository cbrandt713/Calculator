package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicGUIModel.
 */
public class BasicGUIModel implements ActionEventHandler {

	/** The Constant EXPRESSION. */
	//Display Line Number Constants:
	public static final int EXPRESSION = 0;
	
	/** The Constant INPUT. */
	public static final int INPUT = 1;
	
	/** The m input. */
	private double m_input;
	
	/** The m total. */
	private double m_total;
	
	/** The m replace. */
	private boolean m_replace;
	
	/** The m misc operation. */
	private boolean m_miscOperation;
	
	/** The m amt operands. */
	private int m_amtOperands;
	
	/** The m calculator. */
	//Calculator and Required Data:
	private BasicCalculator m_calculator;
	
	/** The m display. */
	private BasicTextArea m_display;
	
	/**
	 * Instantiates a new basic GUI model.
	 */
	public BasicGUIModel()
	{
		//Create calculator
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
	 * Format double.
	 *
	 * @param a_result the a result
	 * @return the string
	 */
	private String formatDouble(double a_result)
	{
		String res = ((Double) a_result).toString();
		String s = res.indexOf(".") < 0 ? res : res.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
	}
	
	/**
	 * Sets the up calculation.
	 *
	 * @param a_operator the new up calculation
	 */
	private void setupCalculation(String a_operator)
	{
		//Get user input and send to calculator:
		String userInput = m_display.getUserEnteredText();
		m_input = Double.parseDouble(userInput);
		m_calculator.setInput(m_input);
		
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
	 * Display result.
	 *
	 * @param a_result the a result
	 * @param a_operator the a operator
	 */
	private void displayResult(double a_result, String a_operator)
	{
		String formattedResult = formatDouble(a_result);
		if (a_operator.equals("%")) m_display.changeDisplay(formattedResult, EXPRESSION, m_replace);
		m_display.changeDisplay(formattedResult, INPUT, false);
	}

	/* (non-Javadoc)
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
	
	/* (non-Javadoc)
	 * @see calculator.ActionEventHandler#enterActionPerformed(java.awt.event.ActionEvent)
	 */
	public void enterActionPerformed(ActionEvent a_event)
	{
		//Enter key is equivalent to the = operator.
		//Do not change the operator in calculator here
		setupCalculation("=");
		m_display.clearExpression();
	}
	
	/* (non-Javadoc)
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
	
	/* (non-Javadoc)
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
	 * Misc operator action performed.
	 *
	 * @param a_event the a event
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

	/* (non-Javadoc)
	 * @see calculator.ActionEventHandler#letterActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void letterActionPerformed(ActionEvent a_event) 
	{
		numberActionPerformed(a_event);
	}	
	
}
