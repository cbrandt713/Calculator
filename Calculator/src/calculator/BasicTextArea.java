package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class BasicTextArea extends JTextArea {
	
	public BasicTextArea()
	{
		super();
	}
	
	public BasicTextArea(String a_input)
	{
		super(a_input);
		
		
	}
	
	private void registerKeybinds()
	{
		numberAction = new NumberAction("0", "Insert number");
		deleteAction = new DeleteAction("Clr", "Delete number");
		operatorAction = new OperatorAction("+", "Add Numbers");
		miscOperatorAction = new MiscOperatorAction("1/x", "Reciprocal");
		
		getActionMap().put("number", numberAction);
		getActionMap().put("delete", deleteAction);
		getActionMap().put("operation", operatorAction);
		
		for (Integer i = 0; i <= 9; i++)
		{
			getInputMap().put(KeyStroke.getKeyStroke(i.toString()), "number");
			getInputMap().put(KeyStroke.getKeyStroke("NUMPAD" + i.toString()), "number");
		} 
		
		getInputMap().put(KeyStroke.getKeyStroke('.'), "number");
		getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "delete");
		getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "delete");
		getInputMap().put(KeyStroke.getKeyStroke('+'), "operation");
		getInputMap().put(KeyStroke.getKeyStroke('-'), "operation");
		getInputMap().put(KeyStroke.getKeyStroke('*'), "operation");
		getInputMap().put(KeyStroke.getKeyStroke('/'), "operation");
		getInputMap().put(KeyStroke.getKeyStroke('='), "operation");
		getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "operation");
	}
	
	public class NumberAction extends AbstractAction
	{
		public NumberAction(String name, String shortDescription)
		{
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println(e.getActionCommand());
			
			//Possible error: If user uses an operation before a number
			if (m_firstInputFlag)
			{
				changeDisplay("Clear", EXPRESSION);
				m_firstInputFlag = false;
			}
			
			changeDisplay(e.getActionCommand(), INPUT);
			
		}
		
	}
	
	public class DeleteAction extends AbstractAction
	{
		
		public DeleteAction(String name, String shortDescription)
		{
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String keyStroke = "";
			
			if (e.getSource() instanceof JTextArea)
			{
			 	KeyEvent ke = (KeyEvent)queue.getCurrentEvent();
		        keyStroke = ke.getKeyText( ke.getKeyCode() );
		        System.out.println(keyStroke);
			}
				
			System.out.println(e.getActionCommand());
			//Clear:
			if (e.getActionCommand().equals("Clr") || keyStroke.equals("Delete"))
			{
				changeDisplay("Clear", EXPRESSION);
				changeDisplay("Clear", INPUT);
				changeDisplay("0", INPUT);
				m_input = 0;
				m_total = 0;
				m_typeOverFlag = true;
			}
			//Clear Entry:
			else if (e.getActionCommand().equals("CE"))
			{
				changeDisplay("Clear", INPUT);
				changeDisplay("0", INPUT);
				m_input = 0;
				m_typeOverFlag = true;
			}
			//Backspace:
			else
			{
				changeDisplay("Backspace", INPUT);
			}
			
		}
	}
	
	public class OperatorAction extends AbstractAction
	{
		public OperatorAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			//Find the given operator:
			String operator = a_event.getActionCommand();
			
			//Enter key is equivalent to the = operator.
			if (operator.equals("\n")) operator = "=";
			
			calculator.pushOperator(operator);
			System.out.println("Operator: " + operator);
			
			//Get user input and send to calculator:
			String userInput = getUserInput();
			m_input = Double.parseDouble(userInput);
			calculator.pushOperand(m_input);
			
			//Show the input and operator on the expression line
			changeDisplay(userInput + " " + operator + " ", EXPRESSION);
			changeDisplay("Clear", INPUT);
			
			m_total = calculator.doCalculation();
			
			//Format and display the total to the user:
			String formattedTotal = formatDouble(m_total);
			changeDisplay(formattedTotal, INPUT);
			
			m_typeOverFlag = true;
			
		}
		
	}
	
}
