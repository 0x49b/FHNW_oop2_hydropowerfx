package ch.fhnw.oop2.hydropowerfx.view.menubar;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Menubar extends VBox implements ViewMixin {

    private RootPM rootPM;

    private ImageView hpfxLogo;
    private Button undo;
    private Button redo;
    private Button newstation;
    private Button deletestation;
    private Button savestation;
    private Button search;
    private Button settings;
    private Label version;

    private ImageView undoImage;
    private ImageView redoImage;
    private ImageView newstationImage;
    private ImageView deletestationImage;
    private ImageView savestationImage;
    private ImageView searchImage;
    private ImageView settingsImage;

    public Menubar(RootPM rootPM) {
        this.rootPM = rootPM;
        init();
    }

    @Override
    public void initializeSelf() {
        this.getStyleClass().add("menubar");
    }

    @Override
    public void initializeControls() {
        // Logo
        Image logo = new Image(this.getClass().getResource("../assets/images/hpfxlogo.png").toExternalForm());
        hpfxLogo = new ImageView(logo);
        hpfxLogo.getStyleClass().addAll("menubar-item", "logo");

        // undo Button
        undoImage = new ImageView(new Image(this.getClass().getResource("../assets/images/undo.png").toExternalForm()));
        undo = new Button();
        undo.getStyleClass().addAll("menubar-item", "menubar-button", "undo");
        undo.setGraphic(undoImage);

        // redo Button
        redo = new Button();
        redo.getStyleClass().addAll("menubar-item", "menubar-button", "redo");

        // new station
        newstation = new Button();
        newstation.getStyleClass().addAll("menubar-item", "menubar-button", "newstation");

        // delete Station
        deletestation = new Button();
        deletestation.getStyleClass().addAll("menubar-item", "menubar-button", "deletestation");

        // savestation
        savestation = new Button();
        savestation.getStyleClass().addAll("menubar-item", "menubar-button", "savestation");

        // search Button
        search = new Button();
        search.getStyleClass().addAll("menubar-item", "menubar-button", "search");

        // settings Button
        settings = new Button();
        settings.getStyleClass().addAll("menubar-item", "menubar-button", "settings");

        // version Label
        version = new Label();
        version.getStyleClass().addAll("menubar-item", "version");
    }

    @Override
    public void layoutControls() {
        this.getChildren().addAll(hpfxLogo, undo, redo, search, settings, version);
    }

    @Override
    public void setupEventHandlers() {

    }

    @Override
    public void setupValueChangedListeners() {

    }

    @Override
    public void setupBindings() {
        version.textProperty().bind(rootPM.versionInformationProperty());
    }
}
