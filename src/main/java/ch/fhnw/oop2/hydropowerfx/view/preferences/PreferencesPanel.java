package ch.fhnw.oop2.hydropowerfx.view.preferences;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.layout.VBox;

import java.util.List;

public class PreferencesPanel extends VBox implements ViewMixin {

    private RootPM rootPM;

    public PreferencesPanel(RootPM rootPM) {
        this.rootPM = rootPM;

        init();
    }

    @Override
    public void initializeSelf() {
        this.getStyleClass().add("preferences-panel");
    }

    @Override public void initializeControls() {

    }

    @Override public void layoutControls() {

    }
}
