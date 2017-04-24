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
	
	//GUI State Indicator Constants:
	/** The BASIC mode. */
	private final static int BASIC = 0;
	
	/** The MATRIX mode. */
	private final static int MATRIX = 1;
	
	
	//GUI Data Objects:
	/** The GUI object. */
	private static GUI m_GUIObject;
	
	/** The GUI state. */
	private int m_GUIState;
	
	/** The basic GUI size. */
	private Dimension m_basicSize;
	
	/** The matrix GUI size. */
	private Dimension m_matrixSize;
	
	
	//GUI Elements:
	/** The frame. */
	private JFrame m_frame;
	
	/** The no wrap panel. */
	private JPanel m_noWrapPanel;
	
	/** The basic display. */
	private BasicTextArea m_basicDisplay;
	
	/** The basic model. */
	private BasicGUIModel m_basicModel;
	
	/** The matrix display. */
	private MatrixTextPane m_matrixDisplay;
	
	/** The matrix model. */
	private MatrixGUIModel m_matrixModel;
	
	/** The menu. */
	private JMenu m_menu;
	
	/** The menu bar. */
	private JMenuBar m_menuBar;
	
	/** The basic. */
	private JMenuItem m_basicMenuItem;
	
	/** The matrix. */
	private JMenuItem m_matrixMenuItem;
	
	/** The scroll pane. */
	private JScrollPane m_scrollPane;
	
	
	//Number Buttons:
	/** The number buttons. */
	private JButton m_numbers[];
	
	/** The decimal point button. */
	private JButton m_decimal;
	
	
	//Delete Buttons:
	/** The backspace button. */
	private JButton m_backspace;
	
	/** The clear button. */
	private JButton m_clear;
	
	/** The clear entry button. */
	private JButton m_clearEntry;
	
	
	//Operator Buttons:
	/** The addition button. */
	private JButton m_addition;
	
	/** The subtraction button. */
	private JButton m_subtraction;
	
	/** The multiplication button. */
	private JButton m_multiplication;
	
	/** The division button. */
	private JButton m_division;
	
	/** The equals button. */
	private JButton m_equals;
	
	
	//Basic Only Buttons:
	/** The invert button. */
	private JButton m_plusMinus;
	
	/** The reciprocal button. */
	private JButton m_reciprocal;
	
	/** The square root button. */
	private JButton m_squareRoot;
	
	/** The percent button. */
	private JButton m_percent;
	
	
	//Matrix Only Buttons:
	/** The create matrix button. */
	private JButton m_createMatrix;
	
	/** The list matrices button. */
	private JButton m_listMatrices;
	
	/** The rref button. */
	private JButton m_rref;
	
	/** The ref button. */
	private JButton m_ref;
	
	/** The inverse button. */
	private JButton m_inverse;
	
	/** The determinant button. */
	private JButton m_determinant;
	
	/** The rank button. */
	private JButton m_rank;
	
	/** The scalar button. */
	private JButton m_scalar;
	
	/** The transpose button. */
	private JButton m_transpose;

	
	//Action Elements:
	/** The number action. */
	private NumberAction m_numberAction;
	
	/** The enter action. */
	private EnterAction m_enterAction;
	
	/** The delete action. */
	private DeleteAction m_deleteAction;
	
	/** The operator action. */
	private OperatorAction m_operatorAction;
	
	/** The misc operator action. */
	private MiscOperatorAction m_miscOperatorAction;
	
	/** The menu item action. */
	private MenuItemAction m_menuItemAction;
	
	/** The matrix action. */
	private MatrixAction m_matrixAction;
	
	/** The letter action. */
	private LetterAction m_letterAction;
	
	/** The arrow action. */
	private ArrowAction m_arrowAction;
	
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
		
	}
	
	/**
	 * Gets the GUI instance.
	 *
	 * @return the GUI instance
	 */
	public static GUI getGUIInstance()
	{
		if (m_GUIObject == null)
		{
			m_GUIObject = new GUI();
		}
		
		return m_GUIObject;
	}
	
	/**
	 * Creates the frame to host the GUI.
	 */
	private void createFrame()
	{
		m_frame = new JFrame("Calculator");
		
		m_frame.getContentPane().add(this);
		m_frame.setJMenuBar(getJMenuBar());
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setResizable(false);
		
        // Size the frame.
        m_frame.pack();
        
        // Show the frame
        m_frame.setVisible(true);
        
        System.out.println(m_frame.getSize());
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
		
		m_basicSize = new Dimension(243, 240);	
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
		createMatrixDisplay();
		createNumbers();
		createDeleteButtons();
		createOperators();
		createMatrixButtons();
		
		m_matrixSize = new Dimension(370, 338);
	}
	
	/**
	 * Creates the menu.
	 */
	private void createMenu()
	{
		m_menuBar = new JMenuBar();
		m_menu = new JMenu("View");
		
		m_menuBar.add(m_menu);
		
		m_menuItemAction = new MenuItemAction("Basic", "Show Basic Calculator");
		m_basicMenuItem = new JMenuItem(m_menuItemAction);
		m_menu.add(m_basicMenuItem);
		
		m_menuItemAction = new MenuItemAction("Matrix", "Show Matrix Calculator");
		m_matrixMenuItem = new JMenuItem(m_menuItemAction);
		m_menu.add(m_matrixMenuItem);
	}
	
	/**
	 * Gets the JMenuBar.
	 *
	 * @return the JMenuBar
	 */
	private JMenuBar getJMenuBar()
	{
		return m_menuBar;
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
		m_numberAction = new NumberAction("", "");
		m_deleteAction = new DeleteAction("", "");
		m_operatorAction = new OperatorAction("", "");
		m_enterAction = new EnterAction("", "");
		
		m_basicDisplay.getActionMap().put("number", m_numberAction);
		m_basicDisplay.getActionMap().put("delete", m_deleteAction);
		m_basicDisplay.getActionMap().put("operation", m_operatorAction);
		m_basicDisplay.getActionMap().put("enter", m_enterAction);
		
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
		m_scrollPane = new JScrollPane(m_noWrapPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		createMatrixKeybinds();
		
		GridBagConstraints displayC = new GridBagConstraints();
		displayC.gridx = 0;
		displayC.gridy = 0;
		displayC.gridwidth = 6;
		displayC.weightx = 1.0;
		displayC.weighty = 1.0;
		displayC.fill = GridBagConstraints.BOTH;
		
		add(m_scrollPane, displayC);
	}
	
	/**
	 * Creates the matrix keybinds.
	 */
	private void createMatrixKeybinds()
	{
		m_enterAction = new EnterAction("", "");
		m_numberAction = new NumberAction("", "");
		m_deleteAction = new DeleteAction("", "");
		m_letterAction = new LetterAction("", "");
		m_arrowAction = new ArrowAction("", "");
		m_operatorAction = new OperatorAction("", "");
		
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
		m_matrixDisplay.getActionMap().put("enter", m_enterAction);
		m_matrixDisplay.getActionMap().put("number", m_numberAction);
		m_matrixDisplay.getActionMap().put("delete", m_deleteAction);
		m_matrixDisplay.getActionMap().put("letter", m_letterAction);
		m_matrixDisplay.getActionMap().put("arrow", m_arrowAction);
		m_matrixDisplay.getActionMap().put("operation", m_operatorAction);
	}
	
	/**
	 * Creates the number buttons.
	 */
	private void createNumbers()
	{
		m_numbers = new JButton[10];
		
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
			
			m_numberAction = new NumberAction(i.toString(), "Insert number");
			m_numbers[i] = new JButton(m_numberAction);
			m_numbers[i].setFocusable(false);
			
			numC.gridy = ((9 - i) / 3) + 2;
			
			if (i == 0) numC.gridx = 0;
			else numC.gridx = (i % 3) - 1;
			
			add(m_numbers[i], numC);
		}
		
		m_numberAction = new NumberAction(".", "Decimal");
		m_decimal = new JButton(m_numberAction);
		m_decimal.setFocusable(false);
		
		GridBagConstraints decC = new GridBagConstraints();
		decC.gridx = 2;
		decC.gridy = 5;
		decC.weightx = 0.5;
		decC.fill = GridBagConstraints.HORIZONTAL;
		
		add(m_decimal, decC);	
	}
	
	/**
	 * Creates the delete buttons.
	 */
	private void createDeleteButtons()
	{
		m_deleteAction = new DeleteAction("←", "Delete number");
		m_backspace = new JButton(m_deleteAction);
		m_backspace.setFocusable(false);
	
		GridBagConstraints backspaceC = new GridBagConstraints();
		backspaceC.gridx = 0;
		backspaceC.gridy = 1;
		backspaceC.weightx = 0.5;
		
		add(m_backspace, backspaceC);
		
		m_deleteAction = new DeleteAction("Clr", "Clear All Input");
		m_clear = new JButton(m_deleteAction);
		m_clear.setFocusable(false);
		
		GridBagConstraints clearC = new GridBagConstraints();
		clearC.gridx = 2;
		clearC.gridy = 1;
		clearC.weightx = 0.5;
		
		add(m_clear, clearC);
		
		m_deleteAction = new DeleteAction("CE", "Clear Entry");
		m_clearEntry = new JButton(m_deleteAction);
		m_clearEntry.setFocusable(false);
		
		clearC.gridx = 1;
		
		add(m_clearEntry, clearC);
	}
	
	/**
	 * Creates the operators.
	 */
	private void createOperators()
	{
		m_operatorAction = new OperatorAction("+", "Add numbers");
		m_addition = new JButton(m_operatorAction);
		m_addition.setFocusable(false);
		
		GridBagConstraints operatorC = new GridBagConstraints();
		
		operatorC.gridx = 3;
		operatorC.gridy = 5;
		operatorC.weightx = 0.5;
		operatorC.fill = GridBagConstraints.BOTH;
		
		add(m_addition, operatorC);
		
		m_operatorAction = new OperatorAction("-", "Subtract numbers");
		m_subtraction = new JButton(m_operatorAction);
		m_subtraction.setFocusable(false);
		
		operatorC.gridy = 4;
		
		add(m_subtraction, operatorC);

		m_operatorAction = new OperatorAction("*", "Multiply numbers");
		m_multiplication = new JButton(m_operatorAction);
		m_multiplication.setFocusable(false);
		
		operatorC.gridy = 3;
		
		add(m_multiplication, operatorC);
		
		m_operatorAction = new OperatorAction("/", "Divide numbers");
		m_division = new JButton(m_operatorAction);
		m_division.setFocusable(false);
		
		operatorC.gridy = 2;
		
		add(m_division, operatorC);
		
		m_operatorAction = new OperatorAction("=", "Find Total");
		m_equals = new JButton(m_operatorAction);
		m_equals.setFocusable(false);
		
		operatorC.gridy = 4;
		operatorC.gridx = 4;
		operatorC.gridheight = 2;
		operatorC.fill = GridBagConstraints.BOTH;
		operatorC.weighty = 1.0;
		operatorC.weightx = 1.0;
		
		add(m_equals, operatorC);
	}
	
	/**
	 * Creates the misc operators.
	 */
	private void createMiscOperators()
	{
		GridBagConstraints operatorC = new GridBagConstraints();
		
		m_miscOperatorAction = new MiscOperatorAction("±", "Negate");
		m_plusMinus = new JButton(m_miscOperatorAction);
		m_plusMinus.setFocusable(false);
		
		operatorC.gridx = 3;
		operatorC.gridy = 1;
		operatorC.weightx = 0.5;
		operatorC.weighty = 0.5;
		operatorC.fill = GridBagConstraints.BOTH;
		
		add(m_plusMinus, operatorC);
		
		m_miscOperatorAction = new MiscOperatorAction("1/x", "Reciprocal");
		m_reciprocal = new JButton(m_miscOperatorAction);
		m_reciprocal.setFocusable(false);
		
		operatorC.gridx = 4;
		operatorC.gridy = 3;
		
		add(m_reciprocal, operatorC);
		
		m_miscOperatorAction = new MiscOperatorAction("%", "Calculate percentages");
		m_percent = new JButton(m_miscOperatorAction);
		m_percent.setFocusable(false);
		
		operatorC.gridy = 2;
		
		add(m_percent, operatorC);
		
		m_miscOperatorAction = new MiscOperatorAction("√", "Square Root");
		m_squareRoot = new JButton(m_miscOperatorAction);
		m_squareRoot.setFocusable(false);
		
		operatorC.gridy = 1;
		
		add(m_squareRoot, operatorC);
	}
	
	/**
	 * Creates the matrix buttons.
	 */
	private void createMatrixButtons()
	{
		m_matrixAction = new MatrixAction("Create", "Create new Matrix");
		m_createMatrix = new JButton(m_matrixAction);
		m_createMatrix.setFocusable(false);
		
		GridBagConstraints buttonC = new GridBagConstraints();
		
		buttonC.gridx = 4;
		buttonC.gridy = 1;
		
		add(m_createMatrix, buttonC);
		
		m_matrixAction = new MatrixAction("Matrix", "Select, Edit, or Delete a Matrix");
		m_listMatrices = new JButton(m_matrixAction);
		m_listMatrices.setFocusable(false);
		
		buttonC.gridy = 2;
		buttonC.fill = GridBagConstraints.BOTH;
		
		add(m_listMatrices, buttonC);
		
		m_matrixAction = new MatrixAction("RREF", "Row-Reduced Echelon Form");
		m_rref = new JButton(m_matrixAction);
		m_rref.setFocusable(false);
		
		buttonC.gridy = 3;
		
		add(m_rref, buttonC);
		
		m_matrixAction = new MatrixAction("A⁻¹", "Inverse of a Matrix");
		m_inverse = new JButton(m_matrixAction);
		m_inverse.setFocusable(false);
		
		buttonC.gridx = 3;
		buttonC.gridy = 1;
		
		add(m_inverse, buttonC);
		
		m_matrixAction = new MatrixAction("REF", "Reduced Echelon Form");
		m_ref = new JButton(m_matrixAction);
		m_ref.setFocusable(false);
		
		buttonC.gridx = 5;
		buttonC.gridy = 1;
		
		add(m_ref, buttonC);
		
		m_matrixAction = new MatrixAction("Det", "Determinant");
		m_determinant = new JButton(m_matrixAction);
		m_determinant.setFocusable(false);
		
		buttonC.gridy = 2;
		
		add(m_determinant, buttonC);
		
		m_matrixAction = new MatrixAction("Rank", "Rank of matrix");
		m_rank = new JButton(m_matrixAction);
		m_rank.setFocusable(false);
		
		buttonC.gridy = 3;
		
		add(m_rank, buttonC);
		
		m_matrixAction = new MatrixAction("Scalar", "Multiply matrix by scalar");
		m_scalar = new JButton(m_matrixAction);
		m_scalar.setFocusable(false);
		
		buttonC.gridy = 4;
		
		add(m_scalar, buttonC);
		
		m_matrixAction = new MatrixAction("Transpose", "Transpose Matrix");
		m_transpose = new JButton(m_matrixAction);
		m_transpose.setFocusable(false);
		
		buttonC.gridy = 5;
		
		add(m_transpose, buttonC);
		
	}
	
	/**
	 * Registers any number actions with the GUI.
	 */
	public class NumberAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new number action.
		 *
		 * @param a_name the name
		 * @param a_shortDescription the short description
		 */
		public NumberAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/**
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
	 * Registers any enter actions with the GUI.
	 */
	public class EnterAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new enter action.
		 *
		 * @param a_name the name
		 * @param a_shortDescription the short description
		 */
		public EnterAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/**
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
	 * Registers any delete actions with the GUI.
	 */
	public class DeleteAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new delete action.
		 *
		 * @param a_name the name
		 * @param a_shortDescription the short description
		 */
		public DeleteAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/**
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
	 * Registers any operator actions with the GUI.
	 */
	public class OperatorAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new operator action.
		 *
		 * @param a_name the name
		 * @param a_shortDescription the short description
		 */
		public OperatorAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/**
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
	 * Registers any misc operator actions with the GUI.
	 */
	public class MiscOperatorAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new misc operator action.
		 *
		 * @param a_name the name
		 * @param a_shortDescription the short description
		 */
		public MiscOperatorAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == BASIC) m_basicModel.miscOperatorActionPerformed(a_event);
		}
	}
	
	/**
	 * Registers any menu item actions with the GUI.
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
		
		/**
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
			
			if (m_GUIState == BASIC) m_frame.setSize(m_basicSize);
			else m_frame.setSize(m_matrixSize);
			
			revalidate();
			repaint();
		}	
	}
	
	/**
	 * Registers any matrix actions with the GUI.
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
	 * Registers any letter actions with the GUI.
	 */
	public class LetterAction extends AbstractAction
	{

		/**
		 * Instantiates a new letter action.
		 *
		 * @param a_name the name
		 * @param a_shortDescription the short description
		 */
		public LetterAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == MATRIX) m_matrixModel.letterActionPerformed(a_event);
		}
		
	}
	
	/**
	 * Registers any arrow actions with the GUI.
	 */
	public class ArrowAction extends AbstractAction
	{
		
		/**
		 * Instantiates a new arrow action.
		 *
		 * @param a_name the name
		 * @param a_shortDescription the short description
		 */
		public ArrowAction(String a_name, String a_shortDescription)
		{
			super(a_name);
			putValue(SHORT_DESCRIPTION, a_shortDescription);
		}
		
		
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent a_event)
		{
			if (getGUIState() == MATRIX) m_matrixModel.arrowActionPerformed(a_event);
		}
	}
	
}