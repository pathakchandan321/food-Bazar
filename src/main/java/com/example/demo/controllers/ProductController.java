package com.example.demo.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @Autowired
    private OrderServices orderServices;

    // ----------------- ADMIN SIDE -----------------

    // Add Product
    @PostMapping("/addingProduct")
    public String addProduct(@ModelAttribute Product product) {
        this.productServices.addProduct(product);
        return "redirect:/admin/services";
    }

    // Update Product
    @GetMapping("/updatingProduct/{productId}")
    public String updateProduct(@ModelAttribute Product product, @PathVariable("productId") int id) {
        this.productServices.updateproduct(product, id);
        return "redirect:/admin/services";
    }

    // Delete Product
    @GetMapping("/deleteProduct/{productId}")
    public String delete(@PathVariable("productId") int id) {
        this.productServices.deleteProduct(id);
        return "redirect:/admin/services";
    }

    // ----------------- USER SIDE -----------------

    // ✅ Step 1: When user clicks "Buy Now"
    @GetMapping("/product/buy")
    public String buyProduct(@RequestParam("pname") String pname,
                             @RequestParam("pprice") double pprice,
                             HttpSession session) {

        // Save selected product in session
        session.setAttribute("pendingProductName", pname);
        session.setAttribute("pendingProductPrice", pprice);

        // Check if user is logged in
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            // User not logged in → redirect to login page
            return "redirect:/login";
        }

        // Already logged in → go to order creation
        return "redirect:/product/orderNow";
    }

    // ✅ Step 2: Automatically place order after login or if already logged in
    @GetMapping("/product/orderNow")
    public String orderNow(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        String pname = (String) session.getAttribute("pendingProductName");
        Double pprice = (Double) session.getAttribute("pendingProductPrice");

        if (pname == null || pprice == null) {
            return "redirect:/BuyProduct";
        }

        Orders order = new Orders();
        order.setoName(pname);
        order.setoPrice(pprice);
        order.setoQuantity(1);  // default quantity = 1
        order.setOrderDate(new Date());
        order.setUser(user);
        order.setTotalAmmout(pprice);

        // Save order to DB
        orderServices.saveOrder(order);

        // Remove product info from session
        session.removeAttribute("pendingProductName");
        session.removeAttribute("pendingProductPrice");

        // Send order amount to success page
        model.addAttribute("amount", pprice);
        return "Order_success";
    }
}
