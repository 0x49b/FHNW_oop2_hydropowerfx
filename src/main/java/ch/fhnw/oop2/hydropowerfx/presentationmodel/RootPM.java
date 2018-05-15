package ch.fhnw.oop2.hydropowerfx.presentationmodel;

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

    private final StringProperty applicationTitle     = new SimpleStringProperty("HydroPowerFX");
    private final StringProperty versionInformation = new SimpleStringProperty("V0.1");
    private final BooleanProperty searchpanelShown = new SimpleBooleanProperty(false);
    private final StringProperty stationListTitleText = new SimpleStringProperty("Kraftwerke");
    private final StringProperty currentMaxItemsText = new SimpleStringProperty("");
    private final ObjectProperty<PowerStation> actualPowerStation = new SimpleObjectProperty();
    private final ObservableList<PowerStation> powerStationList = FXCollections.observableArrayList(station -> new Observable[] { station.maxPowerProperty() });
    private final FilteredList<PowerStation> powerStationFilterList = new FilteredList<>(powerStationList);
    private final IntegerBinding totalPowerStations = Bindings.size(powerStationList);
    private final IntegerBinding actualPowerStations = Bindings.size(powerStationFilterList);
    private final ObservableList<Canton> cantons = FXCollections.observableArrayList();

    public RootPM() {
        powerStationList.addAll(readPowerStations());
        cantons.addAll(readCantons());
        setActualPowerStation(powerStationList.get(0));
        setupBindings();
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

                actualPowerStationsProperty().asString().concat("/").concat(totalPowerStationsProperty()));
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

    public Number getActualPowerStations() {
        return actualPowerStations.get();
    }

    public IntegerBinding actualPowerStationsProperty() {
        return actualPowerStations;
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
}
