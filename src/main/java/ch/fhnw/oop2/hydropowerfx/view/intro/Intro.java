package ch.fhnw.oop2.hydropowerfx.view.intro;

import ch.fhnw.oop2.hydropowerfx.HydroPowerApp;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

import java.util.List;

public class Intro extends Pane implements ViewMixin {
    private RootPM rootPM;
    private Shape rootOverlay;
    private Shape overlay;
    private Shape actualShape;
    private List<IntroItem> introItems;
    private Label text = new Label();
    private ImageView arrow;
    private GridPane controls;
    private Button forward;
    private Button backward;
    private Button close;
    private CheckBox dontShowAgain;

    public Intro(RootPM rootPM) {
        this.rootPM = rootPM;
        this.introItems = rootPM.getIntroItems();

        setLayoutY(0);
        setLayoutY(0);
        setManaged(false);

        init();
    }

    @Override
    public void initializeControls() {
        overlay = new Rectangle(rootPM.getPrimaryStage().getWidth(), rootPM.getPrimaryStage().getHeight());

        rootOverlay = new Rectangle(rootPM.getPrimaryStage().getWidth(), rootPM.getPrimaryStage().getHeight());

        overlay.getStyleClass().add("intro-overlay");

        controls = new GridPane();
        controls.getStyleClass().addAll("intro-controls");

        forward = new Button();
        forward.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_forward.png"))));
        forward.getStyleClass().addAll("intro-button", "intro-forward");
        forward.setTooltip(new Tooltip("Weiter"));

        backward = new Button();
        backward.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_backward.png"))));
        backward.getStyleClass().addAll("intro-button", "intro-backward");
        backward.setTooltip(new Tooltip("Zurück"));

        close = new Button("Schliessen");

        dontShowAgain = new CheckBox();
        dontShowAgain.setText("nicht erneut anzeigen");
        dontShowAgain.getStyleClass().add("intro-checkbox");
    }

    @Override
    public void layoutControls() {
        this.getChildren().add(overlay);

        Bounds boundsInScene = overlay.localToScene(overlay.getBoundsInLocal());

        ColumnConstraints cc = new ColumnConstraints();
        cc.setMinWidth(100.0);
        cc.setHalignment(HPos.CENTER);

        controls.setManaged(false);

        controls.setHgap(5);
        controls.setVgap(5);

        controls.add(backward,0, 0,1,2);
        controls.add(close, 1,0, 2, 1);
        controls.add(dontShowAgain, 1,1, 2, 1);
        controls.add(forward, 3, 0,2,2);

        controls.getColumnConstraints().addAll(cc, cc, cc, cc);

        controls.setLayoutX((boundsInScene.getMaxX()/2) - 300);
        controls.setLayoutY(boundsInScene.getMaxY() - 75);

        this.getChildren().add(controls);
    }

    @Override
    public void setupBindings() {
        backward.disableProperty().bind(rootPM.hasNoPreviousIntroItemProperty());
        forward.disableProperty().bind(rootPM.hasNoNextIntroItemProperty());
    }

    @Override
    public void setupEventHandlers() {
        forward.setOnAction(event -> rootPM.nextIntroItem());
        backward.setOnAction(event -> rootPM.previousIntroItem());
        close.setOnAction(event -> rootPM.closeIntro(!dontShowAgain.isSelected()));
    }

    public void updateUI(IntroItem item) {
        if (this.getChildren().contains(text)) {
            this.getChildren().remove(text);
        }
        if (this.getChildren().contains(arrow)) {
            this.getChildren().remove(arrow);
        }
        updateOverlay(item);

        arrow = getArrow(item);
        this.getChildren().add(arrow);

        text = getLabel(item, arrow);
        this.getChildren().add(text);
    }

    private void updateOverlay(IntroItem item) {
        Bounds boundsInScene = item.getNode().localToScene(item.getNode().getBoundsInLocal());
        Shape shape = new Rectangle(boundsInScene.getWidth(), boundsInScene.getHeight());

        ((Rectangle) shape).setLayoutX(boundsInScene.getMinX());
        ((Rectangle) shape).setLayoutY(boundsInScene.getMinY());

        shape.getStyleClass().add("intro-transparent");

        Shape newOverlay = Shape.subtract(rootOverlay, shape);

        newOverlay.getStyleClass().add("intro-overlay");

        if (this.getChildren().contains(actualShape)) {
            this.getChildren().remove(actualShape);
        }

        this.getChildren().removeAll(overlay, controls);
        this.actualShape = shape;
        this.overlay = newOverlay;

        this.getChildren().addAll(overlay, actualShape, controls);
    }

