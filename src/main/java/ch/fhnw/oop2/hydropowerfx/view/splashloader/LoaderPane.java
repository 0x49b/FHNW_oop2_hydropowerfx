package ch.fhnw.oop2.hydropowerfx.view.splashloader;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class LoaderPane extends StackPane implements ViewMixin {

    private final RootPM rootPM;
    private HydroControl cc;
    private VBox inner;
    private Label loaderText;
    private String[] texts = {"Staue Wasser...", "Berechne Turbinenflügel...", "Verlege Kabel...", "Öffne Schott...", "Wasser marsch!"};
    private int i = 0;
    private Timeline timeline;

    public LoaderPane(RootPM pm) {
        this.rootPM = pm;
        init();
        this.startAnim();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("../assets/style.css");
        this.getStyleClass().add("splashloader-pane");
    }

    @Override
    public void initializeControls() {
        inner = new VBox();
        inner.getStyleClass().add("splashloader-inner");

        loaderText = new Label();
        loaderText.getStyleClass().add("splashloader-label");
        loaderText.setPadding(new Insets(5,5,20,50));
        cc = new HydroControl(rootPM);

    }

    @Override
    public void layoutControls() {
        inner.getChildren().addAll(cc, loaderText);
        getChildren().add(inner);
    }


    @Override
    public void setupBindings() {
        loaderText.textProperty().bind(rootPM.loaderTextProperty());
    }

    private void setLabelTexts() {


        timeline = new Timeline(new KeyFrame(Duration.seconds(0), actionEvent -> {
            if (i < 5) {
                rootPM.setLoaderText(texts[i]);
                i++;
            }
        }), new KeyFrame(Duration.seconds(1.75)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void startAnim() {
        cc.startAnim();
        setLabelTexts();
    }

    public void stopAnim() {
        cc.stopAnim();
        timeline.stop();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
