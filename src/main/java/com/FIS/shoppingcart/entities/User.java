package com.FIS.shoppingcart.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "shoppingcart")
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name ="password")
    private String password;

    @Column(name ="mail")
    private String username;

    @Column(name ="role")
    private String role;

    @Column(name="enabled")
    private Boolean enabled;

    @Column(name = "created_time", updatable = false)
    private Date created_time;

    @Column(name ="name")
    private String name;

    private String phone;

    private String city;
    private String address;
    private String state;
    private int country_id;
    private String postal_code;
    private String avatar;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Cart> carts;


    @Column(name = "one_time_password")
    private String oneTimePassword;



    public User(int id, String password, String username, String role, Boolean enabled, Date created_time, String name,
                String phone, String city, String address, String state, int country_id, String postal_code,String avatar) {
        super();
        this.id = id;
        this.password = password;
        this.username = username;
        this.role = role;
        this.enabled = enabled;
        this.created_time = created_time;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.address = address;
        this.state = state;
        this.country_id = country_id;
        this.postal_code = postal_code;
        this.avatar = avatar;

    }

    public Cart getCart() {
        return (Cart) carts;
    }

    public void setCart(Cart cart) {
        this.carts = (List<Cart>) cart;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }



    @Transient
    public String getAvatarImagePath() {
        if (avatar == null) return null;

        return "/avatar-images/" + id + "/" + avatar;
    }

}