package calculator;

public class Matrix
{
	//Java does not support unsigned, as such do not allow negative numbers
	private int m_rows;
	private int m_columns;
	private double m_numbers[][];
	
	public Matrix(int a_rows, int a_columns)
	{
		m_rows = a_rows;
		m_columns = a_columns;
		m_numbers = new double[m_rows][m_columns];
	}
	
	public int getRows()
	{
		return m_rows;
	}
	
	public int getColumns()
	{
		return m_columns;
	}
	
	public void setRows(int a_rows)
	{
		if (a_rows > 0)
		{
			m_rows = a_rows;
		}
	}
	
	public void setColumns(int a_columns)
	{
		if (a_columns > 0)
		{
			m_columns = a_columns;
		}
	}
	
	public void setCell(int a_row, int a_column, double a_value)
	{
		if (cellExists(a_row, a_column)) m_numbers[a_row][a_column] = a_value;
	}
	
	public double getCell(int a_row, int a_column)
	{
		if (cellExists(a_row, a_column)) return m_numbers[a_row][a_column];
		else return -Double.MAX_VALUE;
	}
	
	public boolean cellExists(int a_row, int a_column)
	{
		if ((a_row <= m_rows) && (a_column <= m_rows)) return true;
		return false;
	}

}
