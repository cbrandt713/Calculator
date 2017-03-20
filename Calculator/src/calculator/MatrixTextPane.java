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

@SuppressWarnings("serial")
public class MatrixTextPane extends JTextPane
{
	//Acceptable Modes for Text Input:
	private final static int NONE = -1;
	private final static int CREATE_ROWS = 0;
	private final static int CREATE_COLUMNS = 1;
	private final static int DRAW_MATRIX = 2;
	private final static int EDIT_MATRIX = 3;
	private final static int NAME_MATRIX = 4;
	private final static int MATRIX_MENU = 5;
	private final static int DELETE_MENU = 7;
	private final static int SELECT_MATRIX = 8;
	private final static int SCALAR = 100;
	private final static int GENERIC_OPERATION = 200;
	private final static int DISPLAY_RESULT = 201;
	private final static int EXCEPTION = 900;
	
	public enum OperationArguments
	{
		UNARY, BINARY;
	}
	private OperationArguments m_amtOperands;
	private int m_amtSelected;
	private int m_mode;
	private String m_operation;
	
	//Matrix creation properties:
	private int m_rows;
	private int m_columns;
	
	//Matrix creation input:
	private StyledDocument m_displayText;
	private String m_runningString;
	private String m_storedString;
	
	//Matrix Helper Strings:
	private String m_matrixText;
	private Character m_defaultName;
	
	//Data objects:
	private Matrix[] m_matrices;
	private Matrix m_selectedMatrix;
	private Matrix m_answerMatrix;
	private Fraction m_answerFraction;
	private Fraction m_scalarFraction;
	private int m_amtMatrices;
	private int m_currentRow;
	private int m_currentColumn;
	private boolean m_answerIsMatrix;
	
	//Various:
	private int m_arrowPointer;
	private int m_underlinePos;
	private MatrixException m_caughtException;
	private int m_underlinedTextLoc;
	private SimpleAttributeSet m_underlineSet;
	
	//Calculator and Required Data:
	private MatrixCalculator calculator;
	
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
	//"Clear" button to reset the calculator to default settings.
	private void setDefaultValues()
	{
		m_rows = 0;
		m_columns = 0;
		m_runningString = "";
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
		m_underlinePos = 0;
		
		calculator.resetInputs();
		
	}
	
	private void setMode(int a_mode)
	{
		m_mode = a_mode;
	}
	
	private int getMode()
	{
		return m_mode;
	}
	
	public void createRows()
	{
		setMode(CREATE_ROWS);
		setText("Matrix Size: _ rows X _ columns");
	}
	
	private void createColumns()
	{
		setMode(CREATE_COLUMNS);
		updateText();
	}
	
	private void drawMatrix()
	{
		setMode(DRAW_MATRIX);
		updateText();
		editMatrix();
	}
	
	private void editMatrix()
	{
		setMode(EDIT_MATRIX);
		updateText();
	}
	
	private void nameMatrix()
	{
		setMode(NAME_MATRIX);
		updateText();
	}
	
	public void matrixMenu()
	{
		setMode(MATRIX_MENU);
		updateText();
	}
	
	public void selectMatrix()
	{
		setMode(SELECT_MATRIX);
		updateText();
	}
	
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
	
	public void doOperation()
	{
		if (m_selectedMatrix == null) 
		{
			matrixMenu();
			return;
		}
		
		if (m_operation.equals("+") || m_operation.equals("-") 
				|| m_operation.equals("*") || m_operation.equals("/")
				|| m_operation.equals("Scalar") )
		{
			m_amtOperands = OperationArguments.BINARY;
		}
		else
		{
			m_amtOperands = OperationArguments.UNARY;
		}
			
		calculator.setOperator(m_operation);		
		
		if (m_operation.equals("Scalar") && m_amtSelected == 1) setMode(SCALAR);
		else setMode(GENERIC_OPERATION);
		
		updateText();
		
		if (m_amtOperands == OperationArguments.BINARY && m_amtSelected < 2)
		{
			return;
		}
		
		if (m_amtOperands == OperationArguments.UNARY && m_amtSelected != 1)
		{
			return;
		}
		
		setMode(DISPLAY_RESULT);
		
		
		try 
		{
			if (m_answerIsMatrix)
			{
				m_answerMatrix = calculator.doCalculation();
				m_answerMatrix.setName("Ans");
				m_selectedMatrix = m_answerMatrix;
				m_amtSelected = 1;
			}
			else
			{
				m_answerFraction = calculator.fractionResultOperation();
				m_answerMatrix = null;
				m_selectedMatrix = null;
				m_amtSelected = 0;
			}
		} 
		catch (MatrixException exception) 
		{
			setMode(EXCEPTION);
			m_caughtException = exception;
		}
		
		updateText();
		
		m_answerIsMatrix = true;
		
	}
	
	
	private boolean tryParse(String a_input)
	{
		try
		{
			Integer.parseInt(a_input);
			return true;
		}
		catch (NumberFormatException a_exception)
		{
			return false;
		}
		
	}
	
