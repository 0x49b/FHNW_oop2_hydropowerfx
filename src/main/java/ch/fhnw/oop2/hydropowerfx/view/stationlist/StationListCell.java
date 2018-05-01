package ch.fhnw.oop2.hydropowerfx.view.stationlist;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.CSVFields;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StationListCell extends ListCell<CSVFields> {
    @Override
    protected void updateItem(CSVFields field, boolean bln) {
        super.updateItem(field, bln);
        if (field != null) {
            VBox vBox = new VBox(new Text(field.getName()), new Text(field.getLastOperation()));

            String canton;

            if (!field.getCanton().isEmpty()) {
                canton = String.format("../assets/cantons/%s.png", field.getCanton());
            } else {
                canton = "../assets/cantons/empty.png";
            }

            Image cantonImage = new Image(getClass().getResource(canton).toExternalForm());
            ImageView cantonView = new ImageView(cantonImage);
            cantonView.setFitWidth(20);
            cantonView.setFitHeight(25);
            HBox hBox = new HBox(cantonView, vBox);
            hBox.setSpacing(10);
            setGraphic(hBox);
        }
    }

}
