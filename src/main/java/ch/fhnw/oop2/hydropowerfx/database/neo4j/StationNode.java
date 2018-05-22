package ch.fhnw.oop2.hydropowerfx.database.neo4j;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "PowerStation")
public class StationNode {

    @Id
    String entitiyID;
    String name;
    String type;
    String site;
    String canton;
    String maxWater;
    String maxPower;
    String startOperation;
    String lastOperation;
    String latitude;
    String longitude;
    String status;
    String waterbodies;
    String imgUrl;

    @Relationship(type = "LOCATED_IN")
    CantonNode cantonNode = null;

    public StationNode() {
    }

    public StationNode(PowerStation station) {
        setEntitiyID(station.getEntitiyID());
        setName(station.getName());
        setType(station.getType());
        setSite(station.getSite());
        setCanton(station.getCanton());
        setMaxWater(station.getMaxWater());
        setMaxPower(station.getMaxPower());
        setStartOperation(station.getStartOperation());
        setLastOperation(station.getLastOperation());
        setLatitude(station.getLatitude());
        setLongitude(station.getLongitude());
        setStatus(station.getStatus());
        setWaterbodies(station.getWaterbodies());
        setImgUrl(station.getImgUrl());
    }

    public StationNode(String[] fields) {
        setEntitiyID(fields[0]);
        setName(fields[1]);
        setType(fields[2]);
        setSite(fields[3]);
        setCanton(fields[4]);
        setMaxWater(fields[5]);
        setMaxPower(fields[6]);
        setStartOperation(fields[7]);
        setLastOperation(fields[8]);
        setLatitude(fields[9]);
        setLongitude(fields[10]);
        setStatus(fields[11]);
        setWaterbodies(fields[12]);
        setImgUrl(fields[13]);
    }

    public PowerStation asPowerStation() {
        PowerStation station = new PowerStation();

        station.setEntitiyID(getEntitiyID());
        station.setName(getName());
        station.setType(getType());
        station.setSite(getSite());
        station.setCanton(getCanton());
        station.setMaxWater(getMaxWater());
        station.setMaxPower(getMaxPower());
        station.setStartOperation(getStartOperation());
        station.setLastOperation(getLastOperation());
        station.setLatitude(getLatitude());
        station.setLongitude(getLongitude());
        station.setStatus(getStatus());
        station.setWaterbodies(getWaterbodies());
        station.setImgUrl(getImgUrl());

        return station;
    }

    public String getEntitiyID() {
        return entitiyID;
    }

    public void setEntitiyID(String entitiyID) {
        this.entitiyID = entitiyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getMaxWater() {
        return maxWater;
    }

    public void setMaxWater(String maxWater) {
        this.maxWater = maxWater;
    }

    public String getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(String maxPower) {
        this.maxPower = maxPower;
    }

    public String getStartOperation() {
        return startOperation;
    }

    public void setStartOperation(String startOperation) {
        this.startOperation = startOperation;
    }

    public String getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(String lastOperation) {
        this.lastOperation = lastOperation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaterbodies() {
        return waterbodies;
    }

    public void setWaterbodies(String waterbodies) {
        this.waterbodies = waterbodies;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public CantonNode getCantonNode() {
        return cantonNode;
    }

    public void setCantonNode(CantonNode cantonNode) {
        if (this.cantonNode != null) {
            this.cantonNode.getPowerStations().remove(this);
        }
        this.cantonNode = cantonNode;
        cantonNode.getPowerStations().add(this);
    }
}
