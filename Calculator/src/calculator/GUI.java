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


// TODO: Auto-generated Javadoc
//Increase gridx = move right
//Increase gridy = move down
/**
 * The Class GUI.
 */
//Starts top left corner (0,0)
@SuppressWarnings("serial")
public class GUI extends JPanel
{	
	
	/** The Constant BASIC. */
	//GUI State Indicator Constants:
	private final static int BASIC = 0;
	
	/** The Constant MATRIX. */
	private final static int MATRIX = 1;
	
	/** The GUI object. */
	//GUI Data Objects:
	private static GUI GUIObject;
	
	/** The m GUI state. */
	private int m_GUIState;
	
	/** The basic size. */
	private Dimension basicSize;
	
	/** The matrix size. */
	private Dimension matrixSize;
	
	/** The frame. */
	//GUI Elements:
	private JFrame frame;
	
	/** The m no wrap panel. */
	private JPanel m_noWrapPanel;
	
	/** The m basic display. */
	private BasicTextArea m_basicDisplay;
	
	/** The m basic model. */
	private BasicGUIModel m_basicModel;
	
	/** The m matrix display. */
	private MatrixTextPane m_matrixDisplay;
	
	/** The m matrix model. */
	private MatrixGUIModel m_matrixModel;
	
	/** The menu. */
	private JMenu menu;
	
	/** The menu bar. */
	private JMenuBar menuBar;
	
	/** The basic. */
	private JMenuItem basic;
	
	/** The matrix. */
	private JMenuItem matrix;
	
	/** The scroll pane. */
	private JScrollPane scrollPane;
	
	/** The numbers. */
	//Number Buttons:
	private JButton numbers[];
	
	/** The decimal. */
	private JButton decimal;
	
	/** The backspace. */
	//Delete Buttons:
	private JButton backspace;
	
	/** The clear. */
	private JButton clear;
	
	/** The clear entry. */
	private JButton clearEntry;
	
	/** The addition. */
	//Operator Buttons:
	private JButton addition;
	
	/** The subtraction. */
	private JButton subtraction;
	
	/** The multiplication. */
	private JButton multiplication;
	
	/** The division. */
	private JButton division;
	
	/** The equals. */
	private JButton equals;
	
	/** The plus minus. */
	//Misc Operator Buttons:
	private JButton plusMinus;
	
	/** The reciprocal. */
	private JButton reciprocal;
	
	/** The square root. */
	private JButton squareRoot;
	
	/** The percent. */
	private JButton percent;
	
	/** The create matrix. */
	//Matrix Buttons:
	private JButton createMatrix;
	
	/** The list matrices. */
	private JButton listMatrices;
	
	/** The rref. */
	private JButton rref;
	
	/** The ref. */
	private JButton ref;
	
	/** The inverse. */
	private JButton inverse;
	
	/** The determinant. */
	private JButton determinant;
	
	/** The rank. */
	private JButton rank;
	
	/** The scalar. */
	private JButton scalar;
	
	/** The transpose. */
	private JButton transpose;

	/** The number action. */
	//Action Elements:
	private NumberAction numberAction;
	
	/** The enter action. */
	private EnterAction enterAction;
	
	/** The delete action. */
	private DeleteAction deleteAction;
	
	/** The operator action. */
	private OperatorAction operatorAction;
	
	/** The misc operator action. */
	private MiscOperatorAction miscOperatorAction;
	
	/** The menu item action. */
	private MenuItemAction menuItemAction;
	
	/** The matrix action. */
	private MatrixAction matrixAction;
	
	/** The letter action. */
	private LetterAction letterAction;
	
