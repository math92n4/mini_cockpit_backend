package com.example.mini_cockpit_backend.service.file;

import com.example.mini_cockpit_backend.api.dto.IvsrDTO;
import com.example.mini_cockpit_backend.exception.FileException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class FileServiceImpl implements FileService {

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new
                File(System.getProperty("java.io.tmpdir")
                +"/"+multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    public List<IvsrDTO> parseFile(String path) throws FileException {

        List<IvsrDTO> list = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        int lineNumber = 1;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] nextLine = line.split(";");

                IvsrDTO ivsrDTO = new IvsrDTO();

                String productionNumber = nextLine[0].replace("'", "");

                if(productionNumber.trim().isEmpty()) {
                    errorMessages.add("Fejl på linje " + lineNumber + " - mangler produktionsnummer");
                }

                if (productionNumber.startsWith("0")) {
                    productionNumber = productionNumber.substring(1);
                }

                ivsrDTO.setProductionNumber(productionNumber);
                ivsrDTO.setBrand(nextLine[3].replace("'", ""));
                ivsrDTO.setModelCode(nextLine[4].replace("'", ""));
                ivsrDTO.setModelDescription(nextLine[5].replace("'", ""));
                ivsrDTO.setColorCode(nextLine[7].replace("'", ""));
                ivsrDTO.setOptionsString(nextLine[9].replace("'", ""));

                if (nextLine.length > 45 && !nextLine[45].isEmpty()) {
                    String salesPerson = nextLine[45].replace("/", "");

                    if (salesPerson.startsWith("0")) {
                        salesPerson = salesPerson.substring(1).trim();
                        ivsrDTO.setSalesPersonNumber(Integer.parseInt(salesPerson));
                    }

                }


                if (nextLine.length > 46 && !nextLine[46].isEmpty()) {
                    String plannedHandOverWeek = nextLine[46].replace("'", "");

                    if(!plannedHandOverWeek.matches("\\d*")) {
                        errorMessages.add("Fejl på linje " + lineNumber + " - planned handover week kan ikke indeholde bogstaver");
                    }

                    if (plannedHandOverWeek.length() != 8) {
                        errorMessages.add("Fejl på linje " + lineNumber + " - planned handover week forventer 8 karakterer");
                    }

                    ivsrDTO.setPlannedHandoverWeek(plannedHandOverWeek);
                }

                if (nextLine.length > 47 && !nextLine[47].isEmpty()) {

                    String expectedDeliveryDate = nextLine[47].replace("'", "");

                    if (expectedDeliveryDate.length() != 8) {
                        errorMessages.add("Fejl på linje " + lineNumber + " - expected delivery week forventer 8 karakterer");

                    }
                    if (!expectedDeliveryDate.matches("\\d*")) {
                        errorMessages.add("Fejl på linje " + lineNumber + " - expected delivery week kan ikke indeholde bogstaver");
                    }

                    ivsrDTO.setExpectedDeliveryWeek(expectedDeliveryDate);
                }




                String productionDate = (nextLine[25].trim());

                if (!productionDate.isEmpty()) {

                    try {
                        LocalDate localDate = parseDate(productionDate);
                        ivsrDTO.setActualProductionDate(localDate);
                    } catch (DateTimeParseException e) {
                        errorMessages.add("Fejl på line " + lineNumber + " - production date har forkert format");
                    }

                }

                String agreementDate = (nextLine[36].trim());

                if(!agreementDate.isEmpty()){

                    try {
                        LocalDate localDate = parseDate(agreementDate);
                        ivsrDTO.setPurchaseAgreementDate(localDate);
                    } catch (DateTimeParseException e) {
                        errorMessages.add("Fejl på line " + lineNumber + " - agreement date har forkert format");
                    }
                }



                if (nextLine.length > 40 && !nextLine[40].trim().isEmpty()) {

                    try {
                        String retailCountingDate = (nextLine[40].trim());
                        LocalDate localDate = parseDate(retailCountingDate);
                        ivsrDTO.setRetailCountingDate(localDate);
                    } catch (DateTimeParseException e) {
                        errorMessages.add("Fejl på line " + lineNumber + " - retail counting date har forkert format");
                    }

                }


                lineNumber++;

                if(nextLine[3].trim().contains("MI")) {
                    list.add(ivsrDTO);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!errorMessages.isEmpty()) {
            throw new FileException(errorMessages);
        }

        return list;
    }


    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }

}
