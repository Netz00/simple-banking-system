package org.netz00.simple_banking_system.util.faker;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;


public class CsvParser<T> {

    public List<T> parseCSV(Class<T> clazz, String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return parseCsvRows(clazz, reader);
        } catch (IOException e) {
            throw new IOException("Failed to read or parse the CSV file", e);
        }
    }

    private List<T> parseCsvRows(Class<T> clazz, Reader reader) {
        return new CsvToBeanBuilder<T>(reader)
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();
    }
}

