package ch.fhnw.oop2.hydropowerfx.view.stationlist;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StationListCell extends ListCell<PowerStation> {

    private String canton;
    private Image cantonImage;

    public StationListCell(){
        getStyleClass().add("stationlist-cell");
    }

    @Override
    protected void updateItem(PowerStation field, boolean bln) {
        super.updateItem(field, bln);
        if (field != null) {

            Text stationName = new Text(field.getName());
            stationName.getStyleClass().add("station-cell-name");
            Text maxPower = new Text(field.getMaxPower() + "kWh");
            maxPower.getStyleClass().add("station-cell-maxpower");

            VBox vBox = new VBox(stationName, maxPower);

            // ToDo Very Bad Code..... Must be changed
            try {
                canton = String.format("../assets/cantons/%s.png", field.getCanton());
                cantonImage = new Image(getClass().getResource(canton).toExternalForm());
            } catch(Exception e) {
                canton = "../assets/cantons/ZZ.png";
                cantonImage = new Image(getClass().getResource(canton).toExternalForm());
            }
            ImageView cantonView = new ImageView(cantonImage);
            cantonView.setFitWidth(30);
            cantonView.setFitHeight(30);
            HBox hBox = new HBox(cantonView, vBox);
            hBox.setSpacing(10);
            setGraphic(hBox);
        }
    }
}
