package ch.fhnw.oop2.hydropowerfx.view.menubar;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class SearchPanel extends StackPane implements ViewMixin {

    private final int SPHEIGHT = 50;
    private final int SPWIDTH = 250;
    private RootPanel rootPanel;
    private RootPM rootPM;
    private Menubar menubar;
    private SearchPanel sp;
    private TextField searchInput;
    private Boolean shown = false;

    public SearchPanel(RootPanel rootPanel, RootPM rootPM, Menubar menubar) {
        this.rootPanel = rootPanel;
        this.rootPM = rootPM;
        this.menubar = menubar;
        this.sp = this;
        this.inits();
        this.init();
    }


    private void inits() {
        this.getStyleClass().add("search-panel");
        this.setManaged(false);
        this.rootPanel.getChildren().add(this);
        this.setWidth(SPWIDTH);
        this.setHeight(SPHEIGHT);

    }

    @Override
    public void initializeControls() {
        searchInput = new TextField();
    }

    @Override
    public void layoutControls() {
        this.getChildren().add(searchInput);
    }

    @Override
    public void setupEventHandlers() {
        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.hide();
            }
        });

    }

    @Override
    public void setupValueChangedListeners() {

    }

    @Override
    public void setupBindings() {

    }

    public void showhide() {
        //Todo Remove that silly ....
        System.out.println(this.rootPM.searchpanelShownProperty());

        if (this.rootPM.searchpanelShownProperty().getValue()) {
            this.hide();
        } else {
            this.show();
        }
    }


    public void show() {
        this.rootPM.setSearchpanelShown(true);
        this.setLayoutY(this.menubar.getSearch().getLayoutY());
        this.setLayoutX(50);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(new Duration(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        sp.setLayoutX(sp.getLayoutX() + 0.3);
                        if (sp.getLayoutY() < (sp.SPWIDTH)) {
                            timeline.stop();
                        }
                    }
                })
        );

    }

    public void hide() {
        this.rootPM.setSearchpanelShown(false);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.getKeyFrames().add(
                new KeyFrame(new Duration(1), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        sp.setLayoutX(sp.getLayoutX() - 0.3);
                        if (sp.getLayoutY() < (sp.SPWIDTH * -1)) {
                            timeline.stop();
                        }
                    }
                }));
        timeline.play();
    }

    public Boolean isShown() {
        return shown;
    }

    public void setShown(Boolean shown) {
        this.shown = shown;
    }
}
