package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Product;


import java.util.List;

public interface ProductService {

    String saveProduct(Product product);

    List<Product> findAllProducts();

    List<Product> findAllProductsForAdmin();

    Product findProductById(Integer id);

    String deleteProduct(Integer id);

    String updateProduct(Product product);

    List<Product> findProductByCategoryId(Integer categoryId);

    Product findProductByIdForAdmin(int id);

}
