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
	//Matrix creation properties:
	private int m_rows;
	private int m_columns;
	
	//Matrix creation input:
	private int m_numsInput;
	private String m_runningString;
	
	//Various:
	private EnterAction enterAction;
	private NumberAction numberAction;
	private String m_mode;
	private Graphics graphics;
	
	public MatrixTextPane()
	{
		super();
		m_rows = 0;
		m_columns = 0;
		m_numsInput = 0;
		m_runningString = "";
		m_mode = "None";
		graphics = getGraphics();
		
		enterAction = new EnterAction("", "");
		numberAction = new NumberAction("", "");
		
		for (Integer i = 0; i <= 9; i++)
		{
			getInputMap().put(KeyStroke.getKeyStroke(i.toString()), "number");
			getInputMap().put(KeyStroke.getKeyStroke("NUMPAD" + i.toString()), "number");
		} 
		
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
	
	private void setMode(String a_mode)
	{
		m_mode = a_mode;
	}
	
	public void createMatrix()
	{
		setText("Matrix Size: _ rows X _ columns");
		setMode("Create Rows");
	}
	
	private void editMatrix()
	{
		setMode("Draw Matrix");
		updateText();
	}
	
	private void updateGraphics()
	{
		graphics = getGraphics();
	}
	
	private void repaintTextArea()
	{
		repaint();
	}
	
	public void updateText()
	{
		if (m_mode.equals("Create Rows"))
		{
			setText("Matrix Size: " + m_runningString + " rows x _ columns");
		}
		else if (m_mode.equals("Create Columns"))
		{
			setText("Matrix Size: " + ((Integer)m_rows).toString() + " rows x " + m_runningString + " columns");
		}
		else if (m_mode.equals("Draw Matrix"))
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
			m_runningString = "";
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
			System.out.println(a_event.getActionCommand());
			//If the fired event is the Enter key:
			if (a_event.getActionCommand().equals("\n"))
			{	
				//Only take acceptable numbers:
				if (tryParse(m_runningString))
				{
					//Typing in the rows section:
					if (m_mode.equals("Create Rows"))
					{
						m_rows = Integer.parseInt(m_runningString);
						System.out.println("Input received: " + m_rows + " rows");
						setMode("Create Columns");
						m_numsInput = 0;
						m_runningString = "";
					}
					//Typing in the columns section:
					else if (m_mode.equals("Create Columns"))
					{
						m_columns = Integer.parseInt(m_runningString);
						System.out.println("Input received: " + m_columns + " columns");
						
						m_numsInput = 0;
						m_runningString = "";
						editMatrix();
						//updateGraphics();
						//repaintTextArea();
					}
					
					m_numsInput = 0;
					m_runningString = "";
				}
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
		
		public boolean tryParse(String a_input)
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
