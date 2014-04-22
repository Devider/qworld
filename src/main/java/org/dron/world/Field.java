package org.dron.world;

public class Field {

    private FieldType fieldType;

    private Point point;

    public Field(FieldType fieldType, Point point) {
        this.fieldType = fieldType;
        this.point = point;
    }

    public FieldType getType() {
        return fieldType;
    }

    public Point getPoint() {
        return point;
    }
}
