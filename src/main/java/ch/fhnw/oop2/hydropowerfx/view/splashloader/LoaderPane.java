package ch.fhnw.oop2.hydropowerfx.view.splashloader;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

public class LoaderPane extends StackPane {

    private final RootPM pm;
    private HydroControl cc;

    public LoaderPane(RootPM pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        this.startAnim();
    }

    private void initializeSelf(){
        this.getStyleClass().add("loaderpane");
    }

    private void initializeControls() {
        setPadding(new Insets(5));
        cc = new HydroControl(pm);
    }

    private void layoutControls() {
        getChildren().add(cc);
    }

    public void startAnim(){
        cc.startAnim();
    }

    public void stopAnim(){
        cc.stopAnim();
    }
}