	private boolean tryFractionParse(String a_input)
	{
		try
		{
			Fraction.parseFraction(a_input);
			return true;
		}
		catch (NumberFormatException a_exception)
		{
			return false;
		}
	}
	
	private int getLengthOfText(int a_beginIndex, String a_textToSearch) throws StringIndexOutOfBoundsException
	{
		int length = a_textToSearch.length();
		
		if (a_beginIndex >= length)
		{
			throw new StringIndexOutOfBoundsException(a_beginIndex);
		}
		
		int index = a_beginIndex;
		int count = 0;
		char current = a_textToSearch.charAt(index++);
		
		while (current != ' ' && current != '\n' && current != '\t' && index < length)
		{
			count++;
			current = a_textToSearch.charAt(index++);
		} 
		
		return count;
	}
	
	private int getLocOfSymbol()
	{
		if (m_currentRow == m_selectedMatrix.getRows()) 
		{
			return m_matrixText.indexOf("Done") - 2;
		}
		
		int loc = -1;
		for (int i = 0; i < m_currentRow + 1; i++)
		{
			loc = m_matrixText.indexOf('[', loc + 1);
			for (int j = 0; j < m_currentColumn; j++)
			{
				loc = m_matrixText.indexOf('|', loc + 1);
			}
		}
		
		return loc;
	}
	
	private void setUnderline(int a_beginIndex, int a_length, boolean a_replace) 
	{
		StyleConstants.setUnderline(m_underlineSet, true);
		getStyledDocument().setCharacterAttributes(a_beginIndex, a_length, m_underlineSet, a_replace);
	}
	
	private void resetUnderline()
	{
		StyleConstants.setUnderline(m_underlineSet, false);
		getStyledDocument().setCharacterAttributes(0, 0, m_underlineSet, true);
	}
	
	private int findMatrixIndexByName(String a_name)
	{
		for (int i = 0; i < m_amtMatrices; i++)
		{
			if (m_matrices[i].getName().equals(a_name)) return i;
		}
		
		return -1;
	}
	
