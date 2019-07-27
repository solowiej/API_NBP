package com.api_nbp;

import com.api_nbp.model.api.ExchangeRatesSeries;
import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class NbpAPI {
    private final JAXBContext JAXBCONTENT;
    private final Unmarshaller UNMARSHALLER;
    private final Gson GSON = new Gson();

    public NbpAPI() throws JAXBException {
        JAXBCONTENT = JAXBContext.newInstance(ExchangeRatesSeries.class);
        UNMARSHALLER = JAXBCONTENT.createUnmarshaller();
    }


    public String loadContentFromURL(String requestURL) {
        String apiContent = null;
        try {
            URL url = new URL(requestURL);
            InputStream inputStream = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String liniaTekstuZReadera;

            while ((liniaTekstuZReadera = bufferedReader.readLine()) != null) {
                builder.append(liniaTekstuZReadera);
            }
            bufferedReader.close();

            apiContent = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiContent;
    }

    public ExchangeRatesSeries loadAndParseExchangeRatesSeriesByJASON(String requestURL) {
        String apiContent = loadContentFromURL(requestURL);
        ExchangeRatesSeries exchangeRatesSeries = GSON.fromJson(apiContent, ExchangeRatesSeries.class);

        return exchangeRatesSeries;
    }


    public ExchangeRatesSeries loadAndParseExchangeRatesSeriesByXML(String requestURL) {
        ExchangeRatesSeries exchangeRatesSeries = null;
        try {
            JAXBContext JAXBCONTENT = JAXBContext.newInstance(ExchangeRatesSeries.class);

            Unmarshaller UNMARSHALLER = JAXBCONTENT.createUnmarshaller();
            exchangeRatesSeries = (ExchangeRatesSeries) UNMARSHALLER.unmarshal(new URL(requestURL));

        } catch (UnmarshalException ue) {
            System.err.println("Brak danych");
            System.exit(1);
        } catch (JAXBException | MalformedURLException e) {
            e.printStackTrace();
        }
        return exchangeRatesSeries;
    }
}