    private ImageView getArrow(IntroItem item) {
        String path;
        ImageView arrow;
        Bounds boundsInScene = item.getNode().localToScene(item.getNode().getBoundsInLocal());

        if (item.getArrow() == IntroItem.ARROW.DOWMLEFT) {
            path = "/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_down_left.png";
            arrow = new ImageView(new Image(getClass().getResourceAsStream(path)));
            arrow.setTranslateX(boundsInScene.getMaxX());
            arrow.setTranslateY(boundsInScene.getMaxY() - 150);
        }
        else if (item.getArrow() == IntroItem.ARROW.DOWNRIGHT) {
            path = "/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_down_right.png";
            arrow = new ImageView(new Image(getClass().getResourceAsStream(path)));
            arrow.setTranslateX(boundsInScene.getMinX() - 143);
            arrow.setTranslateY(boundsInScene.getMaxY() - 150);
        }
        else if (item.getArrow() == IntroItem.ARROW.LEFTDOWN) {
            path = "/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_left_down.png";
            arrow = new ImageView(new Image(getClass().getResourceAsStream(path)));
            arrow.setTranslateX(boundsInScene.getMinX());
            arrow.setTranslateY(boundsInScene.getMinY() - 143);
        }
        else if (item.getArrow() == IntroItem.ARROW.LEFTUP) {
            path = "/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_left_up.png";
            arrow = new ImageView(new Image(getClass().getResourceAsStream(path)));
            arrow.setTranslateX(boundsInScene.getMaxX() - 150);
            arrow.setTranslateY(boundsInScene.getMaxY());
        }
        else if (item.getArrow() == IntroItem.ARROW.RIGHTDOWN) {
            path = "/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_right_down.png";
            arrow = new ImageView(new Image(getClass().getResourceAsStream(path)));
            arrow.setTranslateX(boundsInScene.getMaxX() - 150);
            arrow.setTranslateY(boundsInScene.getMinY() - 143);
        }
        else if (item.getArrow() == IntroItem.ARROW.RIGHTUP) {
            path = "/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_right_up.png";
            arrow = new ImageView(new Image(getClass().getResourceAsStream(path)));
            arrow.setTranslateX(boundsInScene.getMaxX() - 150);
            arrow.setTranslateY(boundsInScene.getMaxY());
        }
        else if (item.getArrow() == IntroItem.ARROW.UPLEFT) {
            path = "/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_up_left.png";
            arrow = new ImageView(new Image(getClass().getResourceAsStream(path)));
            arrow.setTranslateX(boundsInScene.getMaxX());
            arrow.setTranslateY(boundsInScene.getMinY());
        }
        else {
            path = "/ch/fhnw/oop2/hydropowerfx/view/assets/arrows/arrow_up_right.png";
            arrow = new ImageView(new Image(getClass().getResourceAsStream(path)));
            arrow.setTranslateX(boundsInScene.getMinX() - 143);
            arrow.setTranslateY(boundsInScene.getMinY());
        }

        return arrow;
    }

    private Label getLabel(IntroItem item, ImageView arrow) {
        Bounds boundsInScene = arrow.localToScene(arrow.getBoundsInLocal());

        Label textLabel = new Label();
        textLabel.getStyleClass().add("intro-text");
        textLabel.setText(item.getText());
        textLabel.setFont(Font.loadFont(HydroPowerApp.class.getResource("view/assets/fonts/HandofSean.ttf").toExternalForm(), 10));

        if (item.getArrow() == IntroItem.ARROW.DOWMLEFT) {
            textLabel.setLayoutX(boundsInScene.getMinX() + 20);
            textLabel.setLayoutY(boundsInScene.getMinY() - 40);
        }
        else if (item.getArrow() == IntroItem.ARROW.DOWNRIGHT) {
            textLabel.setLayoutX(boundsInScene.getMaxX() - textLabel.getWidth());
            textLabel.setLayoutY(boundsInScene.getMinY() - textLabel.getHeight());
        }
        else if (item.getArrow() == IntroItem.ARROW.LEFTDOWN) {
            textLabel.setLayoutX(boundsInScene.getMinX() - textLabel.getWidth());
            textLabel.setLayoutY(boundsInScene.getMinY());
        }
        else if (item.getArrow() == IntroItem.ARROW.LEFTUP) {
            textLabel.setLayoutX(boundsInScene.getMaxX());
            textLabel.setLayoutY(boundsInScene.getMaxY() - textLabel.getHeight());
        }
        else if (item.getArrow() == IntroItem.ARROW.RIGHTDOWN) {
            textLabel.setTranslateX(boundsInScene.getMaxX() - 500);
            textLabel.setLayoutY(boundsInScene.getMinY() - 15);
        }
        else if (item.getArrow() == IntroItem.ARROW.RIGHTUP) {
            textLabel.setLayoutX(boundsInScene.getMinX() - textLabel.getWidth());
            textLabel.setLayoutY(boundsInScene.getMaxY() - textLabel.getHeight());
        }
        else if (item.getArrow() == IntroItem.ARROW.UPLEFT) {
            textLabel.setLayoutX(boundsInScene.getMinX() + 20);
            textLabel.setLayoutY(boundsInScene.getMaxY());
        }
        else {
            textLabel.setLayoutX(boundsInScene.getMaxX() - 300);
            textLabel.setLayoutY(boundsInScene.getMinY() + 160);
        }

        return textLabel;
    }

}
