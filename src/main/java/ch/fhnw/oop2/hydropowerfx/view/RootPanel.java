package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.notification.NotificationPanel;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import static ch.fhnw.oop2.hydropowerfx.view.notification.NotificationPanel.Type.*;

public class RootPanel extends HBox implements ViewMixin {
    private final RootPM rootPM;
    private RootPanel rootPanel;

    private Button successButton;
    private Button errorButton;
    private Button infoButton;
    private Button warningButton;


    public RootPanel(RootPM model) {
        this.rootPM = model;
        this.rootPanel = this;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {
        successButton = new Button();
        errorButton = new Button();
        infoButton = new Button();
        warningButton = new Button();
    }

    @Override
    public void layoutControls() {
        getChildren().addAll(successButton, errorButton, infoButton, warningButton);
    }

    @Override
    public void setupBindings() {
        successButton.textProperty().bind(rootPM.successButtonCaptionProperty());
        errorButton.textProperty().bind(rootPM.errorButtonCaptionProperty());
        infoButton.textProperty().bind(rootPM.infoButtonCaptionProperty());
        warningButton.textProperty().bind(rootPM.warningButtonCaptionProperty());
    }

    @Override
    public void setupEventHandlers(){

        successButton.setOnAction(event -> {
            NotificationPanel np = new NotificationPanel(rootPanel, "This is a long line\nwith 2 Line breaks.\nJust saying", SUCCESS, 800);
            np.show();
        });

        errorButton.setOnAction(event -> {
            NotificationPanel np = new NotificationPanel(rootPanel, "error", ERROR, 800);
            np.show();
        });

        infoButton.setOnAction(event -> {
            NotificationPanel np = new NotificationPanel(rootPanel, "info", INFO, 800);
            np.show();
        });

        warningButton.setOnAction(event -> {
            NotificationPanel np = new NotificationPanel(rootPanel, "warn", WARNING, 800);
            np.show();
        });
    }
}
