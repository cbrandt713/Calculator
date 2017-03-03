package calculator;

import java.util.Vector;

public class Calculator {
	
	private static Calculator calcObj = null;
	private double m_total;
	private double m_input;
	private String m_operator;
	private Matrix m_matrixResult;
	private Matrix m_matrixInput;
	private Matrix m_matrixInput2;
	
	private Calculator()
	{
		m_total = -Double.MAX_VALUE;
		m_input = -Double.MAX_VALUE;
		m_operator = "";
		m_matrixResult = null;
		m_matrixInput = null;
		m_matrixInput2 = null;
	}
	
	public static Calculator getCalculatorInstance()
	{
		if (calcObj == null)
		{
			calcObj = new Calculator();	
		}
		
		return calcObj;
	}
	
	public void setOperator(String a_operator)
	{
		m_operator = a_operator;
	}
	
	public void setInput(double a_input)
	{
		m_input = a_input;
	}
	
	public void setMatrixInput(Matrix a_operand)
	{
		if (m_matrixInput == null) m_matrixInput = a_operand;
		else m_matrixInput2 = a_operand;
	}
	
	
	public double doBasicCalculation()
	{
		//If less than two operands, no calculation. Return original value.
		if (m_total == -Double.MAX_VALUE && m_input == -Double.MAX_VALUE)
		{
			return m_input;
		}
		
		switch (m_operator)
		{
			case "+":
			{
				m_total = add(m_total, m_input);
				break;
			}
			case "-":
			{
				m_total = subtract(m_total, m_input);
				break;
			}
			case "*":
			{
				m_total = multiply(m_total, m_input);
				break;
			}
			case "/":
			{
				m_total = divide(m_total, m_input);
				break;
			}	
			case "=":
			{
				m_total = m_input;
				break;
			}
			//Error case:
			default:
			{
				System.out.println("An unknown error has occurred");
				break;
			}
		}
		
		m_operator = "";
		
		return m_total;
	}
	
	public Matrix doMatrixOperation() throws MatrixException
	{
		
		switch (m_operator)
		{
			//Binary operations. Requires two operands.
			case "+":
			case "-":
			case "*":
			case "/":
			{
				m_matrixResult = matrixBinaryOperation();
				break;
			}
			//Unary operation:
			case "RREF":
			case "REF":
			case "Inverse":
			{
				
				m_matrixResult = RREF(m_matrixInput);
				break;
			}
			case "":
			default:
			{
				System.out.println("An unknown error has occurred.");
				break;
			}
		}
		
		m_matrixInput = m_matrixResult;
		m_matrixInput2 = null;
		
		return m_matrixResult;
	}
	
	public Matrix matrixBinaryOperation() throws MatrixException
	{
		if (m_matrixInput == null || m_matrixInput2 == null)
		{
			return new Matrix(0, 0);
		}
		
		
		if (m_operator.equals("+")) m_matrixResult = addMatrices(m_matrixInput, m_matrixInput2);
		else if (m_operator.equals("-")) m_matrixResult = subtractMatrices(m_matrixInput, m_matrixInput2);
		else if (m_operator.equals("*")) m_matrixResult = multiplyMatrices(m_matrixInput, m_matrixInput2);
		else m_matrixResult = divideMatrices(m_matrixInput, m_matrixInput2);
		
		return m_matrixResult;
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
	
	public Matrix addMatrices(Matrix a_LHS, Matrix a_RHS) throws MatrixException
	{
		//Make sure the matrices are of compatible size:
		if (a_LHS.getRows() != a_RHS.getRows() || a_LHS.getColumns() != a_RHS.getColumns())
		{
			throw new MatrixException("Sizes do not match", a_LHS, a_RHS); 
		}
		
		//The sum will now be of the same size as either element:
		Matrix sum = new Matrix(a_LHS.getRows(), a_LHS.getColumns());
		
		//Matrices are added element-wise:
		//Loop through the matrix, add each element.
		for (int row = 0; row < sum.getRows(); row++)
		{
			for (int column = 0; column < sum.getColumns(); column++)
			{
				//The value is simply the current element of each matrix added together.
				Fraction value = new Fraction();
				value = a_LHS.getCell(row, column).add(a_RHS.getCell(row, column));
				sum.setCell(row, column, value);
			}
		}
		
		return sum;
	}
	
	public Matrix subtractMatrices(Matrix a_LHS, Matrix a_RHS) throws MatrixException
	{
		//Copy the values of the Right Hand Side so as to not actually alter it:
		Matrix newRHS = new Matrix(a_RHS);
		
		//Take the opposite of every number in the copied RHS:
		for (int row = 0; row < a_RHS.getRows(); row++)
		{
			for (int column = 0; column < a_RHS.getColumns(); column++)
			{
				Fraction current = newRHS.getCell(row, column);
				newRHS.setCell(row, column, current.multiply(-1));
			}
		}
		
		//Now add the two matrices like normal. This functions the same as subtraction:
		return addMatrices(a_LHS, newRHS);
	}
	
	
	private Fraction multiplyRowByColumn(Fraction[] a_row, Fraction[] a_column)
	{
		int length = a_row.length;
		
		Fraction[] products = new Fraction[length];
		
		for (int i = 0; i < length; i++)
		{
			Fraction product = a_row[i].multiply(a_column[i]);
			products[i] = product;
		}
		
		Fraction total = new Fraction(0);
		for (int i = 0; i < length; i++)
		{
			total.add(products[i]);
		}
		
		return total;
	}
	
	public Matrix multiplyMatrices(Matrix a_LHS, Matrix a_RHS) throws MatrixException
	{
		//The amount of columns in the LHS must match number of rows in the RHS:
		if (a_LHS.getColumns() != a_RHS.getRows())
		{
			throw new MatrixException("Invalid dimensions", a_LHS, a_RHS);
		}
		
		//The new product will have the rows of the LHS and the columns of the RHS.
		Matrix product = new Matrix(a_LHS.getRows(), a_RHS.getColumns());
		
		for (int row = 0; row < product.getRows(); row++)
		{	
			Fraction[] LHSrow = a_LHS.getRow(row);
			for (int column = 0; column < product.getColumns(); column++)
			{
				Fraction[] RHScolumn = a_RHS.getColumn(column);
				
				//Run the across the "row" index of LHS, and down the "column" index of RHS.
				Fraction current = multiplyRowByColumn(LHSrow, RHScolumn);
				
				product.setCell(row, column, current);
			}
		}
			
		return product;
	}
	
	public Matrix divideMatrices(Matrix a_LHS, Matrix a_RHS) throws MatrixException
	{
		return new Matrix(0, 0);
	}

	
	public Matrix RREF(Matrix a_matrix)
	{
		int numRows = a_matrix.getRows();
		int numCols = a_matrix.getColumns();
		
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
		
		return rref;	
		
	}
	
	public Matrix invertMatrix(Matrix a_matrix) throws MatrixException
	{
		//Check for square matrix:
		if (!a_matrix.isSquareMatrix())
		{
			throw new MatrixException("Not a square matrix", a_matrix);
		}
		
		return new Matrix(0, 0);
		
	}

}
