package com.FIS.shoppingcart.model;


import com.FIS.shoppingcart.entities.Product;

public class CartItemDTO {

    private Integer id;
    private Product product;
    private UserDTO user;
    private int quantity;

    public CartItemDTO(Integer id, Product product, UserDTO user, int quantity) {
        super();
        this.id = id;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}