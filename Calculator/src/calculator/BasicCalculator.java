package calculator;

import javax.swing.JOptionPane;

/**
 * The class which performs the operations of the calculator in the "Basic" view. 
 */
public class BasicCalculator extends Calculator<Double> 
{
	
	/**
	 * Instantiates a new basic calculator.
	 */
	public BasicCalculator()
	{
		resetAll();
	}
	
	/** 
	 * Resets all values to defaults.
	 * @see calculator.Calculator#resetAll()
	 */
	protected void resetAll()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
		m_result = -Double.MAX_VALUE;
		m_operation = "";
	}
	
	/** 
	 * Resets only the inputs to defaults.
	 * @see calculator.Calculator#resetInputs()
	 */
	protected void resetInputs()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
	}
	
	/**
	 * Set the operation of the calculation.
	 * @param a_operation the operation to set
	 * @see calculator.Calculator#setOperation(java.lang.String)
	 */
	public void setOperation(String a_operation)
	{
		m_operation = a_operation;
	}
	
	/**
	 * Set an input for the calculation.
	 * @param a_input the input to set
	 * @see calculator.Calculator#setInput(java.lang.Object)
	 */
	public void setInput(Double a_input)
	{
		if (m_input == -Double.MAX_VALUE || m_operation.equals("")) m_input = a_input;
		else m_input2 = a_input;
	}

	/** 
	 * Returns the amount of inputs set in the calculator.
	 * @see calculator.Calculator#getAmountInputs()
	 */
	public int getAmountInputs()
	{
		if (m_input == -Double.MAX_VALUE) return 0;
		else if (m_input2 == -Double.MAX_VALUE) return 1;
		
		return 2;
	}
	/**
	 * Do the appropriate calculation.
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
			case "=":
			{
				m_result = m_input;
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
	 * Do the appropriate "miscellaneous" calculation.
	 *
	 * @return the result of the calculation
	 */
	public Double doMiscCalculation() 
	{
		if (m_input == Double.NaN || m_input == Double.NaN)
		{
			JOptionPane.showMessageDialog(GUI.getGUIInstance(), "Please press clear to continue.");
			return m_input;
		}
		
		switch (m_operation)
		{
			case "±":
			{
				m_result = multiply(m_input, -1);
				break;
			}
			case "1/x":
			{
				m_result  = divide(1, m_input);
				break;
			}
			case "%":
			{
				m_result  = percent(m_input, m_input2);
				break;
			}
			case "√":
			{
				m_result  = squareRoot(m_input);
				break;
			}
			//Error:
			default:
			{
				System.out.println("An unknown error has occurred");
			}
		}
		
		resetInputs();
		m_operation = "";
		setInput(m_result);
		
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
