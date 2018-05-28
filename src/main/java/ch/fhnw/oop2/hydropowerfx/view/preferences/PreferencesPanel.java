package ch.fhnw.oop2.hydropowerfx.view.preferences;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

    public PreferencesPanel(RootPM rootPM) {
        this.rootPM = rootPM;

        init();
    }

    @Override
    public void initializeSelf() {
        this.getStyleClass().add("preferences-panel");
    }

    @Override public void initializeControls() {
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

        dbGroup = new ToggleGroup();
        csvButton = new RadioButton();
        csvButton.setToggleGroup(dbGroup);
        sqliteButton = new RadioButton();
        sqliteButton.setToggleGroup(dbGroup);
        neo4jButton = new RadioButton();
        neo4jButton.setToggleGroup(dbGroup);

        RootPM.DATABASES dbType = rootPM.getDatabaseType();

        if (dbType == RootPM.DATABASES.SQLITE) {
            sqliteButton.setSelected(true);
        }
        else if (dbType == RootPM.DATABASES.NEO4J) {
            neo4jButton.setSelected(true);
        }
        else {
            csvButton.setSelected(true);
        }

        vbox.getChildren().addAll(dbLabel, csvButton, sqliteButton, neo4jButton);

        tabPane.getTabs().add(dbTab);

        cancel = new Button("Abbrechen");
        save = new Button("Speichern");
    }

    @Override public void layoutControls() {
        getChildren().addAll(tabPane, cancel, save);
    }

    @Override public void setupEventHandlers() {
        cancel.setOnAction(event -> {
            cancel.getScene().getWindow().hide();
        });

        save.setOnAction(event -> {
            if (sqliteButton.isSelected()) {
                rootPM.updateDatabaseType(RootPM.DATABASES.SQLITE);
            }
            else if (neo4jButton.isSelected()) {
                rootPM.updateDatabaseType(RootPM.DATABASES.NEO4J);
            }
            else {
                rootPM.updateDatabaseType(RootPM.DATABASES.CSV);
            }

            cancel.getScene().getWindow().hide();
        });
    }

    @Override
    public void setupBindings() {
        dbTab.textProperty().bind(rootPM.dbTitleProperty());
        dbLabel.textProperty().bind(rootPM.dbTextProperty());
        csvButton.textProperty().bind(rootPM.dbCsvTextProperty());
        sqliteButton.textProperty().bind(rootPM.dbSqliteTextProperty());
        neo4jButton.textProperty().bind(rootPM.dbNeo4jTextProperty());
    }
}
