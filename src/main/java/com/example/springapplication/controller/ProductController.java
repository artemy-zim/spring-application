package com.example.springapplication.controller;

import com.example.springapplication.entity.Product;
import com.example.springapplication.repository.ProductRepository;
import com.example.springapplication.service.UAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    private final UAService uaService;

    public ProductController(UAService uaService) {
        this.uaService = uaService;
    }

    @GetMapping("/list")
    public ModelAndView getAllProducts() {
        ModelAndView mav = new ModelAndView("list-products");
        mav.addObject("products", productRepository.findAll());
        return mav;
    }

    @GetMapping("/addProductForm")
    public ModelAndView addProductForm() {
        ModelAndView mav = new ModelAndView("add-product-form");
        Product product = new Product();
        mav.addObject("product", product);
        return mav;
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product) {
        uaService.saveUserAction(currentUsername() + " save product " + product.getName());
        productRepository.save(product);
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long productId) {
        ModelAndView mav = new ModelAndView("add-product-form");
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = new Product();
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        }
        mav.addObject("product", product);
        return mav;
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        uaService.saveUserAction(currentUsername() + " delete product " + product.get().getName());
        productRepository.deleteById(productId);

        return "redirect:/list";
    }

    private String currentUsername() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }
}
