package com.example.demo.controllers;

import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.count.Logic;
import com.example.demo.entities.*;
import com.example.demo.loginCredentials.*;
import com.example.demo.services.*;

@Controller
public class AdminController {

    @Autowired
    private UserServices services;
    @Autowired
    private AdminServices adminServices;
    @Autowired
    private ProductServices productServices;
    @Autowired
    private OrderServices orderServices;


    // ---------------- USER LOGIN ----------------
    @PostMapping("/userLogin")
    public String userLogin(@ModelAttribute("userLogin") UserLogin login,
                            Model model,
                            HttpSession session) {

        String email = login.getUserEmail();
        String password = login.getUserPassword();

        if (services.validateLoginCredentials(email, password)) {

            User user = this.services.getUserByEmail(email);
            session.setAttribute("loggedUser", user);

            return "redirect:/home";
        } else {
            model.addAttribute("error2", "Invalid email or password");
            return "Login";
        }
    }


    // ---------------- PRODUCT SEARCH ----------------
    @PostMapping("/product/search")
    public String searchProduct(@RequestParam("productName") String name,
                                Model model,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        Product product = productServices.getProductByName(name);
        List<Orders> orders = orderServices.getOrdersForUser(user);

        if (product == null) {
            model.addAttribute("message", "SORRY...! Product Unavailable");
        }

        model.addAttribute("product", product);
        model.addAttribute("orders", orders);
        model.addAttribute("name", user.getUname());

        return "BuyProduct";
    }


    // ---------------- PLACE ORDER ----------------
    @PostMapping("/product/order")
    public String placeOrder(@ModelAttribute Orders order,
                             Model model,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        double totalAmount = Logic.countTotal(order.getoPrice(), order.getoQuantity());
        order.setTotalAmmout(totalAmount);
        order.setUser(user);
        order.setOrderDate(new Date());

        orderServices.saveOrder(order);

        List<Orders> orders = orderServices.getOrdersForUser(user);

        model.addAttribute("orders", orders);
        model.addAttribute("name", user.getUname());
        model.addAttribute("success", "Order placed successfully!");

        return "BuyProduct";
    }


    // ---------------- BACK BUTTON ----------------
    @GetMapping("/product/back")
    public String backButton(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        List<Orders> orders = orderServices.getOrdersForUser(user);

        model.addAttribute("orders", orders);
        model.addAttribute("name", user.getUname());

        return "BuyProduct";
    }
}
