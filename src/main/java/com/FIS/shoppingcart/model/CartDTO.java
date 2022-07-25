package com.FIS.shoppingcart.model;


import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartDTO {
    private Long id;
    private UserModel user;
    private String buyDate;
    private BigDecimal priceTotal;
    private String status;

    private List<ProductDTO> itemInCart=new ArrayList<>();

    public List<ProductDTO> getItemInCart() {
        return itemInCart;
    }

    public void setItemInCart(List<ProductDTO> itemInCart) {
        this.itemInCart = itemInCart;
    }

    public BigDecimal getPriceTotal(ProductServiceImpl productService) {
        BigDecimal decimal = BigDecimal.ZERO;
        for(ProductDTO phamTrongGioHang : itemInCart) {
            Optional<Product> product = productService.findProductById(phamTrongGioHang.getId());

                decimal = decimal.add(product.get().getPrice());
        }
        return decimal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
