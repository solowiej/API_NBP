package com.api_nbp.model.api;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@XmlRootElement(name = "ExchangeRatesSeries")
public class ExchangeRatesSeries {
    @XmlElement(name = "Table")
    @SerializedName(value = "table")
    private String Table;

    @XmlElement(name = "Currency")
    @SerializedName(value = "currency")
    private String Currency;

    @XmlElement(name = "Code")
    @SerializedName(value = "code")
    private String Code;

    @XmlElement(name = "Rate")
    @XmlElementWrapper(name = "Rates")
    @SerializedName(value = "rates")
    private List<Rate> Rates;
}
