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
	public Fraction[] addRow(Fraction[] fromRow, Fraction[] toRow, boolean a_subtract)
	{
		Fraction[] resultRow = new Fraction[fromRow.length];
		
		for (int i = 0; i < fromRow.length; i++)
		{
			if (a_subtract)
			{ 
				resultRow[i] = fromRow[i].subtract(toRow[i]);
			}
			else
			{
				resultRow[i] = fromRow[i].add(toRow[i]);
			}
		}
		
		return resultRow;
		
	}
	
	public Fraction[] multiplyRow(Fraction[] a_row, Fraction a_multBy, boolean a_divide)
	{
		if (a_divide) a_multBy = a_multBy.reciprocal(); 
		
		Fraction[] newRow = new Fraction[a_row.length];
		
		for (int i = 0; i < a_row.length; i++)
		{
			newRow[i] = a_row[i].multiply(a_multBy);
		}
		
		return newRow;
	}
	
	public Matrix RREF(Matrix a_matrix)
	{
		int numRows = a_matrix.getRows();
		int numCols = a_matrix.getColumns();
		
		System.out.println("To string:");
		System.out.println(a_matrix.toString());
		
		//Copy the original matrix:
		Matrix rref = new Matrix(a_matrix);
		
		//Go through each column: start at the leftmost column
		for (int rowIndex = 0; rowIndex < numRows; rowIndex++)
		{
			int columnIndex = rowIndex;
			int i;
			//Check for column of zeroes:
			for (i = columnIndex; i < numCols; i++)
			{
				if (!rref.isColumnZeroes(i))
				{
					break;
				}
			}
			
			//Break from last loop at non-zero column. Use this column:
			columnIndex = i;
			
			//Create leading one Step:
			
			//Get the cell to create the leading one
			Fraction cellValue = rref.getCell(rowIndex, columnIndex);
			//divide the whole row by that value to create a one:
			Fraction[] leadOneRow = multiplyRow(rref.getRow(rowIndex), cellValue, true);
			rref.setRow(rowIndex, leadOneRow);
			
			//Create zeroes above and below step:
			//Find value to create the zero. Multiply lead one row by this value
			//Then subtract the produced row from the current row
			
			for (int j = 0; j < numRows; j++)
			{
				if (j == rowIndex) continue;
				
				Fraction multVal = rref.getCell(j, columnIndex);
				Fraction[] producedRow = multiplyRow(leadOneRow, multVal, false);
				Fraction[] resultRow = addRow(rref.getRow(j), producedRow, true);
				
				rref.setRow(j, resultRow);
			}
			
		}
		
		
		
		System.out.println("RREF matrix:");
		System.out.println(rref.toString());
		
		return rref;
		
		/*
		for (int row = 0; row < numRows; row++)
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
			
			double[] rowToMultiply;
			
			if (rref.getCell(row, lead) != 0)
			{
				rowToMultiply = a_matrix.getRow(row);
				rowToMultiply = multiplyRow(rowToMultiply, rref.getCell(row, lead), true);
			}
			
			for (int j = i; j < numRows; j++)
			{
				 if (j != row)
				 {
					 double [] originalRow = a_matrix.getRow(row);
					 double multNum = rref.getCell(j, lead);
					 
					 double[] resultRow = multiplyRow(originalRow, multNum, false);
					 double[] toRow = a_matrix.getRow(j);
					 
					 resultRow = addRow(resultRow, toRow, true);
					 rref.setRow(row, resultRow);
				 }
			}
			
			lead++;
		}
		*/
		
		
		
	}
	
	

}
