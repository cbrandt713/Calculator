package calculator;

import calculator.MatrixTextPane.OperationArguments;

public class MatrixGUIModel 
{
	public MatrixGUIModel()
	{
		
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
			matrixMenu();
			return;
		}
		
		//Differentiate between unary (one operand) and binary (two operands) operations.
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
		
		//Set the operation to the user's selected operator
		calculator.setOperation(m_operation);		
		
		//Set the text appropriately for the operation:
		if (m_operation.equals("Scalar") && m_amtSelected == 1) getScalar();
		else setTextForOperation();
		
		//Check amount of operands specified.
		//If it's the wrong amount, do not proceed.
		if (m_amtOperands == OperationArguments.BINARY && m_amtSelected < 2)
		{
			return;
		}
		
		if (m_amtOperands == OperationArguments.UNARY && m_amtSelected != 1)
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
			}
			else
			{
				//The operation was successful and the answer is a number/fraction.
				//So there are no matrices selected and no chaining is allowed.
				m_answerFraction = new Fraction(calculator.fractionResultOperation());
				m_answerMatrix = null;
				m_selectedMatrix = null;
				m_amtSelected = 0;
			}
			
			//Reset the operation and finally display the result.
			m_operation = "";
			displayResult();
			
		}
		//If the operation failed, catch the exception and display it.
		catch (MatrixException exception) 
		{
			setMode(EXCEPTION);
			displayException(exception);
		}
		
		//By default, answer is matrix is true (most operations are), so reset it.
		m_answerIsMatrix = true;
		
	}
	
}
