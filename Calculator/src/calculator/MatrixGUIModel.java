package calculator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MatrixGUIModel implements ActionEventHandler
{
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
	private Character m_defaultName;
	private int m_underlinePos;
	
	//Matrix creation properties:
	private int m_rows;
	private int m_columns;
	private int m_arrowPointer;
	private int m_amtOperands;
	private int m_amtSelected;

	private String m_operation;
	
	private MatrixTextPane m_displayText;
	private MatrixCalculator calculator;
	
	public MatrixGUIModel()
	{
		m_displayText = MatrixTextPane.getInstance();
		m_matrices = new Matrix[20];
		m_amtMatrices = 0;
		m_defaultName = 'A';

		calculator = new MatrixCalculator();
		
		setDefaultValues();
		
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
	
	private void setDefaultValues()
	{
		m_operation = "";
		m_amtSelected = 0;
		m_arrowPointer = 1;
		m_amtOperands = 1;
		m_currentRow = 0;
		m_currentColumn = 0;
		m_rows = 0;
		m_columns = 0;
		m_underlinePos = 0;
		m_answerMatrix = null;
		m_selectedMatrix = null;
		m_answerFraction = null;
		m_answerIsMatrix = true;
		
		
		calculator.resetInputs();
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
	 * Handles all the specific cases where operations differ, then attempts to
	 * do the operation specified by the user, then attempts to display the result or 
	 * the problem with the operation (if applicable).
	 */
	public void doOperation()
	{
		//Make the user choose a matrix to do the operation:
		if (m_selectedMatrix == null) 
		{
			m_displayText.matrixMenu(m_matrices, m_arrowPointer, m_underlinePos);
			return;
		}
		
		//Differentiate between unary (one operand) and binary (two operands) operations.
		if (m_operation.equals("+") || m_operation.equals("-") 
				|| m_operation.equals("*") || m_operation.equals("/")
				|| m_operation.equals("Scalar") )
		{
			m_amtOperands = 2;
		}
		else
		{
			m_amtOperands = 1;
		}
		
		//Set the operation to the user's selected operator
		calculator.setOperation(m_operation);		
		
		//Set the text appropriately for the operation:
		if (m_operation.equals("Scalar") && m_amtSelected == 1) m_displayText.getScalar();
		else if (m_amtOperands == 1) m_displayText.setTextForUnaryOperation(m_operation, m_selectedMatrix);
		else m_displayText.setTextForBinaryOperation(m_operation, m_selectedMatrix, m_amtSelected, m_scalarFraction);
			
		//Check amount of operands specified.
		//If it's the wrong amount, do not proceed.
		if ( (m_amtOperands == 1 && m_amtSelected != 1) || (m_amtOperands == 2 && m_amtSelected != 2) )
		{
			return;
		}
		
		//Attempt to do the actual calculation.
		//If the operation is not possible or failed for some reason, the function will return
		//A MatrixException containing the problem and what matrices caused it.
		try 
		{
			//Handles how the data will be displayed and handled.
			//If the result is a matrix, call the matrix calculation method.
			//If it's not, call the fraction calculation method.
			if (m_answerIsMatrix)
			{
				//The operation was successful and store the answer as a new matrix so as to not
				//Change data in the original matrix, and let it be displayed as "Ans" on the screen.
				m_answerMatrix = new Matrix(calculator.doCalculation());
				m_answerMatrix.setName("Ans");
				
				//These lines allow the user to chain operations, setting the answer as the new
				//Matrix if the user wants to chain operations (for example add 3 matrices).
				m_selectedMatrix = m_answerMatrix;
				m_amtSelected = 1;
				
				//Display the result:
				m_displayText.displayMatrixResult(m_answerMatrix);
			}
			else
			{
				//The operation was successful and the answer is a number/fraction.
				//So there are no matrices selected and no chaining is allowed.
				m_answerFraction = new Fraction(calculator.fractionResultOperation());
				m_answerMatrix = null;
				m_selectedMatrix = null;
				m_amtSelected = 0;
				
				//Display the result:
				m_displayText.displayFractionResult(m_answerFraction);
			}
			
			//Reset the operation
			m_operation = "";
			
		}
		//If the operation failed, catch the exception and display it.
		catch (MatrixException exception) 
		{
			m_displayText.displayException(exception);
		}
		
		//By default, answer is matrix is true (most operations are), so reset it.
		m_answerIsMatrix = true;
		
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
			m_displayText.createColumns();
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
			m_displayText.drawMatrix(m_selectedMatrix);
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
			if (m_underlinePos == 1) m_displayText.setText("Matrix Edited Successfully!");
			else m_displayText.nameMatrix();
			
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
		m_displayText.resetUnderline();
		
		//If nothing was entered, use default naming scheme:
		if (a_enteredText.equals(""))
		{
			a_enteredText = (m_defaultName.toString());
			m_defaultName++;
		}
		//Name matrix:
		m_matrices[m_amtMatrices - 1].setName(a_enteredText);
		
		m_displayText.setText("Matrix Added Successfully!");
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
			
			if (m_operation.equals("")) 
			{
				m_displayText.setText(m_selectedMatrix.getName());
			}
			else 
			{
				doOperation();
			}
		}
		//Edit:
		else if (m_underlinePos == 1)
		{
			m_currentRow = 0;
			m_currentColumn = 0;
			m_displayText.setText("Editing " + m_selectedMatrix.getName() + ":\n");
			m_displayText.drawMatrix(m_selectedMatrix);
		}
		//Delete:
		else if (m_underlinePos == 2)
		{
			m_arrowPointer = 1;
			m_displayText.deleteMenu(m_selectedMatrix, m_arrowPointer);
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
			
			m_displayText.setText("Matrix deleted successfully!");
		}
		else
		{
			m_displayText.matrixMenu(m_matrices, m_arrowPointer, m_underlinePos);
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
			//m_storedString = m_scalarFraction.toString() + " * ";
			doOperation();
		}
	}
	
	/**
	 * The user pressed the "enter" key to input data.
	 *
	 * @param a_event: the event which triggered the call of the function.
	*/
	public void enterActionPerformed(ActionEvent a_event)
	{	
		
		String enteredText = m_displayText.getUserEnteredText();
		
		//Switch based on the mode:
		switch (m_displayText.getMode()) 
		{
			//Typing in the rows section:
			case MatrixTextPane.CREATE_ROWS:
			{	
				createRowsEnterPress(enteredText);
				break;
			}
			//Typing in the columns section:
			case MatrixTextPane.CREATE_COLUMNS:
			{	
				createColumnsEnterPress(enteredText);
				break;
			}
			//Editing a matrix cell:
			case MatrixTextPane.EDIT_MATRIX:
			{	
	        	boolean finished = editMatrixEnterPress(enteredText);
	        	
	        	//If the user pushed enter on "Done", return.
	        	if (finished) return;
	        	
	        	//Move right if user used enter to accept data
	        	editMatrixArrowAction("Right");
	        	
	        	//Since we weren't done, recall the method to edit.
	    		m_displayText.editMatrix(m_currentRow, m_currentColumn, m_selectedMatrix);
	
				break;
			}
			//Naming the matrix:
			case MatrixTextPane.NAME_MATRIX:
			{
				nameMatrixEnterPress(enteredText);
				break;
			}
			//Picking a matrix:
			case MatrixTextPane.MATRIX_MENU:
			{
				matrixMenuEnterPress();
				break;
			}
			//Deleting a matrix:
			case MatrixTextPane.DELETE_MENU:
			{
				deleteMenuEnterPress();
				break;
			}
			//Entering the scalar:
			case MatrixTextPane.SCALAR:
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
			m_displayText.setDefaultValues();
			m_displayText.setText("");
		}
		//Clear entry command:
		else if (a_event.getActionCommand().equals("CE"))
		{
			m_displayText.clearEntry();
		}
		//Backspace command:
		else
		{	
			m_displayText.backspace();
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
		
		String text = m_displayText.getText();
		
		//If we are hovering over done, add an "arrow" to indicate position.
		if (m_currentRow == m_selectedMatrix.getRows() && !text.substring(text.length() - 3, text.length()).equals("<--"))
		{
			m_displayText.append(" <--");
		}
		//If we are not on done but the arrow is still there, remove it.
		else if (m_currentRow != m_selectedMatrix.getRows() && text.substring(text.length() - 3, text.length()).equals("<--"))
		{
			m_displayText.remove(text.length() - 3, 3);
			
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
        
        if (m_displayText.getMode() == MatrixTextPane.EDIT_MATRIX)
        {
    		String enteredText = m_displayText.getUserEnteredText();
    		
        	if (m_currentRow != m_selectedMatrix.getRows()) editMatrixEnterPress(enteredText);
        	editMatrixArrowAction(direction);
        	m_displayText.editMatrix(m_currentRow, m_currentColumn, m_selectedMatrix);
        }
        
        if (m_displayText.getMode() == MatrixTextPane.DELETE_MENU)
        {
        	deleteMatrixArrowAction(direction);
        	m_displayText.deleteMenu(m_selectedMatrix, m_arrowPointer);
        }
        
		if (m_displayText.getMode() != MatrixTextPane.MATRIX_MENU)
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

		m_displayText.matrixMenu(m_matrices, m_arrowPointer, m_underlinePos);
	}

	/**
	 * Number action performed.
	 *
	 * @param a_event the a event
	 */
	public void numberActionPerformed(ActionEvent a_event)
	{
		String text = a_event.getActionCommand();
		
		m_displayText.insertCharacter(text);
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
		if (m_displayText.getMode() == MatrixTextPane.EDIT_MATRIX)
		{
			letterActionPerformed(a_event);
			return;
		}
		
		//Otherwise we are using the generic meaning of the operator:
		m_operation = a_event.getActionCommand();
		doOperation();
	}

	/**
	 * Matrix action performed.
	 *
	 * @param a_event the a event
	 */
	public void matrixActionPerformed(ActionEvent a_event) 
	{
		m_displayText.setText("");
		String operation = a_event.getActionCommand();
		
		if (operation.equals("Det") || operation.equals("Trace") 
				|| operation.equals("Rank")) m_answerIsMatrix = false;
		
		if (operation.equals("Create")) 
		{
			m_displayText.createRows();
		}
		else if (operation.equals("Matrix")) 
		{
			m_displayText.matrixMenu(m_matrices, m_arrowPointer, m_underlinePos);
		}
		else 
		{
			m_operation = operation;
			doOperation();
		}
		
	}
	
	
}
