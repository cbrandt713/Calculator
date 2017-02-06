package calculator;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

public class MatrixTextPane extends JTextPane
{
	//Acceptable Modes for Text Input:
	private final static int CREATE_ROWS = 0;
	private final static int CREATE_COLUMNS = 1;
	private final static int DRAW_MATRIX = 2;
	private final static int EDIT_MATRIX = 3;
	
	//Matrix creation properties:
	private int m_rows;
	private int m_columns;
	
	//Matrix creation input:
	private int m_numsInput;
	private String m_runningString;
	
	//Matrix Helper Strings:
	private String m_blankMatrixText;
	
	//Data objects:
	private Matrix[] m_matrices;
	private int m_amtMatrices;
	private int m_currentRow;
	private int m_currentColumn;
	
	//Various:
	private EnterAction enterAction;
	private NumberAction numberAction;
	private int m_mode;
	
	public MatrixTextPane()
	{
		super();
		m_rows = 0;
		m_columns = 0;
		m_numsInput = 0;
		m_runningString = "";
		m_mode = -1;
		
		m_matrices = new Matrix[20];
		m_amtMatrices = 0;
		m_currentRow = 0;
		m_currentColumn = 0;
		
		enterAction = new EnterAction("", "");
		numberAction = new NumberAction("", "");
		
		for (Integer i = 0; i <= 9; i++)
		{
			getInputMap().put(KeyStroke.getKeyStroke(i.toString()), "number");
			getInputMap().put(KeyStroke.getKeyStroke("NUMPAD" + i.toString()), "number");
		} 
		
		getInputMap().put(KeyStroke.getKeyStroke('.'), "number");
		
		getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
		getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "enter");
		
		
		getActionMap().put("enter", enterAction);
		getActionMap().put("number", numberAction);
		
	}
	
	/*
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int i = 0; i < m_rows; i++)
		{
			for (int j = 0; j < m_columns; j++)
			{
				g.drawRect((j * 30) + 10, (i *30) + 30, 20, 20);
			}
		}
		
	}
	*/
	
	private void setMode(int a_mode)
	{
		m_mode = a_mode;
	}
	
	private int getMode()
	{
		return m_mode;
	}
	
	public void createMatrix()
	{
		setMode(CREATE_ROWS);
		setText("Matrix Size: _ rows X _ columns");
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
	
	
	public void updateText()
	{
		if (getMode() == CREATE_ROWS)
		{
			setText("Matrix Size: " + m_runningString + " rows x _ columns");
		}
		else if (getMode() == CREATE_COLUMNS)
		{
			setText("Matrix Size: " + ((Integer)m_rows).toString() + " rows x " + m_runningString + " columns");
		}
		else if (getMode() == DRAW_MATRIX)
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
					if (j != m_columns - 1) m_runningString += "|"; 
				}
				//End the row with a closing brace:
				m_runningString += " ]";
			}
			
			setText(m_runningString);
			m_blankMatrixText = getText();
			m_runningString = "";
		}
		else if (getMode() == EDIT_MATRIX)
		{
			
			int loc = m_blankMatrixText.indexOf('[');
			setText(m_blankMatrixText.substring(0, loc + 1) + m_runningString + m_blankMatrixText.substring(loc + 1));
		}
	}
	
	private void enterKeyPress()
	{
		//Only take acceptable numbers:
		if (tryParse(m_runningString))
		{
			//Typing in the rows section:
			if (getMode() == CREATE_ROWS)
			{
				m_rows = Integer.parseInt(m_runningString);
				System.out.println("Input received: " + m_rows + " rows");
				setMode(CREATE_COLUMNS);
				m_numsInput = 0;
				m_runningString = "";
			}
			//Typing in the columns section:
			else if (getMode() == CREATE_COLUMNS)
			{
				m_columns = Integer.parseInt(m_runningString);
				System.out.println("Input received: " + m_columns + " columns");
				
				m_numsInput = 0;
				m_runningString = "";
				drawMatrix();
			}
			//Typing inside the matrix cells:
			else if (getMode() == EDIT_MATRIX)
			{
				m_matrices[m_amtMatrices - 1].setCell(m_currentRow, m_currentColumn, Double.parseDouble(m_runningString));
				if (m_currentColumn < m_columns)
				{
					m_currentColumn++;
				}
				else if (m_currentRow < m_rows)
				{
					m_currentRow++;
					m_currentColumn = 0;
				}
				else
				{
					m_currentRow = 0;
					m_currentColumn = 0;
				}
			}
		}
		
	}
	
	public class EnterAction extends AbstractAction 
	{
		
		public EnterAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			//If the fired event is the Enter key:
			if (a_event.getActionCommand().equals("\n"))
			{	
				enterKeyPress();
			}
			//Backspace: renders strange symbol
			else
			{	
				if (!m_runningString.equals(""))
				{
					m_runningString = m_runningString.substring(0, m_runningString.length()-1);
					m_numsInput--;
					updateText();
				}			
			}
		}
		
	}
	
	public class NumberAction extends AbstractAction
	{

		public NumberAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			m_numsInput++;
			m_runningString += a_event.getActionCommand();
			updateText();
		}
		
	}
	
	
}
