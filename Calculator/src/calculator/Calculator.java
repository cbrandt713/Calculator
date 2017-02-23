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
	
	public double doBasicCalculation()
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
	
	//Add the rows in a_matrix in index a_fromIndex to the row in a_toIndex
	public double[] addRow(Matrix a_matrix, int a_fromIndex, int a_toIndex, boolean a_subtract)
	{
		double[] fromRow = a_matrix.getRow(a_fromIndex);
		double[] toRow = a_matrix.getRow(a_toIndex);
		
		double[] resultRow = new double[fromRow.length];
		
		for (int i = 0; i < fromRow.length; i++)
		{
			if (a_subtract)
			{ 
				resultRow[i] = fromRow[i] - toRow[i];
			}
			else
			{
				resultRow[i] = fromRow[i] + toRow[i];
			}
		}
		
		a_matrix.setRow(a_toIndex, resultRow);
		return resultRow;
		
	}
	
	public double[] multiplyRow(Matrix a_matrix, int a_rowIndex, double a_multBy, boolean a_divide)
	{
		double [] row = a_matrix.getRow(a_rowIndex);
		
		if (a_divide) a_multBy = (1 / a_multBy); 
		
		for (int i = 0; i < row.length; i++)
		{
			row[i] *= a_multBy;
		}
		
		a_matrix.setRow(a_rowIndex, row);
		
		return row;
	}
	
	public Matrix RREF(Matrix a_matrix)
	{
		int lead = 0;
		int numRows = a_matrix.getRows();
		int numCols = a_matrix.getColumns();
		
		
		System.out.println("Original matrix:");
		a_matrix.outputMatrix();
		
		Matrix rref = a_matrix;
		
		
		for (int row = 0; row < rref.getRows(); row++)
		{
			if (rref.getColumns() <= lead)
			{
				return rref;
			}
			int i = row;
			
			while (rref.getCell(i, lead) == 0)
			{
				i++;
				if (numRows == i)
				{
					i = row;
					lead++;
					if (numCols == lead)
					{
						return rref;
					}
				}
			}
			
			rref.swapRows(i, row);
			
			if (rref.getCell(row, lead) != 0) multiplyRow(rref, row, rref.getCell(row, lead), true);
			
			for (int j = i; j < numRows; j++)
			{
				 if (j != row)
				 {
					 double multNum = rref.getCell(j, lead);
					 multiplyRow(rref, row, multNum, false);
					 addRow(rref, row, j, true);
				 }
			}
			
			lead++;
		}
		
		System.out.println("RREF matrix:");
		rref.outputMatrix();
		
		return rref;
	}
	
	

}
