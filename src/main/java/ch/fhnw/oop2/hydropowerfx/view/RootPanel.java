package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.editor.Editor;
import ch.fhnw.oop2.hydropowerfx.view.footer.CantonList;
import ch.fhnw.oop2.hydropowerfx.view.menubar.Menubar;
import ch.fhnw.oop2.hydropowerfx.view.stationlist.StationList;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class RootPanel extends HBox implements ViewMixin {
    private final RootPM rootPM;
    private final RootPanel rootPanel;

    private Menubar menubar;
    private Editor editor;
    private StationList stationList;
    private CantonList cantonList;

    private SplitPane outerSplitPane;
    private SplitPane innerSplitPane;

    public RootPanel(RootPM model) {
        this.rootPM = model;
        this.rootPanel = this;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("assets/style.css");
    }

    @Override
    public void initializeControls() {
        outerSplitPane = new SplitPane();
        innerSplitPane = new SplitPane();

        outerSplitPane.setOrientation(Orientation.VERTICAL);
        innerSplitPane.setOrientation(Orientation.HORIZONTAL);

        outerSplitPane.setDividerPosition(0, 0.0);
        innerSplitPane.setDividerPosition(0, 0.265);

        menubar = new Menubar(rootPM, rootPanel);
        editor = new Editor(rootPM);
        stationList = new StationList(rootPM);
        cantonList = new CantonList(rootPM);

        innerSplitPane.getItems().addAll(stationList, editor);
        outerSplitPane.getItems().addAll(innerSplitPane, cantonList);
        setHgrow(outerSplitPane, Priority.ALWAYS);
    }

    @Override
    public void layoutControls() {
        getChildren().addAll(menubar, outerSplitPane);
    }

    @Override
    public void setupBindings() {

    }

    @Override
    public void setupEventHandlers() {

    }
}
