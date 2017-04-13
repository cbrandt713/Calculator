package calculator;

// TODO: Auto-generated Javadoc
/**
 * The Class Matrix.
 */
public class Matrix
{
	
	/** The m rows. */
	//Java does not support unsigned, as such do not allow negative numbers
	private int m_rows;
	
	/** The m columns. */
	private int m_columns;
	
	/** The m numbers. */
	private Fraction m_numbers[][];
	
	/** The m name. */
	private String m_name;
	
	/**
	 * Instantiates a new matrix.
	 *
	 * @param a_rows the a rows
	 * @param a_columns the a columns
	 */
	//Regular Constructor:
	public Matrix(int a_rows, int a_columns)
	{
		setRows(a_rows);
		setColumns(a_columns);
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
	 * Instantiates a new matrix.
	 *
	 * @param a_matrix the a matrix
	 */
	//Regular Constructor:
	public Matrix(Fraction[][] a_matrix)
	{
		setRows(a_matrix.length);
		setColumns(a_matrix[0].length);
		m_numbers = a_matrix;
		setName("");
	}
	
	/**
	 * Instantiates a new matrix.
	 *
	 * @param a_other the a other
	 */
	//Copy Constructor:
	public Matrix(Matrix a_other)
	{
		setRows(a_other.getRows());
		setColumns(a_other.getColumns());
		this.m_numbers = new Fraction[m_rows][m_columns];
		setName("Copy_" + a_other.getName());
		
		//Copy each row into this matrix:
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
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public int getRows()
	{
		return m_rows;
	}
	
	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public int getColumns()
	{
		return m_columns;
	}
	
	/**
	 * Gets the row.
	 *
	 * @param a_row the a row
	 * @return the row
	 */
	public Fraction[] getRow(int a_row)
	{
		return m_numbers[a_row];
	}
	
	/**
	 * Sets the row.
	 *
	 * @param a_row the a row
	 * @param a_values the a values
	 */
	public void setRow(int a_row, Fraction[] a_values)
	{
		if (a_values.length != getColumns()) return;
		m_numbers[a_row] = a_values;
	}
	
	/**
	 * Gets the column.
	 *
	 * @param a_column the a column
	 * @return the column
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
	 * Sets the column.
	 *
	 * @param a_column the a column
	 * @param a_values the a values
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
	
	//Inconsistent state: create new Matrix!
	/**
	 * Sets the rows.
	 *
	 * @param a_rows the new rows
	 */
	//Possibly delete these? Keep private for now
	private void setRows(int a_rows)
	{
		m_rows = (a_rows > 0) ? a_rows : 1;
	}
	
	/**
	 * Sets the columns.
	 *
	 * @param a_columns the new columns
	 */
	private void setColumns(int a_columns)
	{
		m_columns = (a_columns > 0) ? a_columns : 1;
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
	 * Sets the cell.
	 *
	 * @param a_row the a row
	 * @param a_column the a column
	 * @param a_value the a value
	 */
	public void setCell(int a_row, int a_column, Fraction a_value)
	{
		if (cellExists(a_row, a_column)) m_numbers[a_row][a_column] = a_value;
	}
	
	/**
	 * Sets the cell.
	 *
	 * @param a_row the a row
	 * @param a_column the a column
	 * @param a_value the a value
	 */
	public void setCell(int a_row, int a_column, int a_value)
	{
		Fraction value = new Fraction(a_value);
		if (cellExists(a_row, a_column)) m_numbers[a_row][a_column] = value;
	}
	
	/**
	 * Gets the cell.
	 *
	 * @param a_row the a row
	 * @param a_column the a column
	 * @return the cell
	 */
	public Fraction getCell(int a_row, int a_column)
	{
		if (cellExists(a_row, a_column)) return m_numbers[a_row][a_column];
		else return new Fraction(0, 0);
	}
	
	/**
	 * Cell exists.
	 *
	 * @param a_row the a row
	 * @param a_column the a column
	 * @return true, if successful
	 */
	public boolean cellExists(int a_row, int a_column)
	{
		if (rowExists(a_row) && columnExists(a_column)) return true;
		else return false;
	}
	
	/**
	 * Row exists.
	 *
	 * @param a_row the a row
	 * @return true, if successful
	 */
	public boolean rowExists(int a_row)
	{
		if (a_row < m_rows) return true;
		else return false;
	}
	
	/**
	 * Column exists.
	 *
	 * @param a_column the a column
	 * @return true, if successful
	 */
	public boolean columnExists(int a_column)
	{
		if (a_column < m_columns) return true;
		else return true;
	}
	
	/**
	 * Checks if is row zeroes.
	 *
	 * @param a_rowIndex the a row index
	 * @return true, if is row zeroes
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
	 * Checks if is column zeroes.
	 *
	 * @param a_columnIndex the a column index
	 * @return true, if is column zeroes
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
	 * Amount zeroes in row.
	 *
	 * @param a_rowIndex the a row index
	 * @return the int
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
	 * Amount zeroes in column.
	 *
	 * @param a_columnIndex the a column index
	 * @return the int
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
	 * Checks if is square matrix.
	 *
	 * @return true, if is square matrix
	 */
	public boolean isSquareMatrix()
	{
		return (getRows() == getColumns());
	}
	
	/**
	 * Swap rows.
	 *
	 * @param a_firstRow the a first row
	 * @param a_secondRow the a second row
	 */
	//Rows passes in by index: starting at 0, ending at RowCount - 1
	public void swapRows(int a_firstRow, int a_secondRow)
	{
		//Ensure both rows exist in the matrix:
		if (!rowExists(a_firstRow) || !rowExists(a_secondRow)) return;
		
		//Store the first and second rows:
		Fraction[] firstRow = getRow(a_firstRow);
		Fraction[] secondRow = getRow(a_secondRow);
		
		//Do the swap:
		setRow(a_secondRow, firstRow);
		setRow(a_firstRow, secondRow);	
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder matrixString = new StringBuilder("");
		
		for (int i = 0; i < getRows(); i++)
		{
			matrixString.append("[ ");
			for (int j = 0; j < getColumns() - 1; j++)
			{
				matrixString.append(m_numbers[i][j].toString() + " | ");
			}
			matrixString.append(m_numbers[i][getColumns() - 1].toString());
			matrixString.append(" ]\n");
		}
		
		return matrixString.toString();
	}

}
