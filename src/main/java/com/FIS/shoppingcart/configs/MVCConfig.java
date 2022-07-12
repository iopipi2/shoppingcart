package com.FIS.shoppingcart.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
//    public static String ROOT_UPLOAD_PATH = "C:/Users/Admin/Desktop/com.devpro.shop13/com.devpro.shop13/upload/";
//    //cấu hình vị trí chứa folder chứa view
//
//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/user/**").addResourceLocations("classpath:/user/");
//
//        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
//
//        registry.addResourceHandler("/login/**").addResourceLocations("classpath:/login/");
//
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + ROOT_UPLOAD_PATH);
//    }
//
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setViewClass(JstlView.class);
//        bean.setPrefix("/WEB-INF/views/");
//        bean.setSuffix(".jsp");
//        return bean;
//    }
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {

    Path productUploadDir = Paths.get("./product-images");
    Path blogUploadDir = Paths.get("./blog-images");
    Path avatarUploadDir = Paths.get("./avatar-images");

    String productUploadPath = productUploadDir.toFile().getAbsolutePath();
    String blogUploadPath = blogUploadDir.toFile().getAbsolutePath();
    String avatarUploadPath = avatarUploadDir.toFile().getAbsolutePath();

    registry.addResourceHandler("/product-images/**").addResourceLocations("file:/" + productUploadPath + "/");
    registry.addResourceHandler("/blog-images/**").addResourceLocations("file:/" + blogUploadPath + "/");
    registry.addResourceHandler("/avatar-images/**").addResourceLocations("file:/" + avatarUploadPath + "/");


}



}
