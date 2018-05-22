package ch.fhnw.oop2.hydropowerfx.view.editor;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

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

    private ImageView stationImage;

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
        stationImage.setFitHeight(200);
        stationImage.setFitWidth(350);

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
        editorHead.setRight(stationImage);
        editorHead.setMargin(title, new Insets(10, 10, 25, 10));
        editorHead.setMargin(stationImage, new Insets(10, 10, 25, 10));

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

        setActualBindings();

    }

    public void setActualBindings() {
        titleStationName.textProperty().bind(rootPM.getActualPowerStation().nameProperty());
        titlestationSite.textProperty().bind(rootPM.getActualPowerStation().siteProperty());
        titleStationCanton.textProperty().bind(rootPM.getActualPowerStation().cantonProperty());
        titleStationPowerOutput.textProperty().bind(rootPM.getActualPowerStation().maxPowerProperty().asString());
        titleStationStartOperation.textProperty().bind(rootPM.getActualPowerStation().startOperationProperty().asString());

        /*********************************** TextField Bindings ***********************************/
        stationName.textProperty().bindBidirectional(rootPM.getActualPowerStation().nameProperty());
        stationSite.textProperty().bindBidirectional(rootPM.getActualPowerStation().siteProperty());
        stationWaterflow.textProperty().bindBidirectional(rootPM.getActualPowerStation().maxWaterProperty(), new NumberStringConverter());
        startOperation.textProperty().bindBidirectional(rootPM.getActualPowerStation().startOperationProperty(), new NumberStringConverter());
        longitude.textProperty().bindBidirectional(rootPM.getActualPowerStation().longitudeProperty(), new NumberStringConverter());
        status.textProperty().bindBidirectional(rootPM.getActualPowerStation().statusProperty());
        waterbodies.textProperty().bindBidirectional(rootPM.getActualPowerStation().waterbodiesProperty());
        imageURL.textProperty().bindBidirectional(rootPM.getActualPowerStation().imgUrlProperty());
        powerOutput.textProperty().bindBidirectional(rootPM.getActualPowerStation().maxPowerProperty(), new NumberStringConverter());
        lastOperation.textProperty().bindBidirectional(rootPM.getActualPowerStation().lastOperationProperty(), new NumberStringConverter());
        latitude.textProperty().bindBidirectional(rootPM.getActualPowerStation().latitudeProperty(), new NumberStringConverter());
    }

    public void unsetBindings(PowerStation oldValue) {
        titleStationName.textProperty().unbind();
        titlestationSite.textProperty().unbind();
        titleStationCanton.textProperty().unbind();
        titleStationPowerOutput.textProperty().unbind();
        titleStationStartOperation.textProperty().unbind();

        /*********************************** TextField Bindings release ***********************************/
        stationName.textProperty().unbindBidirectional(oldValue.nameProperty());
        stationSite.textProperty().unbindBidirectional(oldValue.siteProperty());
        stationWaterflow.textProperty().unbindBidirectional(oldValue.maxWaterProperty());
        startOperation.textProperty().unbindBidirectional(oldValue.startOperationProperty());
        longitude.textProperty().unbindBidirectional(oldValue.longitudeProperty());
        status.textProperty().unbindBidirectional(oldValue.statusProperty());
        waterbodies.textProperty().unbindBidirectional(oldValue.waterbodiesProperty());
        imageURL.textProperty().unbindBidirectional(oldValue.imgUrlProperty());
        powerOutput.textProperty().unbindBidirectional(oldValue.maxPowerProperty());
        lastOperation.textProperty().unbindBidirectional(oldValue.lastOperationProperty());
        latitude.textProperty().unbindBidirectional(oldValue.latitudeProperty());
    }

    @Override
    public void setupValueChangedListeners() {
        rootPM.actualPowerStationProperty().addListener(((observable, oldValue, newValue) -> {
            unsetBindings((PowerStation) oldValue);
            setActualBindings();
        }));
    }
}
