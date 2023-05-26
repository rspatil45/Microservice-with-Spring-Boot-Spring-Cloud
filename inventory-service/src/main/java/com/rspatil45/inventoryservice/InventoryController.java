package com.rspatil45.inventoryservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InventoryController {
    List<Inventory> inventoryList = new ArrayList<Inventory>();
    @GetMapping("/inventory/{productid}")
    public  Mono<Inventory> getProductDetails(@PathVariable Long productid)
    {
        //Inventory inventory = new Inventory();
        //inventory = getInventoryInfo(productid);
        Mono<Inventory> inventory = Mono.just(getInventoryInfo(productid));
        return inventory;
    }

    private Inventory getInventoryInfo(Long productid)
    {
        populateProductList();
        for(Inventory p : inventoryList)
        {
            if(productid.equals(p.getProductID()))
                    return p;
        }
        return null;
    }
    private void populateProductList()
    {
        inventoryList.clear();
        inventoryList.add(new Inventory(301L, 101L, true));
        inventoryList.add(new Inventory(302L, 102L, true));
        inventoryList.add(new Inventory(303L, 103L, false));
    }
}
