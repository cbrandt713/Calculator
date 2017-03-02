package calculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;


//Increase gridx = move right
//Increase gridy = move down
//Starts top left corner (0,0)
public class GUI extends JPanel
{	
	//GUI State Indicator Constants:
	private final static int BASIC = 0;
	private final static int MATRIX = 1;
	
	//GUI Data Objects:
	private static GUI GUIObject;
	private int m_GUIState;
	private Dimension basicSize;
	private Dimension matrixSize;
	
	//GUI Elements:
	private BasicTextArea m_basicDisplay;
	private JFrame frame;
	private JPanel m_noWrapPanel;
	private MatrixTextPane m_matrixDisplay;
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
	private JButton listMatrices;
	private JButton rref;

	//Action Elements:
	private NumberAction numberAction;
	private EnterAction enterAction;
	private DeleteAction deleteAction;
	private OperatorAction operatorAction;
	private MiscOperatorAction miscOperatorAction;
	private MenuItemAction menuItemAction;
	private MatrixAction matrixAction;
	private LetterAction letterAction;
	private ArrowAction arrowAction;
	
	private GUI()
	{
		//Call JPanel constructor
		super();
		
		//Create the menu:
		createMenu();
		
		//Create elements of the layout
		setMatrixLayout();
		
		//Create Frame to host GUI:
		createFrame();	
	}
	
	public static GUI getGUIInstance()
	{
		if (GUIObject == null)
		{
			GUIObject = new GUI();
		}
		
		return GUIObject;
	}
	
