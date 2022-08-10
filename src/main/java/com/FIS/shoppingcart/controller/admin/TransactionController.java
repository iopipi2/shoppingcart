package com.FIS.shoppingcart.controller.admin;

import com.FIS.shoppingcart.dao.CategoryRepository;
import com.FIS.shoppingcart.dao.ProductRepository;
import com.FIS.shoppingcart.entities.Category;
import com.FIS.shoppingcart.entities.Product;
import com.FIS.shoppingcart.service.impl.CategoryServiceImpl;
import com.FIS.shoppingcart.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @Autowired
    CategoryServiceImpl categoryServiceImpl;



    @Autowired
    ProductRepository productRepository ;
    @Autowired
    CategoryRepository categoryRepository;



    @GetMapping("/admin/warehouse")
    public String viewWarehouse(Model model, @RequestParam(name = "page", required = false) Integer page,
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
        List<Category> listCategories = categoryServiceImpl.findAllCategories();
        model.addAttribute("listCategories", listCategories);
        return "/admin/viewWarehouse";
    }

//    @RequestMapping(value = { "/admin/addProduct" }, method = RequestMethod.GET)
//    public String saveProduct(final ModelMap model, final HttpServletRequest request,
//                              final HttpServletResponse response) throws Exception {
//        model.addAttribute("product", new Product());
//        return "admin/addProduct";
//    }

//    @RequestMapping(value = { "/admin/addProduct" }, method = RequestMethod.POST)
//    public String saveProduct(@RequestParam("product_image") MultipartFile[] productImages,
//                              @ModelAttribute("product") Product product, final ModelMap model, final HttpServletRequest request,
//                              final HttpServletResponse response) throws Exception {
//
//        Slugify slg = new Slugify();
//        String result = slg.slugify(product.getTitle() + "-" + System.currentTimeMillis());
//        product.setSeo(result);
//        Date d = Calendar.getInstance().getTime();
//        product.setCreatedDate(d);
//        productService.save(productImages, product);
//        return "redirect:/admin/listProducts/?add=success";
//    }



    @GetMapping("/admin/product/new")
    public String addProduct(Model model) {

        List<Category> listCategories = categoryServiceImpl.findAllCategories();

        model.addAttribute("product", new Product());

        model.addAttribute("listCategories", listCategories);

        return "/admin/addProduct";
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

        return "redirect:/admin/viewWarehouse";
    }

    @GetMapping("/admin/warehouse/export")
    public String issueProduct(Model model) {

        List<Category> listCategories = categoryServiceImpl.findAllCategories();

        model.addAttribute("product", new Product());

        model.addAttribute("listCategories", listCategories);

        return "/admin/issueProduct";
    }

    @PostMapping("/admin/warehouse/saveExport")
    public String registration(@ModelAttribute("product") Product product, Model model) throws ParseException {
        Product pro = new Product();
        product.setProductquantity(product.getProductquantity());
        return "redirect:/admin/viewWarehouse";

    }



}
