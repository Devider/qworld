package org.nn.world;

public class Field {

	private FieldType fieldType;

	private boolean visited = false;

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public Field(FieldType type) {
		this.fieldType = type;
	}
	
	public FieldType getType() {
		return fieldType;
	}
}
