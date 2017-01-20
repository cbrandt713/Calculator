package calculator;

public class Calculator {
	
	private static Calculator calcObj = null;
	
	private Calculator()
	{
		
	}
	
	public static Calculator getCalculatorInstance()
	{
		if (calcObj == null)
		{
			calcObj = new Calculator();
			
		}
		
		return calcObj;
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
	
	public double divide(double LHS, double RHS)
	{
		return LHS / RHS;
	}
	
	

}
