/*
 * 
 */
package calculator;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

// TODO: Auto-generated Javadoc
/**
 * The Class MatrixTextPane.
 */
@SuppressWarnings("serial")
public class MatrixTextPane extends JTextPane implements TextManipulation
{
	//Acceptable Modes for Text Input:
	public final static int NONE = -1;
	public final static int CREATE_ROWS = 0;
	public final static int CREATE_COLUMNS = 1;
	public final static int DRAW_MATRIX = 2;
	public final static int EDIT_MATRIX = 3;
	public final static int NAME_MATRIX = 4;
	public final static int MATRIX_MENU = 5;
	public final static int DELETE_MENU = 6;
	public final static int SCALAR = 100;
	public final static int GENERIC_OPERATION = 200;
	public final static int DISPLAY_RESULT = 201;
	public final static int EXCEPTION = 900;
	
	//Matrix creation input:
	private StyledDocument m_displayText;
	private String m_storedString;
	
	//Text manipulation:
	private int m_currentTextPos;
	private int m_beginTextPos;
	private boolean m_replace;
	private int m_underlinedTextLoc;
	private SimpleAttributeSet m_underlineSet;
	
	private static MatrixTextPane instance;
	private int m_mode;
	
	/**
	 * Instantiates a new matrix text pane.
	 */
	private MatrixTextPane()
	{	
		super();

		m_displayText = getStyledDocument();
		m_underlineSet = new SimpleAttributeSet();
		
		setDefaultValues();
		
		DefaultCaret caret = (DefaultCaret) getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
	}
	
	public static MatrixTextPane getInstance()
	{
		if (instance == null)
		{
			instance = new MatrixTextPane();
		}
		
		return instance;
	}
	//Helper method used by the constructor to initialize and the
	/**
	 * Sets the default values.
	 */
	//"Clear" button to reset the calculator to default settings.
	public void setDefaultValues()
	{
		m_mode = NONE;
		m_underlinedTextLoc = -1;
		m_storedString = "";
		m_replace = false;
		m_currentTextPos = 0;
		m_beginTextPos = 0;
	}
	
	/**
	 * Sets the mode.
	 *
	 * @param a_mode: the new mode
	 */
	private void setMode(int a_mode)
	{
		m_mode = a_mode;
	}
	
	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public int getMode()
	{
		return m_mode;
	}
	
	/**
	 * Creates the rows.
	 */
	public void createRows()
	{
		setMode(CREATE_ROWS);
		String text = "Matrix Size: ";
		m_beginTextPos = text.length();
		m_currentTextPos = m_beginTextPos;
		m_replace = true;
		append(text + "_ rows X _ columns\n");
	}
	
	/**
	 * Creates the columns.
	 */
	public void createColumns()
	{
		setMode(CREATE_COLUMNS);
		m_beginTextPos = getText().indexOf('_');
		m_currentTextPos = m_beginTextPos;
		m_replace = true;
	}
	
	/**
	 * Draw matrix.
	 */
	public void drawMatrix(Matrix a_selectedMatrix)
	{
		setMode(DRAW_MATRIX);
		append(a_selectedMatrix.toString() + "Done");
		editMatrix(0, 0, a_selectedMatrix);
	}
	
	/**
	 * Edits the matrix.
	 */
	public void editMatrix(int a_currentRow, int a_currentColumn, Matrix a_selectedMatrix)
	{
		setMode(EDIT_MATRIX);
		m_underlinedTextLoc = getLocOfSymbol(a_currentRow, a_currentColumn, a_selectedMatrix) + 2;
		m_beginTextPos = m_currentTextPos = m_underlinedTextLoc;
		m_replace = true;
		
		resetUnderline();
		setUnderline(m_underlinedTextLoc, getLengthOfTextAtPos(m_underlinedTextLoc), true);
	}
	
	/**
	 * Name matrix.
	 */
	public void nameMatrix()
	{
		setMode(NAME_MATRIX);
		setText(getText().substring(getText().indexOf('[')));
		String text = "Name Matrix (or \"Enter\" to skip): ";
		m_beginTextPos = m_currentTextPos = text.length();
		m_replace = false;
		
		insertAtFront(text + "\n");
	}
	
