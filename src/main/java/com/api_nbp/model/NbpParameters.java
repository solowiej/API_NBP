package com.api_nbp.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NbpParameters {
    private String table;
    private CurrencyCode code;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private DataFormat dataFormat;

    public boolean startIsAfterEnd() {
        return dateStart.isAfter(dateEnd);
    }
}
