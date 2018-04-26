package ch.fhnw.oop2.hydropowerfx.view.editor;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import ch.fhnw.oop2.hydropowerfx.view.notification.NotificationPanel;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import static ch.fhnw.oop2.hydropowerfx.view.notification.NotificationPanel.Type.*;

public class Editor extends HBox implements ViewMixin {

    private RootPM rootPM;
    private RootPanel rootPanel;

    private Button successButton;
    private Button errorButton;
    private Button infoButton;
    private Button warningButton;

    private WebView webview;

    public Editor(RootPM rootPM, RootPanel rootPanel) {
        this.rootPM = rootPM;
        this.rootPanel = rootPanel;
        this.init();
    }

    public void init(){
        this.initializeControls();
        this.layoutControls();
        this.setupBindings();
        this.setupEventHandlers();
        this.getStyleClass().add("editor");
    }

    @Override
    public void initializeControls() {
        successButton = new Button();
        errorButton = new Button();
        infoButton = new Button();
        warningButton = new Button();
        webview = new WebView();
        WebEngine webengine = webview.getEngine();
        webengine.loadContent("<embed " +
                "style=\"width:100%;height:96%;border: 0px;display: block;\"" +
                "  src=\"https://www.google.com/maps/embed/v1/place?key=AIzaSyDQgG9WJLTOrznoQKXSpiff0Nl3_zFp1CE" +
                "  &q=46.06608148,6.90128367&zoom=18" +
                "  &maptype=satellite\">" +
                "</embed>");
        webview.setMaxHeight(400);
        webview.setMaxWidth(400);
    }

    @Override
    public void layoutControls() {
        getChildren().addAll(successButton, errorButton, infoButton, warningButton, webview);
    }

    @Override
    public void setupBindings() {
        successButton.textProperty().bind(rootPM.successButtonCaptionProperty());
        errorButton.textProperty().bind(rootPM.errorButtonCaptionProperty());
        infoButton.textProperty().bind(rootPM.infoButtonCaptionProperty());
        warningButton.textProperty().bind(rootPM.warningButtonCaptionProperty());
    }

    @Override
    public void setupEventHandlers() {

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
