package calculator;

public interface TextManipulation {

	public abstract void backspace();

	public abstract void clearEntry();
	
	public abstract void remove(int a_location, int a_amtChars);
	
	public abstract void insertString();
	
}
