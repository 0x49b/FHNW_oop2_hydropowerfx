package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private static final String FILE_NAME = "/data/HYDRO_POWERSTATION.csv";
    private static final String DELIMITER = ";";

    private final StringProperty applicationTitle     = new SimpleStringProperty("HydroPowerFX");
    private final StringProperty versionInformation = new SimpleStringProperty("V0.1");
    private final BooleanProperty searchpanelShown = new SimpleBooleanProperty(false);
    private final StringProperty stationListTitleText = new SimpleStringProperty("Kraftwerke");
    private final StringProperty currentMaxItemsText = new SimpleStringProperty("999/999");
    private final StringProperty editorStationName = new SimpleStringProperty("STATIONNAME");
    private final ObservableList<PowerStation> powerStationList = FXCollections.observableArrayList();
    IntegerBinding totalPowerStations = Bindings.size(powerStationList);

    public RootPM() {
        powerStationList.addAll(readFromFile(FILE_NAME));
        setupBindings();
    }

    /************************************************ File reading and writing ************************************************/

    private List<PowerStation> readFromFile(String fileName) {
        try (Stream<String> stream = getStreamOfLines(fileName)) {
            return stream.skip(1)
                    .map(line -> new PowerStation(line.split(DELIMITER, 22))).collect(Collectors.toList());
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

    public ObservableList<PowerStation> getPowerStationList() {
        return powerStationList;
    }

    /************************************************ Helper functions ************************************************/

    public void setupBindings() {
        currentMaxItemsTextProperty().bind(
                totalPowerStationsProperty().asString().concat("/").concat(totalPowerStationsProperty()));
    }

    /************************************************ Property Methods ************************************************/

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

    // editor
    public String getEditorStationName() {
        return editorStationName.get();
    }
    public StringProperty editorStationNameProperty() {
        return editorStationName;
    }
    public void setEditorStationName(String editorStationName) {
        this.editorStationName.set(editorStationName);
    }

    public Number getTotalPowerStations() {
        return totalPowerStations.get();
    }

    public IntegerBinding totalPowerStationsProperty() {
        return totalPowerStations;
    }
}
