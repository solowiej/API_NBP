package com.api_nbp;

import com.api_nbp.model.CurrencyCode;
import com.api_nbp.model.DataFormat;
import com.api_nbp.model.NbpParameters;

public class ApiNbpURLBuilder {
    private static final String BASE_NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/";
    private final StringBuilder builder;

    public ApiNbpURLBuilder() {
        builder = new StringBuilder(BASE_NBP_API_URL);
    }

    public void appendTypeofTable(NbpParameters nbpParameters) {
        if (!builder.toString().contains("/{table}")) {
            builder.append(nbpParameters.getTable());
        } else if (builder.toString().contains("{table}")) {
            System.err.println("Kategoria została już dopisana.");
        }
    }

    public void appendCurrencyCode(CurrencyCode currencyCode) {
        if (!builder.toString().contains("/{code}")) {
            builder.append("/");
            builder.append(currencyCode.toString().toLowerCase());
        } else if (builder.toString().contains("/{code}")) {
            System.err.println("Kategoria została już dopisana.");
        }
    }

    public void appendStartDate(NbpParameters nbpParameters) {
        if (!builder.toString().contains("/{startDate}")) {
            builder.append("/");
            builder.append(nbpParameters.getDateStart());
        } else if (builder.toString().contains("/{startDate}")) {
            System.err.println("Kategoria została już dopisana.");
        }
    }

    public void appendEndDate(NbpParameters nbpParameters) {
        if (!builder.toString().contains("/{endDate}")) {
            builder.append("/");
            builder.append(nbpParameters.getDateEnd());
        } else if (builder.toString().contains("/{endDate}")) {
            System.err.println("Kategoria została już dopisana.");
        }
    }

    public void appendDataFormat(DataFormat dataFormat) {
        if (!builder.toString().contains("/?format=")) {
            builder.append("/?format=");
            builder.append(dataFormat);
        } else if (builder.toString().contains("/?format=")) {
            System.err.println("Kategoria została już dopisana.");
        }
    }

    public void loadParameters(NbpParameters nbpParameters) {
        appendTypeofTable(nbpParameters);
        appendCurrencyCode(nbpParameters.getCode());
        appendStartDate(nbpParameters);
        appendEndDate(nbpParameters);
        appendDataFormat(nbpParameters.getDataFormat());
    }

    public String compileURL() {
        return builder.toString();
    }
}
