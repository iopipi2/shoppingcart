package com.FIS.shoppingcart.service.impl;


import java.util.*;

import com.FIS.shoppingcart.dao.ProductRepository;
import com.FIS.shoppingcart.entities.Product;

import com.FIS.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Qualifier("productRepository")
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;
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
    public Optional<Product> findProductById(Integer id) {
        // TODO Auto-generated method stub
       Optional<Product>  product = productRepository.findById(id);
        if (!product.get().isActive()){
            product = null;}

        return product;
    }

    @Override
    public long count() {
            return productRepository.count();
        }
    @Override
    public List<Product> search(String keyword, String category, int page, int sizePerPage) {


        Pageable pageable = (Pageable) PageRequest.of(page, sizePerPage, Sort.by("id").descending());

        List<Product> productEntities = productRepository.findAll(keyword,category, pageable);


        return productEntities;
    }

    @Override
    public String deleteProduct(Integer id) {

        Product product = productRepository.findById(id).get();

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
        Product product = productRepository.findById(id).get();
        return product;
    }


    public List<Product> getProductPage(String findName, long priceStart, long priceEnd, int start, int length) {
        try {
            String hql = "SELECT p FROM Product p WHERE p.name LIKE :pname and p.price between :priceStart AND :priceEnd";

            return entityManager.createQuery(hql, Product.class).setParameter("pname", "%" + findName + "%").setParameter("priceStart", priceStart).setParameter("priceEnd", priceEnd).setMaxResults(length).getResultList();

        }catch(Exception e) {
            System.out.println("Error" + e);
        }
        return null;
    }
    @Override
    public List<Product> getProductForProductPage(String findName, long priceStart, long priceEnd, int start, int length) {

        List<Product> listProducts = getProductPage(findName,priceStart,priceEnd, start, length);

//        for(Product p : listProducts) {
//            Product product = new Product();
//            product.setId(p.getId());
//            product.setName(p.getName());
//            product.setPrice(p.getPrice());
//            Category category = new Category();
//            category.setId(p.getCategory().getId());
//            category.setType(p.getCategory().getType());
//            product.setCategory(category);
//            product.setDescription(p.getDescription());
//            product.setImg_main(p.getImg_main());
//            product.setImg_hover(p.getImg_hover());
//            product.setImg_sub(p.getImg_sub());
//            productRepository.saveAndFlush(product);
//        }
        return listProducts;
    }

    @Override
    public List<Product> getProductForProductPagePriceHigh(String sort) {
        List<Product> listProducts = new ArrayList<>();

        if(sort.equals("true") ) {
            listProducts = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
            return listProducts;
        }else {
            listProducts = productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
            return listProducts;
        }
    }
}
