package com.example.application.service;

import com.example.application.model.Book;
import com.example.application.model.CartItem;
import com.example.application.model.User;

public interface CartItemService {

    CartItem updateCartItem(CartItem cartItem);
    CartItem addBookToCartItem(Book book, User user, Integer quantity);
    CartItem getById(Long id);
    void removeCartItem(Long id);

}
