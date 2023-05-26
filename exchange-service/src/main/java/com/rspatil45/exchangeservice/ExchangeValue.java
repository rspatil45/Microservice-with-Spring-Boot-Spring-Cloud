package com.rspatil45.exchangeservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeValue {
    private  Long id;
    private Currencies from;
    private Currencies to;
    private Integer exgVal;
}
