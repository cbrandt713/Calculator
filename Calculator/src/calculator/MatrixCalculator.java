package calculator;

public class MatrixCalculator extends Calculator<Matrix> {

	private Fraction m_fractionScalar;
	private Fraction m_fractionResult;
	
	public MatrixCalculator()
	{
		resetAll();
	}
	
	protected void resetAll()
	{
		m_input = null;
		m_input2 = null;
		m_result = null;
		m_operator = "";
		m_fractionScalar = null;
		m_fractionResult = null;
	}
	
	protected void resetInputs()
	{
		m_input = null;
		m_input2 = null;
		m_fractionScalar = null;
	}
	
	public void setOperator(String a_operator)
	{
		m_operator = a_operator;
	}
	
	public void setInput(Matrix a_input)
	{
		if (m_input == null) m_input = a_input;
		else m_input2 = a_input;
	}
	
	public void setScalar(Fraction a_input)
	{
		m_fractionScalar = a_input;
	}
	
	public Matrix doCalculation() throws MatrixException
	{
		switch (m_operator)
		{
			//Binary operations. Requires two operands.
			case "+":
			case "-":
			case "*":
			case "/":
			{
				m_result = matrixBinaryOperation();
				break;
			}
			case "=":
			{
				m_result = m_input;
				break;
			}
			//Unary operation:
			case "RREF":
			{
				m_result = RREF(m_input);
				break;
			}
			case "REF":
			{
				m_result = REF(m_input);
				break;
			}
			case "Inverse":
			{
				m_result = invertMatrix(m_input);
				break;
			}
			case "Scalar":
			{
				m_result = scalarMultiply(m_fractionScalar, m_input);
				break;
			}
			case "Transpose":
			{
				m_result = transpose(m_input);
				break;
			}
			case "":
			default:
			{
				System.out.println("An unknown error has occurred.");
				break;
			}
		}
		
		resetInputs();
		m_operator = "";
		m_input = m_result;
		
		return m_result;
	}
	
	public Fraction fractionResultOperation() throws MatrixException
	{
		switch (m_operator)
		{
			case "Det":
			{
				m_fractionResult = determinant(m_input);
				break;
			}
			case "Trace":
			{
				m_fractionResult = trace(m_input);
				break;
			}
			case "Rank":
			{
				m_fractionResult = rank(m_input);
				break;
			}
			default:
			{
				System.out.println("Unhandled Case in fractionResultOperation");
				break;
			}
		}
		
		return m_fractionResult;
	
	}
	
