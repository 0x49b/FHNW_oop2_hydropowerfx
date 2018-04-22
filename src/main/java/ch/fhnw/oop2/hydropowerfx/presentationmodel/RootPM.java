package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RootPM {
    private final StringProperty applicationTitle     = new SimpleStringProperty("HydroPowerFX");
    private final StringProperty successButtonCaption = new SimpleStringProperty("success Notification");
    private final StringProperty errorButtonCaption   = new SimpleStringProperty("error Notification");
    private final StringProperty infoButtonCaption   = new SimpleStringProperty("info Notification");
    private final StringProperty warningButtonCaption   = new SimpleStringProperty("warn Notification");

    // all getters and setters
    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public String getSuccessButtonCaption() { return successButtonCaption.get(); }

    public StringProperty successButtonCaptionProperty() { return successButtonCaption; }

    public void setSuccessButtonCaption(String successButtonCaption) { this.successButtonCaption.set(successButtonCaption); }

    public String getErrorButtonCaption() { return errorButtonCaption.get(); }

    public StringProperty errorButtonCaptionProperty() { return errorButtonCaption; }

    public void setErrorButtonCaption(String errorButtonCaption) { this.errorButtonCaption.set(errorButtonCaption); }

    public String getInfoButtonCaption() {
        return infoButtonCaption.get();
    }

    public StringProperty infoButtonCaptionProperty() {
        return infoButtonCaption;
    }

    public void setInfoButtonCaption(String infoButtonCaption) {
        this.infoButtonCaption.set(infoButtonCaption);
    }

    public String getWarningButtonCaption() {
        return warningButtonCaption.get();
    }

    public StringProperty warningButtonCaptionProperty() {
        return warningButtonCaption;
    }

    public void setWarningButtonCaption(String warningButtonCaption) {
        this.warningButtonCaption.set(warningButtonCaption);
    }
}
