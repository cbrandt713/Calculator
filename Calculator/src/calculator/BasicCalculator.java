package calculator;

public class BasicCalculator extends Calculator<Double> 
{
	public BasicCalculator()
	{
		resetInputs();
	}
	
	public void resetInputs()
	{
		m_input = -Double.MAX_VALUE;
		m_input2 = -Double.MAX_VALUE;
		m_result = -Double.MAX_VALUE;
		m_operator = "";
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
