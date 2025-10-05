package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String username = csvRecord.get("username");
                String password = csvRecord.get("password");
                data.add(new String[]{username, password});
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