	/** The arrow action. */
	private ArrowAction arrowAction;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		getGUIInstance();	
	}
	
	/**
	 * Instantiates a new gui.
	 */
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
		
		///System.out.println(frame.getSize());
		
	}
	
	/**
	 * Gets the GUI instance.
	 *
	 * @return the GUI instance
	 */
	public static GUI getGUIInstance()
	{
		if (GUIObject == null)
		{
			GUIObject = new GUI();
		}
		
		return GUIObject;
	}
	
	/**
	 * Creates the frame.
	 */
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
	
	/**
	 * Sets the basic layout.
	 */
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
		
		basicSize = new Dimension(253, 224);	
	}
	
	/**
	 * Sets the matrix layout.
	 */
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
		
		matrixSize = new Dimension(380, 313);
	}
	
	/**
	 * Creates the menu.
	 */
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
	
	/**
	 * Gets the j menu bar.
	 *
	 * @return the j menu bar
	 */
	private JMenuBar getJMenuBar()
	{
		return menuBar;
	}
	
	/**
	 * Gets the GUI state.
	 *
	 * @return the GUI state
	 */
	private int getGUIState()
	{
		return m_GUIState;
	}
	
	/**
	 * Creates the basic display.
	 */
	private void createBasicDisplay()
	{
		m_basicDisplay = BasicTextArea.getBasicTextAreaInstance();
		m_basicDisplay.setRows(2);
		m_basicDisplay.setText("\n0");
		m_basicDisplay.setEditable(false);
		m_basicModel = new BasicGUIModel();
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
	
	/**
	 * Creates the basic keybinds.
	 */
	private void createBasicKeybinds()
	{
		numberAction = new NumberAction("", "");
		deleteAction = new DeleteAction("", "");
		operatorAction = new OperatorAction("", "");
		enterAction = new EnterAction("", "");
		
		m_basicDisplay.getActionMap().put("number", numberAction);
		m_basicDisplay.getActionMap().put("delete", deleteAction);
		m_basicDisplay.getActionMap().put("operation", operatorAction);
		m_basicDisplay.getActionMap().put("enter", enterAction);
		
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
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke('='), "enter");
		m_basicDisplay.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
	}
	
	/**
	 * Creates the matrix display.
	 */
	private void createMatrixDisplay()
	{
		m_matrixDisplay = MatrixTextPane.getInstance();
		m_matrixDisplay.setText("\n\n\n\n\n\n");
		m_matrixDisplay.setEditable(false);
		m_matrixModel = new MatrixGUIModel();
		m_noWrapPanel = new JPanel( new BorderLayout() );
		m_noWrapPanel.add(m_matrixDisplay);
		scrollPane = new JScrollPane(m_noWrapPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		createMatrixKeybinds();
		
		GridBagConstraints displayC = new GridBagConstraints();
		displayC.gridx = 0;
		displayC.gridy = 0;
		displayC.gridwidth = 6;
		displayC.weightx = 1.0;
		displayC.weighty = 1.0;
		displayC.fill = GridBagConstraints.BOTH;
		
		add(scrollPane, displayC);
	}
	
	/**
	 * Creates the matrix keybinds.
	 */
	private void createMatrixKeybinds()
	{
		enterAction = new EnterAction("", "");
		numberAction = new NumberAction("", "");
		deleteAction = new DeleteAction("", "");
		letterAction = new LetterAction("", "");
		arrowAction = new ArrowAction("", "");
		operatorAction = new OperatorAction("", "");
		
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
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke('+'), "operation");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke('-'), "operation");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke('*'), "operation");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke('/'), "operation");
		m_matrixDisplay.getInputMap().put(KeyStroke.getKeyStroke('='), "operation");
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
		m_matrixDisplay.getActionMap().put("operation", operatorAction);
	}
	
	/**
	 * Creates the numbers.
	 */
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
	
	/**
	 * Creates the delete buttons.
	 */
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
	
	/**
	 * Creates the operators.
	 */
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
	
	/**
	 * Creates the misc operators.
	 */
	private void createMiscOperators()
	{
		GridBagConstraints operatorC = new GridBagConstraints();
		
		miscOperatorAction = new MiscOperatorAction("±", "Negate");
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
	
	/**
	 * Creates the matrix buttons.
	 */
	private void createMatrixButtons()
	{
		matrixAction = new MatrixAction("Create", "Create new Matrix");
		createMatrix = new JButton(matrixAction);
		createMatrix.setFocusable(false);
		
		GridBagConstraints buttonC = new GridBagConstraints();
		
		buttonC.gridx = 4;
		buttonC.gridy = 1;
		
		add(createMatrix, buttonC);
		
		matrixAction = new MatrixAction("Matrix", "Select, Edit, or Delete a Matrix");
		listMatrices = new JButton(matrixAction);
		listMatrices.setFocusable(false);
		
		buttonC.gridy = 2;
		buttonC.fill = GridBagConstraints.BOTH;
		
		add(listMatrices, buttonC);
		
		matrixAction = new MatrixAction("RREF", "Row-Reduced Echelon Form");
		rref = new JButton(matrixAction);
		rref.setFocusable(false);
		
		buttonC.gridy = 3;
		
		add(rref, buttonC);
		
		matrixAction = new MatrixAction("A⁻¹", "Inverse of a Matrix");
		inverse = new JButton(matrixAction);
		inverse.setFocusable(false);
		
		buttonC.gridx = 3;
		buttonC.gridy = 1;
		
		add(inverse, buttonC);
		
		matrixAction = new MatrixAction("REF", "Reduced Echelon Form");
		ref = new JButton(matrixAction);
		ref.setFocusable(false);
		
		buttonC.gridx = 5;
		buttonC.gridy = 1;
		
		add(ref, buttonC);
		
		matrixAction = new MatrixAction("Det", "Determinant");
		determinant = new JButton(matrixAction);
		determinant.setFocusable(false);
		
		buttonC.gridy = 2;
		
		add(determinant, buttonC);
		
		matrixAction = new MatrixAction("Rank", "Rank of matrix");
		rank = new JButton(matrixAction);
		rank.setFocusable(false);
		
		buttonC.gridy = 3;
		
		add(rank, buttonC);
		
		matrixAction = new MatrixAction("Scalar", "Multiply matrix by scalar");
		scalar = new JButton(matrixAction);
		scalar.setFocusable(false);
		
		buttonC.gridy = 4;
		
		add(scalar, buttonC);
		
		matrixAction = new MatrixAction("Transpose", "Transpose Matrix");
		transpose = new JButton(matrixAction);
		transpose.setFocusable(false);
		
		buttonC.gridy = 5;
		
		add(transpose, buttonC);
		
	}
	
	/**
	 * The Class NumberAction.
	 */
	public class NumberAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new number action.
		 *
		 * @param name the name
		 * @param shortDescription the short description
		 */
		public NumberAction(String name, String shortDescription)
		{
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			if (getGUIState() == BASIC) m_basicModel.numberActionPerformed(a_event);
			else m_matrixModel.numberActionPerformed(a_event);
		}
		
	}
	
	/**
	 * The Class EnterAction.
	 */
	public class EnterAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new enter action.
		 *
		 * @param name the name
		 * @param shortDescription the short description
		 */
		public EnterAction(String name, String shortDescription)
		{
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			if (getGUIState() == BASIC) m_basicModel.enterActionPerformed(a_event);
			else m_matrixModel.enterActionPerformed(a_event);
		}
	}
	
	/**
	 * The Class DeleteAction.
	 */
	public class DeleteAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new delete action.
		 *
		 * @param name the name
		 * @param shortDescription the short description
		 */
		public DeleteAction(String name, String shortDescription)
		{
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			if (getGUIState() == BASIC) m_basicModel.deleteActionPerformed(a_event);
			else m_matrixModel.deleteActionPerformed(a_event);
		}
	}
	
	/**
	 * The Class OperatorAction.
	 */
	public class OperatorAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new operator action.
		 *
		 * @param a_name the a name
		 * @param a_shortDescription the a short description
		 */
		public OperatorAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event) 
		{
			if (getGUIState() == BASIC) m_basicModel.operatorActionPerformed(a_event);
			else m_matrixModel.operatorActionPerformed(a_event);
		}
	}
	
	/**
	 * The Class MiscOperatorAction.
	 */
	public class MiscOperatorAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new misc operator action.
		 *
		 * @param a_name the a name
		 * @param a_shortDescription the a short description
		 */
		public MiscOperatorAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == BASIC) m_basicModel.miscOperatorActionPerformed(a_event);
		}
	}
	
	/**
	 * The Class MenuItemAction.
	 */
	public class MenuItemAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new menu item action.
		 *
		 * @param a_name the a name
		 * @param a_shortDescription the a short description
		 */
		public MenuItemAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
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
			
			if (m_GUIState == BASIC) frame.setSize(basicSize);
			else frame.setSize(matrixSize);
			
			revalidate();
			repaint();
		}	
	}
	
	/**
	 * The Class MatrixAction.
	 */
	public class MatrixAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new matrix action.
		 *
		 * @param a_name the a name
		 * @param a_shortDescription the a short description
		 */
		public MatrixAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == MATRIX) m_matrixModel.matrixActionPerformed(a_event);
		}
	}
	
	
	/**
	 * The Class LetterAction.
	 */
	public class LetterAction extends AbstractAction
	{

		/**
		 * Instantiates a new letter action.
		 *
		 * @param a_name the a name
		 * @param a_shortDescription the a short description
		 */
		public LetterAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == MATRIX) m_matrixModel.letterActionPerformed(a_event);
		}
		
	}
	
	/**
	 * The Class ArrowAction.
	 */
	public class ArrowAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new arrow action.
		 *
		 * @param a_name the a name
		 * @param a_shortDescription the a short description
		 */
		public ArrowAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == MATRIX) m_matrixModel.arrowActionPerformed(a_event);
		}
	}
	
}