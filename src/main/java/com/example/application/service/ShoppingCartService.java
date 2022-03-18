package com.example.application.service;

import com.example.application.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);
    ShoppingCart findById(Long id);
}
