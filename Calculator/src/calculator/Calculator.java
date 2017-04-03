package calculator;

public abstract class Calculator<T> {

	protected T m_input;
	protected T m_input2;
	protected T m_result;
	protected String m_operation;
	
	protected abstract void resetAll();
	
	protected abstract void resetInputs();
	
	public abstract void setOperation(String a_operation);
	
	public abstract void setInput(T a_input);
	
	public abstract T doCalculation() throws Exception;

}