	public void updateText()
	{
		switch (getMode())
		{
			case NONE:
			{
				setText("");
				break;
			}
			case CREATE_ROWS:
			{
				setText("Matrix Size: " + m_runningString + " rows X _ columns\n");
				break;
			}
			case CREATE_COLUMNS:
			{
				setText("Matrix Size: " + m_rows + " rows X " + m_runningString + " columns\n");
				break;
			}
			case DRAW_MATRIX:
			{
				append(m_selectedMatrix.toString() + "Done");
				m_matrixText = getText();
				m_runningString = "";
				break;
			}
			case EDIT_MATRIX:
			{
				m_underlinedTextLoc = getLocOfSymbol() + 2;
					
				String beginText = m_matrixText.substring(0, m_underlinedTextLoc);
				String endText = m_matrixText.substring(m_underlinedTextLoc 
						+ getLengthOfText(m_underlinedTextLoc, m_matrixText));
				
				if (m_runningString.equals("")) setText(beginText 
						+ m_selectedMatrix.getCell(m_currentRow, m_currentColumn).toString() 
						+ endText);
				else setText(beginText + m_runningString + endText);
				
				setUnderline(m_underlinedTextLoc, getLengthOfText(m_underlinedTextLoc, getText()), false);
				break;
			}
			case NAME_MATRIX:
			{
				setText(getText().substring(getText().indexOf('[')));
				insertString(0, "Name Matrix (or \"Enter\" to skip): " + m_runningString + "\n");
				break;
			}
			case MATRIX_MENU:
			{
				StringBuilder line = new StringBuilder("Select\tEdit\tDelete\n");
				for (Integer i = 1; i < m_matrices.length + 1; i++)
				{
					if (m_matrices[i-1] != null)
					{
						line.append(i.toString() + ". " + m_matrices[i-1].getName());
					}
					else
					{
						line.append(i.toString() + ".");
					}
					if (m_arrowPointer == i)
					{
						line.append(" <--\n");
					}
					else
					{
						line.append("\n");
					}	
				}
				setText(line.toString());
				
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
				
				setUnderline(beginPos, length, false);
				break;
			}
			
			case SELECT_MATRIX:
			{
				if (m_operation.equals("")) 
				{
					setText(m_selectedMatrix.getName());
				}
				else 
				{
					doOperation();
				}
				break;
			}
			case DELETE_MENU:
			{
				StringBuilder line = new StringBuilder("");
				
				line.append("Delete " + m_selectedMatrix.getName() + "?\n");
				line.append("Yes ");
				if (m_arrowPointer == 1) line.append(" <--\n");
				else line.append("\n");
				line.append("No ");
				if (m_arrowPointer == 2) line.append(" <--");
				
				setText(line.toString());
				
				break;
			}
			case GENERIC_OPERATION:
			{
				if (m_amtOperands == OperationArguments.UNARY)
				{
					if (m_operation.equals("=")) setText(m_selectedMatrix.getName() + " =\n");
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
				break;
			}
			case SCALAR:
			{
				setText("Enter Scalar: " + m_runningString);
				break;
			}
			case DISPLAY_RESULT:
			{
				if (m_answerIsMatrix) append(m_answerMatrix.toString());
				else append(m_answerFraction.toString());
				break;
			}
			case EXCEPTION:
			{
				setText(m_caughtException.getMessage() + "\nMatrices: ");
				
				String[] offenders = m_caughtException.getOffendingMatrices();
				
				for (String matrixName : offenders)
				{
					if (matrixName != null)
					append(matrixName + " ");
				}
			}
		}
		
	}

	public void enterActionPerformed(ActionEvent a_event)
	{	
		switch (getMode()) 
		{
			//Typing in the rows section:
			case CREATE_ROWS:
			{
				//Only take acceptable numbers:
				if (tryParse(m_runningString))
				{
					m_rows = Integer.parseInt(m_runningString);
					m_runningString = "";
					createColumns();
				}
				break;
			}
			//Typing in the columns section:
			case CREATE_COLUMNS:
			{
				//Only take acceptable numbers:
				if (tryParse(m_runningString))
				{
					m_columns = Integer.parseInt(m_runningString);
					m_runningString = "";
					m_matrices[m_amtMatrices++] = new Matrix(m_rows, m_columns);
					m_selectedMatrix = m_matrices[m_amtMatrices-1];
					drawMatrix();
				}
				break;
			}
			case EDIT_MATRIX:
			{	
				if (m_currentRow == m_selectedMatrix.getRows())
				{
					m_currentRow = 0;
					m_currentColumn = 0;
					m_runningString = "";
					if (m_underlinePos == 1) setText("Matrix Edited Successfully!");
					else nameMatrix();
					break;
				}
				
				if (!tryFractionParse(m_runningString))
				{
					break;
				}
						
				m_selectedMatrix.setCell(m_currentRow, m_currentColumn, Fraction.parseFraction(m_runningString));
				m_runningString = "";
				
				m_matrixText = getText();
				
				if (m_currentColumn + 1 < m_selectedMatrix.getColumns())
				{
					m_currentColumn++;
				}
				else if (m_currentRow < m_selectedMatrix.getRows())
				{
					m_currentRow++;
					m_currentColumn = 0;
				}
				
				//Returns the location of the symbol. Add 2 for the location of the text.
				m_underlinedTextLoc = getLocOfSymbol() + 2;
				
				//If the location is 1, the function returned -1, meaning not found, so do not underline.
				if (m_underlinedTextLoc != 1) 
				{
					resetUnderline();
					setText(m_matrixText);
					setUnderline(m_underlinedTextLoc, 
						getLengthOfText(m_underlinedTextLoc, getText()), true);
					
				}
				break;
			}
			case NAME_MATRIX:
			{
				m_underlinedTextLoc = -1;
				
				//If nothing was entered, use default naming scheme:
				if (m_runningString.equals(""))
				{
					m_runningString = (m_defaultName.toString());
					m_defaultName++;
				}
				m_matrices[m_amtMatrices - 1].setName(m_runningString);
				m_runningString = "";
				
				setText("Matrix Added Successfully!");
				
				break;
			}
			case SELECT_MATRIX:
			case MATRIX_MENU:
			{
				m_selectedMatrix = m_matrices[m_arrowPointer-1];
				
				//Select:
				if (m_underlinePos == 0)
				{
					m_amtSelected++;
					calculator.setInput(m_selectedMatrix);
					setMode(SELECT_MATRIX);
					updateText();
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
					updateText();
				}
				
				break;
			}
			case DELETE_MENU:
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
						break;
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
					setMode(MATRIX_MENU);
					updateText();
				}
				break;
			}
			case SCALAR:
			{
				if (tryFractionParse(m_runningString))
				{
					m_scalarFraction = Fraction.parseFraction(m_runningString);
					calculator.setScalar(m_scalarFraction);
					m_amtSelected++;
					m_storedString = m_scalarFraction.toString() + " * ";
					doOperation();
				}
				break;
			}
			default:
			{
				System.out.println("Unknown error in enter action");
				
				break;
			}
		}
		
	}

	public void numberActionPerformed(ActionEvent a_event)
	{
		m_runningString += a_event.getActionCommand();
		updateText();
	}
	
	public void deleteActionPerformed(ActionEvent a_event)
	{
		if (a_event.getActionCommand().equals("Clr"))
		{
			setDefaultValues();
			updateText();
		}
		else if (a_event.getActionCommand().equals("CE"))
		{
			m_runningString = "";
		}
		else if (!m_runningString.equals(""))
		{
			m_runningString = m_runningString.substring(0, m_runningString.length()-1);
		}
		
		updateText();
	}
	
	private void editMatrixArrowAction(String a_direction)
	{
		if (a_direction.equals("Down"))
		{
			if (m_currentRow >= m_selectedMatrix.getRows()) 
			{
				m_runningString = "Done";
				return;
			}
			
			m_currentRow++;
			
			if (m_currentRow == m_selectedMatrix.getRows())
			{
				m_runningString = "Done";
				m_matrixText += " <--";
			}
		}
		else if (a_direction.equals("Up"))
		{
			if (m_currentRow > 0) m_currentRow--;
			
			if (m_matrixText.substring(m_matrixText.length() - 3, m_matrixText.length()).equals("<--"))
			{
				m_runningString = "";
				m_matrixText = m_matrixText.substring(0, m_matrixText.length() - 3);
			}
		}
		else if (a_direction.equals("Right"))
		{
			if (m_currentColumn < m_selectedMatrix.getColumns() - 1) m_currentColumn++;
		}
		else if (a_direction.equals("Left"))
		{
			if (m_currentColumn > 0) m_currentColumn--;
		}
	}
	
	private void deleteMatrixArrowAction(String a_direction) 
	{
		if (a_direction.equals("Down"))
		{
			m_arrowPointer = 2;
		}
		else if (a_direction.equals("Up"))
		{
			m_arrowPointer = 1;
		}
	}
	
	public void arrowActionPerformed(ActionEvent a_event)
	{
		KeyEvent ke = (KeyEvent) EventQueue.getCurrentEvent();
        String direction = KeyEvent.getKeyText( ke.getKeyCode() );
     
        m_runningString = "";
        
        if (getMode() == EDIT_MATRIX)
        {
        	editMatrixArrowAction(direction);
    		updateText();
        }
        
        if (getMode() == DELETE_MENU)
        {
        	deleteMatrixArrowAction(direction);
        	updateText();
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

		//setMode(MATRIX_MENU);
		updateText();
	}

	public void letterActionPerformed(ActionEvent a_event)
	{
		m_runningString += a_event.getActionCommand();
		updateText();
	}

	public void operatorActionPerformed(ActionEvent a_event) 
	{
		//In the case that we are using the "/" operator to indicate a fraction:
		if (getMode() == EDIT_MATRIX)
		{
			m_runningString += a_event.getActionCommand();
			updateText();
			return;
		}
		
		//Otherwise we are using the generic meaning of the operator:
		m_operation = a_event.getActionCommand();
		doOperation();
	}

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
			doOperation();
		}
		
	}
	
}