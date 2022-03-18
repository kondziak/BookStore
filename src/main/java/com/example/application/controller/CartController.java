package com.example.application.controller;

import com.example.application.model.Book;
import com.example.application.model.CartItem;
import com.example.application.model.ShoppingCart;
import com.example.application.model.User;
import com.example.application.service.BookService;
import com.example.application.service.CartItemService;
import com.example.application.service.ShoppingCartService;
import com.example.application.service.UserService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart_action")
public class CartController {

    private final UserService userService;
    private final BookService bookService;
    private final CartItemService cartItemService;
    private final ShoppingCartService shoppingCartService;

    public CartController(@NonNull @Lazy UserService userService, @NonNull @Lazy BookService bookService,
                          @NonNull @Lazy CartItemService cartItemService,@NonNull @Lazy ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.bookService = bookService;
        this.cartItemService = cartItemService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/showCart")
    public String getCart(Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("user",user);
        model.addAttribute("cartItems",cartItemList);
        model.addAttribute("shoppingCart",shoppingCart);

        return "cart";
    }

    @PostMapping("/addToCart")
    public String setCart(@RequestParam(name = "quantity") Integer quantity, @ModelAttribute("book") Book book,
                          @RequestParam(name = "id") Long id, Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        Book bookToAdd = bookService.getBookById(book.getId());

        cartItemService.addBookToCartItem(bookToAdd,user,quantity);
        model.addAttribute("book", book);
        model.addAttribute("bookSuccess",true);
        model.addAttribute("id", id);
        return "redirect:/book_action/book_detail?id=" + id.toString();
    }

    @GetMapping("/bookItems")
    public String bookItems(@ModelAttribute("shoppingCart") ShoppingCart shoppingCart, Model model){
        model.addAttribute("shoppingCart",shoppingCart);

        return "booking_items";
    }


}
