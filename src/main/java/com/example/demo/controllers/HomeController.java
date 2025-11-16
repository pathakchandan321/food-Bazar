package com.example.demo.controllers;

import java.util.List;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entities.Product;
import com.example.demo.loginCredentials.AdminLogin;
import com.example.demo.services.ProductServices;
import com.example.demo.services.OrderServices;

@Controller
public class HomeController {

    @Autowired
    private ProductServices productServices;

    @Autowired
    private OrderServices orderServices;

    @GetMapping("/")
    public String defaultRedirect() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("name", user.getUname());
        return "Home";
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> allProducts = this.productServices.getAllProducts();
        model.addAttribute("products", allProducts);
        return "Products";
    }

    @GetMapping("/buyProduct")
    public String buyProduct(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        List<Orders> orders = orderServices.getOrdersForUser(user);

        model.addAttribute("orders", orders);
        model.addAttribute("name", user.getUname());

        return "BuyProduct";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/location")
    public String location() {
        return "Locate_us";
    }

    @GetMapping("/about")
    public String about() {
        return "About";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("adminLogin", new AdminLogin());
        return "Login";
    }
}
