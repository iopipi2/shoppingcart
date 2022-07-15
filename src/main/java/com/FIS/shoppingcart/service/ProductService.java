package com.FIS.shoppingcart.service;

import com.FIS.shoppingcart.entities.Product;


import java.util.List;
import java.util.Optional;

public interface ProductService {

    String saveProduct(Product product);

    List<Product> findAllProducts();

    List<Product> findAllProductsForAdmin();

    Optional<Product> findProductById(Integer id);

    String deleteProduct(Integer id);

    String updateProduct(Product product);

    List<Product> findProductByCategoryId(Integer categoryId);
    public long count();
    public List<Product> search(String keyword, String category, int page, int sizePerPage);
    Product findProductByIdForAdmin(int id);
    public List<Product> getProductForProductPage(String findName, long priceStart, long priceEnd, int start, int length);

    public List<Product> getProductForProductPagePriceHigh(String sort);

}
