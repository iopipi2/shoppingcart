package com.FIS.shoppingcart.controller.admin;



import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import com.FIS.shoppingcart.dao.CategoryRepository;
import com.FIS.shoppingcart.dao.ProductRepository;
import com.FIS.shoppingcart.entities.Category;
import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.service.ProductService;
import com.FIS.shoppingcart.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/admin/product")
    public String viewProduct(Model model, @RequestParam(name = "page", required = false) Integer page,
                              @RequestParam(name = "size", required = false) Integer size,
                              @RequestParam(name="keyword",required = false) String keyword,
                              @RequestParam(name="category",required = false) String category) {

        if(keyword == null) keyword = "";

        if (category == null) category = "";

        if (page == null) page = 0;

        if (size == null) size = 5;

        long count = productServiceImpl.count();

        long pageTotal = (long) Math.ceil((double)count/size);

        List<Product> products = productServiceImpl.search(keyword, category, page, size);

        model.addAttribute("products", products);

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pageTotal", pageTotal);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        System.out.println(products);

        List<Category> listCategories = categoryRepository.findAll();

        model.addAttribute("listCategories", listCategories);

        return "/admin/viewProduct";
    }

//    @GetMapping("/admin/product/new")
//    public String addProduct(Model model) {
//
//        List<Categories> listCategories = categoryRepository.findAll();
//
//        model.addAttribute("product", new Product());
//
//        model.addAttribute("listCategories", listCategories);
//
//        return "/admin/addProduct";
//    }

    //Viet
    @GetMapping("/admin/product-detail")
    public String viewProductDetail(Model model, @RequestParam int id) {
        Product product=productServiceImpl.findProductById(id).get();
        model.addAttribute("product", product);
        System.out.println(product);
        return "/admin/productDetail";
    }
    @PostMapping("/admin/product/save")
    public String addProductSave(@ModelAttribute("product") Product product,@RequestParam(name="primaryImage") MultipartFile fileMain,
                                 @RequestParam(name="img_sub1") MultipartFile[] fileSub) throws IOException {

        String fileMainName = StringUtils.cleanPath(fileMain.getOriginalFilename());
        int count = 0;
        for(MultipartFile sub : fileSub) {
            String fileSubName = StringUtils.cleanPath(sub.getOriginalFilename());
            if(count == 0) product.setImg_hover(fileSubName);
            if(count == 1) product.setImg_sub(fileSubName);
            count++;
        }
        Product savedProduct = productRepository.save(product);

        String uploadDir ="./product-images/";

        //Lưu file ảnh chính
        Path uploadPathMain = Paths.get(uploadDir);

        if(!Files.exists(uploadPathMain)) {
            Files.createDirectories(uploadPathMain);
        }

        try(InputStream inputStream = fileMain.getInputStream()){

            Path filePathMain = uploadPathMain.resolve(fileMainName);
            System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());

            Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);

        }catch(IOException e) {
            throw new IOException("Could not save upload file : " + fileMainName);
        }
        //Lưu file ảnh con
        for(MultipartFile sub: fileSub) {

            String fileSubName = StringUtils.cleanPath(sub.getOriginalFilename());

            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = sub.getInputStream()){

                Path filePath = uploadPath.resolve(fileSubName);
                System.out.println("Check : " + filePath.toFile().getAbsolutePath());

                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e) {
                throw new IOException("Could not save upload file : " + fileSubName);
            }

        }

        product.setImg_main(fileMainName);

       productServiceImpl.saveProduct(product);
        return "redirect:'/admin/productDetail/id=?'+${product.id}";
    }
    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable(name="id") int id) {
        productServiceImpl.deleteProduct(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/productDetail/edit/{id}")
    public String editProduct(@PathVariable(name="id") int id, Model model) {
        model.addAttribute("product", productServiceImpl.findProductById(id).get());
        List<Category> listCategories = categoryRepository.findAll();
        model.addAttribute("listCategories", listCategories);
        return "/admin/editProduct";
    }
    @PostMapping("/admin/product/edit")
    public String saveEditProduct(@RequestParam(name="id") int id,@ModelAttribute("product") Product product, @RequestParam(name="primaryImage") MultipartFile fileMain,
                                  @RequestParam(name="img_sub1") MultipartFile[] fileSub) throws IOException {
        String fileMainName = StringUtils.cleanPath(fileMain.getOriginalFilename());
        if(fileMainName != "") {
            product.setImg_hover(productService.findProductById(id).get().getImg_hover());
            product.setImg_sub(productService.findProductById(id).get().getImg_sub());
            product.setImg_main(fileMainName);
            Product savedProduct = productRepository.save(product);
            String uploadDir ="./product-images/" + savedProduct.getId();
            //Lưu file ảnh chính
            Path uploadPathMain = Paths.get(uploadDir);
            if(!Files.exists(uploadPathMain)) {
                Files.createDirectories(uploadPathMain);
            }
            try(InputStream inputStream = fileMain.getInputStream()){

                Path filePathMain = uploadPathMain.resolve(fileMainName);
                System.out.println("Check : " + filePathMain.toFile().getAbsolutePath());

                Files.copy(inputStream, filePathMain, StandardCopyOption.REPLACE_EXISTING);

            }catch(IOException e) {
                throw new IOException("Could not save upload file : " + fileMainName);
            }
        }else {
        }
        String fileSubName = "";
        int count = 0;
        for(MultipartFile sub : fileSub) {
            fileSubName = StringUtils.cleanPath(sub.getOriginalFilename());
            if(fileSubName != "") {
                if(count == 0) product.setImg_hover(fileSubName);
                if(count == 1) product.setImg_sub(fileSubName);
            }
            count++;
        }
        if(fileSubName != "") {

            Product savedProduct = productRepository.save(product);
            String uploadDir ="./product-images/";

            //Lưu file ảnh con
            for(MultipartFile sub: fileSub) {
                fileSubName = StringUtils.cleanPath(sub.getOriginalFilename());
                Path uploadPath = Paths.get(uploadDir);
                if(!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try(InputStream inputStream = sub.getInputStream()){
                    Path filePath = uploadPath.resolve(fileSubName);
                    System.out.println("Check : " + filePath.toFile().getAbsolutePath());
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }catch(IOException e) {
                    throw new IOException("Could not save upload file : " + fileSubName);
                }
            }
        }else {
            product.setImg_hover(productService.findProductById(id).get().getImg_hover());
            product.setImg_sub(productService.findProductById(id).get().getImg_sub());
            product.setImg_main(productService.findProductById(id).get().getImg_main());
            productRepository.save(product);
        }
        return "redirect:/admin/product";
    }

}

