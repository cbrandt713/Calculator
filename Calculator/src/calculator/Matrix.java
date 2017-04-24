package calculator;

/**
 * The representation of a Matrix for the Matrix Calculator.
 */
public class Matrix
{
	
	/** The amount of rows. */
	private int m_rows;
	
	/** The amount of columns. */
	private int m_columns;
	
	/** The array of numbers. */
	private Fraction m_numbers[][];
	
	/** The name. */
	private String m_name;
	
	/**
	 * Creates a new blank matrix from an amount of rows and columns.
	 *
	 * @param a_rows the amount of rows
	 * @param a_columns the amount of columns
	 */
	//Regular Constructor:
	public Matrix(int a_rows, int a_columns)
	{
		m_rows = a_rows;
		m_columns = a_columns;
		m_numbers = new Fraction[m_rows][m_columns];
		setName("");
		
		//Initialize each element to 0:
		for (int row = 0; row < a_rows; row++)
		{
			for (int column = 0; column < a_columns; column++)
			{
				Fraction current = new Fraction();
				m_numbers[row][column] = current;
			}
		}
		
	}
	
	/**
	 * Creates a new matrix from a 2d array.
	 *
	 * @param a_matrix the 2d array of numbers to use.
	 */
	//Regular Constructor:
	public Matrix(Fraction[][] a_matrix)
	{
		m_rows = a_matrix.length;
		m_columns = a_matrix[0].length;
		m_numbers = a_matrix;
		setName("");
	}
	
	/**
	 * Copy constructor. Creates a new matrix.
	 *
	 * @param a_other the a other
	 */
	//Copy Constructor:
	public Matrix(Matrix a_other)
	{
		m_rows = a_other.getRows();
		m_columns = a_other.getColumns();
		this.m_numbers = new Fraction[m_rows][m_columns];
		setName("Copy_" + a_other.getName());
		
		//Copy each cell into this matrix:
		for (int row = 0; row < a_other.getRows(); row++)
		{
			for (int column = 0; column < a_other.getColumns(); column++)
			{
				Fraction current = new Fraction(a_other.getCell(row, column));
				this.m_numbers[row][column] = current;
			}
		}
	}
	
	/**
	 * Gets the amount of rows.
	 *
	 * @return the amount of rows
	 */
	public int getRows()
	{
		return m_rows;
	}
	
	/**
	 * Gets the amount of columns.
	 *
	 * @return the amount of columns
	 */
	public int getColumns()
	{
		return m_columns;
	}
	
	/**
	 * Gets the row at the index.
	 *
	 * @param a_row the index
	 * @return the row at the index
	 */
	public Fraction[] getRow(int a_row)
	{
		return m_numbers[a_row];
	}
	
	/**
	 * Sets the row at the index.
	 * Note: uses shallow copy, not deep copy.
	 * 
	 * @param a_row the row
	 * @param a_values the values to set
	 */
	public void setRow(int a_row, Fraction[] a_values)
	{
		if (a_values.length != getColumns()) return;
		m_numbers[a_row] = a_values;
	}
	
	/**
	 * Gets the column at the index.
	 *
	 * @param a_column the index
	 * @return the column at the index
	 */
	public Fraction[] getColumn(int a_column)
	{
		Fraction[] column = new Fraction[getRows()];
		
		//Go through each row and get it's "a_column"th element.
		for (int i = 0; i < getRows(); i++)
		{
			column[i] = getRow(i)[a_column];
		}
		
		return column;
	}
	
	/**
	 * Sets the column at the index.
	 *
	 * @param a_column the index
	 * @param a_values the values
	 */
	public void setColumn(int a_column, Fraction[] a_values)
	{
		if (a_values.length != getRows()) return;
		
		//Go through each row (i), and set the element of "a_column" to the "i"th element of a_values:
		for (int i = 0; i < getRows(); i++)
		{
			m_numbers[i][a_column] = a_values[i];
		}
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return m_name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		m_name = name;
	}
	
	/**
	 * Sets the cell at the given row and column with a Fraction value.
	 *
	 * @param a_row the row index
	 * @param a_column the column index
	 * @param a_value the value to set
	 */
	public void setCell(int a_row, int a_column, Fraction a_value)
	{
		if (cellExists(a_row, a_column)) m_numbers[a_row][a_column] = a_value;
	}
	
	/**
	 * Sets the cell at the given row and column with an int value.
	 *
	 * @param a_row the row index
	 * @param a_column the column index
	 * @param a_value the value
	 */
	public void setCell(int a_row, int a_column, int a_value)
	{
		Fraction value = new Fraction(a_value);
		if (cellExists(a_row, a_column)) m_numbers[a_row][a_column] = value;
	}
	
