package ch.fhnw.oop2.hydropowerfx;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import ch.fhnw.oop2.hydropowerfx.view.intro.Intro;
import ch.fhnw.oop2.hydropowerfx.view.splashloader.LoaderPane;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Locale;

public class HydroPowerApp extends Application {

    private Parent loaderPane;
    private RootPM rootPM;
    private static final Locale CH = new Locale("de", "CH");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Font.loadFont(HydroPowerApp.class.getResource("view/assets/fonts/Montserrat-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(HydroPowerApp.class.getResource("view/assets/fonts/Rubik-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(HydroPowerApp.class.getResource("view/assets/fonts/HandofSean.ttf").toExternalForm(), 10);
        Locale.setDefault(CH);

        Stage loaderStage = new Stage();
        showSplash(loaderStage);

        FadeTransition fadeSplash = new FadeTransition(Duration.seconds(8.0), loaderPane);
        fadeSplash.play();

        fadeSplash.setOnFinished(event -> {
            loaderStage.hide();
            showMainStage(primaryStage);
        });

    }


    private void showSplash(Stage loaderStage) {
       loaderPane = new LoaderPane(rootPM);
       Scene scene = new Scene(loaderPane);

       loaderStage.setScene(scene);
       loaderStage.setWidth(300);
       loaderStage.setHeight(450);
       loaderStage.initStyle(StageStyle.UNDECORATED);
       loaderStage.show();

    }

    private void showMainStage(Stage primaryStage) {
        rootPM = new RootPM();
        Parent rootPanel = new RootPanel(rootPM);
        rootPM.setPrimaryStage(primaryStage);
        Scene scene = new Scene(rootPanel);



        primaryStage.titleProperty().bind(rootPM.applicationTitleProperty());
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(1100);
        primaryStage.setHeight(780);
        primaryStage.show();

        rootPM.initIntro();
    }

    @Override
    public void stop() throws Exception {
        rootPM.close();
        super.stop();
    }

    public RootPM getRootPM() {
        return rootPM;
    }

    public void setRootPM(RootPM rootPM) {
        this.rootPM = rootPM;
    }
}
