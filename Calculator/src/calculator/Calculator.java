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
	
	public double add(double firstAddend, double secondAddend)
	{
		return firstAddend + secondAddend;
	}
	
	

}
