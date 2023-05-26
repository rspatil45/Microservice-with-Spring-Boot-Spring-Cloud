package com.rspatil45.productpricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PriceController {
    List<Price> priceList = new ArrayList<Price>();

    public PriceController() {
        populatePriceList();
    }

    public WebClient webClient = WebClient.create();

    @GetMapping("/price/{productid}")
    public Mono<Price> getPriceDetails(@PathVariable Long productid)
    {
        //Price price = getPriceInfo(productid);
        Mono<Price> price = Mono.just(getPriceInfo(productid));

        //Integer exgVal = restTemplate.getForObject("http://localhost:8085/currexg/from/"+Currencies.USD+"/to/"+Currencies.INR, ExchangeValue.class).getExgVal();
        Mono<ExchangeValue> exgVal = webClient.get().uri("http://localhost:8085/currexg/from/"+Currencies.USD+"/to/"+Currencies.INR).retrieve().bodyToMono(ExchangeValue.class);
        //return new Price(price.getPriceID(), price.getProductID(), price.getOriginalPrice(), Math.multiplyExact(exgVal, price.getDiscountedPrice()));
        return  Mono.zip(price,exgVal).map(this::buildPrice);
    }

    public Price buildPrice(Tuple2<Price, ExchangeValue> tuple2)
    {
        return new Price(tuple2.getT1().getPriceID(), tuple2.getT1().getProductID(), tuple2.getT1().getOriginalPrice(),
                Math.multiplyExact(tuple2.getT2().getExgVal(), tuple2.getT1().getDiscountedPrice()));
    }
    public Price getPriceInfo(Long productid)
    {
        for(Price p : priceList)
        {
            if(productid.equals(p.getProductID()))
                return  p;
        }
        return null;
    }
    public void populatePriceList()
    {
        priceList.clear();
        priceList.add(new Price(201L,101L,1999,999));
        priceList.add(new Price(202L,102L,199,19));
        priceList.add(new Price(203L,103L,1222,600));
    }
}
