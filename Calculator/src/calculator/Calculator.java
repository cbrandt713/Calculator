package calculator;
/**
 * The base for which any implemented Calculators should be derived. The Calculator class sets 
 * the minimum requirements for what should be included in any calculator model.
 *
 * @param <T> the type to perform operations on
 */
public abstract class Calculator<T> {

	/** The primary input. */
	protected T m_input;
	
	/** The secondary input. */
	protected T m_input2;
	
	/** The result of the operation. */
	protected T m_result;
	
	/** The operation. */
	protected String m_operation;
	
	/**
	 * Resets all values to defaults.
	 */
	protected abstract void resetAll();
	
	/**
	 * Resets only the inputs to defaults.
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
	 * Gets the amount of inputs set.
	 *
	 * @return the amount of inputs set
	 */
	public abstract int getAmountInputs();
	
	/**
	 * Do the appropriate calculation.
	 *
	 * @return the result of the calculation
	 * @throws Exception the reason the calculation cannot be performed, if applicable.
	 */
	public abstract T doCalculation() throws Exception;

}
