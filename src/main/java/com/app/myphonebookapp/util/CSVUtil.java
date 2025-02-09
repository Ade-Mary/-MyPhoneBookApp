package com.app.myphonebookapp.util;

import com.app.myphonebookapp.model.Contact;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    public static byte[] exportToCSV(List<Contact> contacts) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT)) {

            // Write header
            printer.printRecord("firstName", "lastName", "email", "phoneNumber", "group", "isFavorite");

            // Write data
            for (Contact contact : contacts) {
                printer.printRecord(
                        contact.getFirstName(),
                        contact.getLastName(),
                        contact.getEmail(),
                        contact.getPhoneNumber(),
                        contact.getGroup(),
                        contact.isFavorite()
                );
            }

            printer.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to export CSV: " + e.getMessage());
        }
    }

    public static List<Contact> parseCSV(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<Contact> contacts = new ArrayList<>();
            csvParser.forEach(record -> {
                Contact contact = Contact.builder()
                        .firstName(record.get("firstName"))
                        .lastName(record.get("lastName"))
                        .email(record.get("email"))
                        .phoneNumber(record.get("phoneNumber"))
                        .group(record.get("group"))
                        .isFavorite(Boolean.parseBoolean(record.get("isFavorite")))
                        .build();
                contacts.add(contact);
            });

            return contacts;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV: " + e.getMessage());
        }
    }
}
