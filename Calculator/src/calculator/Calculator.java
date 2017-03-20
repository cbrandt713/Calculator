package calculator;

public abstract class Calculator<T> {

	protected T m_input;
	protected T m_input2;
	protected T m_result;
	protected String m_operator;
	
	public void setOperator(String a_operator)
	{
		m_operator = a_operator;
	}
	
	public void setInput(double a_input)
	{
		m_input = a_input;
	}
	
	
	public abstract void resetInputs()
	{
		m_result = null;
		m_input = null;
		m_operator = "";
		m_matrixResult = null;
		m_matrixInput = null;
		m_matrixInput2 = null;
		m_fractionResult = null;
	}
	
	public void setMatrixInput(Matrix a_operand)
	{
		if (m_matrixInput == null) m_matrixInput = a_operand;
		else m_matrixInput2 = a_operand;
	}
	
	public void setScalar(Fraction a_input)
	{
		m_fractionScalar = a_input;
	}
	
	public double doBasicCalculation()
	{
		//If less than two operands, no calculation. Return original value.
		if (m_result == -Double.MAX_VALUE && m_input == -Double.MAX_VALUE)
		{
			return m_input;
		}
		
		switch (m_operator)
		{
			case "+":
			{
				m_result = add(m_result, m_input);
				break;
			}
			case "-":
			{
				m_result = subtract(m_result, m_input);
				break;
			}
			case "*":
			{
				m_result = multiply(m_result, m_input);
				break;
			}
			case "/":
			{
				m_result = divide(m_result, m_input);
				break;
			}	
			case "=":
			{
				m_result = m_input;
				break;
			}
			//Error case:
			default:
			{
				System.out.println("An unknown error has occurred");
				break;
			}
		}
		
		m_operator = "";
		
		return m_result;
	}
	
	public Matrix doMatrixOperation() throws MatrixException
	{
		
		switch (m_operator)
		{
			//Binary operations. Requires two operands.
			case "+":
			case "-":
			case "*":
			case "/":
			{
				m_matrixResult = matrixBinaryOperation();
				break;
			}
			case "=":
			{
				m_matrixResult = m_matrixInput;
				break;
			}
			//Unary operation:
			case "RREF":
			{
				m_matrixResult = RREF(m_matrixInput);
				break;
			}
			case "REF":
			{
				m_matrixResult = REF(m_matrixInput);
				break;
			}
			case "Inverse":
			{
				m_matrixResult = invertMatrix(m_matrixInput);
				break;
			}
			case "Scalar":
			{
				m_matrixResult = scalarMultiply(m_fractionScalar, m_matrixInput);
				break;
			}
			case "Transpose":
			{
				m_matrixResult = transpose(m_matrixInput);
				break;
			}
			case "":
			default:
			{
				System.out.println("An unknown error has occurred.");
				break;
			}
		}
		
		m_matrixInput = m_matrixResult;
		m_matrixInput2 = null;
		
		return m_matrixResult;
	}
	
	public Matrix transpose(Matrix a_matrix) 
	{
		int amtRows = a_matrix.getRows();
		int amtColumns = a_matrix.getColumns();
		
		Matrix transpose = new Matrix(amtColumns, amtRows);
		
		for (int rowIndex = 0; rowIndex < amtColumns; rowIndex++)
		{
			for (int columnIndex = 0; columnIndex < amtRows; columnIndex++)
			{
				Fraction current = new Fraction(a_matrix.getCell(columnIndex, rowIndex));
				transpose.setCell(rowIndex, columnIndex, current);
			}
		}
		
		return transpose;
	}

	public Fraction fractionResultOperation() throws MatrixException
	{
		switch (m_operator)
		{
			case "Det":
			{
				m_fractionResult = determinant(m_matrixInput);
				break;
			}
			case "Trace":
			{
				m_fractionResult = trace(m_matrixInput);
				break;
			}
			case "Rank":
			{
				m_fractionResult = rank(m_matrixInput);
				break;
			}
			default:
			{
				System.out.println("Unhandled Case in fractionResultOperation");
				break;
			}
		}
		
		return m_fractionResult;
	
	}
	
	public Matrix matrixBinaryOperation() throws MatrixException
	{
		if (m_matrixInput == null || m_matrixInput2 == null)
		{
			return new Matrix(0, 0);
		}
		
		if (m_operator.equals("+")) m_matrixResult = addMatrices(m_matrixInput, m_matrixInput2);
		else if (m_operator.equals("-")) m_matrixResult = subtractMatrices(m_matrixInput, m_matrixInput2);
		else if (m_operator.equals("*")) m_matrixResult = multiplyMatrices(m_matrixInput, m_matrixInput2);
		else m_matrixResult = divideMatrices(m_matrixInput, m_matrixInput2);
		
		return m_matrixResult;
	}
	
	

}
