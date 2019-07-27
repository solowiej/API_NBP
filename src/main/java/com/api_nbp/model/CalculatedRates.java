package com.api_nbp.model;

import com.api_nbp.model.api.ExchangeRatesSeries;
import com.api_nbp.model.api.Rate;
import lombok.Data;

@Data
public class CalculatedRates {
    private Double ask, bid, mid;

    public void calculateRates(ExchangeRatesSeries exchangeRatesSeries, String choice) {

        CalculatedRates valueMinimum = calculateMinimum(exchangeRatesSeries);
        CalculatedRates valueMaximum = calculateMaximum(exchangeRatesSeries);
        CalculatedRates valueAverege = calculateMid(exchangeRatesSeries);

        switch (choice) {
            case "A":
                if (valueAverege.getMid() != null) {
                    System.out.println("Average mid: " + valueAverege.getMid());
                }
                if (valueAverege.getAsk() != null) {
                    System.out.println("Average ask: " + valueAverege.getAsk());
                }
                if (valueAverege.getBid() != null) {
                    System.out.println("Average bid: " + valueAverege.getBid());
                }
                break;
            case "B":
                if (valueMaximum.getMid() != null && valueMinimum.getMid() != null) {
                    System.out.println("Deviation mid: " + (valueMaximum.getMid() - valueMinimum.getMid()));
                }
                if (valueMaximum.getAsk() != null && valueMinimum.getAsk() != null) {
                    System.out.println("Deviation ask: " + (valueMaximum.getAsk() - valueMinimum.getAsk()));
                }
                if (valueMaximum.getBid() != null && valueMinimum.getBid() != null) {
                    System.out.println("Deviation bid: " + (valueMaximum.getBid() - valueMinimum.getBid()));
                }

                break;
            case "C":
                if (valueMaximum.getMid() != null && valueMinimum.getMid() != null) {
                    System.out.println("Max value mid: " + valueMaximum.getMid() + ", min value mid: " + valueMinimum.getMid());
                }
                if (valueMaximum.getAsk() != null && valueMinimum.getAsk() != null) {
                    System.out.println("Max value ask: " + valueMaximum.getAsk() + ", min value ask: " + valueMinimum.getAsk());
                }
                if (valueMaximum.getBid() != null && valueMinimum.getBid() != null) {
                    System.out.println("Max value bid: " + valueMaximum.getBid() + ", min value bid: " + valueMinimum.getBid());
                }

                break;
        }
    }

    private CalculatedRates calculateMinimum(ExchangeRatesSeries exchangeRatesSeries) {
        CalculatedRates calculatedRates = new CalculatedRates();
        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getAsk() != null)
                .mapToDouble(Rate::getAsk)
                .min()
                .ifPresent(calculatedRates::setAsk);

        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getBid() != null)
                .mapToDouble(Rate::getBid)
                .min()
                .ifPresent(streamResult -> calculatedRates.setBid(streamResult));

        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getMid() != null)
                .mapToDouble(Rate::getMid)
                .min()
                .ifPresent(streamResult -> calculatedRates.setMid(streamResult));

        return calculatedRates;
    }

    private CalculatedRates calculateMaximum(ExchangeRatesSeries exchangeRatesSeries) {
        CalculatedRates calculatedRates = new CalculatedRates();
        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getAsk() != null)
                .mapToDouble(Rate::getAsk)
                .max()
                .ifPresent(calculatedRates::setAsk);

        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getBid() != null)
                .mapToDouble(Rate::getBid)
                .max()
                .ifPresent(streamResult -> calculatedRates.setBid(streamResult));

        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getMid() != null)
                .mapToDouble(Rate::getMid)
                .max()
                .ifPresent(streamResult -> calculatedRates.setMid(streamResult));

        return calculatedRates;
    }

    private static CalculatedRates calculateMid(ExchangeRatesSeries exchangeRatesSeries) {
        CalculatedRates calculatedRates = new CalculatedRates();
        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getAsk() != null)
                .mapToDouble(Rate::getAsk)
                .average()
                .ifPresent(calculatedRates::setAsk);

        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getBid() != null)
                .mapToDouble(Rate::getBid)
                .average()
                .ifPresent(streamResult -> calculatedRates.setBid(streamResult));

        exchangeRatesSeries
                .getRates()
                .stream()
                .filter(rate -> rate.getMid() != null)
                .mapToDouble(Rate::getMid)
                .average()
                .ifPresent(streamResult -> calculatedRates.setMid(streamResult));

        return calculatedRates;
    }


}
