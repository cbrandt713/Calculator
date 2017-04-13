package calculator;

// TODO: Auto-generated Javadoc
/**
 * The Class MatrixException.
 */
@SuppressWarnings("serial")
public class MatrixException extends Exception 
{
	
	/** The m offending matrices. */
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
	 * Instantiates a new matrix exception.
	 *
	 * @param a_message the a message
	 */
	public MatrixException(String a_message)
	{
		super(a_message);
		m_offendingMatrices = new String[10];
	}
	
	/**
	 * Instantiates a new matrix exception.
	 *
	 * @param a_message the a message
	 * @param a_matrices the a matrices
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
