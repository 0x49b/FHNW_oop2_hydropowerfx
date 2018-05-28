package ch.fhnw.oop2.hydropowerfx.view.preferences;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PreferencesPanel extends VBox implements ViewMixin {

    private RootPM rootPM;

    private TabPane tabPane;
    private Tab dbTab;

    private Button cancel;
    private Button save;

    private Label dbLabel;
    private ToggleGroup dbGroup;
    private ToggleButton csvButton;
    private ToggleButton sqliteButton;
    private ToggleButton neo4jButton;

    private HBox buttonBox;

    public PreferencesPanel(RootPM rootPM) {
        this.rootPM = rootPM;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("../assets/style.css");
        this.getStyleClass().add("preferences-panel");
    }

    @Override
    public void initializeControls() {
        getStyleClass().add("preference-panel");

        tabPane = new TabPane();
        tabPane.setPrefSize(400, 360);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        dbTab = new Tab();

        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setTranslateX(10);
        vbox.setTranslateY(10);

        dbTab.setContent(vbox);

        dbLabel = new Label();
        dbLabel.getStyleClass().add("preference-title");

        dbGroup = new ToggleGroup();
        csvButton = new RadioButton();
        csvButton.setToggleGroup(dbGroup);
        csvButton.getStyleClass().add("preference-radio");
        sqliteButton = new RadioButton();
        sqliteButton.setToggleGroup(dbGroup);
        sqliteButton.getStyleClass().add("preference-radio");
        neo4jButton = new RadioButton();
        neo4jButton.setToggleGroup(dbGroup);
        neo4jButton.getStyleClass().add("preference-radio");
        vbox.getChildren().addAll(dbLabel, csvButton, sqliteButton, neo4jButton);

        tabPane.getTabs().add(dbTab);
        buttonBox = new HBox();
        cancel = new Button("Abbrechen");
        cancel.getStyleClass().add("button-primary");
        save = new Button("Speichern");
        save.getStyleClass().add("button-light");

    }

    @Override
    public void layoutControls() {
        buttonBox.getChildren().addAll(cancel, save);
        getChildren().addAll(tabPane, buttonBox);
    }

    @Override
    public void setupBindings() {
        dbTab.textProperty().bind(rootPM.dbTitleProperty());
        dbLabel.textProperty().bind(rootPM.dbTextProperty());
        csvButton.textProperty().bind(rootPM.dbCsvTextProperty());
        sqliteButton.textProperty().bind(rootPM.dbSqliteTextProperty());
        neo4jButton.textProperty().bind(rootPM.dbNeo4jTextProperty());

        cancel.setOnAction(event -> {
            cancel.getScene().getWindow().hide();
        });
    }
}
