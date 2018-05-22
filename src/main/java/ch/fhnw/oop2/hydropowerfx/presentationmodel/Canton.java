package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.stream.Collectors;

public class Canton {

    private StringProperty cantonName = new SimpleStringProperty();
    private StringProperty shortName = new SimpleStringProperty();
    private StringProperty number = new SimpleStringProperty();
    private DoubleProperty civilVote = new SimpleDoubleProperty();
    private IntegerProperty accession = new SimpleIntegerProperty();
    private StringProperty mainVillage = new SimpleStringProperty();
    private IntegerProperty population = new SimpleIntegerProperty();
    private DoubleProperty foreigners = new SimpleDoubleProperty();
    private IntegerProperty area = new SimpleIntegerProperty();
    private IntegerProperty inhabitantDensity = new SimpleIntegerProperty();
    private IntegerProperty villages = new SimpleIntegerProperty();
    private StringProperty language = new SimpleStringProperty();

    private FilteredList<PowerStation> cantonStationsList;
    private DoubleBinding totalPower;

    public Canton(ObservableList<PowerStation> stationList) {
        cantonStationsList = new FilteredList<>(stationList);
        cantonStationsList.setPredicate(s -> s.getCanton().equals(shortName.get()));

        setupBinding();
    }

    public Canton(String[] fields, ObservableList<PowerStation> list) {
        setCantonName(fields[0]);
        setShortName(fields[1]);
        setNumber(fields[2]);
        setCivilVote(Double.parseDouble(fields[3].replaceAll("'", "")));
        setAccession(Integer.parseInt(fields[4].replaceAll("'", "")));
        setMainVillage(fields[5]);
        setPopulation(Integer.parseInt(fields[6].replaceAll("'", "")));
        setForeigners(Double.parseDouble(fields[7].replaceAll("'", "")));
        setArea(Integer.parseInt(fields[8].replaceAll("'", "")));
        setInhabitantDensity(Integer.parseInt(fields[9].replaceAll("'", "")));
        setVillages(Integer.parseInt(fields[10].replaceAll("'", "")));
        setLanguage(fields[11]);

        cantonStationsList = new FilteredList<>(list);
        cantonStationsList.setPredicate(s -> s.getCanton().equals(shortName.get()));

        setupBinding();
    }

    private void setupBinding() {
        totalPower = Bindings.createDoubleBinding(
                () -> cantonStationsList.stream()
                        .collect(Collectors.summingDouble(PowerStation::getMaxPower)), cantonStationsList
        );
    }

    public String getCantonName() {
        return cantonName.get();
    }

    public StringProperty cantonNameProperty() {
        return cantonName;
    }

    public void setCantonName(String cantonName) {
        this.cantonName.set(cantonName);
    }

    public String getShortName() {
        return shortName.get();
    }

    public StringProperty shortNameProperty() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public double getCivilVote() {
        return civilVote.get();
    }

    public DoubleProperty civilVoteProperty() {
        return civilVote;
    }

    public void setCivilVote(double civilVote) {
        this.civilVote.set(civilVote);
    }

    public int getAccession() {
        return accession.get();
    }

    public IntegerProperty accessionProperty() {
        return accession;
    }

    public void setAccession(int accession) {
        this.accession.set(accession);
    }

    public String getMainVillage() {
        return mainVillage.get();
    }

    public StringProperty mainVillageProperty() {
        return mainVillage;
    }

    public void setMainVillage(String mainVillage) {
        this.mainVillage.set(mainVillage);
    }

    public int getPopulation() {
        return population.get();
    }

    public IntegerProperty populationProperty() {
        return population;
    }

    public void setPopulation(int population) {
        this.population.set(population);
    }

    public double getForeigners() {
        return foreigners.get();
    }

    public DoubleProperty foreignersProperty() {
        return foreigners;
    }

    public void setForeigners(double foreigners) {
        this.foreigners.set(foreigners);
    }

    public int getArea() {
        return area.get();
    }

    public IntegerProperty areaProperty() {
        return area;
    }

    public void setArea(int area) {
        this.area.set(area);
    }

    public int getInhabitantDensity() {
        return inhabitantDensity.get();
    }

    public IntegerProperty inhabitantDensityProperty() {
        return inhabitantDensity;
    }

    public void setInhabitantDensity(int inhabitantDensity) {
        this.inhabitantDensity.set(inhabitantDensity);
    }

    public int getVillages() {
        return villages.get();
    }

    public IntegerProperty villagesProperty() {
        return villages;
    }

    public void setVillages(int villages) {
        this.villages.set(villages);
    }

    public String getLanguage() {
        return language.get();
    }

    public StringProperty languageProperty() {
        return language;
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public FilteredList<PowerStation> getCantonStationsList() {
        return cantonStationsList;
    }

    public void setCantonStationsList(FilteredList<PowerStation> cantonStationsList) {
        this.cantonStationsList = cantonStationsList;
    }

    public Number getTotalPower() {
        return totalPower.get();
    }

    public DoubleBinding totalPowerProperty() {
        return totalPower;
    }
}
