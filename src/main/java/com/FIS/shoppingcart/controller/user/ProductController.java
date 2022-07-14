package com.FIS.shoppingcart.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.FIS.shoppingcart.service.impl.CategoryServiceImpl;
import com.FIS.shoppingcart.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.FIS.shoppingcart.dao.CategoryRepository;
import com.FIS.shoppingcart.dao.ProductRepository;
import com.FIS.shoppingcart.model.CategoriesDTO;
import com.FIS.shoppingcart.model.ProductDTO;
import com.FIS.shoppingcart.model.UserDTO;
import com.FIS.shoppingcart.service.CategoryService;
import com.FIS.shoppingcart.service.ProductService;
import com.FIS.shoppingcart.service.UserService;
import org.springframework.web.servlet.ModelAndView;

public class ProductController {



}
