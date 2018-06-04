package ch.fhnw.oop2.hydropowerfx.view.menubar;

import ch.fhnw.oop2.hydropowerfx.export.PDFExport;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Menubar extends VBox implements ViewMixin {

    private RootPM rootPM;
    private RootPanel rootPanel;
    private Menubar menubar;
    private VBox buttonCol;
    private VBox footerCol;


    private Button undo;
    private Button redo;
    private Button newstation;
    private Button deletestation;
    private Button savestation;
    private Button search;
    private Button clearFilter;
    private Button topdf;
    private Button settings;
    private Label version;

    private ImageView hpfxlogo;
    private ImageView undoImage;
    private ImageView redoImage;
    private ImageView newstationImage;
    private ImageView deletestationImage;
    private ImageView savestationImage;
    private ImageView searchImage;
    private ImageView clearFilterImage;
    private ImageView topdfImage;
    private ImageView settingsImage;
    private SearchPanel searchpanel;

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
        // undo Button
        undoImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/undo.png")));
        undo = new Button();
        undo.getStyleClass().addAll("menubar-item", "menubar-button", "undo");
        undo.setGraphic(undoImage);
        undo.setTooltip(new Tooltip("Widerrufen"));


        // redo Button
        redoImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/redo.png")));
        redo = new Button();
        redo.getStyleClass().addAll("menubar-item", "menubar-button", "redo");
        redo.setGraphic(redoImage);
        redo.setTooltip(new Tooltip("Wiederholen"));

        // new station
        newstationImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/new.png")));
        newstation = new Button();
        newstation.getStyleClass().addAll("menubar-item", "menubar-button", "newstation");
        newstation.setGraphic(newstationImage);
        newstation.setTooltip(new Tooltip("Neue Station einfügen"));

        // delete Station
        deletestationImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/delete.png")));
        deletestation = new Button();
        deletestation.getStyleClass().addAll("menubar-item", "menubar-button", "deletestation");
        deletestation.setGraphic(deletestationImage);
        deletestation.setTooltip(new Tooltip("Station löschen"));

        // savestation
        savestationImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/save.png")));
        savestation = new Button();
        savestation.getStyleClass().addAll("menubar-item", "menubar-button", "savestation");
        savestation.setGraphic(savestationImage);
        savestation.setTooltip(new Tooltip("Station speichern"));

        // search Button
        searchImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/search.png")));
        search = new Button();
        search.getStyleClass().addAll("menubar-item", "menubar-button", "search");
        search.setGraphic(searchImage);
        searchpanel = new SearchPanel(rootPanel, rootPM, menubar);
        search.setTooltip(new Tooltip("Station suchen"));

        // Delete Filter
        clearFilterImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/clearfilter.png")));
        clearFilter = new Button();
        clearFilter.getStyleClass().addAll("menubar-item", "menubar-button", "clearfilter");
        clearFilter.setGraphic(clearFilterImage);
        clearFilter.setTooltip(new Tooltip("Filter löschen"));

        // PDF Exporter
        topdfImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/topdf.png")));
        topdf = new Button();
        topdf.getStyleClass().addAll("menubar-item", "menubar-button", "topdf");
        topdf.setGraphic(topdfImage);
        topdf.setTooltip(new Tooltip("Station als PDF speichern"));

        // settings Button
        settingsImage = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/settings.png")));
        settings = new Button();
        settings.getStyleClass().addAll("menubar-item", "menubar-button", "settings");
        settings.setGraphic(settingsImage);
        settings.setTooltip(new Tooltip("Einstellungen öffnen"));

        // version Label
        version = new Label();
        version.getStyleClass().addAll("menubar-item", "version");
        version.setAlignment(Pos.CENTER);

        buttonCol = new VBox();
        footerCol = new VBox();

    }

    @Override
    public void layoutControls() {
        buttonCol.getChildren().addAll(undo, redo, newstation, savestation, deletestation, search, clearFilter, topdf);
        footerCol.getChildren().addAll(settings, version);
        this.getChildren().addAll(buttonCol, footerCol);
        this.setVgrow(buttonCol, Priority.ALWAYS);
    }

    @Override
    public void setupEventHandlers() {
        undo.setOnAction(event -> rootPM.undo());
        redo.setOnAction(event -> rootPM.redo());

        settings.setOnAction(event -> rootPM.openPreferences());
        newstation.setOnAction(event -> rootPM.addPowerStation());


        deletestation.setOnAction(ae -> {
            String name = rootPM.getActualPowerStation().getName();
            rootPM.deletePowerStation();
            new NotificationPanel(rootPanel, name + " gelöscht", NotificationPanel.Type.SUCCESS).show();
        });


        savestation.setOnAction(event -> {
            rootPM.save();
        });

        search.setOnAction(event -> searchpanel.showhide());
        topdf.setOnAction(event -> new PDFExport(rootPM.getActualPowerStation(), rootPM, rootPanel));

        clearFilter.setOnAction(event -> {
            rootPM.setCantonFilter("");
            rootPM.setSearchText("");
            new NotificationPanel(rootPanel, "Filter gelöscht", NotificationPanel.Type.SUCCESS).show();
        });

    }


    @Override
    public void setupBindings() {
        undo.disableProperty().bind(rootPM.undoDisabledProperty());
        redo.disableProperty().bind(rootPM.redoDisabledProperty());
        clearFilter.disableProperty().bind(rootPM.disabledClearFilterProperty());
        savestation.disableProperty().bind(rootPM.disableSaveProperty());
        version.textProperty().bind(rootPM.versionInformationProperty());
    }

    //getter for searchbutton

    public Button getSearch() {
        return search;
    }
}
