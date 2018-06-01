package ch.fhnw.oop2.hydropowerfx.view.footer;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.Canton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

public class CantonListCellFactory extends ListCell<Canton> {

    private String canton;
    private Image cantonImage;
    private RootPM rootPM;
    private Button filterButton;


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
                canton = String.format("/ch/fhnw/oop2/hydropowerfx/view/assets/cantons/%s.png", field.getShortName());
                cantonImage = new Image(getClass().getResourceAsStream(canton));
                ImageView cantonView = new ImageView(cantonImage);
                cantonView.setFitWidth(20);
                cantonView.setFitHeight(20);

                Label stationName = new Label(field.getCantonName());
                stationName.getStyleClass().add("canton-cell-name");


                StringBuilder sb = new StringBuilder();
                sb.append("Kraftwerke: ");
                sb.append(field.getTotatStations());

                Label cantonCountText = new Label(sb.toString());
                cantonCountText.getStyleClass().add("canton-cell-count-text");

                DecimalFormat df = new DecimalFormat("#.00");
                sb = new StringBuilder();
                sb.append("Max. Leistung: ");
                sb.append(df.format(field.getTotalPower().doubleValue()));

                Label cantonPowerText = new Label(sb.toString());
                cantonPowerText.getStyleClass().add("canton-cell-power");

                Label portionSwiss = new Label("Anteil Schweiz: ");
                portionSwiss.getStyleClass().add("canton-cell-swiss");

                ProgressBar cantonPowerCount = new ProgressBar(calcStationCountWidth(field.getCantonStationsList().size()));
                cantonPowerCount.setPrefWidth(300);
                cantonPowerCount.getStyleClass().add("canton-cell-bar");
                cantonPowerCount.setPrefWidth(200);

                ImageView filterImage = new ImageView(new Image(this.getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/filter.png")));
                filterImage.setFitHeight(15);
                filterImage.setFitWidth(15);
                filterButton = new Button();
                filterButton.setGraphic(filterImage);
                filterButton.getStyleClass().add("canton-cell-filter");

                stationName.setMinWidth(200);
                cantonCountText.setMinWidth(130);
                cantonPowerText.setMinWidth(180);
                portionSwiss.setMinWidth(130);


                HBox hBox = new HBox(cantonView, stationName, cantonCountText, cantonPowerText, portionSwiss, cantonPowerCount, filterButton);
                hBox.setSpacing(10);
                hBox.setAlignment(Pos.TOP_LEFT);

                setGraphic(hBox);
                setupBindings(field);
            }
        }
    }

    public void setupBindings(Canton canton) {
        filterButton.setOnAction(ae -> rootPM.setCantonFilter(canton.getShortName()));
    }

    private double calcStationCountWidth(int numStations) {
        double totalStations = (double) rootPM.totalPowerStationsProperty().get();
        double cantonStations = Double.valueOf(numStations);
        double cantonCount = (cantonStations / totalStations) * 4;
        return cantonCount;
    }
}
