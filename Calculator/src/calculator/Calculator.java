package calculator;

import java.util.Vector;

public class Calculator {
	
	private static Calculator calcObj = null;
	private Vector<Double> operands;
	private Vector<String> operators;
	
	private Calculator()
	{
		operands = new Vector<Double>();
		operators = new Vector<String>();
	}
	
	public static Calculator getCalculatorInstance()
	{
		if (calcObj == null)
		{
			calcObj = new Calculator();
			
		}
		
		return calcObj;
	}
	
	public double doCalculation()
	{
		//If less than two operands, no calculation. Return original value.
		if (operands.size() < 2)
		{
			return operands.get(0);
		}
		
		double total = operands.get(0);
		double input = operands.get(1);
		
		switch (operators.get(0))
		{
			case "+":
			{
				total = add(total, input);
				break;
			}
			case "-":
			{
				total = subtract(total, input);
				break;
			}
			case "*":
			{
				total = multiply(total, input);
				break;
			}
			case "/":
			{
				total = divide(total, input);
				break;
			}	
			case "=":
			{
				total = input;
				break;
			}
			//Error case:
			default:
			{
				System.out.println("An unknown error has occurred");
				break;
			}
		}
		
		operands.remove(0);
		operands.remove(0);
		operands.add(total);
		
		operators.remove(0);
		
		return total;
		
	}
	
	public void pushOperator(String operator)
	{
		operators.addElement(operator);
	}
	
	public void pushOperand(double operand)
	{
		operands.addElement(operand);
	}
	
	public void pushOperand(int operand)
	{
		double op = (double) operand;
		pushOperand(op);
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
