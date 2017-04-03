/*
 * 
 */
package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
public class MatrixTextPane extends JTextPane implements ActionEventHandler
{
	
	/** The Constant NONE. */
	//Acceptable Modes for Text Input:
	private final static int NONE = -1;
	
	/** The Constant CREATE_ROWS. */
	private final static int CREATE_ROWS = 0;
	
	/** The Constant CREATE_COLUMNS. */
	private final static int CREATE_COLUMNS = 1;
	
	/** The Constant DRAW_MATRIX. */
	private final static int DRAW_MATRIX = 2;
	
	/** The Constant EDIT_MATRIX. */
	private final static int EDIT_MATRIX = 3;
	
	/** The Constant NAME_MATRIX. */
	private final static int NAME_MATRIX = 4;
	
	/** The Constant MATRIX_MENU. */
	private final static int MATRIX_MENU = 5;
	
	/** The Constant DELETE_MENU. */
	private final static int DELETE_MENU = 7;
	
	/** The Constant SELECT_MATRIX. */
	private final static int SELECT_MATRIX = 8;
	
	/** The Constant SCALAR. */
	private final static int SCALAR = 100;
	
	/** The Constant GENERIC_OPERATION. */
	private final static int GENERIC_OPERATION = 200;
	
	/** The Constant DISPLAY_RESULT. */
	private final static int DISPLAY_RESULT = 201;
	
	/** The Constant EXCEPTION. */
	private final static int EXCEPTION = 900;
	
	/**
	 * The Enum OperationArguments.
	 */
	private enum OperationArguments { /** The unary. */
 UNARY, /** The binary. */
 BINARY; }
	
	/** The m amt operands. */
	private OperationArguments m_amtOperands;
	
	/** The m amt selected. */
	private int m_amtSelected;
	
	/** The m mode. */
	private int m_mode;
	
	/** The m operation. */
	private String m_operation;
	
	/** The m rows. */
	//Matrix creation properties:
	private int m_rows;
	
	/** The m columns. */
	private int m_columns;
	
	/** The m display text. */
	//Matrix creation input:
	private StyledDocument m_displayText;
	
	/** The m stored string. */
	private String m_storedString;
	
	/** The m default name. */
	//Matrix Helper Strings:
	private Character m_defaultName;
	
	/** The m matrices. */
	//Data objects:
	private Matrix[] m_matrices;
	
	/** The m selected matrix. */
	private Matrix m_selectedMatrix;
	
	/** The m answer matrix. */
	private Matrix m_answerMatrix;
	
	/** The m answer fraction. */
	private Fraction m_answerFraction;
	
	/** The m scalar fraction. */
	private Fraction m_scalarFraction;
	
	/** The m amt matrices. */
	private int m_amtMatrices;
	
	/** The m current row. */
	private int m_currentRow;
	
	/** The m current column. */
	private int m_currentColumn;
	
	/** The m answer is matrix. */
	private boolean m_answerIsMatrix;
	
	/** The m arrow pointer. */
	//Text manipulation:
	private int m_arrowPointer;
	
	/** The m underline pos. */
	private int m_underlinePos;
	
	/** The m current text pos. */
	private int m_currentTextPos;
	
	/** The m begin text pos. */
	private int m_beginTextPos;
	
	/** The m replace. */
	private boolean m_replace;
	
	/** The m underlined text loc. */
	private int m_underlinedTextLoc;
	
	/** The m underline set. */
	private SimpleAttributeSet m_underlineSet;
	
	/** The calculator. */
	//Calculator and Required Data:
	private MatrixCalculator calculator;
	
	private MatrixGUIModel m_model;
	
