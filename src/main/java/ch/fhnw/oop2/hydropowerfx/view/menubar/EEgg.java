package ch.fhnw.oop2.hydropowerfx.view.menubar;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Random;

public class EEgg extends StackPane {

    // Constants
    private final int INTRO_DURATION = 8000;
    private final int LOGO_DURATION = 9000;
    private final int CRAWL_TEXT_DURATION = 75;
    private final String INTRO_TEXT = "Es war einmal vor langer Zeit\nin einer weit, weit\nentfernten Galaxis....";
    private final String CRAWL_TEXT = "\n\n\n\n\n\nEpisode FS-XVIII\n\n\nDAS ERWACHEN VON JUNIT\n\n\nEs herrscht Semesterkrieg. Die Studenten, deren Repos von einem geheimen " + "Rechenzentrum aus betrieben werden, haben ihren ersten Sieg gegen den grossen galaktischen Dozenten gewonnen.\n\n" + "Während der Schlacht um MSP ist es den Studenten gelungen, einen Push auf den Masterbranch vorzunehmen " + "und ihr Projekt rechtzeitig abzugeben.\n\n" + "Verfolgt vom bösen BufferOverflow und seinen NullpointerException jagen die Studenten mit einer Flotte Debugger den Sommerferien entgegen um für den nächsten Kampf bereit zu sein....";

    private int width;
    private int height;

    private URL starwarsSound;
    private AudioClip starwarsClip;

    public EEgg(Stage primaryStage) {
        this.getStyleClass().add("stack-pane");
        String stylesheet = EEgg.class.getResource("/ch/fhnw/oop2/hydropowerfx/view/assets/html/egg/egg.css").toExternalForm();
        this.getStylesheets().add(stylesheet);


        Font.loadFont(EEgg.class.getResource("/ch/fhnw/oop2/hydropowerfx/view/assets/fonts/NewsCycle-Regular.ttf").toExternalForm(), 10);
        Font.loadFont(EEgg.class.getResource("/ch/fhnw/oop2/hydropowerfx/view/assets/fonts/SFDistantGalaxy.ttf").toExternalForm(), 10);

        width = (int) primaryStage.getWidth();
        height = (int) primaryStage.getHeight();

        generateStarBackground(this);

        /************************************************* StarWars Sound ********************************************/

        starwarsSound = getClass().getResource("/hydrocontrol/sounds/starwars.wav");
        starwarsClip = new AudioClip(starwarsSound.toExternalForm());
        starwarsClip.play();

        /************************************************* Intro Text ************************************************/
        Text introNode = createIntroText();
        FadeTransition introNodeFade = new FadeTransition(Duration.millis(INTRO_DURATION), introNode);

        introNodeFade.setFromValue(1.0);
        introNodeFade.setToValue(0.0);
        introNodeFade.setCycleCount(1);
        introNodeFade.setAutoReverse(false);


        /************************************************* Logo Text ************************************************/
        ImageView logoNode = createLogo();
        logoNode.setVisible(false);

        ScaleTransition logoNodeScale = new ScaleTransition(Duration.millis(LOGO_DURATION), logoNode);
        logoNodeScale.setByX(-1.001f);
        logoNodeScale.setByY(-1.001f);

        /************************************************* Crawl Text ************************************************/
        Text textNode = createCrawlText();
        textNode.setVisible(false);
        Translate crawlTextTranslate = new Translate();
        textNode.getTransforms().add(new Rotate(-60, 300, height / 2, height / 30, Rotate.X_AXIS));
        textNode.getTransforms().add(crawlTextTranslate);

        Timeline crawlTextTimeline = new Timeline(new KeyFrame(Duration.seconds(CRAWL_TEXT_DURATION), new KeyValue(crawlTextTranslate.yProperty(), -7 * height)));
        textNode.setTranslateY(2 * height);

        Label escape = new Label("press esc to quit");
        escape.getStyleClass().add("escape-label");
        StackPane.setAlignment(escape, Pos.BOTTOM_CENTER);

        this.getChildren().addAll(introNode, logoNode, textNode, escape);

        introNodeFade.setOnFinished(event -> logoNode.setVisible(true));
        logoNodeScale.setOnFinished(event -> textNode.setVisible(true));
        crawlTextTimeline.setOnFinished(e -> this.getScene().getWindow().hide());

        SequentialTransition s = new SequentialTransition(introNodeFade, logoNodeScale, crawlTextTimeline);
        s.play();
    }

    public Scene createScene(StackPane root) {
        Scene scene = new Scene(root);
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setDepthTest(DepthTest.ENABLE);
        scene.setCamera(camera);

        //Listener to close Window
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (key == KeyCode.ESCAPE) {
                scene.getWindow().hide();
                starwarsClip.stop();
            }
        });

        return scene;
    }

    private Text createIntroText() {
        Text introNode = new Text(INTRO_TEXT);
        introNode.setWrappingWidth(width / 2);
        introNode.setFill(Color.rgb(75, 213, 238));
        introNode.setX(+35);
        introNode.setFont(Font.font("News Cycle", width / 25));
        return introNode;
    }

    private ImageView createLogo() {
        ImageView logoNode = new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/star_wars.png")));
        return logoNode;
    }

    private Text createCrawlText() {
        Text textNode = new Text(CRAWL_TEXT);
        textNode.setWrappingWidth(width);
        textNode.setFont(Font.font("News Cycle", width / 14));
        textNode.setFill(Color.rgb(229, 177, 58));
        textNode.setTextAlignment(TextAlignment.CENTER);
        textNode.getStyleClass().add("crawltext-text");

        return textNode;
    }

    private void generateStarBackground(StackPane root) {
        int maxNumberOfStars = width * height / 1000;
        Random range = new Random();

        for (int i = 1; i <= maxNumberOfStars; i++) {
            double hue = range.nextDouble() * 360;
            double saturation = range.nextDouble() * 0.1;
            Color color = Color.hsb(hue, saturation, 1.0);
            Circle circle = new Circle(range.nextInt(width), range.nextInt(height), 2 * range.nextDouble(), color);
            circle.setManaged(false);
            circle.setTranslateZ(range.nextDouble() * height * 1.25);
            root.getChildren().add(circle);
        }
    }

}
