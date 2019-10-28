package com.api_nbp;

import com.api_nbp.model.CalculatedRates;
import com.api_nbp.model.DataFormat;
import com.api_nbp.model.NbpParameters;

import javax.xml.bind.JAXBException;
import java.rmi.UnmarshalException;

public class Main {
    public static void main(String[] args) {
        ApiNbpURLBuilder builder = new ApiNbpURLBuilder();
        NbpParameters nbpParameters = new NbpParameters();
        CalculatedRates calculatedRates = new CalculatedRates();

        System.out.println("Welcome in the console application for downloading exchange rates from the NBP API");
        nbpParameters.setCode(ScanerContentLoader.INSTANCE.loadCurrencyCode());
        nbpParameters.setDataFormat(ScanerContentLoader.INSTANCE.loadDataFormatFromUser());

        do {
            nbpParameters.setDateStart(ScanerContentLoader.INSTANCE.loadDateFromUser());
            nbpParameters.setDateEnd(ScanerContentLoader.INSTANCE.loadDateFromUser());
            if (nbpParameters.startIsAfterEnd()) {
                System.err.println("Error, start date is after end date. Try again.");
            }
        } while (nbpParameters.startIsAfterEnd());

        nbpParameters.setTable(ScanerContentLoader.INSTANCE.loadTableFromUser());
        builder.loadParameters(nbpParameters);
        String requestURL = builder.compileURL();
        System.out.println(requestURL);

        NbpAPI nbpAPI;
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
        } catch (UnmarshalException e) {
            System.err.println("No data.");
            System.exit(1);
        } catch (JAXBException e) {
            System.err.println("Incorrect XML build");
            e.printStackTrace();
            System.exit(1);
        }

    }

}
