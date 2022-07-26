package com.FIS.shoppingcart.entities;

import javax.persistence.*;

@Entity
@Table(name="cart_line")
public class  CartLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;

    public CartLine() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Transient
    private int numorder;

    public int getNumorder() {
        return numorder;
    }

    public void setNumorder(int numorder) {
        this.numorder = numorder;
    }

    public CartLine(Integer id, Product product , int quantity) {
        super();
        this.id = id;
        this.product = product;
        this.quantity = quantity;
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
