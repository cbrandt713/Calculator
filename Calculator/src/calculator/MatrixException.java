package calculator;

/**
 * Contains a message about why an operation can't be performed and allows the offending matrix(ces) to be shown.
 */
@SuppressWarnings("serial")
public class MatrixException extends Exception 
{
	
	/** The offending matrices. */
	private String[] m_offendingMatrices;
	
	/**
	 * Instantiates a new matrix exception.
	 */
	public MatrixException()
	{
		super();
		m_offendingMatrices = new String[10];
	}
	
	/**
	 * Instantiates a new matrix exception with given message.
	 *
	 * @param a_message the message
	 */
	public MatrixException(String a_message)
	{
		super(a_message);
		m_offendingMatrices = new String[10];
	}
	
	/**
	 * Instantiates a new matrix exception with message and offending matrices.
	 *
	 * @param a_message the message
	 * @param a_matrices the offending matrices
	 */
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
	
	/**
	 * Gets the offending matrices.
	 *
	 * @return the offending matrices
	 */
	public String[] getOffendingMatrices()
	{
		return m_offendingMatrices;
	}
}
