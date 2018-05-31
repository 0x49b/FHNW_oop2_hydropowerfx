package ch.fhnw.oop2.hydropowerfx.view.footer;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.Canton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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

                Text stationName = new Text(field.getCantonName());
                stationName.getStyleClass().add("canton-cell-name");
                stationName.setWrappingWidth(160);

                StringBuilder sb = new StringBuilder();
                sb.append("Kraftwerke: ");
                sb.append(field.getTotatStations());
                Text cantonCountText = new Text(sb.toString());
                cantonCountText.getStyleClass().add("canton-cell-count-text");
                cantonCountText.setWrappingWidth(120);

                DecimalFormat df = new DecimalFormat("#.00");
                sb = new StringBuilder();
                sb.append("Max. Leistung: ");
                sb.append(df.format(field.getTotalPower().doubleValue()));
                Text cantonPowerText = new Text(sb.toString());
                cantonPowerText.getStyleClass().add("canton-cell-power");
                cantonPowerText.setWrappingWidth(150);

                Text portionSwiss = new Text("Anteil Schweiz: ");
                portionSwiss.getStyleClass().add("canton-cell-swiss");
                portionSwiss.setWrappingWidth(100);

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

                HBox hBox = new HBox(cantonView, stationName, cantonCountText, cantonPowerText, portionSwiss, cantonPowerCount, filterButton);
                hBox.setSpacing(10);
                setGraphic(hBox);

                setupBindings();
            }
        }
    }

    public void setupBindings() {
        filterButton.setOnAction(ae -> rootPM.setCantonFilter("AG"));
    }

    private double calcStationCountWidth(int numStations) {
        double totalStations = (double) rootPM.totalPowerStationsProperty().get();
        double cantonStations = Double.valueOf(numStations);
        double cantonCount = (cantonStations / totalStations) * 4;
        return cantonCount;
    }
}
