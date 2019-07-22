package com.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static String BASE_NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/{table}/{code}/{startDate}/{endDate}/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CurrencyCode currencyCodeEnum = loadCurrencyCode(scanner);

        LocalDate dateStart;
        LocalDate dateEnd;

        do {
            dateStart = loadDateFromUser(scanner);
            dateEnd = loadDateFromUser(scanner);

            if (dateStart.isAfter(dateEnd)) {
                System.err.println("Data startowa jest pozniejsza niz koncowa." +
                        "Podaj ponownie obie daty.");
            }
        } while (!dateStart.isBefore(dateEnd));


        String table = getTableFromUser(scanner);
        String requestURL = BASE_NBP_API_URL
                .replace("{table}", table)
                .replace("{code}", currencyCodeEnum.toString())
                .replace("{startDate}", dateStart.format(DATE_TIME_FORMATTER))
                .replace("{endDate}", dateEnd.format(DATE_TIME_FORMATTER));

        System.out.println("Twoj request url to: " + requestURL);

    }

    private static CurrencyCode loadCurrencyCode(Scanner scanner) {
        CurrencyCode currencyCodeEnum = null;
        do {
            try {
                System.out.println("Witaj w konsolowej aplikacji do pobierania kursow walut z API NBP");
                System.out.println("Podaj kod waluty: " + Arrays.toString(CurrencyCode.values()) + " ?");
                String currencyCode = scanner.nextLine().toUpperCase();
                currencyCodeEnum = CurrencyCode.valueOf(currencyCode);

            } catch (IllegalArgumentException iae) {
                System.err.println("Niepoprawny kurs waluty, podaj go ponownie.");
            }
        } while (currencyCodeEnum == null);
        return currencyCodeEnum;
    }

    private static String getTableFromUser(Scanner scanner) {
        String table;
        do {
            System.out.println("Podaj tym tabeli: ASK/BID = C, MID -A/B");
            table = scanner.nextLine().toUpperCase();

            if (!table.equalsIgnoreCase("C") && !table.equalsIgnoreCase("A") &&
                    !table.equalsIgnoreCase("B")) {
                table = null;
                System.err.println("Niepoprawny typ tabeli. Wpisz ponownie typ tabeli.");
            }
        } while (table == null);
        return table;
    }

    public static LocalDate loadDateFromUser(Scanner scanner) {
        LocalDate dateFromUser = null;
        do {
            try {
                System.out.println("Podaj date: [yyyy-MM-dd]");
                String date = scanner.nextLine();

                dateFromUser = LocalDate.parse(date, DATE_TIME_FORMATTER);
            } catch (DateTimeParseException dtpe) {
                System.err.println("Niepoprawny format daty, podaj ja ponownie.");
            }
        } while (dateFromUser == null);
        return dateFromUser;
    }


}
