package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class BasicTextArea extends JTextArea implements TextManipulation 
{

	//Display Line Number Constants:
	public static final int EXPRESSION = 0;
	public static final int INPUT = 1;
	
	private static BasicTextArea m_instance;
	private Document m_displayText;
	
	private BasicTextArea()
	{
		super();
		
		m_displayText = getDocument();
	}
	
	public static BasicTextArea getBasicTextAreaInstance()
	{
		if (m_instance == null)
		{
			m_instance = new BasicTextArea();
		}
		
		return m_instance;
	}
	
	public String getUserEnteredText()
	{
		int newLineChar = getLocNewLineChar();
		
		if (newLineChar == -1) return "";
		
		try 
		{
			return m_displayText.getText(newLineChar + 1, m_displayText.getLength() - 1);
		} 
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	private int getLocNewLineChar()
	{
		int newLineChar = -1;
		
		try 
		{
			newLineChar = getLineEndOffset(0) - 1;
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
		
		return newLineChar;
		
	}
	
	public void changeDisplay(String message, int lineNum, boolean a_replace)
	{
		
		int newLineChar = getLocNewLineChar();
		
		if (lineNum == EXPRESSION)
		{
			changeExpressionLine(message, newLineChar, a_replace);
		}
		else
		{
			changeInputLine(message, newLineChar, a_replace);
		}
		
	}
	
	private void changeExpressionLine(String a_message, int a_newLineChar, boolean a_replace)
	{
		if (a_message.equals("Clear"))
		{
			setText(getText().substring(a_newLineChar - 1));
		}
		else
		{
			insert(a_message, a_newLineChar);
		}	
	}
	
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
			append(a_message);
		}
	}
	
	
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

	@Override
	public void clearEntry() 
	{
		int newLineChar = getLocNewLineChar();
		
		if (newLineChar == -1) return;
		
		try 
		{
			m_displayText.remove(newLineChar, m_displayText.getLength() - newLineChar - 1);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void insertAtFront(String a_string) 
	{
		insertString(0, a_string);
	}

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
