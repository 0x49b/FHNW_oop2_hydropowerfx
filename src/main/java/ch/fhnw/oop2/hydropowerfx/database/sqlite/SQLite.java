package ch.fhnw.oop2.hydropowerfx.database.sqlite;

import ch.fhnw.oop2.hydropowerfx.database.Database;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.Canton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.collections.ObservableList;

import java.util.Collection;

public class SQLite extends Database {

    public SQLite(RootPM rootPM, ObservableList<Canton> cantons, ObservableList<PowerStation> stations) {
        super(rootPM, cantons, stations);


        init();
    }

    @Override protected Collection<Canton> getAllCantons() {
        return null;
    }

    @Override protected void addCanton(Canton canton) {

    }

    @Override protected void updateCanton(Canton canton) {

    }

    @Override protected void deleteCanton(Canton canton) {

    }

    @Override protected Collection<PowerStation> getAllStations() {
        return null;
    }

    @Override protected void addStation(PowerStation station) {

    }

    @Override protected void updateStation(PowerStation station) {

    }

    @Override protected void deleteStation(PowerStation station) {

    }
}
