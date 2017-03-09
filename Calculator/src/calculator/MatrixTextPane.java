package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;

public class MatrixTextPane extends JTextPane
{
	//Acceptable Modes for Text Input:
	private final static int NONE = -1;
	private final static int CREATE_ROWS = 0;
	private final static int CREATE_COLUMNS = 1;
	private final static int DRAW_MATRIX = 2;
	private final static int EDIT_MATRIX = 3;
	private final static int NAME_MATRIX = 4;
	private final static int SELECT_MATRIX = 5;
	private final static int RREF = 100;
	private final static int ADD = 101;
	private final static int SUBTRACT = 102;
	private final static int MULTIPLY = 103;
	private final static int DIVIDE = 104;
	private final static int INVERSE = 105;
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
	private String m_runningString;
	private String m_storedString;
	
	//Matrix Helper Strings:
	private String m_matrixText;
	private Character m_defaultName;
	
	//Data objects:
	private Matrix[] m_matrices;
	private Matrix m_selectedMatrix;
	private Matrix m_answerMatrix;
	private int m_amtMatrices;
	private int m_currentRow;
	private int m_currentColumn;
	
	//Various:
	private int m_arrowPointer;
	private MatrixException m_caughtException;
	
	//Calculator and Required Data:
	private Calculator calculator;
	
	public MatrixTextPane()
	{
		
		super();
		
		m_matrices = new Matrix[20];
		m_amtMatrices = 0;
		m_defaultName = 'A';
		
		calculator = Calculator.getCalculatorInstance();
		
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
		
	}
	
	//Helper method used by the constructor to initialize and the
	//"Clear" button to reset the calculator to default settings.
	private void setDefaultValues()
	{
		m_rows = 0;
		m_columns = 0;
		m_runningString = "";
		m_arrowPointer = 1;
		m_mode = NONE;
		m_operation = "";
		m_answerMatrix = null;
		m_selectedMatrix = null;		
		m_currentRow = 0;
		m_currentColumn = 0;
		m_amtSelected = 0;
		m_amtOperands = OperationArguments.UNARY;
		m_storedString = "";
		
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
	
	public void createColumns()
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
		m_matrices[m_amtMatrices++] = new Matrix(m_rows, m_columns);
		updateText();
	}
	
	private void nameMatrix()
	{
		setMode(NAME_MATRIX);
		updateText();
	}
	
	public void selectMatrix()
	{
		setMode(SELECT_MATRIX);
		setText("Select Matrix:\n");
		String line = "";
		for (Integer i = 1; i < m_matrices.length + 1; i++)
		{
			if (m_matrices[i-1] != null)
			{
				line = i.toString() + ". " + m_matrices[i-1].getName();
			}
			else
			{
				line = i.toString() + ".";
			}
			if (m_arrowPointer == i)
			{
				line += " <--\n";
			}
			else
			{
				line += "\n";
			}
			setText(getText() + line);
		}
	}
	
