package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import ch.fhnw.oop2.hydropowerfx.database.Database;
import ch.fhnw.oop2.hydropowerfx.database.neo4j.Neo4j;
import ch.fhnw.oop2.hydropowerfx.database.sqlite.SQLite;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import ch.fhnw.oop2.hydropowerfx.view.notification.NotificationPanel;
import ch.fhnw.oop2.hydropowerfx.view.preferences.PreferencesPanel;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RootPM {

    public enum DATABASES {
        CSV(0), SQLITE(1), NEO4J(2);

        private int type;

        DATABASES(int type) {
            this.type = type;
        }

        public int getValue() {
            return this.type;
        }

        public static DATABASES fromInt(final int id) {
            return (id == 2) ? DATABASES.NEO4J : (id == 1) ? DATABASES.SQLITE : DATABASES.CSV;
        }
    }

    private final String DATABASETYPE = "DatabaseType";

    private static final String POWERSTATIONS_FILE = "/data/HYDRO_POWERSTATION.csv";
    private static final String CANTONS_FILE = "/data/cantons.csv";
    private static final String DELIMITER = ";";
    private String filePath;
    private URI filePathURI;
    private String powerStationFilePath;
    private String cantonFilePath;

    private final PowerStation powerStationProxy = new PowerStation();
    private Database database;
    private Stage primaryStage;
    private final StringProperty applicationTitle = new SimpleStringProperty("HydroPowerFX");
    private final StringProperty versionInformation = new SimpleStringProperty("V0.1");
    private final BooleanProperty searchpanelShown = new SimpleBooleanProperty(false);
    private final StringProperty stationListTitleText = new SimpleStringProperty("Kraftwerke");
    private final StringProperty currentMaxItemsText = new SimpleStringProperty("");
    private final StringProperty subtitle = new SimpleStringProperty();
    private final ObjectProperty<PowerStation> actualPowerStation = new SimpleObjectProperty();
    private final ObservableList<PowerStation> powerStationList = FXCollections.observableArrayList(station -> new Observable[]{station.nameProperty(), station.typeProperty(), station.siteProperty(), station.cantonProperty(), station.maxWaterProperty(), station.maxPowerProperty(), station.startOperationProperty(), station.lastOperationProperty(), station.latitudeProperty(), station.longitudeProperty(), station.statusProperty(), station.waterbodiesProperty(), station.imgUrlProperty()});
    private final FilteredList<PowerStation> powerStationFilterList = new FilteredList<>(powerStationList);
    private final IntegerBinding totalPowerStations = Bindings.size(powerStationList);
    private final IntegerBinding numberOfPowerStations = Bindings.size(powerStationFilterList);
    private final ObservableList<Canton> cantons = FXCollections.observableArrayList(canton -> new Observable[]{canton.totalPowerProperty()});

    private final StringProperty searchText = new SimpleStringProperty("");
    private final StringProperty cantonFilter = new SimpleStringProperty("");
    private final DoubleProperty totalSwissPowerOutput = new SimpleDoubleProperty();

    /************************************************ Editor Labels ************************************************/

    private final StringProperty labelName = new SimpleStringProperty("Name");
    private final StringProperty labelPlace = new SimpleStringProperty("Standort");
    private final StringProperty labelWaterflow = new SimpleStringProperty("Wassermenge");
    private final StringProperty labelFirstOperation = new SimpleStringProperty("1. Inbetriebnahme");
    private final StringProperty labelLongitude = new SimpleStringProperty("Breitengrad");
    private final StringProperty labelStatus = new SimpleStringProperty("Status");
    private final StringProperty labelUsedFlows = new SimpleStringProperty("Genutzte Gewässer");
    private final StringProperty labelImageURL = new SimpleStringProperty("Bild");
    private final StringProperty labelType = new SimpleStringProperty("Typ");
    private final StringProperty labelCanton = new SimpleStringProperty("Kanton");
    private final StringProperty labelPowerOutput = new SimpleStringProperty("Leistung (MW)");
    private final StringProperty labelLastOperation = new SimpleStringProperty("Sanierung");
    private final StringProperty labelLatitude = new SimpleStringProperty("Längengrad");

    /************************************************ Preferences Panel ************************************************/

    private Preferences prefs;

    private final StringProperty preferencesTitle = new SimpleStringProperty("Einstellungen");
    private final StringProperty dbTitle = new SimpleStringProperty("Datenbank");
    private final StringProperty dbText = new SimpleStringProperty("Wie sollen die Daten gespeichert werden?");
    private final StringProperty dbCsvText = new SimpleStringProperty("Speichern als CSV");
    private final StringProperty dbSqliteText = new SimpleStringProperty("Speichern in SQLite Datenbank");
    private final StringProperty dbNeo4jText = new SimpleStringProperty("Speichern in Neo4j Datenbank");
    private final StringProperty aboutText = new SimpleStringProperty("");

    /************************************************ Map Properties ************************************************/
    private final String GMAPS_API_KEY = "AIzaSyAjogSXUchu1pQFS3ZqjF06WYfYSGZZb-M";
    private final String ZOOM = "14";
    private final String SIZE = "350x200";
    private final String MAPTYPE = "satellite";
    private final StringProperty mapURL = new SimpleStringProperty("");

    /************************************************ Undo/Redo ************************************************/
    private final ObservableList<Command> undoStack = FXCollections.observableArrayList();
    private final ObservableList<Command> redoStack = FXCollections.observableArrayList();

    private final BooleanProperty undoDisabled = new SimpleBooleanProperty();
    private final BooleanProperty redoDisabled = new SimpleBooleanProperty();

    private final ChangeListener<Object> propertyChangeListenerForUndoSupport = (observable, oldValue, newValue) -> {
        redoStack.clear();
        undoStack.add(0, new ValueChangeCommand(this, (Property) observable, oldValue, newValue));
    };

    private final BooleanProperty disableSave = new SimpleBooleanProperty(true);

    public RootPM() {
        undoDisabled.bind(Bindings.isEmpty(undoStack));
        redoDisabled.bind(Bindings.isEmpty(redoStack));

        prefs = Preferences.userRoot().node(this.getClass().getName());
        DATABASES db = DATABASES.fromInt(prefs.getInt(DATABASETYPE, DATABASES.CSV.getValue()));

        moveCsvToUserFolder();

        initDatabase(db, true);

        cantons.sort(Comparator.comparing(Canton::getCantonName));

        setupBindings();
        setupListeners();

        setActualPowerStation(powerStationList.get(0));
        setTotalSwissPowerOutput(calcSwissPowerOutput());
        bindToProxy(powerStationList.get(0));
    }

    public void close() {
        closeDatabase();
    }

    private void closeDatabase() {
        if (database != null) {
            database.close();
        }
    }

    private void initDatabase(DATABASES dbType, boolean initial) {

        if (dbType == DATABASES.SQLITE) {
            database = new SQLite(this, cantons, powerStationList);
            prefs.putInt(DATABASETYPE, DATABASES.SQLITE.getValue());
            disableSave.set(true);
        } else if (dbType == DATABASES.NEO4J) {
            database = new Neo4j(this, cantons, powerStationList);
            prefs.putInt(DATABASETYPE, DATABASES.NEO4J.getValue());
            disableSave.set(true);
        } else {
            if (initial) {
                cantons.addAll(readCantons());
                powerStationList.addAll(readPowerStations());
            }
            closeDatabase();
            database = null;

            prefs.putInt(DATABASETYPE, DATABASES.CSV.getValue());
            disableSave.set(false);
        }
    }

    public void updateDatabaseType(DATABASES dbType) {
        if (dbType != DATABASES.fromInt(prefs.getInt(DATABASETYPE, DATABASES.CSV.getValue()))) {
            initDatabase(dbType, false);
        }
    }

    public DATABASES getDatabaseType() {
        return DATABASES.fromInt(prefs.getInt(DATABASETYPE, DATABASES.CSV.getValue()));
    }

    /************************************************ Primary Stage ***********************************************************/
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /************************************************ File reading and writing ************************************************/

    private List<PowerStation> readPowerStations() {
        try (Stream<String> stream = getStreamOfLines(powerStationFilePath)) {
            return stream.skip(1).map(line -> new PowerStation(line.split(DELIMITER, 22))).collect(Collectors.toList());
        }
    }

    private List<Canton> readCantons() {
        try (Stream<String> stream = getStreamOfLines(cantonFilePath)) {
            return stream.skip(1).map(line -> new Canton(line.split(DELIMITER, 22), powerStationList)).collect(Collectors.toList());
        }
    }

    public void save() {
        Path filePath = new File(powerStationFilePath).toPath();
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

            writer.write("ENTITY_ID;NAME;TYPE;SITE;CANTON;MAX_WATER_VOLUME_M3_S;MAX_POWER_MW;START_OF_OPERATION_FIRST;START_OF_OPERATION_LAST;LATITUDE;LONGITUDE;STATUS;WATERBODIES;IMAGE_URL"); writer.newLine();
            powerStationList.stream()
                    .map(resultat -> resultat.infoAsLine(DELIMITER))
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    });
            new NotificationPanel((RootPanel) primaryStage.getScene().getRoot(), "Daten gespeichert", NotificationPanel.Type.SUCCESS).show();

        } catch (IOException e) {
            new NotificationPanel((RootPanel) primaryStage.getScene().getRoot(), "Speichern fehlgeschlagen", NotificationPanel.Type.ERROR).show();
            throw new IllegalStateException("save failed"); }
    }

    private Stream<String> getStreamOfLines(String fileName) {
        try {
            return Files.lines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void moveCsvToUserFolder() {
        filePath = System.getProperty("user.home") + File.separator + "HydroPowerFX";
        File directory = new File(filePath);
        filePathURI = directory.toURI();

        if (directory.exists() || directory.mkdirs()) {
            powerStationFilePath = filePath + File.separator + "HYDRO_POWERSTATION.csv";
            cantonFilePath = filePath + File.separator + "cantons.csv";

            File powerStationFile = new File(powerStationFilePath);
            File cantonFile = new File(cantonFilePath);

            try {
                if (powerStationFile.createNewFile()) {
                    copyFileFromRessources(POWERSTATIONS_FILE, powerStationFile);
                }
            } catch (IOException e) {
                // TODO: handle exceptions
            }

            try {
                if (cantonFile.createNewFile()) {
                    copyFileFromRessources(CANTONS_FILE, cantonFile);
                }
            } catch (IOException e) {
                // TODO: handle exceptions
            }
        }
    }

    private void copyFileFromRessources(String source, File destination) {
        try {
            InputStream in = getClass().getResourceAsStream(source);
            if (in == null) {
                return;
            }

            try (FileOutputStream out = new FileOutputStream(destination)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /************************************************ Helper functions ************************************************/

    private void refreshMapURL() {

        StringBuilder coords = new StringBuilder();
        coords.append(getActualPowerStation().getLatitude());
        coords.append(",");
        coords.append(getActualPowerStation().getLongitude());

        String mapsURL = String.format("https://maps.googleapis.com/maps/api/staticmap?center=%s&markers=color:red%7Clabel:%s7C%s&zoom=%s&size=%s&maptype=%s&key=%s", coords.toString(), getActualPowerStation().getName(), coords.toString(), ZOOM, SIZE, MAPTYPE, GMAPS_API_KEY);
        setMapURL(mapsURL);
    }

    //TODO Funktion kann gelöscht werden wenn PropertyBinding korrekt implementiert ist
    public String getMapURL() {
        StringBuilder coords = new StringBuilder();
        coords.append(getActualPowerStation().getLatitude());
        coords.append(",");
        coords.append(getActualPowerStation().getLongitude());
        String coord = coords.toString();

        String mapsURL = String.format("https://maps.googleapis.com/maps/api/staticmap?center=%s&zoom=%s&size=%s&maptype=%s&markers=color:red|label:Sicher|%s&key=%s", coords, ZOOM, SIZE, MAPTYPE, coords, GMAPS_API_KEY);

        return mapsURL;
    }

    public String getImageURL() {
        return getActualPowerStation().getImgUrl();
    }

    public String getOnlineMapUrl() {
        StringBuilder map = new StringBuilder();
        map.append("https://www.google.com/maps/place/");
        map.append(getActualPowerStation().getLatitude());
        map.append(",");
        map.append(getActualPowerStation().getLongitude());
        return map.toString();
    }

    public StringProperty mapURLProperty() {
        return mapURL;
    }

    public void setMapURL(String mapURL) {
        this.mapURL.set(mapURL);
    }


    public void addPowerStation() {
        PowerStation ps = new PowerStation();

        int id = powerStationList.stream().max(Comparator.comparing(PowerStation::getEntitiyID)).get().getEntitiyID();
        id++;
        ps.setEntitiyID(id);
        // powerStationList.add(ps);
        addToList(powerStationList.size(), ps);
        actualPowerStation.set(ps);

        redoStack.clear();
        undoStack.add(0, new AddCommand(this, ps, powerStationList.size() - 1));
    }

    public void deletePowerStation() {
        PowerStation ps = getActualPowerStation();

        int index = powerStationList.indexOf(ps);
        // powerStationList.remove(ps);
        removeFromList(ps);

        actualPowerStation.set(powerStationFilterList.get(0));

        redoStack.clear();
        undoStack.add(0, new RemoveCommand(this, ps, index));
    }

    public void openPreferences() {
        Parent rootPanel = new PreferencesPanel(this);
        Scene scene = new Scene(rootPanel);
        Stage preferences = new Stage();
        preferences.initModality(Modality.APPLICATION_MODAL);
        preferences.titleProperty().bind(preferencesTitleProperty());
        preferences.setScene(scene);
        preferences.setWidth(500);
        preferences.setHeight(300);
        preferences.setResizable(false);
        preferences.initStyle(StageStyle.UNDECORATED);
        preferences.showAndWait();
    }

    private void setupBindings() {
        currentMaxItemsTextProperty().bind(numberOfPowerStationsProperty().asString().concat("/").concat(totalPowerStationsProperty()));
    }

    private void unbindFromProxy(PowerStation station) {
        powerStationProxy.entitiyIDProperty().unbindBidirectional(station.entitiyIDProperty());
        powerStationProxy.nameProperty().unbindBidirectional(station.nameProperty());
        powerStationProxy.typeProperty().unbindBidirectional(station.typeProperty());
        powerStationProxy.siteProperty().unbindBidirectional(station.siteProperty());
        powerStationProxy.cantonProperty().unbindBidirectional(station.cantonProperty());
        powerStationProxy.maxWaterProperty().unbindBidirectional(station.maxWaterProperty());
        powerStationProxy.maxPowerProperty().unbindBidirectional(station.maxPowerProperty());
        powerStationProxy.startOperationProperty().unbindBidirectional(station.startOperationProperty());
        powerStationProxy.lastOperationProperty().unbindBidirectional(station.lastOperationProperty());
        powerStationProxy.latitudeProperty().unbindBidirectional(station.latitudeProperty());
        powerStationProxy.longitudeProperty().unbindBidirectional(station.longitudeProperty());
        powerStationProxy.statusProperty().unbindBidirectional(station.statusProperty());
        powerStationProxy.waterbodiesProperty().unbindBidirectional(station.waterbodiesProperty());
        powerStationProxy.imgUrlProperty().unbindBidirectional(station.imgUrlProperty());
    }

    private void bindToProxy(PowerStation station) {
        powerStationProxy.entitiyIDProperty().bindBidirectional(station.entitiyIDProperty());
        powerStationProxy.nameProperty().bindBidirectional(station.nameProperty());
        powerStationProxy.typeProperty().bindBidirectional(station.typeProperty());
        powerStationProxy.siteProperty().bindBidirectional(station.siteProperty());
        powerStationProxy.cantonProperty().bindBidirectional(station.cantonProperty());
        powerStationProxy.maxWaterProperty().bindBidirectional(station.maxWaterProperty());
        powerStationProxy.maxPowerProperty().bindBidirectional(station.maxPowerProperty());
        powerStationProxy.startOperationProperty().bindBidirectional(station.startOperationProperty());
        powerStationProxy.lastOperationProperty().bindBidirectional(station.lastOperationProperty());
        powerStationProxy.latitudeProperty().bindBidirectional(station.latitudeProperty());
        powerStationProxy.longitudeProperty().bindBidirectional(station.longitudeProperty());
        powerStationProxy.statusProperty().bindBidirectional(station.statusProperty());
        powerStationProxy.waterbodiesProperty().bindBidirectional(station.waterbodiesProperty());
        powerStationProxy.imgUrlProperty().bindBidirectional(station.imgUrlProperty());
    }

    private void setupListeners() {
        searchText.addListener((observable, oldValue, newValue) -> filterPowerStations());

        cantonFilter.addListener((observable, oldValue, newValue) -> filterPowerStations());

        actualPowerStationProperty().addListener(((observable, oldValue, newValue) -> {

            if (oldValue != null) {
                unbindFromProxy((PowerStation) oldValue);
                disableUndoSupport((PowerStation) oldValue);
            }

            if (newValue != null) {
                bindToProxy((PowerStation) newValue);
                enableUndoSupport((PowerStation) newValue);
            }
        }));
    }

    public void filterPowerStations() {
        String search = getSearchText();

        if (search == null || search.length() == 0) {
            powerStationFilterList.setPredicate(s -> {
                boolean filter = true;

                if (getCantonFilter() != "") {
                    filter = s.getCanton().equals(getCantonFilter());
                }

                return filter;
            });
        } else {
            powerStationFilterList.setPredicate(s -> {
                boolean filter = true;

                filter = s.getName().contains(search);

                if (getCantonFilter() != "") {
                    filter = filter && s.getCanton().equals(getCantonFilter());
                }

                return filter;
            });
        }
    }

    /************************************************ Undo/Redo ************************************************/

    void setPropertyValue(Property property, Object newValue) {
        property.removeListener(propertyChangeListenerForUndoSupport);
        property.setValue(newValue);
        property.addListener(propertyChangeListenerForUndoSupport);
    }

    void addToList(int position, PowerStation station) {
        powerStationList.add(position, station);
        setActualPowerStation(station);
    }

    void removeFromList(PowerStation station) {
        unbindFromProxy(station);
        disableUndoSupport(station);

        powerStationList.remove(station);

        if (!powerStationFilterList.isEmpty()) {
            setActualPowerStation(powerStationFilterList.get(0));
        }
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            return;
        }
        Command cmd = undoStack.get(0);
        undoStack.remove(0);
        redoStack.add(0, cmd);

        cmd.undo();
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        }
        Command cmd = redoStack.get(0);
        redoStack.remove(0);
        undoStack.add(0, cmd);

        cmd.redo();
    }

    private void disableUndoSupport(PowerStation station) {
        station.entitiyIDProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.nameProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.typeProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.siteProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.cantonProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.maxWaterProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.maxPowerProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.startOperationProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.lastOperationProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.latitudeProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.longitudeProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.statusProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.waterbodiesProperty().removeListener(propertyChangeListenerForUndoSupport);
        station.imgUrlProperty().removeListener(propertyChangeListenerForUndoSupport);
    }

    private void enableUndoSupport(PowerStation station) {
        station.entitiyIDProperty().addListener(propertyChangeListenerForUndoSupport);
        station.nameProperty().addListener(propertyChangeListenerForUndoSupport);
        station.typeProperty().addListener(propertyChangeListenerForUndoSupport);
        station.siteProperty().addListener(propertyChangeListenerForUndoSupport);
        station.cantonProperty().addListener(propertyChangeListenerForUndoSupport);
        station.maxWaterProperty().addListener(propertyChangeListenerForUndoSupport);
        station.maxPowerProperty().addListener(propertyChangeListenerForUndoSupport);
        station.startOperationProperty().addListener(propertyChangeListenerForUndoSupport);
        station.lastOperationProperty().addListener(propertyChangeListenerForUndoSupport);
        station.latitudeProperty().addListener(propertyChangeListenerForUndoSupport);
        station.longitudeProperty().addListener(propertyChangeListenerForUndoSupport);
        station.statusProperty().addListener(propertyChangeListenerForUndoSupport);
        station.waterbodiesProperty().addListener(propertyChangeListenerForUndoSupport);
        station.imgUrlProperty().addListener(propertyChangeListenerForUndoSupport);
    }


    /************************************************ Property Methods ************************************************/

    public boolean isUndoDisabled() {
        return undoDisabled.get();
    }

    public BooleanProperty undoDisabledProperty() {
        return undoDisabled;
    }

    public void setUndoDisabled(boolean undoDisabled) {
        this.undoDisabled.set(undoDisabled);
    }

    public boolean isRedoDisabled() {
        return redoDisabled.get();
    }

    public BooleanProperty redoDisabledProperty() {
        return redoDisabled;
    }

    public void setRedoDisabled(boolean redoDisabled) {
        this.redoDisabled.set(redoDisabled);
    }

    public String getFilePath() {
        return filePath;
    }

    public String getPowerStationFilePath() {
        return powerStationFilePath;
    }

    public String getCantonFilePath() {
        return cantonFilePath;
    }

    public URI getFilePathURI() {
        return filePathURI;
    }

    public String getPreferencesTitle() {
        return preferencesTitle.get();
    }

    public StringProperty preferencesTitleProperty() {
        return preferencesTitle;
    }

    public void setPreferencesTitle(String preferencesTitle) {
        this.preferencesTitle.set(preferencesTitle);
    }

    public String getDbTitle() {
        return dbTitle.get();
    }

    public StringProperty dbTitleProperty() {
        return dbTitle;
    }

    public void setDbTitle(String dbTitle) {
        this.dbTitle.set(dbTitle);
    }

    public String getDbText() {
        return dbText.get();
    }

    public StringProperty dbTextProperty() {
        return dbText;
    }

    public void setDbText(String dbText) {
        this.dbText.set(dbText);
    }

    public String getDbCsvText() {
        return dbCsvText.get();
    }

    public StringProperty dbCsvTextProperty() {
        return dbCsvText;
    }

    public void setDbCsvText(String dbCsvText) {
        this.dbCsvText.set(dbCsvText);
    }

    public String getDbSqliteText() {
        return dbSqliteText.get();
    }

    public StringProperty dbSqliteTextProperty() {
        return dbSqliteText;
    }

    public void setDbSqliteText(String dbSqliteText) {
        this.dbSqliteText.set(dbSqliteText);
    }

    public String getDbNeo4jText() {
        return dbNeo4jText.get();
    }

    public StringProperty dbNeo4jTextProperty() {
        return dbNeo4jText;
    }

    public void setDbNeo4jText(String dbNeo4jText) {
        this.dbNeo4jText.set(dbNeo4jText);
    }

    public boolean getDisableSave() {
        return disableSave.get();
    }

    public BooleanProperty disableSaveProperty() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave.set(disableSave);
    }

    public PowerStation getPowerStationProxy() {
        return powerStationProxy;
    }

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
    public String getVersionInformation() {
        return versionInformation.get();
    }

    public StringProperty versionInformationProperty() {
        return versionInformation;
    }

    public void setVersionInformation(String versionInformation) {
        this.versionInformation.set(versionInformation);
    }

    public String getSearchText() {
        return searchText.get();
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText.set(searchText);
    }

    public String getCantonFilter() {
        return cantonFilter.get();
    }

    public StringProperty cantonFilterProperty() {
        return cantonFilter;
    }

    public void setCantonFilter(String cantonFilter) {
        this.cantonFilter.set(cantonFilter);
    }

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

    public void setStationListTitleText(String stationListTitleText) {
        this.stationListTitleText.set(stationListTitleText);
    }

    public String getCurrentMaxItemsText() {
        return currentMaxItemsText.get();
    }

    public StringProperty currentMaxItemsTextProperty() {
        return currentMaxItemsText;
    }

    public void setCurrentMaxItemsText(String currentMaxItemsText) {
        this.currentMaxItemsText.set(currentMaxItemsText);
    }

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
        this.createSubtitle();
    }

    public String getAboutText() {
        return aboutText.get();
    }

    public StringProperty aboutTextProperty() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText.set(aboutText);
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

    public ObservableList<Canton> getCantons() {
        return cantons;
    }

    public List<String> getCantonShort() {
        ObservableList<Canton> cantons = getCantons();
        return cantons.stream().map(e -> e.getShortName()).collect(Collectors.toList());
    }

    public List<String> getTypes() {
        ObservableList<PowerStation> powerstations = getPowerStationList();
        return powerstations.stream().map(e -> e.getType()).distinct().collect(Collectors.toList());
    }

    public void createSubtitle() {
        StringBuilder subtitle = new StringBuilder();
        subtitle.append(getActualPowerStation().getSite());
        subtitle.append(", ");
        subtitle.append(getActualPowerStation().getCanton());
        subtitle.append("  |  ");
        subtitle.append(getActualPowerStation().getMaxPower());
        subtitle.append(" MW");
        subtitle.append("  |  ");
        subtitle.append(getActualPowerStation().getStartOperation());
        setSubtitle(subtitle.toString());
    }

    public String getSubtitle() {
        return subtitle.get();
    }

    public StringProperty subtitleProperty() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.set(subtitle);
    }

    public double calcSwissPowerOutput() {
        return getPowerStationList().stream().mapToDouble(e -> e.getMaxPower()).sum();
    }

    public double getTotalSwissPowerOutput() {
        return totalSwissPowerOutput.get();
    }

    public DoubleProperty totalSwissPowerOutputProperty() {
        return totalSwissPowerOutput;
    }

    public void setTotalSwissPowerOutput(double totalSwissPowerOutput) {
        this.totalSwissPowerOutput.set(totalSwissPowerOutput);
    }
}
