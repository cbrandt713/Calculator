package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

@SuppressWarnings("serial")
public class BasicTextArea extends JTextArea implements TextManipulation 
{

	//Display Line Number Constants:
	public static final int EXPRESSION = 0;
	public static final int INPUT = 1;
	
	private boolean m_typeOverFlag;
	private boolean m_firstInputFlag;
	
	private static BasicTextArea m_instance;
	
	private BasicTextArea()
	{
		super();
		
		m_typeOverFlag = true;
		m_firstInputFlag = true;
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
		String displayText = getText();
		int newLine = displayText.indexOf('\n');
		return displayText.substring(newLine + 1);
	}
	
	public void changeDisplay(String message, int lineNum)
	{
		int newLineChar = 0;
		
		try 
		{
			newLineChar = getLineEndOffset(0);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
		
		
		if (lineNum == EXPRESSION)
		{
			changeExpressionLine(message, newLineChar);
		}
		else
		{
			changeInputLine(message, newLineChar);
		}
		
	}
	
	private void changeExpressionLine(String a_message, int a_newLineChar)
	{
		if (a_message.equals("Clear"))
		{
			setText(getText().substring(a_newLineChar - 1));
		}
		else
		{
			insert(a_message, a_newLineChar - 1);
		}	
	}
	
	private void changeInputLine(String a_message, int a_newLineChar)
	{
		//Clear the Input Line:
		if (a_message.equals("Clear"))
		{
			setText(getText().substring(0, a_newLineChar));
		}
		
		//Backspace a character:
		else if (a_message.equals("Backspace"))
		{
			//If the text on the input line is not blank, delete the last character
			if (!getText().substring(a_newLineChar).equals(""))
			{
				setText(getText().substring(0, getText().length() - 1));
			}
			
			//If the text is now blank, place a 0 on the input line and set the typeOverFlag
			if (getText().substring(a_newLineChar).equals(""))
			{
				setText(getText() + "0");
				m_typeOverFlag = true;
			}	
		}
		
		//Type over the text on the input line:
		else if (m_typeOverFlag)
		{
			setText(getText().substring(0, a_newLineChar));
			insert(a_message, a_newLineChar);
			m_typeOverFlag = false;
		}
		
		//If no special case, just append a character to the end of the input line:
		else 
		{
			int length = getText().length(); 
			insert(a_message, length);
		}
	}
	
	private String formatDouble(double a_result)
	{
		String res = ((Double) a_result).toString();
		String s = res.indexOf(".") < 0 ? res : res.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
	}

	@Override
	public void backspace() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearEntry() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertAtFront(String a_string) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertString(int a_location, String a_string) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int a_location, int a_amtChars) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceText(int a_locationOfText, int a_lengthToReplace, String a_string) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
