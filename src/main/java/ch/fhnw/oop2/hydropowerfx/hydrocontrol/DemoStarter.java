package ch.fhnw.oop2.hydropowerfx.hydrocontrol;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class DemoStarter extends Application {


    @Override
    public void start(Stage primaryStage) {
        PresentationModel pm = new PresentationModel();
        Region rootPanel = new DemoPane(pm);

        Scene scene = new Scene(rootPanel);

        primaryStage.setTitle("Simple Control Demo");
        primaryStage.setScene(scene);
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);

        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
