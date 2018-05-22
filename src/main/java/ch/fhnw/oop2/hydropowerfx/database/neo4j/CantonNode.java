package ch.fhnw.oop2.hydropowerfx.database.neo4j;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.Canton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import javafx.collections.ObservableList;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Canton")
public class CantonNode {

    private String cantonName;
    @Id
    private String shortName;
    private String number;
    private double civilVote;
    private int accession;
    private String mainVillage;
    private int population;
    private double foreigners;
    private int area;
    private int inhabitantDensity;
    private int villages;
    private String language;

    @Relationship(type = "LOCATED_IN", direction = "INCOMING")
    private Set<StationNode> powerStations = new HashSet<>();

    public CantonNode() {
    }

    public CantonNode(Canton canton) {
        setCantonName(canton.getCantonName());
        setShortName(canton.getShortName());
        setNumber(canton.getNumber());
        setCivilVote(canton.getCivilVote());
        setAccession(canton.getAccession());
        setMainVillage(canton.getMainVillage());
        setPopulation(canton.getPopulation());
        setForeigners(canton.getForeigners());
        setArea(canton.getArea());
        setInhabitantDensity(canton.getInhabitantDensity());
        setVillages(canton.getVillages());
        setLanguage(canton.getLanguage());
    }

    public Canton asCanton(ObservableList<PowerStation> stationList) {

        Canton canton = new Canton(stationList);

        canton.setCantonName(getCantonName());
        canton.setShortName(getShortName());
        canton.setNumber(getNumber());
        canton.setCivilVote(getCivilVote());
        canton.setAccession(getAccession());
        canton.setMainVillage(getMainVillage());
        canton.setPopulation(getPopulation());
        canton.setForeigners(getForeigners());
        canton.setArea(getArea());
        canton.setInhabitantDensity(getInhabitantDensity());
        canton.setVillages(getVillages());
        canton.setLanguage(getLanguage());


        return canton;
    }

    public String getCantonName() {
        return cantonName;
    }

    public void setCantonName(String cantonName) {
        this.cantonName = cantonName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getCivilVote() {
        return civilVote;
    }

    public void setCivilVote(double civilVote) {
        this.civilVote = civilVote;
    }

    public int getAccession() {
        return accession;
    }

    public void setAccession(int accession) {
        this.accession = accession;
    }

    public String getMainVillage() {
        return mainVillage;
    }

    public void setMainVillage(String mainVillage) {
        this.mainVillage = mainVillage;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getForeigners() {
        return foreigners;
    }

    public void setForeigners(double foreigners) {
        this.foreigners = foreigners;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getInhabitantDensity() {
        return inhabitantDensity;
    }

    public void setInhabitantDensity(int inhabitantDensity) {
        this.inhabitantDensity = inhabitantDensity;
    }

    public int getVillages() {
        return villages;
    }

    public void setVillages(int villages) {
        this.villages = villages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<StationNode> getPowerStations() {
        return powerStations;
    }

    public void setPowerStations(Set<StationNode> powerStations) {
        this.powerStations = powerStations;
    }
}
