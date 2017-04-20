package calculator;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicTextArea.
 */
@SuppressWarnings("serial")
public class BasicTextArea extends JTextArea implements TextManipulation 
{

	/** The expression line. */
	public static final int EXPRESSION = 0;
	
	/** The input line. */
	public static final int INPUT = 1;
	
	/** The instance of the basic text area. */
	private static BasicTextArea m_instance;
	
	/** The displayed text. */
	private Document m_displayText;
	
	/**
	 * Instantiates a new basic text area.
	 */
	private BasicTextArea()
	{
		super();
		
		m_displayText = getDocument();
	}
	
	/**
	 * Gets the BasicTextArea instance.
	 *
	 * @return the BasicTextArea instance
	 */
	public static BasicTextArea getBasicTextAreaInstance()
	{
		if (m_instance == null)
		{
			m_instance = new BasicTextArea();
		}
		
		return m_instance;
	}
	
	/**
	 * @see calculator.TextManipulation#getUserEnteredText()
	 */
	public String getUserEnteredText()
	{
		int newLineChar = getLocNewLineChar();
		
		if (newLineChar == -1) return "";
		
		try 
		{
			return m_displayText.getText(newLineChar + 1, getText().length() - newLineChar - 1);
		} 
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * Gets the location of the new line character.
	 *
	 * @return the location of the new line char
	 */
	private int getLocNewLineChar()
	{
		return getText().indexOf("\n");	
	}
	
	/**
	 * Sets the text for a unary operation.
	 *
	 * @param a_operator the operator
	 * @param a_userInput the user input
	 */
	public void setTextForUnary(String a_operator, String a_userInput)
	{
		switch (a_operator)
		{
			case "±":
			{
				clearExpression();
				changeDisplay("negate(" + a_userInput + ") =", EXPRESSION, false);
				break;
			}
			case "1/x":
			{
				clearExpression();
				changeDisplay("reciprocal(" + a_userInput + ") =", EXPRESSION, false);
				break;
			}
			case "√":
			{
				clearExpression();
				changeDisplay("sqrt(" + a_userInput + ") =", EXPRESSION, false);
				break;
			}
		}
		
	}
	
	/**
	 * Sets the text for a binary operation.
	 *
	 * @param a_operator the operator
	 * @param a_userInput the user input
	 */
	public void setTextForBinary(String a_operator, String a_userInput)
	{
		changeDisplay(a_userInput + " " + a_operator + " ", EXPRESSION, false);
	}
	
	/**
	 * Change the display on the given line to the given text.
	 *
	 * @param a_message the message
	 * @param a_lineNum the line number
	 * @param a_replace replace the text if true
	 */
	public void changeDisplay(String a_message, int a_lineNum, boolean a_replace)
	{
		
		int newLineChar = getLocNewLineChar();
		
		if (a_lineNum == EXPRESSION)
		{
			changeExpressionLine(a_message, newLineChar, a_replace);
		}
		else
		{
			changeInputLine(a_message, newLineChar, a_replace);
		}
		
		System.out.println("Text changed to: " + getText());
	}
	
	/**
	 * Change the expression line's text.
	 *
	 * @param a_message the message
	 * @param a_newLineChar the new line character's location
	 * @param a_replace replace the text if true
	 */
	private void changeExpressionLine(String a_message, int a_newLineChar, boolean a_replace)
	{
		if (a_message.equals("Clear"))
		{
			clearExpression();
		}
		else
		{
			insert(a_message, a_newLineChar);
		}	
	}
	
	/**
	 * Change the input line's text.
	 *
	 * @param a_message the a message
	 * @param a_newLineChar the a new line char
	 * @param a_replace the a replace
	 */
	private void changeInputLine(String a_message, int a_newLineChar, boolean a_replace)
	{
		//Clear the Input Line:
		if (a_message.equals("Clear"))
		{
			clearEntry();
		}
		
		//Backspace a character:
		else if (a_message.equals("Backspace"))
		{
			backspace();
		}
		
		//Type over the text on the input line:
		else if (a_replace)
		{
			int length = getText().length() - a_newLineChar - 1;
			replaceText(a_newLineChar + 1, length, a_message);
		}
		
		//If no special case, just append a character to the end of the input line:
		else 
		{
			//insertString(a_newLineChar + 1, a_message);
			append(a_message);
		}
	}
	
	
	/**
	 * @see calculator.TextManipulation#backspace()
	 */
	@Override
	public void backspace() 
	{
		int newLineChar = getLocNewLineChar();
		
		if (newLineChar == -1) return;
		
		//If the text on the input line is not blank, delete the last character
		if (!getText().substring(newLineChar).equals(""))
		{
			try 
			{
				m_displayText.remove(getText().length() - 1, 1);
			} 
			catch (BadLocationException e) 
			{
				e.printStackTrace();
			}
		}
			
	}
	
	/**
	 * Clear the expression line.
	 */
	public void clearExpression()
	{
		int newLineChar = getLocNewLineChar();
		
		if (newLineChar == -1) return;
		
		try
		{
			m_displayText.remove(0, newLineChar);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see calculator.TextManipulation#clearEntry()
	 */
	@Override
	public void clearEntry() 
	{
		int newLineChar = getLocNewLineChar();
		
		if (newLineChar == -1) return;
		
		try 
		{
			m_displayText.remove(newLineChar + 1, getText().length() - newLineChar - 1);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see calculator.TextManipulation#insertAtFront(java.lang.String)
	 */
	@Override
	public void insertAtFront(String a_string) 
	{
		insertString(0, a_string);
	}

	/**
	 * @see calculator.TextManipulation#insertString(int, java.lang.String)
	 */
	@Override
	public void insertString(int a_location, String a_string) 
	{
		try
		{
			m_displayText.insertString(a_location, a_string, null);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see calculator.TextManipulation#remove(int, int)
	 */
	@Override
	public void remove(int a_location, int a_amtChars) 
	{
		try
		{
			m_displayText.remove(a_location, a_amtChars);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see calculator.TextManipulation#replaceText(int, int, java.lang.String)
	 */
	@Override
	public void replaceText(int a_locationOfText, int a_lengthToReplace, String a_string)
	{
		try 
		{
			m_displayText.remove(a_locationOfText, a_lengthToReplace);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
		
		insertString(a_locationOfText, a_string);
	}
	
	
	
}
