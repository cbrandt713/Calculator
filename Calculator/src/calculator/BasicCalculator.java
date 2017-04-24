package calculator;

import javax.swing.JOptionPane;

/**
 * Performs the operations of the calculator in the "Basic" view. 
 */
public class BasicCalculator extends Calculator<Double> 
{
	/** The second operation, if applicable. */
	private String m_operation2;
	
	/**
	 * Instantiates a new basic calculator.
	 */
	public BasicCalculator()
	{
		resetAll();
	}
	
	/** 
	 * @see calculator.Calculator#resetAll()
	 */
	protected void resetAll()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
		m_result = -Double.MAX_VALUE;
		m_operation = "";
		m_operation2 = "";
	}
	
	/** 
	 * @see calculator.Calculator#resetInputs()
	 */
	protected void resetInputs()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
	}
	
	/**
	 * @see calculator.Calculator#setOperation(java.lang.String)
	 */
	public void setOperation(String a_operation)
	{
		if (m_operation.equals("")) m_operation = a_operation;
		else m_operation2 = a_operation;
	}
	
	/**
	 * @see calculator.Calculator#setInput(java.lang.Object)
	 */
	public void setInput(Double a_input)
	{
		if (m_input == -Double.MAX_VALUE || m_operation.equals("")) m_input = a_input;
		else m_input2 = a_input;
	}

	/** 
	 * @see calculator.Calculator#getAmountInputs()
	 */
	public int getAmountInputs()
	{
		if (m_input == -Double.MAX_VALUE) return 0;
		else if (m_input2 == -Double.MAX_VALUE) return 1;
		
		return 2;
	}
	/**
	 * @see calculator.Calculator#doCalculation()
	 */
	public Double doCalculation() 
	{
		//If less than two operands, no calculation. Return original value.
		if (getAmountInputs() <= 1)
		{
			return m_input;
		}
		
		//Attempting to do a calculation on NaN:
		if ( Double.isNaN(m_input) || Double.isNaN(m_input2))
		{
			JOptionPane.showMessageDialog(GUI.getGUIInstance(), "Please press clear to continue.", "Error", JOptionPane.ERROR_MESSAGE);
			return m_input;
		}
		
		//Do the operation and get the result:
		switch (m_operation)
		{
			case "+":
			{
				m_result = add(m_input, m_input2);
				break;
			}
			case "-":
			{
				m_result = subtract(m_input, m_input2);
				break;
			}
			case "*":
			{
				m_result = multiply(m_input, m_input2);
				break;
			}
			case "/":
			{
				m_result = divide(m_input, m_input2);
				break;
			}
			case "%":
			{
				m_result  = percent(m_input, m_input2);
				break;
			}
			//Error case:
			default:
			{
				System.out.println("An unknown error has occurred");
				break;
			}
		}
		
		//Reset inputs and return result:
		resetInputs();
		m_operation = "";
		setInput(m_result);
		
		return m_result;
	}
	
	/**
	 * Do the appropriate unary calculation.
	 *
	 * @return the result of the calculation
	 */
	public Double doUnaryCalculation() 
	{
		if (m_input == Double.NaN || m_input == Double.NaN)
		{
			JOptionPane.showMessageDialog(GUI.getGUIInstance(), "Please press clear to continue.");
			return m_input;
		}
		
		String operation = "";
		
		if (!m_operation2.equals("")) operation = m_operation2;
		else operation = m_operation;
		
		switch (operation)
		{
			case "±":
			{
				//Use the most recent input:
				if (m_input2 != -Double.MAX_VALUE) m_result = multiply(m_input2, -1);
				else m_result = multiply(m_input, -1);
				
				break;
			}
			case "1/x":
			{
				//Use the most recent input:
				if (m_input2 != -Double.MAX_VALUE) m_result = divide(1, m_input2);
				else m_result = divide(1, m_input);
				
				break;
			}
			case "√":
			{
				//Use the most recent input:
				if (m_input2 != -Double.MAX_VALUE) m_result = squareRoot(m_input2);
				else m_result = squareRoot(m_input);
				
				break;
			}
			case "=":
			{
				m_result = doCalculation();
				m_input2 = -Double.MAX_VALUE;
				m_operation2 = "";
				break;
			}
			//Error:
			default:
			{
				System.out.println("An unknown error has occurred");
			}
		}
		
		//Set the most recent input:
		if (m_input2 != -Double.MAX_VALUE) m_input2 = m_result;
		else m_input = m_result;
		
		//Reset the most recent operation:
		if (!m_operation2.equals("")) m_operation2 = "";
		else m_operation = "";
		
		return m_result;
	}
	
	/**
	 * Adds two inputs.
	 *
	 * @param a_LHS the left-hand side
	 * @param a_RHS the right-hand side
	 * @return the sum
	 */
	public double add(double a_LHS, double a_RHS)
	{
		return a_LHS + a_RHS;
	}
	
	/**
	 * Subtracts two inputs.
	 *
	 * @param a_LHS the left-hand side
	 * @param a_RHS the right-hand side
	 * @return the difference
	 */
	public double subtract(double a_LHS, double a_RHS)
	{
		return a_LHS - a_RHS;
	}
	
	/**
	 * Multiplies two inputs.
	 *
	 * @param a_LHS the left-hand side
	 * @param a_RHS the right-hand side
	 * @return the product
	 */
	public double multiply(double a_LHS, double a_RHS)
	{
		return a_LHS * a_RHS;
	}
	
	/**
	 * Divide two inputs.
	 *
	 * @param dividend the dividend
	 * @param divisor the divisor
	 * @return the quotient
	 */
	public double divide(double a_dividend, double a_divisor)
	{
		return (a_dividend / a_divisor);
	}
	
	/**
	 * Computes the square root of the input.
	 *
	 * @param a_input the number
	 * @return the square root of the input
	 */
	public double squareRoot(double a_input)
	{
		return Math.sqrt(a_input);
	}
	
	/**
	 * Calculate the percent as defined by Microsoft's calculator. 
	 * In this case, the method will just calculate a percentage of a total
	 * and return the total.
	 *
	 * @param a_total the total
	 * @param a_percentage the percentage
	 * @return the percentage of the total
	 */
	public double percent(double a_total, double a_percentage)
	{
		a_percentage *= 0.01;
		a_total *= a_percentage;
		return a_total;
	}
}
