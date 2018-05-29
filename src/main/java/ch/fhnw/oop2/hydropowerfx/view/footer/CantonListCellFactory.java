package ch.fhnw.oop2.hydropowerfx.view.footer;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.Canton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CantonListCellFactory extends ListCell<Canton> {

    private String canton;
    private Image cantonImage;
    private RootPM rootPM;


    public CantonListCellFactory(RootPM rootPM) {
        this.rootPM = rootPM;
        getStyleClass().add("cantonlist-cell");
    }

    @Override
    protected void updateItem(Canton field, boolean empty) {
        super.updateItem(field, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (field != null) {
                canton = String.format("../assets/cantons/%s.png", field.getShortName());
                cantonImage = new Image(getClass().getResource(canton).toExternalForm());
                ImageView cantonView = new ImageView(cantonImage);
                cantonView.setFitWidth(15);
                cantonView.setFitHeight(15);

                Text stationName = new Text(field.getCantonName());
                stationName.getStyleClass().add("canton-cell-name");
                stationName.setWrappingWidth(200);

                ProgressBar stationCount = new ProgressBar(calcStationCountWidth(field.getNumber()));
                stationCount.setPrefWidth(300);

                HBox hBox = new HBox(cantonView, stationName, stationCount);
                hBox.setSpacing(10);
                setGraphic(hBox);
            }
        }
    }

    private double calcStationCountWidth(String numStations) {

        double totalStations = (double) rootPM.totalPowerStationsProperty().get();
        double cantonStations = Double.valueOf(numStations);

        double cantonCount = (cantonStations * 10) / totalStations;

        return cantonCount;
    }
}
