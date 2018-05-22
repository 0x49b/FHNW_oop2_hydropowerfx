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
    private String civilVote;
    private String accession;
    private String mainVillage;
    private String population;
    private String foreigners;
    private String area;
    private String inhabitantDensity;
    private String villages;
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

    public String getCivilVote() {
        return civilVote;
    }

    public void setCivilVote(String civilVote) {
        this.civilVote = civilVote;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getMainVillage() {
        return mainVillage;
    }

    public void setMainVillage(String mainVillage) {
        this.mainVillage = mainVillage;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getForeigners() {
        return foreigners;
    }

    public void setForeigners(String foreigners) {
        this.foreigners = foreigners;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInhabitantDensity() {
        return inhabitantDensity;
    }

    public void setInhabitantDensity(String inhabitantDensity) {
        this.inhabitantDensity = inhabitantDensity;
    }

    public String getVillages() {
        return villages;
    }

    public void setVillages(String villages) {
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
