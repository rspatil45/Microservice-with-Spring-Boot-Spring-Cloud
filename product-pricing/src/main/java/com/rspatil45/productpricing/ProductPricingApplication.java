package com.rspatil45.productpricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProductPricingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductPricingApplication.class, args);
    }

//    @Bean
//    public RestTemplate getRestTemplate()
//    {
//        return new RestTemplate();
//    }
}
