package com.solera.bootcamp.springbootdemo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solera.bootcamp.springbootdemo.Service.ShoppingCartService;
import com.solera.bootcamp.springbootdemo.Contracts.IShoppingCart;

@RestController
@RequestMapping("/API/v1/shoppingcart")
public class ShoppingCartController {
    private final IShoppingCart persist;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.persist = shoppingCartService;
    }
}
