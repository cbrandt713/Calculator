package calculator;

// TODO: Auto-generated Javadoc
/**
 * The Class Calculator.
 *
 * @param <T> the generic type
 */
public abstract class Calculator<T> {

	/** The m input. */
	protected T m_input;
	
	/** The m input 2. */
	protected T m_input2;
	
	/** The m result. */
	protected T m_result;
	
	/** The m operation. */
	protected String m_operation;
	
	/**
	 * Reset all.
	 */
	protected abstract void resetAll();
	
	/**
	 * Reset inputs.
	 */
	protected abstract void resetInputs();
	
	/**
	 * Sets the operation.
	 *
	 * @param a_operation the new operation
	 */
	public abstract void setOperation(String a_operation);
	
	/**
	 * Sets the input.
	 *
	 * @param a_input the new input
	 */
	public abstract void setInput(T a_input);
	
	/**
	 * Gets the amount inputs.
	 *
	 * @return the amount inputs
	 */
	public abstract int getAmountInputs();
	
	/**
	 * Do calculation.
	 *
	 * @return the t
	 * @throws Exception the exception
	 */
	public abstract T doCalculation() throws Exception;

}