	/**
	 * Instantiates a new matrix text pane.
	 */
	public MatrixTextPane()
	{	
		super();
		
		m_matrices = new Matrix[20];
		m_amtMatrices = 0;
		m_defaultName = 'A';
		m_displayText = getStyledDocument();
		
		calculator = new MatrixCalculator();
		m_underlineSet = new SimpleAttributeSet();
		
		setDefaultValues();
		
		DefaultCaret caret = (DefaultCaret) getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		Fraction[][] test = new Fraction[][] {
			{new Fraction(3), new Fraction(4), new Fraction(5), new Fraction(23)},
			{new Fraction(5), new Fraction(-2), new Fraction(-4), new Fraction(-1)},
			{new Fraction(2), new Fraction(5), new Fraction(3), new Fraction(4)}
		};
		m_matrices[m_amtMatrices] = new Matrix(test);
		m_matrices[m_amtMatrices++].setName("RREFExample");
			
		Fraction[][] inverseExample = new Fraction[][] {
			{new Fraction(1), new Fraction(3), new Fraction(3)},
			{new Fraction(1), new Fraction(4), new Fraction(3)},
			{new Fraction(1), new Fraction(3), new Fraction(4)}
		};
		
		m_matrices[m_amtMatrices] = new Matrix(inverseExample);
		m_matrices[m_amtMatrices++].setName("InverseExample");
		
		Fraction[][] one = new Fraction[][] {
			{new Fraction(2), new Fraction(5)},
			{new Fraction(6), new Fraction(3)}
		};
		
		m_matrices[m_amtMatrices] = new Matrix(one);
		m_matrices[m_amtMatrices++].setName("Add1");
		
		Fraction[][] two = new Fraction[][] {
			{new Fraction(3), new Fraction(6)},
			{new Fraction(7), new Fraction(9)}
		};
		
		m_matrices[m_amtMatrices] = new Matrix(two);
		m_matrices[m_amtMatrices++].setName("Add2");	
		
		
		Fraction[][] determinant = new Fraction[][] {
			{new Fraction(3), new Fraction(0), new Fraction(2), new Fraction(-1)},
			{new Fraction(1), new Fraction(2), new Fraction(0), new Fraction(-2)},
			{new Fraction(4), new Fraction(0), new Fraction(6), new Fraction(-3)},
			{new Fraction(5), new Fraction(0), new Fraction(2), new Fraction(0)}
		};
		
		m_matrices[m_amtMatrices] = new Matrix(determinant);
		m_matrices[m_amtMatrices++].setName("DeterminantExample");
	}
	
