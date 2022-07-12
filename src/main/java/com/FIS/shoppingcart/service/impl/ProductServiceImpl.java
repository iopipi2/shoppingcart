package com.FIS.shoppingcart.service.impl;


import java.util.ArrayList;
import java.util.List;

import com.FIS.shoppingcart.dao.ProductRepository;
import com.FIS.shoppingcart.entities.Product;

import com.FIS.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Qualifier("productRepository")
    @Autowired
    private ProductRepository productRepository;

    @Override
    public String saveProduct(Product product) {
        // TODO Auto-generated method stub
        productRepository.saveAndFlush(product);
        return "Product Added Sucessfully";
    }

    @Override
    public List<Product> findAllProducts() {
        try {
            List<Product> products = new ArrayList<>();
            List<Product> productsCopy = new ArrayList<>();
            productRepository.findAll().forEach(products::add);
            productsCopy.addAll(products);
            for (Product p : products) {
                if (!p.isActive()) {
                    productsCopy.remove(p);
                }
            }
            return productsCopy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Product findProductById(Integer id) {
        // TODO Auto-generated method stub
        Product product = productRepository.getOne(id);
        if (!product.isActive())
            product = null;

        return product;
    }

    @Override
    public String deleteProduct(Integer id) {

        Product product = productRepository.getOne(id);

        product.setActive(false);
        productRepository.saveAndFlush(product);

        return "Product Deleted Sucessfully";
    }

    @Override
    public String updateProduct(Product product) {
        // TODO Auto-generated method stub
        productRepository.saveAndFlush(product);
        return "Product Updated Sucessfully";
    }

    @Override
    public List<Product> findProductByCategoryId(Integer categoryId) {
        // TODO Auto-generated method stub
        List<Product> products = new ArrayList<>();
        List<Product> productsCopy = new ArrayList<>();
        productRepository.findProductByCategoryId(categoryId).forEach(products::add);
        productsCopy.addAll(products);
        for (Product p : products) {
            if (!p.isActive()) {
                productsCopy.remove(p);
            }
        }
        return productsCopy;
    }

    @Override
    public List<Product> findAllProductsForAdmin() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public Product findProductByIdForAdmin(int id) {
        // TODO Auto-generated method stub
        Product product = productRepository.getOne(id);
        return product;
    }

}
