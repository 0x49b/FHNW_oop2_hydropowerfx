package ch.fhnw.oop2.hydropowerfx.view.menubar;

import ch.fhnw.oop2.hydropowerfx.export.PDFWriter;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import ch.fhnw.oop2.hydropowerfx.view.notification.NotificationPanel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Menubar extends VBox implements ViewMixin {

    private RootPM rootPM;
    private RootPanel rootPanel;
    private Menubar menubar;

    private ImageView hpfxLogo;
    private Button undo;
    private Button redo;
    private Button newstation;
    private Button deletestation;
    private Button savestation;
    private Button search;
    private Button topdf;
    private Button settings;
    private Label version;

    private ImageView undoImage;
    private ImageView redoImage;
    private ImageView newstationImage;
    private ImageView deletestationImage;
    private ImageView savestationImage;
    private ImageView searchImage;
    private ImageView topdfImage;
    private ImageView settingsImage;
    private SearchPanel searchpanel;

    private Tooltip toPDFTooltip;


    public Menubar(RootPM rootPM, RootPanel rootPanel) {
        this.rootPM = rootPM;
        this.rootPanel = rootPanel;
        this.menubar = this;
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
        redoImage = new ImageView(new Image(this.getClass().getResource("../assets/images/redo.png").toExternalForm()));
        redo = new Button();
        redo.getStyleClass().addAll("menubar-item", "menubar-button", "redo");
        redo.setGraphic(redoImage);

        // new station
        newstationImage = new ImageView(new Image(this.getClass().getResource("../assets/images/new.png").toExternalForm()));
        newstation = new Button();
        newstation.getStyleClass().addAll("menubar-item", "menubar-button", "newstation");
        newstation.setGraphic(newstationImage);

        // delete Station
        deletestationImage = new ImageView(new Image(this.getClass().getResource("../assets/images/delete.png").toExternalForm()));
        deletestation = new Button();
        deletestation.getStyleClass().addAll("menubar-item", "menubar-button", "deletestation");
        deletestation.setGraphic(deletestationImage);

        // savestation
        savestationImage = new ImageView(new Image(this.getClass().getResource("../assets/images/save.png").toExternalForm()));
        savestation = new Button();
        savestation.getStyleClass().addAll("menubar-item", "menubar-button", "savestation");
        savestation.setGraphic(savestationImage);

        // search Button
        searchImage = new ImageView(new Image(this.getClass().getResource("../assets/images/search.png").toExternalForm()));
        search = new Button();
        search.getStyleClass().addAll("menubar-item", "menubar-button", "search");
        search.setGraphic(searchImage);
        searchpanel = new SearchPanel(rootPanel, rootPM, menubar);

        topdfImage = new ImageView(new Image(this.getClass().getResource("../assets/images/topdf.png").toExternalForm()));
        topdf = new Button();
        topdf.getStyleClass().addAll("menubar-item", "menubar-button", "topdf");
        topdf.setGraphic(topdfImage);
        toPDFTooltip = new Tooltip();
        toPDFTooltip.setText("Station als PDF speichern");
        topdf.setTooltip(toPDFTooltip);

        // settings Button
        settingsImage = new ImageView(new Image(this.getClass().getResource("../assets/images/settings.png").toExternalForm()));
        settings = new Button();
        settings.getStyleClass().addAll("menubar-item", "menubar-button", "settings");
        settings.setGraphic(settingsImage);

        // version Label
        version = new Label();
        version.getStyleClass().addAll("menubar-item", "version");
        version.setAlignment(Pos.CENTER);
    }

    @Override
    public void layoutControls() {
        this.getChildren().addAll(hpfxLogo, undo, redo, newstation, savestation, deletestation, search, topdf, settings, version);
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
        search.setOnAction(event -> {
            searchpanel.showhide();
        });
        savestation.setOnAction(event -> {
            new NotificationPanel(rootPanel, "gespeichert", NotificationPanel.Type.SUCCESS).show();
        });

        topdf.setOnAction(event -> {
            PDFWriter pdfwriter = new PDFWriter(rootPM.getActualPowerStation(), rootPM);
            new NotificationPanel(rootPanel, "PDF wird erstellt", NotificationPanel.Type.INFO).show();
        });

    }

    //getter for searchbutton

    public Button getSearch() {
        return search;
    }
}
