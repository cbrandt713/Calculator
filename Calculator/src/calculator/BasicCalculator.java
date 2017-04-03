package calculator;

public class BasicCalculator extends Calculator<Double> 
{
	public BasicCalculator()
	{
		resetAll();
	}
	
	protected void resetAll()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
		m_result = -Double.MAX_VALUE;
		m_operation = "";
	}
	
	protected void resetInputs()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
	}
	
	public void setOperation(String a_operation)
	{
		m_operation = a_operation;
	}
	
	public void setInput(Double a_input)
	{
		if (m_input == null || m_operation.equals("")) m_input = a_input;
		else m_input2 = a_input;
	}

	public Double doCalculation()
	{
		//If less than two operands, no calculation. Return original value.
		if (m_input == -Double.MAX_VALUE && m_input2 == -Double.MAX_VALUE)
		{
			return m_input;
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
		m_input = m_result;
		
		return m_result;
	}
	
	public double add(double LHS, double RHS)
	{
		return LHS + RHS;
	}
	
	public double subtract(double LHS, double RHS)
	{
		return LHS - RHS;
	}
	
	public double multiply(double LHS, double RHS)
	{
		return LHS * RHS;
	}
	
	public double divide(double dividend, double divisor)
	{
		return (dividend / divisor);
	}
	
	public double squareRoot(double input)
	{
		return Math.sqrt(input);
	}
	
	public double percent(double total, double percentage)
	{
		percentage *= 0.01;
		total *= percentage;
		return total;
	}
}
