package calculator;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

//Increase gridx = move right
//Increase gridy = move down
//Starts top left corner (0,0)
public class GUI extends JPanel
{
	//Display Line Number Constants:
	public static final int EXPRESSION = 0;
	public static final int INPUT = 1;
	
	//Calculator and Required Data:
	private Calculator calculator;
	private double m_input;
	private double m_total;
	
	//GUI Data Objects:
	private static GUI GUIObject;
	private boolean m_typeOverFlag;
	private boolean m_firstInputFlag;
	private EventQueue queue;
	private Dimension basicSize;
	private Dimension matrixSize;
	
	//GUI Elements:
	private JTextArea display;
	private JFrame frame;
	private MatrixTextPane matrixDisplay;
	private JMenu menu;
	private JMenuBar menuBar;
	private JMenuItem basic;
	private JMenuItem matrix;
	private JScrollPane scrollPane;
	
	//Number Buttons:
	private JButton numbers[];
	private JButton decimal;
	
	//Delete Buttons:
	private JButton backspace;
	private JButton clear;
	private JButton clearEntry;
	
	//Operator Buttons:
	private JButton addition;
	private JButton subtraction;
	private JButton multiplication;
	private JButton division;
	private JButton equals;
	
	//Misc Operator Buttons:
	private JButton plusMinus;
	private JButton reciprocal;
	private JButton squareRoot;
	private JButton percent;
	
	//Matrix Buttons:
	private JButton createMatrix;

	//Action Elements:
	private NumberAction numberAction;
	private DeleteAction deleteAction;
	private OperatorAction operatorAction;
	private MiscOperatorAction miscOperatorAction;
	private MenuItemAction menuItemAction;
	private MatrixAction matrixAction;
	
