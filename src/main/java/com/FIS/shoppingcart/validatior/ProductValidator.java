package com.FIS.shoppingcart.validatior;

import com.FIS.shoppingcart.entities.Product;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub
        Product product = (Product) target;

        // whether file has been selected or not
//        if (product.getImg_main() == null || product.getImg_main().getOriginalFilename().equals("")) {
//            errors.rejectValue("file", null, "Please select an image file to upload!");
//            return;
//        }
//        if (!(product.getImg_main().getContentType().equals("image/jpeg")
//                || product.get().getContentType().equals("image/png"))) {
//            errors.rejectValue("file", null, "Please use only an image file to upload!");
//            return;
//
//        }

    }

}