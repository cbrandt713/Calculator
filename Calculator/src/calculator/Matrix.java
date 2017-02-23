package calculator;

public class Matrix
{
	//Java does not support unsigned, as such do not allow negative numbers
	private int m_rows;
	private int m_columns;
	private double m_numbers[][];
	private String m_name;
	
	public Matrix(int a_rows, int a_columns)
	{
		setRows(a_rows);
		setColumns(a_columns);
		m_numbers = new double[m_rows][m_columns];
		m_name = "";
	}
	
	public int getRows()
	{
		return m_rows;
	}
	
	public int getColumns()
	{
		return m_columns;
	}
	
	public double[] getRow(int a_row)
	{
		return m_numbers[a_row];
	}
	
	public void setRow(int a_row, double[] a_values)
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
	
	//Rows passes in by index: starting at 0, ending at RowCount - 1
	public void swapRows(int a_firstRow, int a_secondRow)
	{
		//Ensure both rows exist in the matrix:
		if (!rowExists(a_firstRow) || !rowExists(a_secondRow)) return;
		
		//Store the first and second rows:
		double[] firstRow = getRow(a_firstRow);
		double[] secondRow = getRow(a_secondRow);
		
		//Do the swap:
		setRow(a_secondRow, firstRow);
		setRow(a_firstRow, secondRow);	
	}
	
	public void outputMatrix()
	{
		for (int i = 0; i < getRows(); i++)
		{
			for (int j = 0; j < getColumns(); j++)
			{
				System.out.print(m_numbers[i][j] + " ");
			}
			System.out.println();
		}
	}

}
