package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RootPM {

    private final StringProperty applicationTitle     = new SimpleStringProperty("HydroPowerFX bingo");
    private final StringProperty versionInformation = new SimpleStringProperty("V0.1");
    private final BooleanProperty searchpanelShown = new SimpleBooleanProperty(false);

    private final StringProperty stationListTitleText = new SimpleStringProperty("Kraftwerke");
    private final StringProperty currentMaxItemsText = new SimpleStringProperty("999/999");

    private final StringProperty editorStationName = new SimpleStringProperty("STATIONNAME");

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
    public String getVersionInformation() {  return versionInformation.get(); }
    public StringProperty versionInformationProperty() { return versionInformation; }
    public void setVersionInformation(String versionInformation) { this.versionInformation.set(versionInformation); }


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

    // editor


    public String getEditorStationName() {
        return editorStationName.get();
    }

    public StringProperty editorStationNameProperty() {
        return editorStationName;
    }

    public void setEditorStationName(String editorStationName) {
        this.editorStationName.set(editorStationName);
    }
}
