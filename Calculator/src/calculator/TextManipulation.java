package calculator;

// TODO: Auto-generated Javadoc
/**
 * The Interface TextManipulation.
 */
public interface TextManipulation {
	
	/**
	 * Append.
	 *
	 * @param a_string the a string
	 */
	public abstract void append(String a_string);

	/**
	 * Backspace.
	 */
	public abstract void backspace();

	/**
	 * Clear entry.
	 */
	public abstract void clearEntry();
	
	/**
	 * Gets the user entered text.
	 *
	 * @return the user entered text
	 */
	public abstract String getUserEnteredText();
	
	/**
	 * Insert at front.
	 *
	 * @param a_string the a string
	 */
	public abstract void insertAtFront(String a_string);
	
	/**
	 * Insert string.
	 *
	 * @param a_location the a location
	 * @param a_string the a string
	 */
	public abstract void insertString(int a_location, String a_string);
	
	/**
	 * Removes the.
	 *
	 * @param a_location the a location
	 * @param a_amtChars the a amt chars
	 */
	public abstract void remove(int a_location, int a_amtChars);
	
	/**
	 * Replace text.
	 *
	 * @param a_locationOfText the a location of text
	 * @param a_lengthToReplace the a length to replace
	 * @param a_string the a string
	 */
	public abstract void replaceText(int a_locationOfText, int a_lengthToReplace, String a_string);
	
}
