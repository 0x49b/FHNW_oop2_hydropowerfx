package ch.fhnw.oop2.hydropowerfx.database;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.Canton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Comparator;

public abstract class Database {

    protected ObservableList<Canton> cantonList;
    protected ObservableList<PowerStation> stationList;

    public Database(ObservableList<Canton> cantonList, ObservableList<PowerStation> stationList) {

        this.cantonList = cantonList;
        this.stationList = stationList;
    }

    protected void init() {
        if (this.cantonList.size() > 0) {
            this.cantonList.forEach(canton -> this.addCanton(canton));
        }
        else {
            this.getAllCantons().stream().forEach(canton -> cantonList.add(canton));
        }

        if (this.stationList.size() > 0) {
            this.stationList.forEach(station -> this.addStation(station));
        }
        else {
            this.getAllStations().stream().forEach(station -> stationList.add(station));
        }

        stationList.sort(Comparator.comparing(PowerStation::getEntitiyID));

        this.addListeners();
    }

    public void close() {
    }

    private final void addListeners() {
        cantonList.addListener((ListChangeListener<Canton>) c -> {
            while (c.next()) {
                if(c.wasAdded()){
                    int start = c.getFrom() ;
                    int end = c.getTo() ;
                    for (int i = start ; i < end ; i++) {
                        this.addCanton(cantonList.get(i));
                    }
                }
                if(c.wasPermutated()){
                    // TODO handle on permutation if needed
                }
                if(c.wasRemoved()){
                    for(Canton canton : c.getRemoved()) {
                        this.deleteCanton(canton);
                    }
                }
                if(c.wasReplaced()){
                    // TODO handle replacement if needed
                }
                if(c.wasUpdated()){
                    int start = c.getFrom() ;
                    int end = c.getTo() ;
                    for (int i = start ; i < end ; i++) {
                        this.updateCanton(cantonList.get(i));
                    }
                }
            }
        });

        stationList.addListener((ListChangeListener<PowerStation>) c -> {
            while (c.next()) {
                if(c.wasAdded()){
                    int start = c.getFrom() ;
                    int end = c.getTo() ;
                    for (int i = start ; i < end ; i++) {
                        this.addStation(stationList.get(i));
                    }
                }
                if(c.wasPermutated()){
                    // TODO handle on permutation if needed
                }
                if(c.wasRemoved()){
                    for(PowerStation station : c.getRemoved()) {
                        this.deleteStation(station);
                    }
                }
                if(c.wasReplaced()){
                    // TODO handle replacement if needed
                }
                if(c.wasUpdated()){
                    int start = c.getFrom() ;
                    int end = c.getTo() ;
                    for (int i = start ; i < end ; i++) {
                        this.updateStation(stationList.get(i));
                    }
                }
            }
        });
    }

    protected abstract Collection<Canton> getAllCantons();

    protected abstract void addCanton(Canton canton);
    protected abstract void updateCanton(Canton canton);
    protected abstract void deleteCanton(Canton canton);


    protected abstract Collection<PowerStation> getAllStations();

    protected abstract void addStation(PowerStation station);
    protected abstract void updateStation(PowerStation station);
    protected abstract void deleteStation(PowerStation station);
}
