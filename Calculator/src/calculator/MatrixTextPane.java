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
	private int m_rows;
	private int m_columns;
	private InputAction inputAction;
	private String m_mode;
	
	public MatrixTextPane()
	{
		super();
		m_rows = 0;
		m_columns = 0;
		m_mode = "None";
		
		inputAction = new InputAction("Enter", "Enter Input");
		
		getActionMap().put("input", inputAction);
		getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "input");
		
	}
	
	public MatrixTextPane(int a_rows, int a_columns)
	{
		super();
		m_rows = a_rows;
		m_columns = a_columns;
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int i = 0; i < m_rows; i++)
		{
			for (int j = 0; j < m_columns; j++)
			{
				g.drawRect((j * 30) + 10, (i *30) + 10, 20, 20);
			}
		}
		
	}
	
	public void createMatrix()
	{
		setEditable(true);
		setText("Matrix Size:  rows X  columns");
		setCaretPosition(13);
	}
	
	public void setMode(String a_mode)
	{
		m_mode = a_mode;
	}
	
	public class InputAction extends AbstractAction 
	{
		
		public InputAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			if (a_event.getActionCommand().equals("\n"))
			{
				if (m_mode.equals("Create"))
				{
					
					setCaretPosition(22);
				}	
			}
			
		}
		
	}
	
	
}
