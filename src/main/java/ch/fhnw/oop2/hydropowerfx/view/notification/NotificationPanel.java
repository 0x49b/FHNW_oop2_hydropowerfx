package ch.fhnw.oop2.hydropowerfx.view.notification;

import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class NotificationPanel extends Pane {

    private ImageView icon;
    private Image imageFile;
    private Label textLabel = new Label();
    private int showDuration = 2500;

    private final int NPHEIGHT = 35;
    private final int NPWIDTH = 120;
    private final int NPPADDING = 10;

    private RootPanel rootPanel;
    private NotificationPanel np;

    public enum Type {SUCCESS, ERROR, INFO, WARNING}
    public enum Position {TOPLEFT, TOPCENTER, TOPRIGHT, BOTTOMRIGHT, BOTTOMCENTER, BOTTOMLEFT}

    private NotificationPanel() {
        this.np = this;
        this.getStylesheets().add(this.getClass().getResource("assets/NotificationPanelStyle.css").toExternalForm());
        this.getStyleClass().add("notification-top");
    }

    public NotificationPanel(RootPanel rootPanel, String textLabel, Type type, int duration) {
        this();
        this.setRootPanel(rootPanel);
        this.setTextLabel(textLabel);
        this.setType(type);
        this.setShowDuration(duration);
        this.registerResizeListener();
    }

    public void setTextLabel(String textLabel) {
        this.textLabel.setText(textLabel);
    }

    public void setShowDuration(int duration) {
        this.showDuration = duration;
    }

    public void setRootPanel(RootPanel rootPanel) {
        this.rootPanel = rootPanel;
    }

    public void setType(Type type) {
        switch (type) {
            case SUCCESS:
                this.imageFile = new Image(this.getClass().getResource("assets/success.png").toExternalForm());
                this.getStyleClass().add("success");
                textLabel.getStyleClass().add("success-label");
                break;
            case ERROR:
                this.imageFile = new Image(this.getClass().getResource("assets/error.png").toExternalForm());
                this.getStyleClass().add("error");
                textLabel.getStyleClass().add("error-label");
                break;
            case INFO:
                this.imageFile = new Image(this.getClass().getResource("assets/info.png").toExternalForm());
                this.getStyleClass().add("info");
                textLabel.getStyleClass().add("info-label");
                break;
            case WARNING:
                this.imageFile = new Image(this.getClass().getResource("assets/warning.png").toExternalForm());
                this.getStyleClass().add("warning");
                textLabel.getStyleClass().add("warning-label");
                break;
        }
        this.icon = new ImageView(this.imageFile);
    }

    public void show() {
        this.icon.relocate(20, 10);
        this.textLabel.relocate(45, 8);
        this.textLabel.getStyleClass().add("text-label");

        np.setWidth(this.getContainerWidth());
        np.setHeight(this.getContainerHeight());

        np.setLayoutX(rootPanel.getWidth() - (this.getContainerWidth() + this.NPPADDING));
        np.setManaged(false);
        np.getChildren().addAll(this.icon, this.textLabel);
        rootPanel.getChildren().add(this);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(showDuration),
                ae -> this.hide()));
        timeline.play();
    }

    private void hide() {
        Timeline tick0 = new Timeline();
        tick0.setCycleCount(Timeline.INDEFINITE);
        tick0.getKeyFrames().add(
                new KeyFrame(new Duration(1), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        np.setLayoutY(np.getLayoutY() - 0.1);
                        if (np.getLayoutY() < (np.getContainerHeight() * -1)) {
                            tick0.stop();
                            np.removeNode();
                        }
                    }
                }));
        tick0.play();
    }

    private void registerResizeListener() {
        rootPanel.widthProperty().addListener((observable, oldValue, newValue) -> {
            int npPos = newValue.intValue() - (this.getContainerWidth() + 10);
            np.setLayoutX(npPos);
        });
    }

    //TODO Remove Node --> Memory Usage!!! (Garbage Collector?)
    private void removeNode(){
        rootPanel.getChildren().remove(np);
        // Remove Listener first
         // np = null;
    }


    private int getContainerWidth() {
        int labelWidth = (this.textLabel.getText().length() * 3) + this.NPWIDTH;

        if (labelWidth < this.NPWIDTH) {
            return this.NPWIDTH;
        } else {
            return labelWidth;
        }
    }

    private int getContainerHeight() {
        String[] lines = this.textLabel.getText().split("\n");
        int calcHeight = this.NPHEIGHT + (lines.length * 10);

        if (lines.length > 1) {
            return calcHeight;
        } else {
            return this.NPHEIGHT;
        }

    }

}
