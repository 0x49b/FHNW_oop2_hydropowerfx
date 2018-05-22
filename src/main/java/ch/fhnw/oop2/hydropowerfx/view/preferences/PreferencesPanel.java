package ch.fhnw.oop2.hydropowerfx.view.preferences;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PreferencesPanel extends VBox implements ViewMixin {

    private RootPM rootPM;

    private Button cancel;
    private Button save;

    public PreferencesPanel(RootPM rootPM) {
        this.rootPM = rootPM;

        init();
    }

    @Override
    public void initializeSelf() {
        this.getStyleClass().add("preferences-panel");
    }

    @Override public void initializeControls() {
        cancel = new Button("Abbrechen");
        save = new Button("Speichern");
    }

    @Override public void layoutControls() {
        getChildren().addAll(cancel, save);
    }

    @Override
    public void setupBindings() {
        cancel.setOnAction(event -> {
            cancel.getScene().getWindow().hide();
        });
    }
}
