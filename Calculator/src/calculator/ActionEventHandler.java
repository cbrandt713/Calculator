package calculator;

import java.awt.event.ActionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface ActionEventHandler.
 */
public interface ActionEventHandler 
{
	
	/**
	 * An action was fired that is associated with a "number" value being input.
	 *
	 * @param a_event the event that fired the action
	 */
	public abstract void numberActionPerformed(ActionEvent a_event);
	
	/**
	 * An action was fired that is associated with a "letter" value being input.
	 *
	 * @param a_event the event that fired the action
	 */
	public abstract void letterActionPerformed(ActionEvent a_event);
	
	/**
	 * An action was fired that is associated with an "enter" or "submit" event being input.
	 *
	 * @param a_event the event that fired the action
	 */
	public abstract void enterActionPerformed(ActionEvent a_event);
	
	/**
	 * An action was fired that is associated with a previous input being "deleted".
	 *
	 * @param a_event the event that fired the action
	 */
	public abstract void deleteActionPerformed(ActionEvent a_event);
	
	/**
	 * An action was fired that is associated with a "number" value being input.
	 *
	 * @param a_event the event that fired the action
	 */
	public abstract void operatorActionPerformed(ActionEvent a_event);
	
}