	public Matrix matrixBinaryOperation() throws MatrixException
	{
		
		if (m_input == null) throw new MatrixException("Unable to perform operation on blank matrix:", m_input);
		if (m_input2 == null) throw new MatrixException("Unable to perform operation on blank matrix:", m_input2);
		
		if (m_operator.equals("+")) m_result = addMatrices(m_input, m_input2);
		else if (m_operator.equals("-")) m_result = subtractMatrices(m_input, m_input2);
		else if (m_operator.equals("*")) m_result = multiplyMatrices(m_input, m_input2);
		else if (m_operator.equals("/")) m_result = divideMatrices(m_input, m_input2);
		
		return m_result;
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
		//Make sure the matrices are of compatible size:
		if (a_LHS.getRows() != a_RHS.getRows() || a_LHS.getColumns() != a_RHS.getColumns())
		{
			throw new MatrixException("Sizes do not match", a_LHS, a_RHS); 
		}
		
		//The sum will now be of the same size as either element:
		Matrix difference = new Matrix(a_LHS.getRows(), a_LHS.getColumns());
		
		//Matrices are added element-wise:
		//Loop through the matrix, add each element.
		for (int row = 0; row < difference.getRows(); row++)
		{
			for (int column = 0; column < difference.getColumns(); column++)
			{
				//The value is simply the current element of each matrix subtracted together.
				Fraction value = new Fraction();
				value = a_LHS.getCell(row, column).subtract(a_RHS.getCell(row, column));
				difference.setCell(row, column, value);
			}
		}
		
		return difference;
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
			total = total.add(products[i]);
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
	
	//Matrix definition does not formally exist.
	//For consistency's sake, we will define matrix definition as multiplying a_LHS by
	//the inverse of a_RHS.
	public Matrix divideMatrices(Matrix a_LHS, Matrix a_RHS) throws MatrixException
	{
		//Create the inverse of the right hand side.
		a_RHS = invertMatrix(a_RHS);
		
		//Multiply the left hand side by the inverse of the right-hand side.
		return multiplyMatrices(a_LHS, a_RHS);
	}
	
	public Matrix scalarMultiply(Fraction a_scalar, Matrix a_matrix) throws MatrixException
	{	
		if (a_scalar == null)
		{
			throw new MatrixException("No scalar set for operation");
		}
		
		//Copy matrix:
		Matrix scalarMatrix = new Matrix(a_matrix);
		
		for (int row = 0; row < a_matrix.getRows(); row++)
		{
			for (int column = 0; column < a_matrix.getColumns(); column++)
			{
				//Simply multiply each cell in the copy by the scalar, then return the copy:
				Fraction current = scalarMatrix.getCell(row, column);
				scalarMatrix.setCell(row, column, current.multiply(a_scalar));
			}
		}
		
		return scalarMatrix;
	}

	public Matrix REF(Matrix a_matrix)
	{
		int numRows = a_matrix.getRows();
		int numCols = a_matrix.getColumns();
		
		//Copy the original matrix:
		Matrix ref = new Matrix(a_matrix);
		
		//Go through each row:
		for (int rowIndex = 0; rowIndex < numRows; rowIndex++)
		{
			int columnIndex = rowIndex;
			int i;
			
			//Check for column of zeroes:
			for (i = columnIndex; i < numCols; i++)
			{
				if (!ref.isColumnZeroes(i))
				{
					break;
				}
			}
			
			//Break from last loop at non-zero column. Use this column:
			columnIndex = i;
			
			//Check for row of zeroes:
			if (ref.isRowZeroes(rowIndex))
			{
				//Ensure any rows of zero are in the bottom spots:
				for (int k = numRows - 1; k > 0; k--)
				{
					//If the current row isn't a row of zeroes, simply swap rows.
					if (!ref.isRowZeroes(rowIndex))
					{
						ref.swapRows(k, rowIndex);
						break;
					}
				}
				continue;
			}
			
			//Check for leading zero:
			//Rows with leading zeroes must be moved to the bottom:
			if (ref.getCell(rowIndex, columnIndex).equals(0))
			{
				//Find latest row in matrix without a zero in this spot:
				for (int k = numRows - 1; k > 0; k--)
				{
					if (!ref.getCell(k, columnIndex).equals(0))
					{
						ref.swapRows(rowIndex, k);
						break;
					}
				}
			}
			
			//Create leading one Step:
			//Get the cell to create the leading one
			Fraction cellValue = ref.getCell(rowIndex, columnIndex);
			
			//divide the whole row by that value to create a one:
			Fraction[] leadOneRow = multiplyRow(ref.getRow(rowIndex), cellValue, true);
			ref.setRow(rowIndex, leadOneRow);
			
			//Create zeroes below step:
			//Find value to create the zero. Multiply lead one row by this value
			//Then subtract the produced row from the current row
			for (int j = rowIndex + 1; j < numRows; j++)
			{
				Fraction multVal = ref.getCell(j, columnIndex);
				Fraction[] producedRow = multiplyRow(leadOneRow, multVal, false);
				Fraction[] resultRow = addRow(ref.getRow(j), producedRow, true);
				
				ref.setRow(j, resultRow);
			}
			
		}
		
		return ref;	
	}
	
	public Matrix RREF(Matrix a_matrix)
	{
		int numRows = a_matrix.getRows();
		int numCols = a_matrix.getColumns();
		
		//Copy the original matrix:
		Matrix rref = new Matrix(a_matrix);
		
		//Go through each row: start at the top:
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

			//Check for row of zeroes:
			if (rref.isRowZeroes(rowIndex))
			{
				//Ensure any rows of zero are in the bottom spots:
				for (int k = numRows - 1; k > 0; k--)
				{
					//If the current row isn't a row of zeroes, simply swap rows.
					if (!rref.isRowZeroes(rowIndex))
					{
						rref.swapRows(k, rowIndex);
						break;
					}
				}
				continue;
			}
			
			//Check for leading zero:
			//Rows with leading zeroes must be moved to the bottom:
			if (rref.getCell(rowIndex, columnIndex).equals(0))
			{
				//Find latest row in matrix without a zero in this spot:
				for (int k = numRows - 1; k > 0; k--)
				{
					if (!rref.getCell(k, columnIndex).equals(0))
					{
						rref.swapRows(rowIndex, k);
						break;
					}
				}
			}
			
			
			
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
		int amtRows = a_matrix.getRows();
		int amtColumns = a_matrix.getColumns();
		
		//Check for square matrix:
		if (!a_matrix.isSquareMatrix())
		{
			throw new MatrixException("Not a square matrix", a_matrix);
		}
		
		if (determinant(a_matrix).equals(0))
		{
			throw new MatrixException("Singular matrix, not invertible", a_matrix);
		}
		
		//Create a joint matrix of size rows x (2x) columns
		Matrix jointMatrix = new Matrix(amtRows, amtColumns * 2);
		
		//Populate the left half square matrix with the input matrix:
		for (int row = 0; row < amtRows; row++)
		{
			for (int column = 0; column < amtColumns; column++)
			{
				jointMatrix.setCell(row, column, a_matrix.getCell(row, column));
			}
		}
		
		//Populate the right half with the identity matrix:
		for (int row = 0; row < amtRows; row++)
		{
			for (int column = amtColumns; column < jointMatrix.getColumns(); column++)
			{
				if (row == (column - amtColumns))
				jointMatrix.setCell(row, column, 1);
			}
		}
		
		//Perform an RREF on this matrix:
		jointMatrix = RREF(jointMatrix);
		
		//The left half should be the identity matrix, the right half is the inverse we want:
		Matrix inverse = new Matrix(amtRows, amtColumns);
		
		for (int row = 0; row < amtRows; row++)
		{
			for (int column = 0; column < amtColumns; column++)
			{
				int colIndex = column + amtColumns;
				inverse.setCell(row, column, jointMatrix.getCell(row, colIndex));
			}
		}
		
		return inverse;
		
	}
	
	public Fraction determinant2by2(Matrix a_matrix) throws MatrixException
	{
		if (a_matrix.getRows() != 2 && a_matrix.getColumns() != 2)
		{
			throw new MatrixException("Not a 2 by 2 matrix", a_matrix);
		}
		
		Fraction LHS = a_matrix.getCell(0, 0).multiply(a_matrix.getCell(1, 1));
		Fraction RHS = a_matrix.getCell(0, 1).multiply(a_matrix.getCell(1, 0));
		return LHS.subtract(RHS);
	}
	
	public Fraction determinant(Matrix a_matrix) throws MatrixException
	{
		if (!a_matrix.isSquareMatrix())
		{
			throw new MatrixException("Not a square matrix", a_matrix);
		}
		
		int amtRows = a_matrix.getRows();
		
		if (amtRows == 2) return determinant2by2(a_matrix);
		
		Fraction determinant = new Fraction();
		
		int highestCount = 0;
		int highestIndex = 0;
		boolean pivotIsRow = true;
		
		//Optimization: Pick the row or column with most zeroes:
		for (int index = 0; index < amtRows; index++)
		{
			int amountZeroes = a_matrix.amountZeroesInRow(index);
			if (amountZeroes > highestCount)
			{
				highestCount = amountZeroes;
				highestIndex = index;
				pivotIsRow = true;
			}
			
			amountZeroes = a_matrix.amountZeroesInColumn(index);
			if (amountZeroes > highestCount)
			{
				highestCount = amountZeroes;
				highestIndex = index;
				pivotIsRow = false;
			}
			
		}
		
		//We now have the row/column to pivot on.
		//The below index is the row/column to delete! It may refer to column OR row index.
		//If isRow is true, then it refers to column. If false, it refers to row.
		for (int index = 0; index < amtRows; index++)
		{
			Fraction multValue;
			
			//Each iteration must create a submatrix of dimension n-1:
			Matrix subMatrix = new Matrix(amtRows - 1, amtRows - 1);
			
			//Get the value from the pivot row/column:
			//If the pivot is a row, the highestIndex refers to the row index
			if (pivotIsRow) multValue = new Fraction(a_matrix.getCell(highestIndex, index));
			else multValue = new Fraction(a_matrix.getCell(index, highestIndex));
			
			//If the value to multiply by is a 0, we can just skip this iteration:
			if (multValue.equals(0)) continue;
			
			int subMatrixRow = 0;
			int subMatrixColumn = 0;
			
			//Create the submatrix:
			for (int subRow = 0; subRow < amtRows; subRow++)
			{
				//If the pivot is this row, delete this row.
				if (pivotIsRow && subRow == highestIndex) continue;
				//Else if the pivot is a column, we are eliminating rows.
				//The current row to eliminate will be the index row.
				else if (!pivotIsRow && subRow == index) continue;
				
				for (int subColumn = 0; subColumn < amtRows; subColumn++)
				{
					//If the pivot is a column and is the current column index, delete this column.
					if (!pivotIsRow && subColumn == highestIndex) continue;
					//Else if the pivot is a row, we are eliminating columns.
					//The current column to eliminate will be the index column.
					else if (pivotIsRow && subColumn == index) continue;
					
					Fraction current = a_matrix.getCell(subRow, subColumn);
					subMatrix.setCell(subMatrixRow, subMatrixColumn, current);
					
					subMatrixColumn++;
				}
				subMatrixColumn = 0;
				subMatrixRow++;
			}
			
			//Calculate the determinant of submatrices:
			Fraction innerDeterminant = determinant(subMatrix);
			
			//Chain the determinants:
			
			//If the index and highestIndex added together is an odd number,
			//We subtract the value of multValue multiplied by the innerDeterminant.
			if ((index + highestIndex) % 2 != 0) multValue = multValue.multiply(-1);
			
			innerDeterminant = multValue.multiply(innerDeterminant);
			
			//Finally, add all of this to the total determinant:
			determinant = determinant.add(innerDeterminant);
		}
		
		//After all of the submatrices have been added together, return the chained result as the determinant:
		return determinant;
	}
	
	public Fraction rank(Matrix a_matrix)
	{
		//Rank will always be a positive integer.
		int rank = 0;
		
		Matrix ref = REF(a_matrix);
		
		//It is simply the amount of non-zero rows in the matrix's REF or RREF.
		for (int rowIndex = 0; rowIndex < a_matrix.getRows(); rowIndex++)
		{
			if (!ref.isRowZeroes(rowIndex)) rank++;
		}
		
		return new Fraction(rank);
	}
	
	public Fraction trace(Matrix a_matrix) throws MatrixException
	{
		if (!a_matrix.isSquareMatrix())
		{
			throw new MatrixException("Not a square matrix", a_matrix);
		}
		
		Fraction trace = new Fraction();
		
		for (int index = 0; index < a_matrix.getRows(); index++)
		{
			trace = trace.add(a_matrix.getCell(index, index));
		}
		
		return trace;
	}
	
	public Matrix transpose(Matrix a_matrix) 
	{
		int amtRows = a_matrix.getRows();
		int amtColumns = a_matrix.getColumns();
		
		Matrix transpose = new Matrix(amtColumns, amtRows);
		
		for (int rowIndex = 0; rowIndex < amtColumns; rowIndex++)
		{
			for (int columnIndex = 0; columnIndex < amtRows; columnIndex++)
			{
				Fraction current = new Fraction(a_matrix.getCell(columnIndex, rowIndex));
				transpose.setCell(rowIndex, columnIndex, current);
			}
		}
		
		return transpose;
	}

}