	private GUI()
	{
		//Call JPanel constructor
		super();
		
		//Create the menu:
		createMenu();
		
		frame = new JFrame("Calculator");
		
		//Create elements of the layout
		setMatrixLayout();
		
		frame.getContentPane().add(this);
		frame.setJMenuBar(getJMenuBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // Size the frame.
        frame.pack();
        //basicSize = frame.getSize();
        //System.out.println(basicSize);
        
        // Show the frame
        frame.setVisible(true);
		
		//Create calculator
		calculator = Calculator.getCalculatorInstance();
		m_input = 0;
		m_total = 0;
		m_typeOverFlag = true;
		m_firstInputFlag = true;
		
		queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		
	}
	
	public static GUI getGUIInstance()
	{
		if (GUIObject == null)
		{
			GUIObject = new GUI();
		}
		
		return GUIObject;
	}
	
	private void setBasicLayout()
	{	
		//Set Layout of the body
		setLayout(new GridBagLayout());
		
		//Create elements of the layout:
		createBasicDisplay();
		createNumbers();
		createDeleteButtons();
		createOperators();
		createMiscOperators();
		
	}
	
	private void setMatrixLayout()
	{
		
		//Set Layout of the body
		setLayout(new GridBagLayout());
		
		//Create elements of the layout:
		//frame.setSize(400, 300);
		createMatrixDisplay();
		createNumbers();
		createDeleteButtons();
		createOperators();
		createMatrixButtons();
		//createMiscOperators();
	}
	
	private void createMenu()
	{
		menuBar = new JMenuBar();
		menu = new JMenu("View");
		
		menuBar.add(menu);
		
		menuItemAction = new MenuItemAction("Basic", "Show Basic Calculator");
		basic = new JMenuItem(menuItemAction);
		menu.add(basic);
		
		menuItemAction = new MenuItemAction("Matrix", "Show Matrix Calculator");
		matrix = new JMenuItem(menuItemAction);
		menu.add(matrix);
	}
	
	public JMenuBar getJMenuBar()
	{
		return menuBar;
	}
	
	private void createBasicDisplay()
	{
		display = new JTextArea("\n0");
		display.setRows(2);
		display.setEditable(false);
		
		numberAction = new NumberAction("0", "Insert number");
		deleteAction = new DeleteAction("Clr", "Delete number");
		operatorAction = new OperatorAction("+", "Add Numbers");
		miscOperatorAction = new MiscOperatorAction("1/x", "Reciprocal");
		
		display.getActionMap().put("number", numberAction);
		display.getActionMap().put("delete", deleteAction);
		display.getActionMap().put("operation", operatorAction);
		display.getActionMap().put("miscOperation", miscOperatorAction);
		
		for (Integer i = 0; i <= 9; i++)
		{
			display.getInputMap().put(KeyStroke.getKeyStroke(i.toString()), "number");
			display.getInputMap().put(KeyStroke.getKeyStroke("NUMPAD" + i.toString()), "number");
		} 
		
		display.getInputMap().put(KeyStroke.getKeyStroke('.'), "number");
		display.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "delete");
		display.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "delete");
		display.getInputMap().put(KeyStroke.getKeyStroke('+'), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke('-'), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke('*'), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke('/'), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke('='), "operation");
		display.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "operation");
		
		GridBagConstraints displayC = new GridBagConstraints();
		
		displayC.gridx = 0;
		displayC.gridy = 0;
		displayC.gridwidth = 5;
		displayC.weighty = 1.0;
		displayC.weightx = 1.0;
		displayC.fill = GridBagConstraints.BOTH;
		
		add(display, displayC);
	}
	
	private void createMatrixDisplay()
	{
		matrixDisplay = new MatrixTextPane();
		matrixDisplay.setText("\n\n\n\n");
		matrixDisplay.setEditable(false);
		scrollPane = new JScrollPane(matrixDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		GridBagConstraints displayC = new GridBagConstraints();
		displayC.gridx = 0;
		displayC.gridy = 0;
		displayC.gridwidth = 5;
		displayC.weightx = 1.0;
		displayC.weighty = 1.0;
		displayC.fill = GridBagConstraints.BOTH;
		
		add(scrollPane, displayC);
	}
	
	private void createNumbers()
	{
		numbers = new JButton[10];
		
		GridBagConstraints numC = new GridBagConstraints();
		
		for (Integer i = 0; i <= 9; i++)
		{
			if (i == 0)
			{
				numC.gridwidth = 2;
				numC.weightx = 1;
				numC.fill = GridBagConstraints.HORIZONTAL;
			}
			else 
			{
				numC.gridwidth = 1;
				numC.weightx = 0.5;
				numC.fill = GridBagConstraints.HORIZONTAL;
			}
			
			numberAction = new NumberAction(i.toString(), "Insert number");
			numbers[i] = new JButton(numberAction);
			numbers[i].setFocusable(false);
			
			numC.gridy = ((9 - i) / 3) + 2;
			
			if (i == 0) numC.gridx = 0;
			else numC.gridx = (i % 3) - 1;
			
			add(numbers[i], numC);
		}
		
		numberAction = new NumberAction(".", "Decimal");
		decimal = new JButton(numberAction);
		decimal.setFocusable(false);
		
		GridBagConstraints decC = new GridBagConstraints();
		decC.gridx = 2;
		decC.gridy = 5;
		decC.weightx = 0.5;
		decC.fill = GridBagConstraints.HORIZONTAL;
		
		add(decimal, decC);	
	}
	
	private void createDeleteButtons()
	{
		deleteAction = new DeleteAction("←", "Delete number");
		backspace = new JButton(deleteAction);
		backspace.setFocusable(false);
	
		GridBagConstraints backspaceC = new GridBagConstraints();
		backspaceC.gridx = 0;
		backspaceC.gridy = 1;
		backspaceC.weightx = 0.5;
		
		add(backspace, backspaceC);
		
		deleteAction = new DeleteAction("Clr", "Clear All Input");
		clear = new JButton(deleteAction);
		clear.setFocusable(false);
		
		GridBagConstraints clearC = new GridBagConstraints();
		clearC.gridx = 2;
		clearC.gridy = 1;
		clearC.weightx = 0.5;
		
		add(clear, clearC);
		
		deleteAction = new DeleteAction("CE", "Clear Entry");
		clearEntry = new JButton(deleteAction);
		clearEntry.setFocusable(false);
		
		clearC.gridx = 1;
		
		add(clearEntry, clearC);
	}
	
	private void createOperators()
	{
		operatorAction = new OperatorAction("+", "Add numbers");
		addition = new JButton(operatorAction);
		addition.setFocusable(false);
		
		GridBagConstraints operatorC = new GridBagConstraints();
		
		operatorC.gridx = 3;
		operatorC.gridy = 5;
		operatorC.weightx = 0.5;
		operatorC.fill = GridBagConstraints.BOTH;
		
		add(addition, operatorC);
		
		operatorAction = new OperatorAction("-", "Subtract numbers");
		subtraction = new JButton(operatorAction);
		subtraction.setFocusable(false);
		
		operatorC.gridy = 4;
		
		add(subtraction, operatorC);

		operatorAction = new OperatorAction("*", "Multiply numbers");
		multiplication = new JButton(operatorAction);
		multiplication.setFocusable(false);
		
		operatorC.gridy = 3;
		
		add(multiplication, operatorC);
		
		operatorAction = new OperatorAction("/", "Divide numbers");
		division = new JButton(operatorAction);
		division.setFocusable(false);
		
		operatorC.gridy = 2;
		
		add(division, operatorC);
		
		operatorAction = new OperatorAction("=", "Find Total");
		equals = new JButton(operatorAction);
		equals.setFocusable(false);
		
		operatorC.gridy = 4;
		operatorC.gridx = 4;
		operatorC.gridheight = 2;
		operatorC.fill = GridBagConstraints.BOTH;
		operatorC.weighty = 1.0;
		operatorC.weightx = 1.0;
		
		add(equals, operatorC);
	}
	
	private void createMiscOperators()
	{
		GridBagConstraints operatorC = new GridBagConstraints();
		
		miscOperatorAction = new MiscOperatorAction("±", "Make number opposite");
		plusMinus = new JButton(miscOperatorAction);
		plusMinus.setFocusable(false);
		
		operatorC.gridx = 3;
		operatorC.gridy = 1;
		operatorC.weightx = 0.5;
		operatorC.weighty = 0.5;
		operatorC.fill = GridBagConstraints.BOTH;
		
		add(plusMinus, operatorC);
		
		miscOperatorAction = new MiscOperatorAction("1/x", "Reciprocal");
		reciprocal = new JButton(miscOperatorAction);
		reciprocal.setFocusable(false);
		
		operatorC.gridx = 4;
		operatorC.gridy = 3;
		
		add(reciprocal, operatorC);
		
		miscOperatorAction = new MiscOperatorAction("%", "Calculate percentages");
		percent = new JButton(miscOperatorAction);
		percent.setFocusable(false);
		
		operatorC.gridy = 2;
		
		add(percent, operatorC);
		
		miscOperatorAction = new MiscOperatorAction("√", "Square Root");
		squareRoot = new JButton(miscOperatorAction);
		squareRoot.setFocusable(false);
		
		operatorC.gridy = 1;
		
		add(squareRoot, operatorC);
	}
	
	private void createMatrixButtons()
	{
		matrixAction = new MatrixAction("Create", "Create new Matrix");
		createMatrix = new JButton(matrixAction);
		createMatrix.setFocusable(false);
		
		GridBagConstraints buttonC = new GridBagConstraints();
		
		buttonC.gridx = 4;
		buttonC.gridy = 1;
		
		add(createMatrix, buttonC);
		
	}
		
	private String getUserInput()
	{
		String displayText = display.getText();
		int newLine = displayText.indexOf('\n');
		
		System.out.println("User input: " + displayText.substring(newLine + 1));
		return displayText.substring(newLine + 1);
	}
	
	private void changeDisplay(double expression, int lineNum)
	{
		String exp = ((Double) expression).toString();
		
		changeDisplay(exp, lineNum);
	}
	
	private void changeDisplay(int expression, int lineNum)
	{
		String exp = ((Integer) expression).toString();
		
		changeDisplay(exp, lineNum);
	}
	
	private void changeDisplay(String message, int lineNum)
	{
		int newLineChar = 0;
		
		try 
		{
			newLineChar = display.getLineEndOffset(0);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
		
		
		if (lineNum == EXPRESSION)
		{
			changeExpressionLine(message, newLineChar);
		
		}
		else
		{
			changeInputLine(message, newLineChar);
		}
		
	}
	
	private void changeExpressionLine(String a_message, int a_newLineChar)
	{
		if (a_message.equals("Clear"))
		{
			display.setText(display.getText().substring(a_newLineChar - 1));
		}
		else
		{
			display.insert(a_message, a_newLineChar - 1);
		}	
	}
	
	private void changeInputLine(String a_message, int a_newLineChar)
	{
		//Clear the Input Line:
		if (a_message.equals("Clear"))
		{
			display.setText(display.getText().substring(0, a_newLineChar));
		}
		
		//Backspace a character:
		else if (a_message.equals("Backspace"))
		{
			//If the text on the input line is not blank, delete the last character
			if (!display.getText().substring(a_newLineChar).equals(""))
			{
				display.setText(display.getText().substring(0, display.getText().length() - 1));
			}
			
			//If the text is now blank, place a 0 on the input line and set the typeOverFlag
			if (display.getText().substring(a_newLineChar).equals(""))
			{
				display.setText(display.getText() + "0");
				m_typeOverFlag = true;
			}	
		}
		
		//Type over the text on the input line:
		else if (m_typeOverFlag)
		{
			display.setText(display.getText().substring(0, a_newLineChar));
			display.insert(a_message, a_newLineChar);
			m_typeOverFlag = false;
		}
		
		//If no special case, just append a character to the end of the input line:
		else 
		{
			int length = display.getText().length(); 
			display.insert(a_message, length);
		}
	}
	
	private String formatDouble(double a_result)
	{
		String res = ((Double) a_result).toString();
		String s = res.indexOf(".") < 0 ? res : res.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
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
	
	public class MiscOperatorAction extends AbstractAction
	{
		public MiscOperatorAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String operator = e.getActionCommand();
			System.out.println("Operator: " + operator);
			
			String formattedTotal = "";
			
			String userInput = getUserInput();
			m_input = Double.parseDouble(userInput);
			String formattedInput = formatDouble(m_input);
			
			changeDisplay("Clear", INPUT);
			
			switch (operator)
			{
				case "±":
				{
					
					m_total = calculator.multiply(m_input, -1);
					break;
				}
				case "1/x":
				{
					changeDisplay("Clear", EXPRESSION);
					changeDisplay("reciprocal(" + formattedInput + ")", EXPRESSION);
					m_total = calculator.divide(1, m_input);
					break;
				}
				case "%":
				{
					double result = calculator.percent(m_total, m_input);
					formattedTotal = formatDouble(result);
					changeDisplay(formattedTotal, EXPRESSION);
					break;
				}
				case "√":
				{
					changeDisplay("Clear", EXPRESSION);
					changeDisplay("sqrt(" + formattedInput + ")", EXPRESSION);
					m_total = calculator.squareRoot(m_input);
					break;
				}
				//Error:
				default:
				{
					System.out.println("An unknown error has occurred");
				}
			}
			
			if (!operator.equals("%"))	formattedTotal = formatDouble(m_total);
	
			changeDisplay(formattedTotal, INPUT);
			m_typeOverFlag = true;
		}
	}
	
	public class MenuItemAction extends AbstractAction
	{
		public MenuItemAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{	
			removeAll();
			
			if (a_event.getActionCommand().equals("Basic"))
			{
				//setVisible(false);
				setBasicLayout();	
			}
			else
			{
				setMatrixLayout();
			}
			
			revalidate();
			repaint();
		}	
	}
	
	public class MatrixAction extends AbstractAction
	{
		public MatrixAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			String operation = a_event.getActionCommand();
			
			switch (operation)
			{
				case "Create":
				{
					matrixDisplay.createMatrix();	
				}
			}
		}
	}
	
}