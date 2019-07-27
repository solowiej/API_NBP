package com.api_nbp;

import com.api_nbp.model.CalculatedRates;
import com.api_nbp.model.DataFormat;
import com.api_nbp.model.NbpParameters;

import javax.xml.bind.JAXBException;

public class Main {
    public static void main(String[] args) {
        ApiNbpURLBuilder builder = new ApiNbpURLBuilder();
        NbpParameters nbpParameters = new NbpParameters();
        CalculatedRates calculatedRates = new CalculatedRates();

        System.out.println("Witaj w konsolowej aplikacji do pobierania kursow walut z API NBP");
        nbpParameters.setCode(ScanerContentLoader.INSTANCE.loadCurrencyCode());
        nbpParameters.setDataFormat(ScanerContentLoader.INSTANCE.loadDataFormatFromUser());

        do {
            nbpParameters.setDateStart(ScanerContentLoader.INSTANCE.loadDateFromUser());
            nbpParameters.setDateEnd(ScanerContentLoader.INSTANCE.loadDateFromUser());
            if (nbpParameters.startIsAfterEnd()) {
                System.err.println("Blad, data startowa jest po dacie koncowej. Sprobuj ponownie.");
            }
        } while (nbpParameters.startIsAfterEnd());

        nbpParameters.setTable(ScanerContentLoader.INSTANCE.loadTableFromUser());
        builder.loadParameters(nbpParameters);
        String requestURL = builder.compileURL();
        System.out.println(requestURL);

        NbpAPI nbpAPI ;
        try {
            nbpAPI = new NbpAPI();
            if (nbpParameters.getDataFormat() == DataFormat.XML) {
                calculatedRates.calculateRates(nbpAPI.loadAndParseExchangeRatesSeriesByXML(requestURL),
                        ScanerContentLoader.INSTANCE.loadCalculationsToDo());
            } else {
                nbpAPI.loadAndParseExchangeRatesSeriesByJASON(requestURL);
                calculatedRates.calculateRates(nbpAPI.loadAndParseExchangeRatesSeriesByJASON(requestURL),
                        ScanerContentLoader.INSTANCE.loadCalculationsToDo());
            }
        } catch (JAXBException e) {
            System.err.println("Niepoprawna budowa XML");
            e.printStackTrace();
            System.exit(1);
        }

    }

}
