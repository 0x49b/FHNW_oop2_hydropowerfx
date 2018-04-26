package ch.fhnw.oop2.hydropowerfx.view.menubar;

import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Menubar extends VBox implements ViewMixin {

    private Image logo;
    private ImageView hpfxLogo;
    private Button undo;
    private Button redo;
    private Button search;
    private Button settings;
    private Label version;


    @Override
    public void initializeSelf() {
        this.getStyleClass().add("menubar");
    }

    @Override
    public void initializeControls() {
        logo = new Image(this.getClass().getResource("../assets/hpfxlogo.png").toExternalForm());
        hpfxLogo = new ImageView(logo);
        hpfxLogo.getStyleClass().addAll("menubar-item","logo");
        undo = new Button();
        undo.getStyleClass().addAll("menubar-item","undo");
        redo = new Button();
        redo.getStyleClass().addAll("menubar-item","redo");
        search = new Button();
        search.getStyleClass().addAll("menubar-item","search");
        settings = new Button();
        settings.getStyleClass().addAll("menubar-item","settings");
        version = new Label();
        version.getStyleClass().addAll("menubar-item","version");
        version.setText("V0.1");
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

    }
}
