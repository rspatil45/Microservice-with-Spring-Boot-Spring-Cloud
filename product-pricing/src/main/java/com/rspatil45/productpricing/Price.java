package com.rspatil45.productpricing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private Long priceID;
    private Long productID;
    private Integer originalPrice;
    private Integer discountedPrice;
}
