package org.nn.world.ui.event;



public class ControlActionEvent {

	private ActionType actionType;

	public ControlActionEvent(ActionType at){
		this.actionType = at;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public enum ActionType{
		RESET;
	}
}
