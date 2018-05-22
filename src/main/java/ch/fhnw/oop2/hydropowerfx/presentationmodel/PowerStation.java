package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PowerStation {
    private StringProperty entitiyID = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty site = new SimpleStringProperty();
    private StringProperty canton = new SimpleStringProperty();
    private StringProperty maxWater = new SimpleStringProperty();
    private StringProperty maxPower = new SimpleStringProperty();
    private StringProperty startOperation = new SimpleStringProperty();
    private StringProperty lastOperation = new SimpleStringProperty();
    private StringProperty latitude = new SimpleStringProperty();
    private StringProperty longitude = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();
    private StringProperty waterbodies = new SimpleStringProperty();
    private StringProperty imgUrl = new SimpleStringProperty();

    public PowerStation() {

    }

    public PowerStation(String[] fields) {
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

    public String getEntitiyID() {
        return entitiyID.get();
    }

    public StringProperty entitiyIDProperty() {
        return entitiyID;
    }

    public void setEntitiyID(String entitiyID) {
        this.entitiyID.set(entitiyID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getSite() {
        return site.get();
    }

    public StringProperty siteProperty() {
        return site;
    }

    public void setSite(String site) {
        this.site.set(site);
    }

    public String getCanton() {
        return canton.get();
    }

    public StringProperty cantonProperty() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton.set(canton);
    }

    public String getMaxWater() {
        return maxWater.get();
    }

    public StringProperty maxWaterProperty() {
        return maxWater;
    }

    public void setMaxWater(String maxWater) {
        this.maxWater.set(maxWater);
    }

    public String getMaxPower() {
        return maxPower.get();
    }

    public StringProperty maxPowerProperty() {
        return maxPower;
    }

    public void setMaxPower(String maxPower) {
        this.maxPower.set(maxPower);
    }

    public String getStartOperation() {
        return startOperation.get();
    }

    public StringProperty startOperationProperty() {
        return startOperation;
    }

    public void setStartOperation(String startOperation) {
        this.startOperation.set(startOperation);
    }

    public String getLastOperation() {
        return lastOperation.get();
    }

    public StringProperty lastOperationProperty() {
        return lastOperation;
    }

    public void setLastOperation(String lastOperation) {
        this.lastOperation.set(lastOperation);
    }

    public String getLatitude() {
        return latitude.get();
    }

    public StringProperty latitudeProperty() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude.set(latitude);
    }

    public String getLongitude() {
        return longitude.get();
    }

    public StringProperty longitudeProperty() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude.set(longitude);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getWaterbodies() {
        return waterbodies.get();
    }

    public StringProperty waterbodiesProperty() {
        return waterbodies;
    }

    public void setWaterbodies(String waterbodies) {
        this.waterbodies.set(waterbodies);
    }

    public String getImgUrl() {
        return imgUrl.get();
    }

    public StringProperty imgUrlProperty() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl.set(imgUrl);
    }
}
