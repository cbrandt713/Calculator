package calculator;

public class Matrix
{
	//Java does not support unsigned, as such do not allow negative numbers
	private int m_rows;
	private int m_columns;
	private Fraction m_numbers[][];
	private String m_name;
	
	//Regular Constructor:
	public Matrix(int a_rows, int a_columns)
	{
		setRows(a_rows);
		setColumns(a_columns);
		m_numbers = new Fraction[m_rows][m_columns];
		setName("");
	}
	
	//Regular Constructor:
	public Matrix(Fraction[][] a_matrix)
	{
		setRows(a_matrix.length);
		setColumns(a_matrix[0].length);
		m_numbers = a_matrix;
		setName("");
	}
	
	//Copy Constructor:
	public Matrix(Matrix a_other)
	{
		setRows(a_other.getRows());
		setColumns(a_other.getColumns());
		this.m_numbers = new Fraction[m_rows][m_columns];
		setName("Copy_" + a_other.getName());
		
		//Copy each row into this matrix:
		for (int i = 0; i < a_other.getRows(); i++)
		{
			this.setRow(i, a_other.getRow(i));
		}
	}
	
	public int getRows()
	{
		return m_rows;
	}
	
	public int getColumns()
	{
		return m_columns;
	}
	
	public Fraction[] getRow(int a_row)
	{
		return m_numbers[a_row];
	}
	
	public void setRow(int a_row, Fraction[] a_values)
	{
		m_numbers[a_row] = a_values;
	}
	
	public String getName()
	{
		return m_name;
	}
	
	//Inconsistent state: create new Matrix!
	public void setRows(int a_rows)
	{
		m_rows = (a_rows > 0) ? a_rows : 1;
	}
	
	public void setColumns(int a_columns)
	{
		m_columns = (a_columns > 0) ? a_columns : 1;
	}
	
	public void setName(String name)
	{
		m_name = name;
	}
	
	public void setCell(int a_row, int a_column, Fraction a_value)
	{
		if (cellExists(a_row, a_column)) m_numbers[a_row][a_column] = a_value;
	}
	
	public Fraction getCell(int a_row, int a_column)
	{
		if (cellExists(a_row, a_column)) return m_numbers[a_row][a_column];
		else return new Fraction(0, 0);
	}
	
	public boolean cellExists(int a_row, int a_column)
	{
		if (rowExists(a_row) && columnExists(a_column)) return true;
		else return false;
	}
	
	public boolean rowExists(int a_row)
	{
		if (a_row < m_rows) return true;
		else return false;
	}
	
	public boolean columnExists(int a_column)
	{
		if (a_column < m_columns) return true;
		else return true;
	}
	
	public boolean isRowZeroes(int a_row)
	{
		Fraction[] row = getRow(a_row);
		
		for (int i = 0; i < row.length; i++)
		{
			if (!row[i].equals(0)) return false;
		}
		
		return true;
	}
	
	public boolean isColumnZeroes(int a_column)
	{
		
		for (int i = 0; i < getRows(); i++)
		{
			if (!getRow(i)[a_column].equals(0)) return false;
		}
		
		return true;
	}
	
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
	
	@Override
	public String toString()
	{
		String matrixString = "";
		
		for (int i = 0; i < getRows(); i++)
		{
			matrixString += "[";
			for (int j = 0; j < getColumns() - 1; j++)
			{
				matrixString += m_numbers[i][j].toString() + " | ";
			}
			matrixString += m_numbers[i][getColumns() - 1].toString();
			matrixString += "]\n";
		}
		
		return matrixString;
	}

}
