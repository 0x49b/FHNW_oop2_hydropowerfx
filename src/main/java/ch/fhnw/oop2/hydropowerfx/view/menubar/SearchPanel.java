package ch.fhnw.oop2.hydropowerfx.view.menubar;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;


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
        // close search when escape is pressed
        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                hide();
            }
        });

        // close search input when lost focus
        searchInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                hide();
            }
        });
    }

    @Override
    public void setupValueChangedListeners() {
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            rootPM.searchPowerStations(searchInput.getText());
        });
    }

    @Override
    public void setupBindings() {
    }

    public void showhide() {
        if (this.rootPM.isSearchpanelShown()) {
            hide();
        } else {
            show();
        }
    }


    //get that search visible
    public void show() {
        this.setLayoutY(this.menubar.getSearch().getLayoutY());
        this.setLayoutX(50);

        searchInput.selectAll();
        searchInput.requestFocus();
        searchInput.isFocused();
        // searchInput.setText("");

        rootPanel.getChildren().add(this);
        rootPM.setSearchpanelShown(true);
    }

    // empty that search and hide it
    public void hide() {
        this.setLayoutY(this.menubar.getSearch().getLayoutY());
        this.setLayoutX(-(this.SPWIDTH));
        // this.searchInput.setText("");
        rootPM.setSearchpanelShown(false);
        rootPanel.getChildren().remove(this);
    }
}
