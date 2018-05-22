package ch.fhnw.oop2.hydropowerfx.database.neo4j;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "PowerStation")
public class StationNode {

    @Id
    Long entityID;
    String name;
    String type;
    String site;
    String canton;
    double maxWater;
    double maxPower;
    int startOperation;
    int lastOperation;
    double latitude;
    double longitude;
    String status;
    String waterbodies;
    String imgUrl;

    @Relationship(type = "LOCATED_IN")
    CantonNode cantonNode = null;

    public StationNode() {
    }

    public StationNode(PowerStation station) {
        setEntityID(Long.valueOf(station.getEntitiyID()));
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

    public PowerStation asPowerStation() {
        PowerStation station = new PowerStation();

        station.setEntitiyID(getEntityID().intValue());
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

    public Long getEntityID() {
        return entityID;
    }

    public void setEntityID(Long entityID) {
        this.entityID = entityID;
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

    public double getMaxWater() {
        return maxWater;
    }

    public void setMaxWater(double maxWater) {
        this.maxWater = maxWater;
    }

    public double getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
    }

    public int getStartOperation() {
        return startOperation;
    }

    public void setStartOperation(int startOperation) {
        this.startOperation = startOperation;
    }

    public int getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(int lastOperation) {
        this.lastOperation = lastOperation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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
