package com.rspatil45.prodctservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private Long inventoryID;
    private long productID;
    private Boolean inStock;

}
