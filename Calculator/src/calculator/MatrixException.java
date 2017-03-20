package calculator;

@SuppressWarnings("serial")
public class MatrixException extends Exception 
{
	private String[] m_offendingMatrices;
	
	public MatrixException()
	{
		super();
		m_offendingMatrices = new String[10];
	}
	
	public MatrixException(String a_message)
	{
		super(a_message);
		m_offendingMatrices = new String[10];
	}
	
	public MatrixException(String a_message, Matrix...a_matrices)
	{
		super(a_message);
		m_offendingMatrices = new String[10];
		
		int index = 0;
		for (Matrix matrix : a_matrices)
		{
			m_offendingMatrices[index] = matrix.getName();
		}
	}
	
	public String[] getOffendingMatrices()
	{
		return m_offendingMatrices;
	}
}
