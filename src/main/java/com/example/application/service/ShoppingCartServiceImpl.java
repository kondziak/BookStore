package com.example.application.service;

import com.example.application.model.CartItem;
import com.example.application.model.ShoppingCart;
import com.example.application.repository.ShoppingCartRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

    private CartItemService cartItemService;

    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(@NonNull @Lazy CartItemService cartItemService,
                                   @NonNull @Lazy ShoppingCartRepository shoppingCartRepository) {
        this.cartItemService = cartItemService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        BigDecimal total = new BigDecimal(0);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        for(CartItem cartItem : cartItemList){
            if(cartItem.getQuantity() > 0){
                cartItemService.updateCartItem(cartItem);
                total = total.add(cartItem.getSubtotal());
            }
        }

        shoppingCart.setTotal(total);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }
}
