package calculator;

public class Matrix
{
	//Java does not support unsigned, as such do not allow negative numbers
	private int m_rows;
	private int m_columns;
	
	public Matrix(int a_rows, int a_columns)
	{
		m_rows = a_rows;
		m_columns = a_columns;
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

}
