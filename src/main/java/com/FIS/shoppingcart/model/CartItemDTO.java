package com.FIS.shoppingcart.model;


import com.FIS.shoppingcart.entities.Product;

import java.math.BigDecimal;

public class CartItemDTO {

    private Integer id;
    private Product product;

    private int quantity;

    private BigDecimal price;

    private BigDecimal tongGia;

    private String description;
    private CategoriesDTO category;
    private String img_main;
    private String img_hover;
    private String img_sub;
    public BigDecimal getTongGia() {
        return tongGia;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTongGia(BigDecimal tongGia) {
        this.tongGia = tongGia;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CartItemDTO(Integer id, Product product,String name, int quantity, BigDecimal price, BigDecimal tongGia, String img_main, String img_hover, String img_sub) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.tongGia = tongGia;
        this.img_main = img_main;
        this.img_hover = img_hover;
        this.img_sub = img_sub;
    }

    public CartItemDTO(Integer id, Product product, int quantity, BigDecimal price) {
        super();
        this.id = id;
        this.product = product;

        this.quantity = quantity;
        this.price=price;
    }

    public CartItemDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoriesDTO getCategory() {
        return category;
    }

    public void setCategory(CategoriesDTO category) {
        this.category = category;
    }

    public String getImg_main() {
        return img_main;
    }

    public void setImg_main(String img_main) {
        this.img_main = img_main;
    }

    public String getImg_hover() {
        return img_hover;
    }

    public void setImg_hover(String img_hover) {
        this.img_hover = img_hover;
    }

    public String getImg_sub() {
        return img_sub;
    }

    public void setImg_sub(String img_sub) {
        this.img_sub = img_sub;
    }


}