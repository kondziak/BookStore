package com.example.application.service;

import com.example.application.model.Book;
import com.example.application.model.CartItem;
import com.example.application.model.User;
import com.example.application.repository.BookedCartItemRepository;
import com.example.application.repository.CartItemRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;

    private final BookedCartItemRepository bookedCartItemRepository;

    public CartItemServiceImpl(@NonNull @Lazy CartItemRepository cartItemRepository,
                               @NonNull @Lazy BookedCartItemRepository bookedCartItemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.bookedCartItemRepository = bookedCartItemRepository;
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getPrice() * cartItem.getQuantity());
        bigDecimal.setScale(2, RoundingMode.HALF_UP);
        cartItem.setSubtotal(bigDecimal);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public CartItem addBookToCartItem(Book book, User user, Integer quantity) {
        return null;
    }

    @Override
    public CartItem getById(Long id) {
        return cartItemRepository.getById(id);
    }

    @Override
    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