	/**
	 * Matrix menu.
	 */
	public void matrixMenu(Matrix[] a_matrices, int a_arrowPointer, int a_underlinePos)
	{
		setMode(MATRIX_MENU);
		setText("");
		append("Select\tEdit\tDelete\n");
		for (Integer i = 1; i < a_matrices.length + 1; i++)
		{
			if (a_matrices[i-1] != null)
			{
				append(i.toString() + ". " + a_matrices[i-1].getName());
			}
			else
			{
				append(i.toString() + ".");
			}
			if (a_arrowPointer == i)
			{
				append(" <--\n");
			}
			else
			{
				append("\n");
			}	
		}
		
		int beginPos = 0;
		int length = 0;
		
		switch (a_underlinePos)
		{
			case 0:
			{
				beginPos = 0;
				length = 6;
				break;
			}
			case 1:
			{
				beginPos = 7;
				length = 4;
				break;
			}
			case 2:
			{
				beginPos = 12;
				length = 6;
				break;
			}
			default:
			{
				beginPos = 0;
				length = 0;
				System.out.println("Error in m_underlinePos switch");
			}
		}
		
		setUnderline(beginPos, length, true);
	}
	
	/**
	 * Delete menu.
	 */
	public void deleteMenu(Matrix a_selectedMatrix, int a_arrowPointer)
	{
		setText("");
		
		append("Delete " + a_selectedMatrix.getName() + "?\n");
		append("Yes ");
		if (a_arrowPointer == 1) append(" <--\n");
		else append("\n");
		append("No ");
		if (a_arrowPointer == 2) append(" <--");
	}
	
	/**
	 * Gets the scalar.
	 *
	 * @return the scalar
	 */
	public void getScalar()
	{
		setMode(SCALAR);
		String text = "Enter Scalar: ";
		m_beginTextPos = m_currentTextPos = text.length();
		setText(text);
	}
	
	/**
	 * Sets the text for operation.
	 */
	public void setTextForUnaryOperation(String a_operation, Matrix a_selectedMatrix)
	{
		setMode(GENERIC_OPERATION);
		if (a_operation.equals("=")) setText(a_selectedMatrix.getName() + " = \n");
		else setText(a_operation + "(" + a_selectedMatrix.getName() + ") = \n");
	}
	
	/**
	 * Sets the text for operation.
	 */
	public void setTextForBinaryOperation(String a_operation, Matrix a_selectedMatrix, int a_amtSelected, Fraction a_scalarFraction)
	{
		setMode(GENERIC_OPERATION);
		
		if (a_amtSelected == 1)
		{
			setText(a_selectedMatrix.getName() + " " + a_operation + " ");
			m_storedString = getText();
		}
		else if (a_operation.equals("Scalar"))
		{	
			setText(a_scalarFraction + " * " + a_selectedMatrix.getName() + " = \n");
		}
		else
		{
			setText(m_storedString + a_selectedMatrix.getName() + " = \n");
		}
	}
	
	/**
	 * Display matrix result.
	 */
	public void displayMatrixResult(Matrix a_answerMatrix)
	{
		setMode(DISPLAY_RESULT);
		append(a_answerMatrix.toString());
	}
	

	/**
	 * Display fraction result.
	 */
	public void displayFractionResult(Fraction a_answerFraction)
	{
		setMode(DISPLAY_RESULT);
		append(a_answerFraction.toString());
	}
	
	/**
	 * Display exception.
	 *
	 * @param a_exception the exception
	 */
	public void displayException(MatrixException a_exception)
	{
		setMode(EXCEPTION);
		setText("");
		append(a_exception.getMessage() + "\nMatrices: ");
		
		String[] offenders = a_exception.getOffendingMatrices();
		
		for (String matrixName : offenders)
		{
			if (matrixName != null)
			append(matrixName + " ");
		}
	}
	
	/**
	 * Gets the user entered text.
	 *
	 * @return the user entered text
	 */
	@Override
	public String getUserEnteredText()
	{
		int length = m_currentTextPos - m_beginTextPos;
		
		//Prevent the thrown exception from the getText method below:
		if (length == 0) return "";
		
		String enteredText = "";
		try 
		{
			enteredText = m_displayText.getText(m_beginTextPos, length);
		}
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
		
		return enteredText;
	}
	