	private void createFrame()
	{
		frame = new JFrame("Calculator");
		
		frame.getContentPane().add(this);
		frame.setJMenuBar(getJMenuBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // Size the frame.
        frame.pack();
        
        // Show the frame
        frame.setVisible(true);
	}
	
	private void setBasicLayout()
	{	
		//Set Layout of the body
		setLayout(new GridBagLayout());
		
		m_GUIState = BASIC;
		
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
		
		m_GUIState = MATRIX;
		
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
	
	public int getGUIState()
	{
		return m_GUIState;
	}
	
	private void createBasicDisplay()
	{
		m_basicDisplay = new BasicTextArea("\n0");
		m_basicDisplay.setRows(2);
		m_basicDisplay.setEditable(false);
				
		createBasicKeybinds();
		
		GridBagConstraints displayC = new GridBagConstraints();
		
		displayC.gridx = 0;
		displayC.gridy = 0;
		displayC.gridwidth = 5;
		displayC.weighty = 1.0;
		displayC.weightx = 1.0;
		displayC.fill = GridBagConstraints.BOTH;
		
		add(m_basicDisplay, displayC);
	}
	
	private void createBasicKeybinds()
	{
		numberAction = new NumberAction("", "");
		deleteAction = new DeleteAction("", "");
		operatorAction = new OperatorAction("", "");
		
		m_basicDisplay.getActionMap().put("number", numberAction);
		m_basicDisplay.getActionMap().put("delete", deleteAction);
		m_basicDisplay.getActionMap().put("operation", operatorAction);
		
		for (Integer i = 0; i <= 9; i++)
		{
			m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke(i.toString()), "number");
			m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke("NUMPAD" + i.toString()), "number");
		} 
		
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke('.'), "number");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "delete");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "delete");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke('+'), "operation");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke('-'), "operation");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke('*'), "operation");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke('/'), "operation");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke('='), "operation");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "operation");
	}
	
	private void createMatrixDisplay()
	{
		m_matrixDisplay = new MatrixTextPane();
		m_matrixDisplay.setText("\n\n\n\n");
		m_matrixDisplay.setEditable(false);
		m_noWrapPanel = new JPanel( new BorderLayout() );
		m_noWrapPanel.add(m_matrixDisplay);
		scrollPane = new JScrollPane(m_noWrapPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		createMatrixKeybinds();
		
		GridBagConstraints displayC = new GridBagConstraints();
		displayC.gridx = 0;
		displayC.gridy = 0;
		displayC.gridwidth = 5;
		displayC.weightx = 1.0;
		displayC.weighty = 1.0;
		displayC.fill = GridBagConstraints.BOTH;
		
		add(scrollPane, displayC);
	}
	
	private void createMatrixKeybinds()
	{
		enterAction = new EnterAction("", "");
		numberAction = new NumberAction("", "");
		deleteAction = new DeleteAction("", "");
		letterAction = new LetterAction("", "");
		arrowAction = new ArrowAction("", "");
		
		//Register all the keybinds with the associated string:
		//Note this syntax is necessary to use keybinds
		for (Integer i = 0; i <= 9; i++)
		{
			m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke(i.toString()), "number");
			m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke("NUMPAD" + i.toString()), "number");
		} 
		
		for (char letter = 'A'; letter <= 'Z'; letter++)
		{
			m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke(letter), "letter");
		}
		
		for (char letter = 'a'; letter <= 'z'; letter++)
		{
			m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke(letter), "letter");
		}
		
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke('-'), "number");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke('.'), "number");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "delete");
		
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "arrow");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "arrow");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "arrow");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "arrow");
		
		//Register the actions with their respective classes:
		m_matrixDisplay.getActionMap().put("enter", enterAction);
		m_matrixDisplay.getActionMap().put("number", numberAction);
		m_matrixDisplay.getActionMap().put("delete", deleteAction);
		m_matrixDisplay.getActionMap().put("letter", letterAction);
		m_matrixDisplay.getActionMap().put("arrow", arrowAction);
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
		
		matrixAction = new MatrixAction("List", "List current matrices");
		listMatrices = new JButton(matrixAction);
		listMatrices.setFocusable(false);
		
		buttonC.gridy = 2;
		buttonC.fill = GridBagConstraints.BOTH;
		
		add(listMatrices, buttonC);
		
		matrixAction = new MatrixAction("RREF", "Row-Reduced Echelon Form of a Matrix");
		rref = new JButton(matrixAction);
		rref.setFocusable(false);
		
		buttonC.gridy = 3;
		
		add(rref, buttonC);
		
	}
	
	public class NumberAction extends AbstractAction
	{
		public NumberAction(String name, String shortDescription)
		{
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			if (getGUIState() == BASIC) m_basicDisplay.numberActionPerformed(a_event);
			else m_matrixDisplay.numberActionPerformed(a_event);
		}
		
	}
	
	public class EnterAction extends AbstractAction
	{
		
		public EnterAction(String name, String shortDescription)
		{
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			if (getGUIState() == BASIC) m_basicDisplay.enterActionPerformed(a_event);
			else m_matrixDisplay.enterActionPerformed(a_event);
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
		public void actionPerformed(ActionEvent a_event) 
		{
			if (getGUIState() == BASIC) m_basicDisplay.deleteActionPerformed(a_event);
			else m_matrixDisplay.deleteActionPerformed(a_event);
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
			if (getGUIState() == BASIC) m_basicDisplay.operatorActionPerformed(a_event);
			else m_matrixDisplay.operatorActionPerformed(a_event);
		}
	}
	
	public class MiscOperatorAction extends AbstractAction
	{
		public MiscOperatorAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == BASIC) m_basicDisplay.miscOperatorActionPerformed(a_event);
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
			m_matrixDisplay.setText("");
			
			switch (operation)
			{
				case "Create":
				{
					m_matrixDisplay.createRows();
					break;
				}
				case "List":
				{
					m_matrixDisplay.selectMatrix();
					break;
				}
				case "RREF":
				{
					m_matrixDisplay.RREFButton();
					break;
				}
			}
		}
	}
	
	
	public class LetterAction extends AbstractAction
	{

		public LetterAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			m_matrixDisplay.letterActionPerformed(a_event);
		}
		
	}
	
	public class ArrowAction extends AbstractAction
	{
		public ArrowAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == MATRIX) m_matrixDisplay.arrowActionPerformed(a_event);
		}
	}
	
}