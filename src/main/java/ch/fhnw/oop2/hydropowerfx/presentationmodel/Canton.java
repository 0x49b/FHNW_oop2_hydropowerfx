package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Canton {

    private StringProperty cantonName = new SimpleStringProperty();
    private StringProperty shortName = new SimpleStringProperty();
    private StringProperty number = new SimpleStringProperty();
    private StringProperty civilVote = new SimpleStringProperty();
    private StringProperty accession = new SimpleStringProperty();
    private StringProperty mainVillage = new SimpleStringProperty();
    private StringProperty population = new SimpleStringProperty();
    private StringProperty foreigners = new SimpleStringProperty();
    private StringProperty area = new SimpleStringProperty();
    private StringProperty inhabitantDensity = new SimpleStringProperty();
    private StringProperty villages = new SimpleStringProperty();
    private StringProperty language = new SimpleStringProperty();

    public Canton(String[] fields) {
        setCantonName(fields[0]);
        setShortName(fields[1]);
        setNumber(fields[2]);
        setCivilVote(fields[3]);
        setAccession(fields[4]);
        setMainVillage(fields[5]);
        setPopulation(fields[6]);
        setForeigners(fields[7]);
        setArea(fields[8]);
        setInhabitantDensity(fields[9]);
        setVillages(fields[10]);
        setLanguage(fields[11]);
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

    public String getCivilVote() {
        return civilVote.get();
    }

    public StringProperty civilVoteProperty() {
        return civilVote;
    }

    public void setCivilVote(String civilVote) {
        this.civilVote.set(civilVote);
    }

    public String getAccession() {
        return accession.get();
    }

    public StringProperty accessionProperty() {
        return accession;
    }

    public void setAccession(String accession) {
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

    public String getPopulation() {
        return population.get();
    }

    public StringProperty populationProperty() {
        return population;
    }

    public void setPopulation(String population) {
        this.population.set(population);
    }

    public String getForeigners() {
        return foreigners.get();
    }

    public StringProperty foreignersProperty() {
        return foreigners;
    }

    public void setForeigners(String foreigners) {
        this.foreigners.set(foreigners);
    }

    public String getArea() {
        return area.get();
    }

    public StringProperty areaProperty() {
        return area;
    }

    public void setArea(String area) {
        this.area.set(area);
    }

    public String getInhabitantDensity() {
        return inhabitantDensity.get();
    }

    public StringProperty inhabitantDensityProperty() {
        return inhabitantDensity;
    }

    public void setInhabitantDensity(String inhabitantDensity) {
        this.inhabitantDensity.set(inhabitantDensity);
    }

    public String getVillages() {
        return villages.get();
    }

    public StringProperty villagesProperty() {
        return villages;
    }

    public void setVillages(String villages) {
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
}
