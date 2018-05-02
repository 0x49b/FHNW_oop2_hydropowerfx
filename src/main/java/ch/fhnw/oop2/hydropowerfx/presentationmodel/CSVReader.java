package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class CSVReader {

    private final ObservableList<CSVFields> dataList = FXCollections.observableArrayList();
    private final int START_LINE = 1;
    private int counter = 0;

    public ObservableList<CSVFields> readCSV() {
        String csvFile = "src/main/resources/data/HYDRO_POWERSTATION.csv";
        String delimiter = ";";
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            String line;

            while ((line = br.readLine()) != null) {

                if (counter >= START_LINE) {
                    String[] fields = line.split(delimiter, -1);
                    if (fields[4].equals("")) {
                        fields[4] = "ZZ";
                    }
                    CSVFields fieldLine = new CSVFields(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8], fields[9], fields[10], fields[11], fields[12], fields[13]);
                    dataList.add(fieldLine);
                }
                counter++;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Comparator<CSVFields> comparator = Comparator.comparing(CSVFields::getCanton);
        FXCollections.sort(dataList, comparator);

        return dataList;

    }


}