	/**
	 * Gets the value at the cell
	 *
	 * @param a_row the row index
	 * @param a_column the column index
	 * @return the cell value
	 */
	public Fraction getCell(int a_row, int a_column)
	{
		if (cellExists(a_row, a_column)) return m_numbers[a_row][a_column];
		else return new Fraction(0, 0);
	}
	
	/**
	 * Checks if the cell requested is in the given range
	 *
	 * @param a_row the row index
	 * @param a_column the column index
	 * @return true, if it exists
	 */
	public boolean cellExists(int a_row, int a_column)
	{
		if (rowExists(a_row) && columnExists(a_column)) return true;
		else return false;
	}
	
	/**
	 * Checks if a row exists in the matrix.
	 *
	 * @param a_row the row index
	 * @return true, if it exists
	 */
	public boolean rowExists(int a_row)
	{
		if (a_row < m_rows) return true;
		else return false;
	}
	
	/**
	 * Checks if a column exists in the matrix.
	 *
	 * @param a_column the column index
	 * @return true, if it exists
	 */
	public boolean columnExists(int a_column)
	{
		if (a_column < m_columns) return true;
		else return false;
	}
	
	/**
	 * Checks if a row is all zeroes.
	 *
	 * @param a_rowIndex the row index
	 * @return true, if the row is all zeroes
	 */
	public boolean isRowZeroes(int a_rowIndex)
	{
		Fraction[] row = getRow(a_rowIndex);
		
		for (int i = 0; i < row.length; i++)
		{
			if (!row[i].equals(0)) return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if a column is all zeroes.
	 *
	 * @param a_columnIndex the column index
	 * @return true, if the column is all zeroes
	 */
	public boolean isColumnZeroes(int a_columnIndex)
	{
		for (int i = 0; i < getRows(); i++)
		{
			if (!getRow(i)[a_columnIndex].equals(0)) return false;
		}
		
		return true;
	}
	
	/**
	 * Counts the amount of zeroes in a row.
	 *
	 * @param a_rowIndex the row index
	 * @return the amount of zeroes in the row
	 */
	public int amountZeroesInRow(int a_rowIndex)
	{
		int count = 0;
		Fraction[] row = getRow(a_rowIndex);
		
		for (int i = 0; i < row.length; i++)
		{
			if (row[i].equals(0)) count++;
		}
		
		return count;
	}
	
	/**
	 * Counts the amount of zeroes in a column.
	 *
	 * @param a_columnIndex the column index
	 * @return the amount of zeroes in the column
	 */
	public int amountZeroesInColumn(int a_columnIndex)
	{
		int count = 0;
		for (int i = 0; i < getRows(); i++)
		{
			if (getRow(i)[a_columnIndex].equals(0)) count++;
		}
		
		return count;
	}
	
	/**
	 * Checks if the matrix is a square matrix.
	 * As in, amount of rows = amount of columns.
	 * 
	 * @return true, if it is a square matrix
	 */
	public boolean isSquareMatrix()
	{
		return (getRows() == getColumns());
	}
	
	/**
	 * Swap two rows.
	 * Rows passes in by index: starting at 0, ending at RowCount - 1.
	 * 
	 * @param a_firstRowIndex the first row index
	 * @param a_secondRowIndex the second row index
	 */
	public void swapRows(int a_firstRowIndex, int a_secondRowIndex)
	{
		//Ensure both rows exist in the matrix:
		if (!rowExists(a_firstRowIndex) || !rowExists(a_secondRowIndex)) return;
		
		//Store the first and second rows:
		Fraction[] firstRow = getRow(a_firstRowIndex);
		Fraction[] secondRow = getRow(a_secondRowIndex);
		
		//Do the swap:
		setRow(a_secondRowIndex, firstRow);
		setRow(a_firstRowIndex, secondRow);	
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder matrixString = new StringBuilder("");
		
		for (int i = 0; i < getRows(); i++)
		{
			//Open the row with a brace:
			matrixString.append("[ ");
			for (int j = 0; j < getColumns() - 1; j++)
			{
				//Get each cell value and add a | character:
				matrixString.append(m_numbers[i][j].toString() + " | ");
			}
			
			//Append the last number and add a closing brace:
			matrixString.append(m_numbers[i][getColumns() - 1].toString());
			matrixString.append(" ]\n");
		}
		
		return matrixString.toString();
	}

}
