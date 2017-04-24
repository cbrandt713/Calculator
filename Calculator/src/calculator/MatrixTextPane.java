/*
 * 
 */
package calculator;

import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Displays text for the GUI in the "Matrix" view.
 */
@SuppressWarnings("serial")
public class MatrixTextPane extends JTextPane implements TextManipulation
{
	//Acceptable Modes for Text Input:
	/** The Mode NONE. */
	public final static int NONE = -1;
	
	/** The Mode CREATE_ROWS. */
	public final static int CREATE_ROWS = 0;
	
	/** The Mode CREATE_COLUMNS. */
	public final static int CREATE_COLUMNS = 1;
	
	/** The Mode DRAW_MATRIX. */
	public final static int DRAW_MATRIX = 2;
	
	/** The Mode EDIT_MATRIX. */
	public final static int EDIT_MATRIX = 3;
	
	/** The Mode NAME_MATRIX. */
	public final static int NAME_MATRIX = 4;
	
	/** The Mode MATRIX_MENU. */
	public final static int MATRIX_MENU = 5;
	
	/** The Mode DELETE_MENU. */
	public final static int DELETE_MENU = 6;
	
	/** The Mode SCALAR. */
	public final static int SCALAR = 100;
	
	/** The Mode GENERIC_OPERATION. */
	public final static int GENERIC_OPERATION = 200;
	
	/** The Mode DISPLAY_RESULT. */
	public final static int DISPLAY_RESULT = 201;
	
	/** The Mode EXCEPTION. */
	public final static int EXCEPTION = 900;
	
	
	//Matrix creation input:
	/** The display text. */
	private StyledDocument m_displayText;
	
	/** The stored string. */
	private String m_storedString;
	
	
	//Text manipulation:
	/** The current text position. */
	private int m_currentTextPos;
	
	/** The beginning text position. */
	private int m_beginTextPos;
	
	/** Replace current text */
	private boolean m_replace;
	
	
	//Various:
	/** The underlined text location. */
	private int m_underlinedTextLoc;
	
	/** The underline set. */
	private SimpleAttributeSet m_underlineSet;
	
	/** The instance. */
	private static MatrixTextPane instance;
	
	/** The mode. */
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
		
		//Change font size:
		Font currentFont = getFont();
		
		Font newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), 16);
		this.setFont(newFont);
	}
	
	/**
	 * Gets the single instance of MatrixTextPane.
	 *
	 * @return single instance of MatrixTextPane
	 */
	public static MatrixTextPane getInstance()
	{
		if (instance == null)
		{
			instance = new MatrixTextPane();
		}
		
		return instance;
	}
	
	/**
	 * Sets the default values. 
	 * Helper method used by the constructor to initialize and the
	 * "Clear" button to reset the calculator to default settings.
	 */
	
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
	 * @param a_mode the new mode
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
	 * Sets the text to create rows mode.
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
	 * Sets the text to create columns mode.
	 */
	public void createColumns()
	{
		setMode(CREATE_COLUMNS);
		m_beginTextPos = getText().indexOf('_');
		m_currentTextPos = m_beginTextPos;
		m_replace = true;
	}
	
	/**
	 * Draws the selected matrix.
	 *
	 * @param a_selectedMatrix the selected matrix
	 */
	public void drawMatrix(Matrix a_selectedMatrix)
	{
		setMode(DRAW_MATRIX);
		append(a_selectedMatrix.toString() + "Done");
		editMatrix(0, 0, a_selectedMatrix);
	}
	
	/**
	 * Edits the selected matrix at the current position.
	 *
	 * @param a_currentRow the current row
	 * @param a_currentColumn the current column
	 * @param a_selectedMatrix the selected matrix
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
	 * Sets the text to name matrix mode.
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
	 * Displays the matrix menu.
	 *
	 * @param a_matrices the matrices to display.
	 * @param a_arrowPointer the pointer to the matrix on screen
	 * @param a_underlinePos indicates which mode is underlined (edit, select, delete)
	 */
	public void matrixMenu(Matrix[] a_matrices, int a_arrowPointer, int a_underlinePos)
	{
		setMode(MATRIX_MENU);
		setText("");
		
		//Display matrices:
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
		
		//Show the underline:
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
	 * Displays the delete menu.
	 *
	 * @param a_selectedMatrix the selected matrix
	 * @param a_arrowPointer the arrow pointer location
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
	 * Gets the scalar from user input
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
	 * Sets the text for a unary operation.
	 *
	 * @param a_operation the operation
	 * @param a_selectedMatrix the selected matrix
	 */
	public void setTextForUnaryOperation(String a_operation, Matrix a_selectedMatrix)
	{
		setMode(GENERIC_OPERATION);
		if (a_operation.equals("=")) setText(a_selectedMatrix.getName() + " = \n");
		else setText(a_operation + "(" + a_selectedMatrix.getName() + ") = \n");
	}
	
	/**
	 * Sets the text for a binary operation.
	 *
	 * @param a_operation the operation
	 * @param a_selectedMatrix the selected matrix
	 * @param a_amtSelected the amount of matrices selected
	 * @param a_scalarFraction the scalar fraction (can be null if not the selected operation)
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
	 * Display the result of a matrix operation.
	 *
	 * @param a_answerMatrix the matrix to display
	 */
	public void displayMatrixResult(Matrix a_answerMatrix)
	{
		setMode(DISPLAY_RESULT);
		append(a_answerMatrix.toString());
	}
	

	/**
	 * Display the result of a fraction operation.
	 *
	 * @param a_answerFraction the fraction to display.
	 */
	public void displayFractionResult(Fraction a_answerFraction)
	{
		setMode(DISPLAY_RESULT);
		append(a_answerFraction.toString());
	}
	
	/**
	 * Display an exception.
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
	 * @see calculator.TextManipulation#getUserEnteredText()
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
	 * Append to the end of the text.
	 *
	 * @param a_string the string
	 * @see calculator.TextManipulation#append(java.lang.String)
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
	 * Insert string at location.
	 *
	 * @param a_loc the location to insert at
	 * @param a_string the string
	 * @see calculator.TextManipulation#insertString(int, java.lang.String)
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
	 * Insert string at front.
	 *
	 * @param a_string the a string
	 * @see calculator.TextManipulation#insertAtFront(java.lang.String)
	 */
	@Override
	public void insertAtFront(String a_string)
	{
		insertString(0, a_string);
	}
	
	/**
	 * Replace text.
	 *
	 * @param a_locationOfText the location of the text
	 * @param a_lengthToReplace the length of text to replace
	 * @param a_string the string
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
	
	/**
	 * Backspace a character.
	 * 
	 * @see calculator.TextManipulation#backspace()
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
	 * Clear the user's entry.
	 * 
	 * @see calculator.TextManipulation#clearEntry()
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
	 * Gets the length of text at given position. 
	 * Underline text helper method.
	 *
	 * @param a_beginIndex the beginning index of the text
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
	 * @param a_currentRow the current row
	 * @param a_currentColumn the current column
	 * @param a_selectedMatrix the selected matrix
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
	 * @param a_beginIndex the beginning index to underline.
	 * @param a_length the length to underline
	 * @param a_replace replace underline if true, add to current underline if false
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

	/**
	 * Insert a character.
	 *
	 * @param a_char the character to insert.
	 */
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