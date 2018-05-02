package ch.fhnw.oop2.hydropowerfx;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class HydroPowerApp extends Application {

    private Pane splashLayout;
    private Stage splashStage;
    private int splash_width;
    private int splash_height;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        splashStage = new Stage();
        showSplash(splashStage);
        FadeTransition fadeSplash = new FadeTransition(Duration.seconds(2.0), splashLayout);
        fadeSplash.play();
        fadeSplash.setOnFinished(event -> {
            splashStage.hide();
            showMainStage(primaryStage);
        });

    }

    @Override
    public void init() {
        Image splashImage = new Image(this.getClass().getResource("view/assets/images/hydrofx-splash.png").toExternalForm());
        ImageView splash = new ImageView(splashImage);
        splash_width = (int) splashImage.getWidth();
        splash_height = (int) splashImage.getHeight();
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash);
        splashLayout.setEffect(new DropShadow());
    }

    private void showSplash(Stage initStage) {
        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.TRANSPARENT);
        splashScene.setFill(Color.TRANSPARENT);
        initStage.setScene(splashScene);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - splash_width / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - splash_height / 2);
        initStage.show();
    }

    private void showMainStage(Stage primaryStage) {
        RootPM rootPM = new RootPM();
        Parent rootPanel = new RootPanel(rootPM);
        Scene scene = new Scene(rootPanel);
        primaryStage.titleProperty().bind(rootPM.applicationTitleProperty());
        //primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);

        //Todo Width & Height has to be deleted when Application is Full Implemented
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}