package calculator;

public abstract class Calculator<T> {

	protected T m_input;
	protected T m_input2;
	protected T m_result;
	protected String m_operator;
	
	protected abstract void resetAll();
	
	protected abstract void resetInputs();
	
	public abstract void setOperator(String a_operator);
	
	public abstract void setInput(T a_input);
	
	public abstract T doCalculation() throws Exception;

}
