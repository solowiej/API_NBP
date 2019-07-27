package com.api_nbp;

import com.api_nbp.model.CurrencyCode;
import com.api_nbp.model.DataFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class ScanerContentLoader {
    public static final ScanerContentLoader INSTANCE = new ScanerContentLoader();

    private ScanerContentLoader() {
    }

    public Scanner scanner() {
        Scanner scanner = new Scanner(System.in);
        return scanner;
    }

    public CurrencyCode loadCurrencyCode() {
        CurrencyCode currencyCodeEnum = null;
        do {
            try {
                System.out.println("Podaj kod waluty: " + Arrays.toString(CurrencyCode.values()) + " ?");
                String currencyCode = INSTANCE.scanner().nextLine().toUpperCase();
                currencyCodeEnum = CurrencyCode.valueOf(currencyCode);
            } catch (IllegalArgumentException iae) {
                System.err.println("Niepoprawny kurs waluty, podaj go ponownie.");
            }
        } while (currencyCodeEnum == null);
        return currencyCodeEnum;
    }


    public DataFormat loadDataFormatFromUser() {
        DataFormat dataFormat = null;
        do {
            try {
                System.out.println("Podaj format danych " + Arrays.toString(DataFormat.values()) + "?");
                String dataF = INSTANCE.scanner().nextLine();
                dataFormat = DataFormat.valueOf(dataF.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Niepoprawny format danych, podaj go ponownie.");
            }
        } while (dataFormat == null);
        return dataFormat;
    }

    public LocalDate loadDateFromUser() {
        LocalDate dateFromUser = null;
        do {
            try {
                System.out.println("Podaj date [yyyy-MM-dd: ]");
                String date = INSTANCE.scanner().nextLine();
                dateFromUser = dataParser(date);
            } catch (DateTimeParseException dtpe) {
                System.err.println("Niepoprawny format daty, podaj ja ponownie.");
            }
        } while (dateFromUser == null);

        return dateFromUser;
    }

    public String loadTableFromUser() {
        String table;
        do {
            System.out.println("Podaj tym tabeli: ASK/BID = C, MID -A/B");
            table = INSTANCE.scanner().nextLine().toUpperCase();

            if (!table.equalsIgnoreCase("C") && !table.equalsIgnoreCase("A") &&
                    !table.equalsIgnoreCase("B")) {
                table = null;
                System.err.println("Niepoprawny typ tabeli. Wpisz ponownie typ tabeli.");
            }
        } while (table == null);
        return table;
    }

    public String loadCalculationsToDo() {
        String whatToDo;
        do {
            System.out.println("Co chciałbyś obliczyć? [a-średni kurs waluty, b-odchylenie maksymalne, c-znalezienie min i max]");
            whatToDo = INSTANCE.scanner().nextLine().toUpperCase();

            if (!whatToDo.equalsIgnoreCase("C") && !whatToDo.equalsIgnoreCase("A") && !whatToDo.equalsIgnoreCase("B")) {
                whatToDo = null;
                System.err.println("Niepoprawny typ tabeli. Wpisz ponownie typ tabeli.");
            }
        } while (whatToDo == null);
        return whatToDo;
    }

    private LocalDate dataParser(String line) throws DateTimeParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(line, dateTimeFormatter);
    }

}
