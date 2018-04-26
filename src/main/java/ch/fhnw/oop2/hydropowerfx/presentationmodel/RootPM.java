package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class RootPM {
    private final StringProperty applicationTitle     = new SimpleStringProperty("HydroPowerFX");

    private final StringProperty versionInformation = new SimpleStringProperty("V0.1");

    private final StringProperty successButtonCaption = new SimpleStringProperty("success Notification");
    private final StringProperty errorButtonCaption   = new SimpleStringProperty("error Notification");
    private final StringProperty infoButtonCaption   = new SimpleStringProperty("info Notification");
    private final StringProperty warningButtonCaption   = new SimpleStringProperty("warn Notification");


    // Menubar properties setter & getter
    public String getVersionInformation() {  return versionInformation.get(); }
    public StringProperty versionInformationProperty() { return versionInformation; }
    public void setVersionInformation(String versionInformation) { this.versionInformation.set(versionInformation); }


    // all getters and setters for Notification TestButtons
    public String getApplicationTitle() { return applicationTitle.get(); }
    public StringProperty applicationTitleProperty() { return applicationTitle; }
    public void setApplicationTitle(String applicationTitle) { this.applicationTitle.set(applicationTitle); }
    public String getSuccessButtonCaption() { return successButtonCaption.get(); }
    public StringProperty successButtonCaptionProperty() { return successButtonCaption; }
    public void setSuccessButtonCaption(String successButtonCaption) { this.successButtonCaption.set(successButtonCaption); }
    public String getErrorButtonCaption() { return errorButtonCaption.get(); }
    public StringProperty errorButtonCaptionProperty() { return errorButtonCaption; }
    public void setErrorButtonCaption(String errorButtonCaption) { this.errorButtonCaption.set(errorButtonCaption); }
    public String getInfoButtonCaption() { return infoButtonCaption.get(); }
    public StringProperty infoButtonCaptionProperty() { return infoButtonCaption; }
    public void setInfoButtonCaption(String infoButtonCaption) { this.infoButtonCaption.set(infoButtonCaption); }
    public String getWarningButtonCaption() { return warningButtonCaption.get(); }
    public StringProperty warningButtonCaptionProperty() { return warningButtonCaption; }
    public void setWarningButtonCaption(String warningButtonCaption) { this.warningButtonCaption.set(warningButtonCaption); }
}
