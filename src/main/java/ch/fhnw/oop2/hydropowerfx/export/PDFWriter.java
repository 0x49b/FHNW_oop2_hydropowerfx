package ch.fhnw.oop2.hydropowerfx.export;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileOutputStream;

public class PDFWriter {

    private RootPM rootPM;
    private File location;
    private PowerStation actualStation;
    private String stationName;


    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static Font textBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static Font textFont = new Font(Font.FontFamily.HELVETICA, 12);


    public PDFWriter(PowerStation actualStation, RootPM rootPM) {
        this.actualStation = actualStation;
        this.rootPM = rootPM;

        System.out.println("generating PDF for Station: " + actualStation.getEntitiyID() + " NAME" + actualStation.getName());
        chooseFileSaveLocation();
    }

    private void chooseFileSaveLocation() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Speicherort wählen ...");
        location = directoryChooser.showDialog(rootPM.getPrimaryStage());
        if (location != null) {
            System.out.println(location.getAbsolutePath());
            createPDF();
        }
    }

    private void createPDF() {

        try {
            Document document = new Document();
            StringBuilder fileName = new StringBuilder();
            stationName = this.actualStation.getName().replace(" ", "_").replace("(", "").replace(")", "");
            fileName.append(this.location).append("/").append(stationName).append(".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(fileName.toString().toLowerCase()));
            document.open();
            addMetaData(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMetaData(Document document) {
        document.addTitle("Datasheet_" + stationName);
        document.addAuthor(System.getProperty("user.name"));
        document.addCreator(System.getProperty("user.name"));
    }

    private void addContent(Document document) throws DocumentException {

        Header header = new Header("inspired by", "William Shakespeare");
        document.add(header);

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Paragraph("Datenblatt: " + actualStation.getName(), catFont));

        paragraph.add(new Paragraph("\n"));

        Anchor anchor = new Anchor("");
        paragraph.add(anchor);

        createTable(paragraph);
        document.add(paragraph);
    }

    private void createTable(Paragraph subCatPart) throws BadElementException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);


        // table.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        //ROW 1
        table.addCell("Name");
        table.addCell(actualStation.getName());
        table.addCell("Typ");
        table.addCell(actualStation.getType());

        //ROW 2
        table.addCell("Standort");
        table.addCell(actualStation.getSite());
        table.addCell("Kanton");
        table.addCell(actualStation.getCanton());

        //ROW 3
        table.addCell("Wassermenge");
        table.addCell(String.valueOf(actualStation.getMaxWater()));
        table.addCell("Leistung (MW)");
        table.addCell(String.valueOf(actualStation.getMaxPower()));

        //ROW 4
        table.addCell("1. Inbetriebnahme");
        table.addCell(String.valueOf(actualStation.getStartOperation()));
        table.addCell("Sanierung");
        table.addCell(String.valueOf(actualStation.getLastOperation()));

        //ROW 5
        table.addCell("Längengrad");
        table.addCell(String.valueOf(actualStation.getLatitude()));
        table.addCell("Breitengrad");
        table.addCell(String.valueOf(actualStation.getLongitude()));

        //ROW 6
        table.addCell("Status");
        table.addCell(actualStation.getStatus());
        table.addCell("");
        table.addCell("");

        //ROW 7
        table.addCell("Genutzte Gewässer");
        table.addCell(actualStation.getWaterbodies());
        table.addCell("");
        table.addCell("");

        subCatPart.add(table);

    }


}
