package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.security.UserDetails;
import com.github.onechesz.simplediet.services.OrderService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "")
    public String orders(@NotNull Model model) {
        int userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        model.addAttribute("orders", orderService.findAllByUserId(userId));

        return "orders/orders";
    }
}
