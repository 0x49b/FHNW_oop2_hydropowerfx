package ch.fhnw.oop2.hydropowerfx.view.stationlist;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.CSVFields;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StationListCell extends ListCell<CSVFields> {

    private String canton;

    public StationListCell() {
        getStyleClass().add("stationlist-cell");
    }

    @Override
    protected void updateItem(CSVFields field, boolean bln) {
        super.updateItem(field, bln);
        if (field != null) {

            Label stationName = new Label(field.getName());
            stationName.getStyleClass().add("station-cell-title");
            Label maxPower = new Label(field.getMaxPower() + "kWh");
            maxPower.getStyleClass().addAll("station-cell-subline", "station-cell-maxpower");
            Label firstRun = new Label(field.getStartOperation() + "\t");
            firstRun.getStyleClass().addAll("station-cell-subline", "station-cell-firstrun");

            HBox hbox = new HBox(firstRun, maxPower);
            VBox vBox = new VBox(stationName, hbox);

            // ToDo Very Bad Code..... Must be changed
            try {
                canton = String.format("../assets/cantons/%s.png", field.getCanton());
            } catch (Exception e) {
                canton = "../assets/cantons/ZZ.png";
            }
            Image cantonImage = new Image(getClass().getResource(canton).toExternalForm());
            ImageView cantonView = new ImageView(cantonImage);
            cantonView.setFitWidth(30);
            cantonView.setFitHeight(30);
            HBox hBox = new HBox(cantonView, vBox);
            hBox.setSpacing(10);
            setGraphic(hBox);
        }
    }
}
