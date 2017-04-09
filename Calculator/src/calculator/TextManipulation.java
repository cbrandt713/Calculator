package calculator;

public interface TextManipulation {
	
	public abstract void append(String a_string);

	public abstract void backspace();

	public abstract void clearEntry();
	
	public abstract String getUserEnteredText();
	
	public abstract void insertAtFront(String a_string);
	
	public abstract void insertString(int a_location, String a_string);
	
	public abstract void remove(int a_location, int a_amtChars);
	
	public abstract void replaceText(int a_locationOfText, int a_lengthToReplace, String a_string);
	
}
