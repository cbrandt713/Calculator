package calculator;

import java.awt.event.ActionEvent;

public interface ActionEventHandler 
{
	public abstract void numberActionPerformed(ActionEvent a_event);
	
	public abstract void letterActionPerformed(ActionEvent a_event);
	
	public abstract void enterActionPerformed(ActionEvent a_event);
	
	public abstract void deleteActionPerformed(ActionEvent a_event);
	
	public abstract void operatorActionPerformed(ActionEvent a_event);
	
	
	
}
