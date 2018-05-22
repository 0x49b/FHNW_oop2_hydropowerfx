package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import ch.fhnw.oop2.hydropowerfx.database.Database;
import ch.fhnw.oop2.hydropowerfx.database.neo4j.Neo4j;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RootPM {

    private static final String POWERSTATIONS_FILE = "/data/HYDRO_POWERSTATION.csv";
    private static final String CANTONS_FILE = "/data/cantons.csv";
    private static final String DELIMITER = ";";

    private Database database;

    private final StringProperty applicationTitle     = new SimpleStringProperty("HydroPowerFX");
    private final StringProperty versionInformation = new SimpleStringProperty("V0.1");
    private final BooleanProperty searchpanelShown = new SimpleBooleanProperty(false);
    private final StringProperty stationListTitleText = new SimpleStringProperty("Kraftwerke");
    private final StringProperty currentMaxItemsText = new SimpleStringProperty("");
    private final ObjectProperty<PowerStation> actualPowerStation = new SimpleObjectProperty();
    private final ObservableList<PowerStation> powerStationList = FXCollections.observableArrayList(station -> new Observable[] {
            station.nameProperty(),
            station.typeProperty(),
            station.siteProperty(),
            station.cantonProperty(),
            station.maxWaterProperty(),
            station.maxPowerProperty(),
            station.startOperationProperty(),
            station.lastOperationProperty(),
            station.latitudeProperty(),
            station.longitudeProperty(),
            station.statusProperty(),
            station.waterbodiesProperty(),
            station.imgUrlProperty()
    });
    private final FilteredList<PowerStation> powerStationFilterList = new FilteredList<>(powerStationList);
    private final IntegerBinding totalPowerStations = Bindings.size(powerStationList);
    private final IntegerBinding numberOfPowerStations = Bindings.size(powerStationFilterList);
    private final ObservableList<Canton> cantons = FXCollections.observableArrayList();

    /************************************************ Editor Head Title ********************************************/

    /*editorTitleStationName;
    editorTitleStationPlace;
    editorTitleStationCanton;
    editorTitleStationPowerOutput;
    editorTitleStationFirstOperation;*/

    /************************************************ Editor Labels ************************************************/

    private final StringProperty labelName= new SimpleStringProperty("Name");
    private final StringProperty labelPlace= new SimpleStringProperty("Standort");
    private final StringProperty labelWaterflow= new SimpleStringProperty("Wassermenge");
    private final StringProperty labelFirstOperation= new SimpleStringProperty("1. Inbetriebnahme");
    private final StringProperty labelLongitude= new SimpleStringProperty("Breitengrad");
    private final StringProperty labelStatus= new SimpleStringProperty("Status");
    private final StringProperty labelUsedFlows= new SimpleStringProperty("Genutzte Gewässer");
    private final StringProperty labelImageURL= new SimpleStringProperty("Bild");
    private final StringProperty labelType= new SimpleStringProperty("Typ");
    private final StringProperty labelCanton= new SimpleStringProperty("Kanton");
    private final StringProperty labelPowerOutput= new SimpleStringProperty("Leistung (MW)");
    private final StringProperty labelLastOperation= new SimpleStringProperty("Sanierung");
    private final StringProperty labelLatitude= new SimpleStringProperty("Längengrad");

    public RootPM() {
        cantons.addAll(readCantons());
        powerStationList.addAll(readPowerStations());

        // TODO before activating database setup preferences panel
        // database = new Neo4j(cantons, powerStationList);

        setActualPowerStation(powerStationList.get(0));
        setupBindings();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    /************************************************ File reading and writing ************************************************/

    private List<PowerStation> readPowerStations() {
        try (Stream<String> stream = getStreamOfLines(POWERSTATIONS_FILE)) {
            return stream.skip(1)
                    .map(line -> new PowerStation(line.split(DELIMITER, 22))).collect(Collectors.toList());
        }
    }

    private List<Canton> readCantons() {
        try (Stream<String> stream = getStreamOfLines(CANTONS_FILE)) {
            return stream.skip(1)
                    .map(line -> new Canton(line.split(DELIMITER, 22), powerStationList)).collect(Collectors.toList());
        }
    }

    public void save() {
        //TODO implement
    }

    private Path getPath(String fileName)  {
        try {
            return Paths.get(getClass().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Stream<String> getStreamOfLines(String fileName) {
        try {
            return Files.lines(getPath(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /************************************************ Helper functions ************************************************/

    public void setupBindings() {
        currentMaxItemsTextProperty().bind(

                numberOfPowerStationsProperty().asString().concat("/").concat(totalPowerStationsProperty()));
    }

    public void searchPowerStations(String search) {
        if(search == null || search.length() == 0) {
            powerStationFilterList.setPredicate(s -> true);
        }
        else {
            powerStationFilterList.setPredicate(s -> s.getCanton().contains(search) || s.getName().contains(search));
        }
    }

    /************************************************ Property Methods ************************************************/

    public ObservableList<PowerStation> getPowerStationList() {
        return powerStationList;
    }

    public FilteredList<PowerStation> getPowerStationFilterList() {
        return powerStationFilterList;
    }

    // general properties
    public String getApplicationTitle() {
        return applicationTitle.get();
    }
    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }
    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    // Menubar properties setter & getter
    public String getVersionInformation() {  return versionInformation.get(); }
    public StringProperty versionInformationProperty() { return versionInformation; }
    public void setVersionInformation(String versionInformation) { this.versionInformation.set(versionInformation); }


    // searchpanel
    public boolean isSearchpanelShown() {
        return searchpanelShown.get();
    }
    public BooleanProperty searchpanelShownProperty() {
        return searchpanelShown;
    }
    public void setSearchpanelShown(boolean searchpanelShown) {
        this.searchpanelShown.set(searchpanelShown);
    }

    // stationlist
    public String getStationListTitleText() {
        return stationListTitleText.get();
    }
    public StringProperty stationListTitleTextProperty() {
        return stationListTitleText;
    }
    public void setStationListTitleText(String stationListTitleText) { this.stationListTitleText.set(stationListTitleText); }
    public String getCurrentMaxItemsText() {
        return currentMaxItemsText.get();
    }
    public StringProperty currentMaxItemsTextProperty() {
        return currentMaxItemsText;
    }
    public void setCurrentMaxItemsText(String currentMaxItemsText) {this.currentMaxItemsText.set(currentMaxItemsText); }

    public Number getTotalPowerStations() {
        return totalPowerStations.get();
    }

    public IntegerBinding totalPowerStationsProperty() {
        return totalPowerStations;
    }

    public Number getNumberOfPowerStations() {
        return numberOfPowerStations.get();
    }

    public IntegerBinding numberOfPowerStationsProperty() {
        return numberOfPowerStations;
    }

    public PowerStation getActualPowerStation() {
        return actualPowerStation.get();
    }

    public ObjectProperty actualPowerStationProperty() {
        return actualPowerStation;
    }

    public void setActualPowerStation(PowerStation actualPowerStation) {
        this.actualPowerStation.set(actualPowerStation);
    }

    /************************************************ Editor Label Property Functions ************************************************/
    public String getLabelName() {
        return labelName.get();
    }

    public StringProperty labelNameProperty() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName.set(labelName);
    }

    public String getLabelPlace() {
        return labelPlace.get();
    }

    public StringProperty labelPlaceProperty() {
        return labelPlace;
    }

    public void setLabelPlace(String labelPlace) {
        this.labelPlace.set(labelPlace);
    }

    public String getLabelWaterflow() {
        return labelWaterflow.get();
    }

    public StringProperty labelWaterflowProperty() {
        return labelWaterflow;
    }

    public void setLabelWaterflow(String labelWaterflow) {
        this.labelWaterflow.set(labelWaterflow);
    }

    public String getLabelFirstOperation() {
        return labelFirstOperation.get();
    }

    public StringProperty labelFirstOperationProperty() {
        return labelFirstOperation;
    }

    public void setLabelFirstOperation(String labelFirstOperation) {
        this.labelFirstOperation.set(labelFirstOperation);
    }

    public String getLabelLongitude() {
        return labelLongitude.get();
    }

    public StringProperty labelLongitudeProperty() {
        return labelLongitude;
    }

    public void setLabelLongitude(String labelLongitude) {
        this.labelLongitude.set(labelLongitude);
    }

    public String getLabelStatus() {
        return labelStatus.get();
    }

    public StringProperty labelStatusProperty() {
        return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
        this.labelStatus.set(labelStatus);
    }

    public String getLabelUsedFlows() {
        return labelUsedFlows.get();
    }

    public StringProperty labelUsedFlowsProperty() {
        return labelUsedFlows;
    }

    public void setLabelUsedFlows(String labelUsedFlows) {
        this.labelUsedFlows.set(labelUsedFlows);
    }

    public String getLabelImageURL() {
        return labelImageURL.get();
    }

    public StringProperty labelImageURLProperty() {
        return labelImageURL;
    }

    public void setLabelImageURL(String labelImageURL) {
        this.labelImageURL.set(labelImageURL);
    }

    public String getLabelType() {
        return labelType.get();
    }

    public StringProperty labelTypeProperty() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType.set(labelType);
    }

    public String getLabelCanton() {
        return labelCanton.get();
    }

    public StringProperty labelCantonProperty() {
        return labelCanton;
    }

    public void setLabelCanton(String labelCanton) {
        this.labelCanton.set(labelCanton);
    }

    public String getLabelPowerOutput() {
        return labelPowerOutput.get();
    }

    public StringProperty labelPowerOutputProperty() {
        return labelPowerOutput;
    }

    public void setLabelPowerOutput(String labelPowerOutput) {
        this.labelPowerOutput.set(labelPowerOutput);
    }

    public String getLabelLastOperation() {
        return labelLastOperation.get();
    }

    public StringProperty labelLastOperationProperty() {
        return labelLastOperation;
    }

    public void setLabelLastOperation(String labelLastOperation) {
        this.labelLastOperation.set(labelLastOperation);
    }

    public String getLabelLatitude() {
        return labelLatitude.get();
    }

    public StringProperty labelLatitudeProperty() {
        return labelLatitude;
    }

    public void setLabelLatitude(String labelLatitude) {
        this.labelLatitude.set(labelLatitude);
    }
}
