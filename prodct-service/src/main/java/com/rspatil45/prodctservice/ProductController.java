package com.rspatil45.prodctservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    List<ProductInfo> productList = new ArrayList<ProductInfo>();

    public ProductController() {
        populateProductList();
    }

    public WebClient webClient = WebClient.create();

    @GetMapping("/products/details/{productid}")
    public  Mono<Product> getProductDetails(@PathVariable Long productid)
    {
        Mono<ProductInfo> pinfo = Mono.just(getProductInfo(productid));

        //Get price from pricing service
        Mono<Price> price = webClient.get().uri("http://localhost:8083/price/{productid}",productid).retrieve().bodyToMono(Price.class);

        //Get stock available from inventory service
        Mono<Inventory> inventory = webClient.get().uri("http://localhost:8084/inventory/{productid}", productid).retrieve().bodyToMono(Inventory.class);

        //return Mono.zip(pinfo, price, inventory).map(this::buildProduct);
        return Mono.zip(pinfo, price, inventory).map(tuple -> new Product(tuple.getT1().getProductID(), tuple.getT1().getProductName(), tuple.getT1().getProductDesc(), tuple.getT2().getDiscountedPrice(), tuple.getT3().getInStock()));
    }

    public Product buildProduct(Tuple3<ProductInfo, Price, Inventory> tuple)
    {
        return new Product(tuple.getT1().getProductID(), tuple.getT1().getProductName(), tuple.getT1().getProductDesc(), tuple.getT2().getDiscountedPrice(), tuple.getT3().getInStock());
    }

    private ProductInfo getProductInfo(Long productid)
    {
        for(ProductInfo p : productList)
        {
            if(productid.equals(p.getProductID()))
                    return p;
        }
        return null;
    }
    private void populateProductList()
    {
        productList.clear();
        productList.add(new ProductInfo(101L, "iphone","iphone is damm expensive!"));
        productList.add(new ProductInfo(102L, "Book","Yayati and mrutunjay"));
        productList.add(new ProductInfo(103L, "Washing MC","Required part of life"));
    }

    @GetMapping("/products/list")
    public List<Product> getProducts()
    {
        return productList.stream().map(x -> {
            //get price from pricing service
            Price price = webClient.get().uri("http://localhost:8083/price/{productid}",x.getProductID()).retrieve().bodyToMono(Price.class).block();
            //get inventory from inventory
            Inventory inventory = webClient.get().uri("http://localhost:8084/inventory/{productid}",x.getProductID()).retrieve().bodyToMono(Inventory.class).block();

            return new Product(x.getProductID(), x.getProductName(), x.getProductDesc(), price.getDiscountedPrice(), inventory.getInStock());
        }).collect(Collectors.toList());
    }
}
