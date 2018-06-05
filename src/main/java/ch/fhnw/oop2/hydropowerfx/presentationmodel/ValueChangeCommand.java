package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.Property;

public class ValueChangeCommand implements Command {
    private final RootPM rootPM;
    private final Property property;
    private final Object   oldValue;
    private final Object   newValue;

    public ValueChangeCommand(RootPM rootPM, Property property, Object oldValue, Object newValue) {
        this.rootPM = rootPM;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public void undo() {
        rootPM.setPropertyValue(property, oldValue);
    }

    public void redo() {
        rootPM.setPropertyValue(property, newValue);
    }
}
