package calculator;

/**
 * The interface that any class that manipulates text should implement.
 */
public interface TextManipulation {
	
	/**
	 * Append text.
	 *
	 * @param a_string the string to append
	 */
	public abstract void append(String a_string);

	/**
	 * Backspace current text.
	 */
	public abstract void backspace();

	/**
	 * Clear the user's entry.
	 */
	public abstract void clearEntry();
	
	/**
	 * Gets the user entered text.
	 *
	 * @return the user entered text
	 */
	public abstract String getUserEnteredText();
	
	/**
	 * Insert a string at the front of the text.
	 *
	 * @param a_string the string to insert
	 */
	public abstract void insertAtFront(String a_string);
	
	/**
	 * Insert a string.
	 *
	 * @param a_location the location to insert at
	 * @param a_string the string to insert
	 */
	public abstract void insertString(int a_location, String a_string);
	
	/**
	 * Removes the amount of text at the location
	 *
	 * @param a_location the location
	 * @param a_amtChars the amount of characters to remove
	 */
	public abstract void remove(int a_location, int a_amtChars);
	
	/**
	 * Replace text.
	 *
	 * @param a_locationOfText the location of the text to replace
	 * @param a_lengthToReplace the length to replace
	 * @param a_string the string to replace with
	 */
	public abstract void replaceText(int a_locationOfText, int a_lengthToReplace, String a_string);
	
}
