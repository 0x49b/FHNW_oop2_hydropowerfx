package ch.fhnw.oop2.hydropowerfx.view.editor;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Editor extends VBox implements ViewMixin {

    private RootPM rootPM;


    private BorderPane editorHead;
    private VBox title;
    private GridPane editor;

    private Label titleStationName;
    private Label titlestationSite;
    private Label titleStationCanton;
    private Label titleStationPowerOutput;
    private Label titleStationStartOperation;


    private Label labelName;
    private Label labelPlace;
    private Label labelWaterflow;
    private Label labelstartOperation;
    private Label labelLongitude;
    private Label labelStatus;
    private Label labelwaterbodies;
    private Label labelImageURL;
    private Label labelType;
    private Label labelCanton;
    private Label labelPowerOutput;
    private Label labelLastOperation;
    private Label labelLatitude;

    private TextField stationName;
    private TextField stationSite;
    private TextField stationWaterflow;
    private TextField startOperation;
    private TextField longitude;
    private TextField status;
    private TextField waterbodies;
    private TextField imageURL;
    private ComboBox<Character> type;
    private ComboBox<String> canton;
    private TextField powerOutput;
    private TextField lastOperation;
    private TextField latitude;

    private TabPane editorTab;
    private Tab imageTab;
    private Tab mapTab;

    private ImageView stationImage;
    private ImageView mapImage;
    private Image mapImageRaw;
    private final double HEIGHT = 200.0;
    private final double WIDTH = 350.0;


    public Editor(RootPM rootPM) {
        this.rootPM = rootPM;
        this.init();
    }


    @Override
    public void initializeSelf() {
        this.getStyleClass().add("editor");
    }

    @Override
    public void initializeControls() {
        editorHead = new BorderPane();
        title = new VBox();
        editor = new GridPane();

        titleStationName = new Label();
        titleStationName.getStyleClass().addAll("editor-stationname-title");

        titlestationSite = new Label();
        titleStationCanton = new Label();
        titleStationPowerOutput = new Label();
        titleStationStartOperation = new Label();


        stationImage = new ImageView(new Image("https://i.stack.imgur.com/v1Yy8.png"));
        stationImage.getStyleClass().addAll("editor-stationimage");
        stationImage.setFitHeight(HEIGHT);
        stationImage.setFitWidth(WIDTH);

        System.out.println(rootPM.getMapURL());
        mapImageRaw = new Image(rootPM.getMapURL());
        mapImage = new ImageView(mapImageRaw);
        mapImage.getStyleClass().add("editor-mapsimage");
        mapImage.setFitHeight(HEIGHT);
        mapImage.setFitWidth(WIDTH);

        editorTab = new TabPane();
        editorTab.setPrefSize(WIDTH, HEIGHT + 50);
        editorTab.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        editorTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        imageTab = new Tab();
        mapTab = new Tab();

        imageTab.setContent(stationImage);
        imageTab.setText("Bild");
        mapTab.setContent(mapImage);
        mapTab.setText("Karte");

        editorTab.getTabs().addAll(imageTab, mapTab);

        labelName = new Label();
        labelPlace = new Label();
        labelWaterflow = new Label();
        labelstartOperation = new Label();
        labelLongitude = new Label();
        labelStatus = new Label();
        labelwaterbodies = new Label();
        labelImageURL = new Label();
        labelType = new Label();
        labelCanton = new Label();
        labelPowerOutput = new Label();
        labelLastOperation = new Label();
        labelLatitude = new Label();

        stationName = new TextField();
        stationSite = new TextField();
        stationWaterflow = new TextField();
        startOperation = new TextField();
        longitude = new TextField();
        status = new TextField();
        waterbodies = new TextField();
        imageURL = new TextField();
        type = new ComboBox<>();
        canton = new ComboBox<>();
        powerOutput = new TextField();
        lastOperation = new TextField();
        latitude = new TextField();
    }

    @Override
    public void layoutControls() {
        editor.setHgap(25);
        editor.setVgap(25);
        editor.setPadding(new Insets(10, 10, 10, 10));
        title.getChildren().addAll(titleStationName, titlestationSite, titleStationCanton, titleStationPowerOutput, titleStationStartOperation);

        editorHead.setCenter(title);
        editorHead.setRight(editorTab);
        editorHead.setMargin(title, new Insets(10, 10, 25, 10));
        editorHead.setMargin(editorTab, new Insets(10, 10, 25, 10));

        editor.add(labelName, 0, 0);
        editor.add(stationName, 1, 0);
        editor.add(labelType, 2, 0);
        editor.add(type, 3, 0);

        editor.add(labelPlace, 0, 1);
        editor.add(stationSite, 1, 1);
        editor.add(labelCanton, 2, 1);
        editor.add(canton, 3, 1);

        editor.add(labelWaterflow, 0, 2);
        editor.add(stationWaterflow, 1, 2);
        editor.add(labelPowerOutput, 2, 2);
        editor.add(powerOutput, 3, 2);

        editor.add(labelstartOperation, 0, 3);
        editor.add(startOperation, 1, 3);
        editor.add(labelLastOperation, 2, 3);
        editor.add(lastOperation, 3, 3);

        editor.add(labelLongitude, 0, 4);
        editor.add(longitude, 1, 4);
        editor.add(labelLatitude, 2, 4);
        editor.add(latitude, 3, 4);

        editor.add(labelStatus, 0, 5);
        editor.add(status, 1, 5);

        editor.add(labelwaterbodies, 0, 6);
        editor.add(waterbodies, 1, 6, 3, 1);

        editor.add(labelImageURL, 0, 7);
        editor.add(imageURL, 1, 7, 3, 1);

        getChildren().addAll(editorHead, editor);
    }

    @Override
    public void setupBindings() {

        /*********************************** Label Bindings ***********************************/
        labelName.textProperty().bind(rootPM.labelNameProperty());
        labelPlace.textProperty().bind(rootPM.labelPlaceProperty());
        labelWaterflow.textProperty().bind(rootPM.labelWaterflowProperty());
        labelstartOperation.textProperty().bind(rootPM.labelFirstOperationProperty());
        labelLongitude.textProperty().bind(rootPM.labelLongitudeProperty());
        labelStatus.textProperty().bind(rootPM.labelStatusProperty());
        labelwaterbodies.textProperty().bind(rootPM.labelUsedFlowsProperty());
        labelImageURL.textProperty().bind(rootPM.labelImageURLProperty());
        labelType.textProperty().bind(rootPM.labelTypeProperty());
        labelCanton.textProperty().bind(rootPM.labelCantonProperty());
        labelPowerOutput.textProperty().bind(rootPM.labelPowerOutputProperty());
        labelLastOperation.textProperty().bind(rootPM.labelLastOperationProperty());
        labelLatitude.textProperty().bind(rootPM.labelLatitudeProperty());

        titleStationName.textProperty().bind(rootPM.getPowerStationProxy().nameProperty());
        titlestationSite.textProperty().bind(rootPM.getPowerStationProxy().siteProperty());
        titleStationCanton.textProperty().bind(rootPM.getPowerStationProxy().cantonProperty());
        titleStationPowerOutput.textProperty().bind(rootPM.getPowerStationProxy().maxPowerProperty().asString());
        titleStationStartOperation.textProperty().bind(rootPM.getPowerStationProxy().startOperationProperty().asString());

        /*********************************** TextField Bindings ***********************************/
        stationName.textProperty().bindBidirectional(rootPM.getPowerStationProxy().nameProperty());
        stationSite.textProperty().bindBidirectional(rootPM.getPowerStationProxy().siteProperty());
        stationWaterflow.textProperty().bindBidirectional(rootPM.getPowerStationProxy().maxWaterProperty(), new NumberStringConverter());
        startOperation.textProperty().bindBidirectional(rootPM.getPowerStationProxy().startOperationProperty(), new NumberStringConverter());
        longitude.textProperty().bindBidirectional(rootPM.getPowerStationProxy().longitudeProperty(), new NumberStringConverter());
        status.textProperty().bindBidirectional(rootPM.getPowerStationProxy().statusProperty());
        waterbodies.textProperty().bindBidirectional(rootPM.getPowerStationProxy().waterbodiesProperty());
        imageURL.textProperty().bindBidirectional(rootPM.getPowerStationProxy().imgUrlProperty());
        powerOutput.textProperty().bindBidirectional(rootPM.getPowerStationProxy().maxPowerProperty(), new NumberStringConverter());
        lastOperation.textProperty().bindBidirectional(rootPM.getPowerStationProxy().lastOperationProperty(), new NumberStringConverter());
        latitude.textProperty().bindBidirectional(rootPM.getPowerStationProxy().latitudeProperty(), new NumberStringConverter());
    }

    @Override
    public void setupValueChangedListeners() {
      //TODO bind MapURL to ImageView
       mapImage.setOnMouseClicked(event -> {

           System.out.println(rootPM.getOnlineMapUrl());
           if (Desktop.isDesktopSupported()) {
               try {
                   Desktop.getDesktop().browse(new URI(rootPM.getOnlineMapUrl()));
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });
    }
}
