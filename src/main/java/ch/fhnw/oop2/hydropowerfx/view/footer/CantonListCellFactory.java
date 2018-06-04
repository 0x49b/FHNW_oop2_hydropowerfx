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

import java.text.DecimalFormat;

public class CantonListCellFactory extends ListCell<Canton> {

    private String canton;
    private Image cantonImage;
    private RootPM rootPM;
    private Button filterButton;


    private Label cantonName;
    private Label cantonCountText;
    private Label cantonPowerText;
    private Label portionSwiss;
    private ProgressBar cantonPowerCount;


    public CantonListCellFactory(RootPM rootPM) {
        this.rootPM = rootPM;
        getStyleClass().add("cantonlist-cell");
        registerEventListener();
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

                cantonName = new Label(field.getCantonName());
                cantonName.getStyleClass().add("canton-cell-name");


                StringBuilder sb = new StringBuilder();
                sb.append("Kraftwerke: ");
                sb.append(field.getTotatStations());

                cantonCountText = new Label(sb.toString());
                cantonCountText.getStyleClass().add("canton-cell-count-text");

                DecimalFormat df = new DecimalFormat("0.00");
                sb = new StringBuilder();
                sb.append("Max. Leistung: ");
                sb.append(df.format(field.getTotalPower().doubleValue()));
                sb.append(" MW");

                cantonPowerText = new Label(sb.toString());
                cantonPowerText.getStyleClass().add("canton-cell-power");


                String percent = df.format((float) (calcStationPowerWidth(field.getShortName()) * 100.0F));
                portionSwiss = new Label("Anteil Schweiz MW (" + percent + "%): ");
                portionSwiss.getStyleClass().add("canton-cell-swiss");

                cantonPowerCount = new ProgressBar((calcStationPowerWidth(field.getShortName()) * 2));
                cantonPowerCount.getStyleClass().add("canton-cell-bar");

                ImageView filterImage = new ImageView(new Image(this.getClass().getResourceAsStream("/ch/fhnw/oop2/hydropowerfx/view/assets/images/filter.png")));
                filterImage.setFitHeight(15);
                filterImage.setFitWidth(15);
                filterButton = new Button();
                filterButton.setGraphic(filterImage);
                filterButton.getStyleClass().add("canton-cell-filter");

                HBox hBox = new HBox(cantonView, cantonName, cantonCountText, cantonPowerText, portionSwiss, cantonPowerCount, filterButton);
                hBox.setSpacing(10);
                hBox.setAlignment(Pos.TOP_LEFT);

                setColumnWithPercent();

                setGraphic(hBox);
                setupBindings(field);
            }
        }
    }

    private void setColumnWithPercent() {
        cantonName.setMinWidth(calcColumWidth(17.0f));
        cantonCountText.setMinWidth(calcColumWidth(11.0f));
        cantonPowerText.setMinWidth(calcColumWidth(19.0f));
        portionSwiss.setMinWidth(calcColumWidth(20.0f));
        cantonPowerCount.setPrefWidth(calcColumWidth(15.0f));
    }

    private double calcColumWidth(float percentWidth) {
        double windowWidth = rootPM.getPrimaryStage().getWidth();
        double percent = percentWidth / 100.0d;
        return windowWidth * percent;
    }

    public void setupBindings(Canton canton) {
        filterButton.setOnAction(ae -> rootPM.setCantonFilter(canton.getShortName()));
    }

    private double calcStationPowerWidth(String cantonShort) {
        double swissPower = rootPM.totalSwissPowerOutputProperty().get();
        double cantonPower = rootPM.getPowerStationList().stream().filter(e -> e.getCanton().equals(cantonShort)).mapToDouble(e -> e.getMaxPower()).sum();
        double cantonPowerPercent = (cantonPower / swissPower);
        return cantonPowerPercent;
    }

    private void registerEventListener() {
        rootPM.getPrimaryStage().widthProperty().addListener((observable, oldValue, newValue) -> {
            setColumnWithPercent();
        });
    }

}
