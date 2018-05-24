package ch.fhnw.oop2.hydropowerfx.export;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFExport {

    private RootPM rootPM;
    private File location;
    private PowerStation actualStation;
    private String stationName;
    private PdfWriter writer;

    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static Font textFont = new Font(Font.FontFamily.HELVETICA, 12);
    private static Font smallFont = new Font(Font.FontFamily.HELVETICA, 7);


    public PDFExport(PowerStation actualStation, RootPM rootPM) {
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
            writer = PdfWriter.getInstance(document, new FileOutputStream(fileName.toString().toLowerCase()));
            document.open();
            addMetaData(document);
            addHeader(document);
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

    private void addHeader(Document document) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            float textTopPosition = document.top() + 15;

            StringBuilder sbLeft = new StringBuilder();
            sbLeft.append("Datenblatt: ");
            sbLeft.append(actualStation.getName() + "\r\n");
            sbLeft.append("Standort: ");


            StringBuilder sbRight = new StringBuilder();
            sbRight.append("Generiert am: ");
            sbRight.append(dateFormat.format(new Date()));

            Phrase headerLeft = new Phrase(sbLeft.toString(), smallFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, headerLeft, document.leftMargin(), textTopPosition, 0);


            Image img = Image.getInstance(PDFExport.class.getResource("../view/assets/images/hpfxlogo_dark.png"));
            img.scaleAbsolute(25,25);
            img.setAbsolutePosition((document.right() - document.left()) / 2 + img.getHeight() / 4, document.top() + 5);
            document.add(img);

            Phrase headerRight = new Phrase(sbRight.toString(), smallFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, headerRight, document.right(), textTopPosition, 0);


            CMYKColor black = new CMYKColor(0, 0, 0, 255);
            cb.setColorStroke(black);
            cb.moveTo(document.left(), document.top());
            cb.lineTo(document.right(), document.top());
            cb.closePathStroke();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addContent(Document document) throws DocumentException {

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