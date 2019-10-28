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
                System.out.println("Currency code: " + Arrays.toString(CurrencyCode.values()) + " ?");
                String currencyCode = INSTANCE.scanner().nextLine().toUpperCase();
                currencyCodeEnum = CurrencyCode.valueOf(currencyCode);
            } catch (IllegalArgumentException iae) {
                System.err.println("Incorrect currency exchange rate, please enter it again.");
            }
        } while (currencyCodeEnum == null);
        return currencyCodeEnum;
    }


    public DataFormat loadDataFormatFromUser() {
        DataFormat dataFormat = null;
        do {
            try {
                System.out.println("Data format: " + Arrays.toString(DataFormat.values()) + "?");
                String dataF = INSTANCE.scanner().nextLine();
                dataFormat = DataFormat.valueOf(dataF.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Invalid data format, please enter it again.");
            }
        } while (dataFormat == null);
        return dataFormat;
    }

    public LocalDate loadDateFromUser() {
        LocalDate dateFromUser = null;
        do {
            try {
                System.out.println("Date: [yyyy-MM-dd]");
                String date = INSTANCE.scanner().nextLine();
                dateFromUser = dataParser(date);
            } catch (DateTimeParseException dtpe) {
                System.err.println("Invalid date format, please enter it again.");
            }
        } while (dateFromUser == null);

        return dateFromUser;
    }

    public String loadTableFromUser() {
        String table;
        do {
            System.out.println("Type of table: ASK/BID = C, MID -A/B");
            table = INSTANCE.scanner().nextLine().toUpperCase();

            if (!table.equalsIgnoreCase("C") && !table.equalsIgnoreCase("A") &&
                    !table.equalsIgnoreCase("B")) {
                table = null;
                System.err.println("Invalid table type. Enter the table type again.");
            }
        } while (table == null);
        return table;
    }

    public String loadCalculationsToDo() {
        String whatToDo;
        do {
            System.out.println("What would you like to calculate? [a-average exchange rate, b-maximum deviation, c-finding min and max]");
            whatToDo = INSTANCE.scanner().nextLine().toUpperCase();

            if (!whatToDo.equalsIgnoreCase("C") && !whatToDo.equalsIgnoreCase("A") && !whatToDo.equalsIgnoreCase("B")) {
                whatToDo = null;
                System.err.println("Invalid table type. Enter the table type again. ");
            }
        } while (whatToDo == null);
        return whatToDo;
    }

    private LocalDate dataParser(String line) throws DateTimeParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(line, dateTimeFormatter);
    }

}