	//Helper method used by the constructor to initialize and the
	/**
	 * Sets the default values.
	 */
	//"Clear" button to reset the calculator to default settings.
	private void setDefaultValues()
	{
		m_rows = 0;
		m_columns = 0;
		m_arrowPointer = 1;
		m_underlinedTextLoc = -1;
		m_mode = NONE;
		m_operation = "";
		m_answerMatrix = null;
		m_selectedMatrix = null;
		m_answerFraction = null;
		m_currentRow = 0;
		m_currentColumn = 0;
		m_amtSelected = 0;
		m_amtOperands = OperationArguments.UNARY;
		m_storedString = "";
		m_answerIsMatrix = true;
		m_replace = false;
		m_underlinePos = 0;
		m_currentTextPos = 0;
		m_beginTextPos = 0;
		
		calculator.resetInputs();
		
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
	private int getMode()
	{
		return m_mode;
	}
		
	/**
	 * Creates the rows.
	 */
	private void createRows()
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
	private void createColumns()
	{
		setMode(CREATE_COLUMNS);
		m_beginTextPos = getText().indexOf('_');
		m_currentTextPos = m_beginTextPos;
		m_replace = true;
	}
	
	/**
	 * Draw matrix.
	 */
	private void drawMatrix()
	{
		setMode(DRAW_MATRIX);
		append(m_selectedMatrix.toString() + "Done");
		editMatrix();
	}
	
	/**
	 * Edits the matrix.
	 */
	private void editMatrix()
	{
		setMode(EDIT_MATRIX);
		m_underlinedTextLoc = getLocOfSymbol() + 2;
		m_beginTextPos = m_currentTextPos = m_underlinedTextLoc;
		m_replace = true;
		
		resetUnderline();
		setUnderline(m_underlinedTextLoc, getLengthOfTextAtPos(m_underlinedTextLoc), true);
	}
	
	/**
	 * Name matrix.
	 */
	private void nameMatrix()
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
	private void matrixMenu()
	{
		setMode(MATRIX_MENU);
		setText("");
		append("Select\tEdit\tDelete\n");
		for (Integer i = 1; i < m_matrices.length + 1; i++)
		{
			if (m_matrices[i-1] != null)
			{
				append(i.toString() + ". " + m_matrices[i-1].getName());
			}
			else
			{
				append(i.toString() + ".");
			}
			if (m_arrowPointer == i)
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
		
		switch (m_underlinePos)
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
	private void deleteMenu()
	{
		setText("");
		
		append("Delete " + m_selectedMatrix.getName() + "?\n");
		append("Yes ");
		if (m_arrowPointer == 1) append(" <--\n");
		else append("\n");
		append("No ");
		if (m_arrowPointer == 2) append(" <--");
	}
	
	/**
	 * Select matrix.
	 */
	private void selectMatrix()
	{
		setMode(SELECT_MATRIX);
		if (m_operation.equals("")) 
		{
			setText(m_selectedMatrix.getName());
		}
		else 
		{
			m_model.doOperation();
		}
	}
	
	/**
	 * Gets the scalar.
	 *
	 * @return the scalar
	 */
	private void getScalar()
	{
		setMode(SCALAR);
		String text = "Enter Scalar: ";
		m_beginTextPos = m_currentTextPos = text.length();
		setText(text);
	}
	
	/**
	 * Sets the text for operation.
	 */
	private void setTextForOperation()
	{
		setMode(GENERIC_OPERATION);
		if (m_amtOperands == OperationArguments.UNARY)
		{
			if (m_operation.equals("=")) setText(m_selectedMatrix.getName() + " = \n");
			else setText(m_operation + "(" + m_selectedMatrix.getName() + ") = \n");
			
		}
		else
		{
			if (m_amtSelected == 1)
			{
				setText(m_selectedMatrix.getName() + " " + m_operation + " ");
				m_storedString = getText();
			}
			else
			{
				setText(m_storedString + m_selectedMatrix.getName() + " = \n");
			}
		}
	}
	
	/**
	 * Display result.
	 */
	private void displayResult()
	{
		setMode(DISPLAY_RESULT);
		if (m_answerIsMatrix) append(m_answerMatrix.toString());
		else append(m_answerFraction.toString());
	}
	
	/**
	 * Display exception.
	 *
	 * @param a_exception the a exception
	 */
	private void displayException(MatrixException a_exception)
	{
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
	private String getUserEnteredText()
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
	private void append(String a_string)
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
	private void insertString(int a_loc, String a_string)
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
	private void insertAtFront(String a_string)
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
	private void replaceText(int a_locationOfText, int a_lengthToReplace, String a_string)
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
	private void backspace()
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
	private void clearEntry() 
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
	 * Try to parse the integer from the text given.
	 *
	 * @param a_input: the given text to parse.
	 * @return true if the input can be parsed into an int. Otherwise false if not.
	 */
	private boolean tryIntParse(String a_input)
	{
		try
		{
			//Can be parsed:
			Integer.parseInt(a_input);
			return true;
		}
		catch (NumberFormatException a_exception)
		{
			//Can not be parsed:
			return false;
		}
		
	}
	
	/**
	 * Try to parse the Fraction from the text given.
	 *
	 * @param a_input: the given text to parse.
	 * @return true if the input can be parsed into a Fraction Object. Otherwise false if not.
	 */
	private boolean tryFractionParse(String a_input)
	{
		try
		{
			//Can be parsed:
			Fraction.parseFraction(a_input);
			return true;
		}
		catch (NumberFormatException a_exception)
		{
			//Can not be parsed:
			return false;
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
	private int getLocOfSymbol()
	{
		String currentText = getText();
		
		//The symbol is considered "done"
		if (m_currentRow == m_selectedMatrix.getRows()) 
		{
			return currentText.indexOf("Done") - 2;
		}
		
		int loc = -1;
		
		//If column is 0, the symbol we want is the '['.
		for (int i = 0; i < m_currentRow + 1; i++)
		{
			loc = currentText.indexOf('[', loc + 1);
			for (int j = 0; j < m_currentColumn; j++)
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
	private void resetUnderline()
	{
		StyleConstants.setUnderline(m_underlineSet, false);
		m_displayText.setCharacterAttributes(0, m_displayText.getLength(), m_underlineSet, true);
	}
	
	/**
	 * Find matrix index by name.
	 *
	 * @param a_name: the name of the matrix to find.
	 * @return the index in the array of the matrix specified by name. -1 if not found.
	 */
	private int findMatrixIndexByName(String a_name)
	{
		for (int i = 0; i < m_amtMatrices; i++)
		{
			if (m_matrices[i].getName().equals(a_name)) return i;
		}
		
		return -1;
	}
	
	/**
	 * Enter key pressed in "create rows" area.
	 *
	 * @param a_enteredText: the text entered by the user.
	 */
	private void createRowsEnterPress(String a_enteredText)
	{
		//Only take acceptable numbers:
		if (tryIntParse(a_enteredText))
		{
			m_rows = Integer.parseInt(a_enteredText);
			createColumns();
		}
	}
	
	/**
	 * Enter key pressed in "create columns" area.
	 *
	 * @param a_enteredText: the text entered by the user.
	 */
	private void createColumnsEnterPress(String a_enteredText)
	{
		//Only take acceptable numbers:
		if (tryIntParse(a_enteredText))
		{
			m_columns = Integer.parseInt(a_enteredText);
			
			//Create a new matrix, and 
			m_matrices[m_amtMatrices] = new Matrix(m_rows, m_columns);
			m_selectedMatrix = m_matrices[m_amtMatrices];
			m_amtMatrices++;
			drawMatrix();
		}
	}

	/**
	 * Enter key pressed while editing matrix.
	 *
	 * @param a_enteredText: the entered text
	 * @return true if the user is finished editing. False if not.
	 */
	private boolean editMatrixEnterPress(String a_enteredText)
	{
		//Enter pressed on "Done":
		if (m_currentRow == m_selectedMatrix.getRows())
		{
			//Reset row and column:
			m_currentRow = 0;
			m_currentColumn = 0;
			//If editing the matrix, do not rename it.
			if (m_underlinePos == 1) setText("Matrix Edited Successfully!");
			else nameMatrix();
			
			//The user is done editing. Return.
			return true;
		}
		
		//Only take acceptable numbers to set the cell for editing.
		if (!tryFractionParse(a_enteredText))
		{
			return false;
		}
		
		//Set this cell to the user's input number.
		m_selectedMatrix.setCell(m_currentRow, m_currentColumn, Fraction.parseFraction(a_enteredText));
		
		//Not finished editing, return false.
		return false;
	}
	
	/**
	 * Enter pressed to finish naming matrix.
	 *
	 * @param a_enteredText: the entered text
	 */
	private void nameMatrixEnterPress(String a_enteredText)
	{
		//Reset underline:
		m_underlinedTextLoc = -1;
		
		//If nothing was entered, use default naming scheme:
		if (a_enteredText.equals(""))
		{
			a_enteredText = (m_defaultName.toString());
			m_defaultName++;
		}
		//Name matrix:
		m_matrices[m_amtMatrices - 1].setName(a_enteredText);
		
		setText("Matrix Added Successfully!");
	}
	
	/**
	 * Matrix menu enter press.
	 */
	private void matrixMenuEnterPress()
	{
		m_selectedMatrix = m_matrices[m_arrowPointer-1];
		
		//Select:
		if (m_underlinePos == 0)
		{
			m_amtSelected++;
			calculator.setInput(m_selectedMatrix);
			selectMatrix();
		}
		//Edit:
		else if (m_underlinePos == 1)
		{
			m_currentRow = 0;
			m_currentColumn = 0;
			setText("Editing " + m_selectedMatrix.getName() + ":\n");
			drawMatrix();
		}
		//Delete:
		else if (m_underlinePos == 2)
		{
			m_arrowPointer = 1;
			setMode(DELETE_MENU);
			deleteMenu();
		}
	}
	
	/**
	 * Delete menu enter press.
	 */
	private void deleteMenuEnterPress()
	{
		//Yes selected:
		if (m_arrowPointer == 1)
		{
			//We reused arrowpointer so find the index in our array:
			int indexToDelete = findMatrixIndexByName(m_selectedMatrix.getName());
			
			//Some unknown error occurred:
			if (indexToDelete == -1) 
			{
				System.out.println("Unknown error in delete!");
				return;
			}
			
			//Move every matrix up one slot that is passed this index
			//IE: Delete matrix 3 with 6 matrices, move 4 to 3, 5 to 4, and 6 to 5.
			for (int i = indexToDelete; i < m_amtMatrices - 1; i++)
			{
				m_matrices[i] = m_matrices[i + 1];
			}
			
			//Decrement total matrices:
			m_amtMatrices--;
			m_matrices[m_amtMatrices] = null;
			
			setText("Matrix deleted successfully!");
		}
		else
		{
			matrixMenu();
		}
	}
	
	/**
	 * Scalar enter press.
	 *
	 * @param a_enteredText the a entered text
	 */
	private void scalarEnterPress(String a_enteredText)
	{
		if (tryFractionParse(a_enteredText))
		{
			m_scalarFraction = Fraction.parseFraction(a_enteredText);
			calculator.setScalar(m_scalarFraction);
			m_amtSelected++;
			m_storedString = m_scalarFraction.toString() + " * ";
			m_model.doOperation();
		}
	}
	
	/**
	 * The user pressed the "enter" key to input data.
	 *
	 * @param a_event: the event which triggered the call of the function.
	*/
	public void enterActionPerformed(ActionEvent a_event)
	{	
		
		String enteredText = getUserEnteredText();
		
		//Switch based on the mode:
		switch (getMode()) 
		{
			//Typing in the rows section:
			case CREATE_ROWS:
			{	
				createRowsEnterPress(enteredText);
				break;
			}
			//Typing in the columns section:
			case CREATE_COLUMNS:
			{	
				createColumnsEnterPress(enteredText);
				break;
			}
			//Editing a matrix cell:
			case EDIT_MATRIX:
			{	
	        	boolean finished = editMatrixEnterPress(enteredText);
	        	
	        	//If the user pushed enter on "Done", return.
	        	if (finished) return;
	        	
	        	//Move right if user used enter to accept data
	        	editMatrixArrowAction("Right");
	        	
	        	//Since we weren't done, recall the method to edit.
	    		editMatrix();
	
				break;
			}
			//Naming the matrix:
			case NAME_MATRIX:
			{
				nameMatrixEnterPress(enteredText);
				break;
			}
			//Picking a matrix:
			case SELECT_MATRIX:
			case MATRIX_MENU:
			{
				matrixMenuEnterPress();
				break;
			}
			//Deleting a matrix:
			case DELETE_MENU:
			{
				deleteMenuEnterPress();
				break;
			}
			//Entering the scalar:
			case SCALAR:
			{
				scalarEnterPress(enteredText);
				break;
			}
			default:
			{
				System.out.println("Unknown error in enter action");
				break;
			}
		}
		
	}
	
	/**
	 * Delete action performed. This includes backspace, clear, and clear entry.
	 *
	 * @param a_event: the event that triggered the call of the function.
	 */
	public void deleteActionPerformed(ActionEvent a_event)
	{
		//Clear command:
		if (a_event.getActionCommand().equals("Clr"))
		{
			setDefaultValues();
			setText("");
		}
		//Clear entry command:
		else if (a_event.getActionCommand().equals("CE"))
		{
			clearEntry();
		}
		//Backspace command:
		else
		{	
			backspace();
		}
		
	}

	/**
	 * Arrow key pressed while editing matrix.
	 *
	 * @param a_direction: the direction to move
	 */
	private void editMatrixArrowAction(String a_direction)
	{
		//Increase row or column based on direction specified:
		//Up and down and will change row, left and right change
		//Column, unless you are at the end of a row, then it will change the row too.
		if (a_direction.equals("Down")
				&& m_currentRow < m_selectedMatrix.getRows())
		{
			m_currentRow++;
		}
		else if (a_direction.equals("Up")
				&& m_currentRow > 0)
		{
			m_currentRow--;
		}
		else if (a_direction.equals("Right"))
		{
			if (m_currentColumn < m_selectedMatrix.getColumns() - 1) m_currentColumn++;
			else if (m_currentColumn == m_selectedMatrix.getColumns() - 1 
					&& m_currentRow < m_selectedMatrix.getRows()) 
			{
				m_currentColumn = 0;
				m_currentRow++;
			}
		}
		else if (a_direction.equals("Left"))
		{
			if (m_currentColumn > 0) m_currentColumn--;
			else if (m_currentColumn == 0 && m_currentRow > 0)
			{
				m_currentRow--;
				m_currentColumn = m_selectedMatrix.getColumns() - 1;
			}
		}
		
		String text = getText();
		
		//If we are hovering over done, add an "arrow" to indicate position.
		if (m_currentRow == m_selectedMatrix.getRows())
		{
			append(" <--");
		}
		//If we are not on done but the arrow is still there, remove it.
		else if (text.substring(text.length() - 3, text.length()).equals("<--"))
		{
			text = text.substring(0, text.length() - 3);
			setText(text);
		}
		
	}
	
	/**
	 * Move arrow based on arrow keys. Hover over "yes" if up, "no" if down.
	 *
	 * @param a_direction: the direction
	 */
	private void deleteMatrixArrowAction(String a_direction) 
	{
		if (a_direction.equals("Up"))
		{
			m_arrowPointer = 1;
		}
		else if (a_direction.equals("Down"))
		{
			m_arrowPointer = 2;
		}
	}
	
	/**
	 * Arrow key action performed.
	 *
	 * @param a_event: the event that triggered the function call
	 */
	public void arrowActionPerformed(ActionEvent a_event)
	{
		//Parse direction from event:
		KeyEvent ke = (KeyEvent) EventQueue.getCurrentEvent();
        String direction = KeyEvent.getKeyText( ke.getKeyCode() );
        
        if (getMode() == EDIT_MATRIX)
        {
    		String enteredText = getUserEnteredText();
    		
        	if (m_currentRow != m_selectedMatrix.getRows()) editMatrixEnterPress(enteredText);
        	editMatrixArrowAction(direction);
    		editMatrix();
        }
        
        if (getMode() == DELETE_MENU)
        {
        	deleteMatrixArrowAction(direction);
        	deleteMenu();
        }
        
		if (getMode() != MATRIX_MENU && getMode() != SELECT_MATRIX)
		{
			return;
		}
		
		if (direction.equals("Down"))
		{
			if (m_arrowPointer < m_amtMatrices) m_arrowPointer++;
		}
		else if (direction.equals("Up"))
		{
			if (m_arrowPointer > 1) m_arrowPointer--;
		}
		else if (direction.equals("Right"))
		{
			if (m_underlinePos < 2) m_underlinePos++;
		}
		else if (direction.equals("Left"))
		{
			if (m_underlinePos > 0) m_underlinePos--;
		}

		matrixMenu();
	}

	/**
	 * Number action performed.
	 *
	 * @param a_event the a event
	 */
	public void numberActionPerformed(ActionEvent a_event)
	{
		String text = a_event.getActionCommand();
		if (m_replace) 
		{	
			m_replace = false;
			replaceText(m_currentTextPos, getLengthOfTextAtPos(m_currentTextPos), text);
		}
		else insertString(m_currentTextPos, text);
		
		if (getMode() == EDIT_MATRIX)
		{
			setUnderline(m_beginTextPos, getLengthOfTextAtPos(m_beginTextPos), false);
		}
		
		m_currentTextPos++;
	}
	
	/**
	 * Letter action performed.
	 *
	 * @param a_event the a event
	 */
	public void letterActionPerformed(ActionEvent a_event)
	{
		numberActionPerformed(a_event);
	}

	/**
	 * Operator action performed.
	 *
	 * @param a_event the a event
	 */
	public void operatorActionPerformed(ActionEvent a_event) 
	{
		//In the case that we are using the "/" or "-" operator to indicate a fraction:
		if (getMode() == EDIT_MATRIX)
		{
			letterActionPerformed(a_event);
			return;
		}
		
		//Otherwise we are using the generic meaning of the operator:
		m_operation = a_event.getActionCommand();
		m_model.doOperation();
	}

	/**
	 * Matrix action performed.
	 *
	 * @param a_event the a event
	 */
	public void matrixActionPerformed(ActionEvent a_event) 
	{
		
		setText("");
		String operation = a_event.getActionCommand();
		
		if (operation.equals("Det") || operation.equals("Trace") 
				|| operation.equals("Rank")) m_answerIsMatrix = false;
		
		if (operation.equals("Create")) createRows();
		else if (operation.equals("Matrix")) matrixMenu();
		else 
		{
			m_operation = operation;
			m_model.doOperation();
		}
		
	}
	
}