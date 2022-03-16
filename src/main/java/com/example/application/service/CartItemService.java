package com.example.application.service;

import com.example.application.model.Book;
import com.example.application.model.CartItem;
import com.example.application.model.ShoppingCart;
import com.example.application.model.User;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    CartItem updateCartItem(CartItem cartItem);
    CartItem addBookToCartItem(Book book, User user, Integer quantity);
    CartItem getById(Long id);
    void removeCartItem(Long id);

}
