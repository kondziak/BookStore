package com.example.application.service;

import com.example.application.model.*;
import com.example.application.repository.CartItemRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        BigDecimal bigDecimal = BigDecimal.valueOf(cartItem.getBook().getPrice() * cartItem.getQuantity());
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        cartItem.setSubtotal(bigDecimal);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public CartItem addBookToCartItem(Book book, User user, Integer quantity) {
        List<CartItem> cartItems = findByShoppingCart(user.getShoppingCart());

        for(CartItem cartItem : cartItems){
            if(cartItem.getBook().getId().equals(book.getId())){
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setSubtotal(BigDecimal.valueOf(book.getPrice()).multiply(new BigDecimal(quantity)));
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setSubtotal(BigDecimal.valueOf(book.getPrice()).multiply(new BigDecimal(quantity)));
        cartItemRepository.save(cartItem);

        BookedCartItem bookedCartItem = new BookedCartItem();
        bookedCartItem.setCartItem(cartItem);
        bookedCartItem.setBook(book);
        bookedCartItemRepository.save(bookedCartItem);

        return cartItem;
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
