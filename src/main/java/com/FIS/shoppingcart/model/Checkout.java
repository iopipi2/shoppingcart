package com.FIS.shoppingcart.model;

import com.FIS.shoppingcart.entities.CartLine;
import lombok.Data;

import java.util.List;

@Data
public class Checkout {
    private List<CartLine> cartlines;
    private Long total;
}
