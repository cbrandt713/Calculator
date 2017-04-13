package calculator;

import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicCalculator.
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
	
	/* (non-Javadoc)
	 * @see calculator.Calculator#resetAll()
	 */
	protected void resetAll()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
		m_result = -Double.MAX_VALUE;
		m_operation = "";
	}
	
	/* (non-Javadoc)
	 * @see calculator.Calculator#resetInputs()
	 */
	protected void resetInputs()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
	}
	
	/* (non-Javadoc)
	 * @see calculator.Calculator#setOperation(java.lang.String)
	 */
	public void setOperation(String a_operation)
	{
		m_operation = a_operation;
	}
	
	/* (non-Javadoc)
	 * @see calculator.Calculator#setInput(java.lang.Object)
	 */
	public void setInput(Double a_input)
	{
		if (m_input == -Double.MAX_VALUE || m_operation.equals("")) m_input = a_input;
		else m_input2 = a_input;
	}

	/* (non-Javadoc)
	 * @see calculator.Calculator#getAmountInputs()
	 */
	public int getAmountInputs()
	{
		if (m_input == -Double.MAX_VALUE) return 0;
		else if (m_input2 == -Double.MAX_VALUE) return 1;
		
		return 2;
	}
	/* (non-Javadoc)
	 * @see calculator.Calculator#doCalculation()
	 */
	public Double doCalculation() 
	{
		//If less than two operands, no calculation. Return original value.
		if (m_input2 == -Double.MAX_VALUE)
		{
			return m_input;
		}
		
		if ( Double.isNaN(m_input) || Double.isNaN(m_input2))
		{
			JOptionPane.showMessageDialog(GUI.getGUIInstance(), "Please press clear to continue.", "Error", JOptionPane.ERROR_MESSAGE);
			return m_input;
			//throw new Exception("NaN input");
		}
		
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
		
		resetInputs();
		m_operation = "";
		setInput(m_result);
		
		return m_result;
	}
	
	/**
	 * Do misc calculation.
	 *
	 * @return the double
	 */
	public Double doMiscCalculation() 
	{
		if (m_input == Double.NaN || m_input == Double.NaN)
		{
			JOptionPane.showMessageDialog(GUI.getGUIInstance(), "Please press clear to continue.");
			return m_input;
			//throw new NumberFormatException("NaN input");
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
	 * @param LHS the left-hand side
	 * @param RHS the right-hand side
	 * @return the sum of the two
	 */
	public double add(double LHS, double RHS)
	{
		return LHS + RHS;
	}
	
	/**
	 * Subtract.
	 *
	 * @param LHS the lhs
	 * @param RHS the rhs
	 * @return the double
	 */
	public double subtract(double LHS, double RHS)
	{
		return LHS - RHS;
	}
	
	/**
	 * Multiply.
	 *
	 * @param LHS the lhs
	 * @param RHS the rhs
	 * @return the double
	 */
	public double multiply(double LHS, double RHS)
	{
		return LHS * RHS;
	}
	
	/**
	 * Divide.
	 *
	 * @param dividend the dividend
	 * @param divisor the divisor
	 * @return the double
	 */
	public double divide(double dividend, double divisor)
	{
		return (dividend / divisor);
	}
	
	/**
	 * Computes the square root of the input.
	 *
	 * @param input the number
	 * @return the square root of input
	 */
	public double squareRoot(double input)
	{
		return Math.sqrt(input);
	}
	
	/**
	 * Calculate the percent as defined by Microsoft's calculator. 
	 * In this case, the method will just calculate a percentage of a total
	 * and return the total.
	 *
	 * @param total the total
	 * @param percentage the percentage
	 * @return the double
	 */
	public double percent(double total, double percentage)
	{
		percentage *= 0.01;
		total *= percentage;
		return total;
	}
}
