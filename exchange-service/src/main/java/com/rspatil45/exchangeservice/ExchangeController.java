package com.rspatil45.exchangeservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {
    @GetMapping("/currexg/from/{from}/to/{to}")
    public ExchangeValue getInventoryDetails(@PathVariable("from") Currencies from, @PathVariable("to") Currencies to)
    {
        ExchangeValue exgval = null;
        if(Currencies.USD == from && Currencies.INR == to)
        {
            exgval = new ExchangeValue(901l,from,to,60);
        } else if (Currencies.USD == from && Currencies.YEN == to) {
            exgval = new ExchangeValue(901L, from, to, 105);
        }
        return exgval;
    }
}
