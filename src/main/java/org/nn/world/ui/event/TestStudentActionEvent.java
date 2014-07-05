package org.nn.world.ui.event;

import nn.networks.nonlinear.backpropogation.BNetwork;


public class TestStudentActionEvent {
	private BNetwork student;
	
	public TestStudentActionEvent(BNetwork student){
		this.student = student;
	}

	public BNetwork getStudent() {
		return student;
	}

}