	/**
	 * Append.
	 *
	 * @param a_string the a string
	 */
	@Override
	public void append(String a_string)
	{
		try 
		{
			m_displayText.insertString(m_displayText.getLength(), a_string, null);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Insert string.
	 *
	 * @param a_loc the a loc
	 * @param a_string the a string
	 */
	@Override
	public void insertString(int a_loc, String a_string)
	{
		try 
		{
			m_displayText.insertString(a_loc, a_string, null);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Insert at front.
	 *
	 * @param a_string the a string
	 */
	@Override
	public void insertAtFront(String a_string)
	{
		insertString(0, a_string);
	}
	
	/**
	 * Replace text.
	 *
	 * @param a_locationOfText the a location of text
	 * @param a_lengthToReplace the a length to replace
	 * @param a_string the a string
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
	
	/**
	 * Backspace.
	 */
	@Override
	public void backspace()
	{
		if (m_currentTextPos <= m_beginTextPos) return;
		
		try 
		{
			m_displayText.remove(m_currentTextPos-1, 1);
			m_currentTextPos--;
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Clear entry.
	 */
	@Override
	public void clearEntry() 
	{
		try 
		{
			m_displayText.remove(m_currentTextPos, getLengthOfTextAtPos(m_beginTextPos));
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the amount of characters specified at the location.
	 * 
	 * @param a_location the location to remove characters
	 * @param a_amtChars the amount of characters to remove
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
	 * Gets the length of text at given position. 
	 * Underline text helper method.
	 *
	 * @param a_beginIndex: the first index
	 * @return the length of the text starting at a_beginIndex
	 */
	private int getLengthOfTextAtPos(int a_beginIndex)
	{
		int length = m_displayText.getLength();
		
		//Attempting to search text out of bounds:
		if (a_beginIndex >= length)
		{
			return -1;
		}
		
		int index = a_beginIndex;
		int count = 0;
		String current = "";
		try
		{
			current = m_displayText.getText(index++, 1);
		}
		catch (BadLocationException e)
		{
			return -1;
		}
		
		//The end of the text is considered to be a white space, tab, or new line, or end of string.
		while (!current.equals(" ") && !current.equals('\n') && !current.equals('\t') && index < length)
		{
			count++;
			try
			{
				current = m_displayText.getText(index++, 1);
			}
			catch (BadLocationException e)
			{
				return count;
			}
		} 
		
		return count;
	}
	
	/**
	 * Gets the location of the matrix symbol when editing the matrix.
	 * Underline text helper method.
	 *
	 * @return the location of the matrix symbol (either | or [)
	 */
	private int getLocOfSymbol(int a_currentRow, int a_currentColumn, Matrix a_selectedMatrix)
	{
		String currentText = getText();
		
		//The symbol is considered "done"
		if (a_currentRow == a_selectedMatrix.getRows()) 
		{
			return currentText.indexOf("Done") - 2;
		}
		
		int loc = -1;
		
		//If column is 0, the symbol we want is the '['.
		for (int i = 0; i < a_currentRow + 1; i++)
		{
			loc = currentText.indexOf('[', loc + 1);
			for (int j = 0; j < a_currentColumn; j++)
			{
				//If column is not 0 we want the '|' symbol to the left.
				loc = currentText.indexOf('|', loc + 1);
			}
		}
		
		return loc;
	}
	
	/**
	 * Underlines text starting at a_beginIndex, length characters long.
	 *
	 * @param a_beginIndex: the position to begin underlining.
	 * @param a_length: the length of the text to underline
	 * @param a_replace: if true, replace the existing underlined text. If false, add to it.
	 */
	private void setUnderline(int a_beginIndex, int a_length, boolean a_replace) 
	{
		StyleConstants.setUnderline(m_underlineSet, true);
		m_displayText.setCharacterAttributes(a_beginIndex, a_length, m_underlineSet, a_replace);
	}
	
	/**
	 * Removes all underlined text.
	 */
	public void resetUnderline()
	{
		StyleConstants.setUnderline(m_underlineSet, false);
		m_displayText.setCharacterAttributes(0, m_displayText.getLength(), m_underlineSet, true);
	}

	public void insertCharacter(String a_char) 
	{
		if (m_replace) 
		{	
			m_replace = false;
			replaceText(m_currentTextPos, getLengthOfTextAtPos(m_currentTextPos), a_char);
		}
		else insertString(m_currentTextPos, a_char);
		
		if (getMode() == EDIT_MATRIX)
		{
			setUnderline(m_beginTextPos, getLengthOfTextAtPos(m_beginTextPos), false);
		}
		
		m_currentTextPos++;
		
	}
	
}