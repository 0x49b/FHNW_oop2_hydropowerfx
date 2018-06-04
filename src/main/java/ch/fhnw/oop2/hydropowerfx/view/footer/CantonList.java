package ch.fhnw.oop2.hydropowerfx.view.footer;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.Canton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.ViewMixin;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class CantonList extends StackPane implements ViewMixin {

    private ListView<Canton> cantonList;
    private RootPM rootPM;

    public CantonList(RootPM rootPM) {
        this.rootPM = rootPM;
        init();
    }


    @Override
    public void initializeSelf() {
        this.getStyleClass().add("cantonlist");
    }

    @Override
    public void initializeControls() {
        cantonList = new ListView<>();
        cantonList.getStyleClass().add("cantonlist-listview");
        cantonList.setItems(rootPM.getCantons());
        cantonList.setCellFactory(param -> new CantonListCellFactory(rootPM));
        cantonList.scrollTo(0);
    }

    @Override
    public void layoutControls() {
        getChildren().add(cantonList);
    }

    @Override
    public void setupEventHandlers() {

    }

    @Override
    public void setupValueChangedListeners() {

    }

    @Override
    public void setupBindings() {

    }
}
