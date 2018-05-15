package ch.fhnw.oop2.hydropowerfx.view.editor;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Editor extends HBox implements ViewMixin {

    private RootPM rootPM;
    private RootPanel rootPanel;
    private Label editorStationName;
    private TextField maxPower;
    private Label canton;

    private PowerStation actualStation;

    public Editor(RootPM rootPM, RootPanel rootPanel) {
        this.rootPM = rootPM;
        this.rootPanel = rootPanel;
        this.actualStation = rootPM.getActualPowerStation();
        this.init();
    }


    @Override
    public void initializeSelf(){
        this.getStyleClass().add("editor");
    }

    @Override
    public void initializeControls() {
        editorStationName = new Label();
        editorStationName.getStyleClass().addAll("editor-stationname", "title");

        maxPower = new TextField();
        canton = new Label();
    }

    @Override
    public void layoutControls() {
        getChildren().addAll(editorStationName, maxPower, canton);
    }

    @Override
    public void setupBindings() {
        editorStationName.textProperty().bind(rootPM.getActualPowerStation().nameProperty());
        maxPower.textProperty().bindBidirectional(rootPM.getActualPowerStation().maxPowerProperty());
        canton.textProperty().bind(rootPM.getActualPowerStation().cantonProperty());
    }

    public void releaseBindings() {
        editorStationName.textProperty().unbind();
        canton.textProperty().unbind();
        maxPower.textProperty().unbindBidirectional(actualStation.maxPowerProperty());
    }

    @Override
    public void setupEventHandlers() {
        rootPM.actualPowerStationProperty().addListener(event -> {
            releaseBindings();
            actualStation = rootPM.getActualPowerStation();
            maxPower.setText(rootPM.getActualPowerStation().getMaxPower());
            setupBindings();
        });
    }
}