	public void doOperation()
	{
		if (m_selectedMatrix == null) 
		{
			selectMatrix();
			return;
		}
		
		if (m_operation.equals("+") || m_operation.equals("-") 
				|| m_operation.equals("*") || m_operation.equals("/"))
		{
			m_amtOperands = OperationArguments.BINARY;
		}
		else
		{
			m_amtOperands = OperationArguments.UNARY;
		}
			
		calculator.setOperator(m_operation);		
		
		setMode(GENERIC_OPERATION);
		
		updateText();
		
		if (m_amtOperands == OperationArguments.BINARY && m_amtSelected < 2)
		{
			return;
		}
		
		if (m_amtOperands == OperationArguments.UNARY && m_amtSelected != 1)
		{
			return;
		}
		
		try 
		{
			setMode(DISPLAY_RESULT);
			m_answerMatrix = calculator.doMatrixOperation();
			m_answerMatrix.setName("Ans");
			m_selectedMatrix = m_answerMatrix;
			m_amtSelected = 1;
		} 
		catch (MatrixException exception) 
		{
			setMode(EXCEPTION);
			m_caughtException = exception;
		}
		
		updateText();
		
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
	
	private boolean tryDoubleParse(String a_input)
	{
		try
		{
			Double.parseDouble(a_input);
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
				setText("Matrix Size: " + m_runningString + " rows X _ columns");
				break;
			}
			case CREATE_COLUMNS:
			{
				setText("Matrix Size: " + ((Integer)m_rows).toString() + " rows X " + m_runningString + " columns");
				break;
			}
			case DRAW_MATRIX:
			{
				//Start with current display of matrix size
				m_runningString = getText();
				for (int i = 0; i < m_rows; i++)
				{
					//Begin a new row and add an opening brace for each row:
					m_runningString += "\n[ ";
					for (int j = 0; j < m_columns; j++)
					{
						//Add a tab for every column
						m_runningString += "\t";
						if (j != m_columns - 1) m_runningString += "| "; 
					}
					//End the row with a closing brace:
					m_runningString += " ]";
				}
				
				setText(m_runningString);
				m_matrixText = getText();
				m_runningString = "";
				
				break;
			}
			case EDIT_MATRIX:
			{
				int loc = 0;
				for (int i = 0; i < m_currentRow + 1; i++)
				{
					loc = m_matrixText.indexOf('[', loc + 1) + 1;
					for (int j = 0; j < m_currentColumn; j++)
					{
						loc = m_matrixText.indexOf('|', loc) + 1;
					}
				}
				setText(m_matrixText.substring(0, loc + 1) + m_runningString + m_matrixText.substring(loc + 1));
				
				break;
			}
			case NAME_MATRIX:
			{
				setText("Name Matrix (or \"Enter\" to skip):\n" + m_runningString + "\n" + getText().substring(getText().indexOf('[')));
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
			case GENERIC_OPERATION:
			{
				if (m_amtOperands == OperationArguments.UNARY)
				{
					setText(m_operation + "(" + m_selectedMatrix.getName() + ") = \n");
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
			case DISPLAY_RESULT:
			{
				setText(getText() + m_answerMatrix.toString());
				break;
			}
			case EXCEPTION:
			{
				setText(m_caughtException.getMessage() + "\nMatrices: ");
				
				String[] offenders = m_caughtException.getOffendingMatrices();
				
				for (String matrixName : offenders)
				{
					if (matrixName != null)
					setText(getText() + matrixName + " ");
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
					drawMatrix();
				}
				break;
			}
			case EDIT_MATRIX:
			{
				if (!tryFractionParse(m_runningString))
				{
					break;
				}
				m_matrices[m_amtMatrices - 1].setCell(m_currentRow, m_currentColumn, Fraction.parseFraction(m_runningString));
				m_runningString = "";
				
				m_matrixText = getText();
				
				if (m_currentColumn + 1 < m_columns)
				{
					m_currentColumn++;
				}
				else if (m_currentRow + 1< m_rows)
				{
					m_currentRow++;
					m_currentColumn = 0;
				}
				else
				{
					m_currentRow = 0;
					m_currentColumn = 0;
					nameMatrix();
				}
				break;
			}
			case NAME_MATRIX:
			{
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
			{
				m_amtSelected++;
				m_selectedMatrix = m_matrices[m_arrowPointer-1];
				calculator.setMatrixInput(m_selectedMatrix);
				updateText();
				
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
	
	public void arrowActionPerformed(ActionEvent a_event)
	{
		KeyEvent ke = (KeyEvent) EventQueue.getCurrentEvent();
        String direction = KeyEvent.getKeyText( ke.getKeyCode() );
     
		if (getMode() != SELECT_MATRIX)
		{
			return;
		}
		
		if (direction.equals("Down"))
		{
			if (m_arrowPointer < m_amtMatrices) m_arrowPointer++;
		}
		if (direction.equals("Up"))
		{
			if (m_arrowPointer > 1) m_arrowPointer--;
		}
		selectMatrix();
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
		
		if (operation.equals("Create")) createRows();
		else if (operation.equals("List")) selectMatrix();
		else 
		{
			m_operation = operation;
			doOperation();
		}
		
	}
	
}