package calculator;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
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
	private final static int SHOW_MATRICES = 5;
	
	//Matrix creation properties:
	private int m_rows;
	private int m_columns;
	
	//Matrix creation input:
	private String m_runningString;
	
	//Matrix Helper Strings:
	private String m_matrixText;
	private Character m_defaultName = 'A';
	
	//Data objects:
	private Matrix[] m_matrices;
	private int m_amtMatrices;
	private int m_currentRow;
	private int m_currentColumn;
	
	//Various:
	private int m_mode;
	private EventQueue queue;
	
	private int m_arrowPointer;
	
	public MatrixTextPane()
	{
		super();
		m_rows = 0;
		m_columns = 0;
		m_runningString = "";
		m_arrowPointer = 1;
		m_mode = NONE;
		
		m_matrices = new Matrix[20];
		m_amtMatrices = 0;
		m_currentRow = 0;
		m_currentColumn = 0;
		
		registerKeybinds();
		
		DefaultCaret caret = (DefaultCaret) getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
	}
	
	private void registerKeybinds()
	{
		
		
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
	
	public void showMatrices()
	{
		setMode(SHOW_MATRICES);
		setText("");
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
			setText("Matrix Size: " + m_runningString + " rows X _ columns");
		}
		else if (getMode() == CREATE_COLUMNS)
		{
			setText("Matrix Size: " + ((Integer)m_rows).toString() + " rows X " + m_runningString + " columns");
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
					if (j != m_columns - 1) m_runningString += "| "; 
				}
				//End the row with a closing brace:
				m_runningString += " ]";
			}
			
			setText(m_runningString);
			m_matrixText = getText();
			m_runningString = "";
		}
		else if (getMode() == EDIT_MATRIX)
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
		}
		else if (getMode() == NAME_MATRIX)
		{
			setText("Name Matrix (or \"Enter\" to skip):\n" + m_runningString + "\n" + getText().substring(getText().indexOf('[')));
		}
	}
	
	public void numberActionPerformed(ActionEvent a_event)
	{
		m_runningString += a_event.getActionCommand();
		updateText();
	}
	
	public void deleteActionPerformed(ActionEvent a_event)
	{
		if (a_event.getActionCommand().equals("Clr") || a_event.getActionCommand().equals("CE"))
		{
			m_runningString = "";
		}		
		else
		{
			m_runningString = m_runningString.substring(0, m_runningString.length()-1);
		}
		
		updateText();
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
				if (!tryDoubleParse(m_runningString))
				{
					break;
				}
				m_matrices[m_amtMatrices - 1].setCell(m_currentRow, m_currentColumn, Double.parseDouble(m_runningString));
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
			}
			
			
		}
		
	}
	
	public void arrowActionPerformed(ActionEvent a_event)
	{
		KeyEvent ke = (KeyEvent) queue.getCurrentEvent();
        String direction = ke.getKeyText( ke.getKeyCode() );
     
		if (getMode() != SHOW_MATRICES)
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
		showMatrices();
	}
	
	public void letterActionPerformed(ActionEvent a_event)
	{
		m_runningString += a_event.getActionCommand();
		updateText();
	}
	
}
