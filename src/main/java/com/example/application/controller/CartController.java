package com.example.application.controller;

import com.example.application.configuration.CardValidation;
import com.example.application.model.*;
import com.example.application.service.*;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cartAction")
public class CartController {

    private final UserService userService;
    private final BillingService billingService;
    private final BookService bookService;
    private final CartItemService cartItemService;
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    public CartController(@NonNull @Lazy UserService userService, @NonNull @Lazy BookService bookService,
                          @NonNull @Lazy CartItemService cartItemService,@NonNull @Lazy BillingService billingService,
                          @NonNull @Lazy ShoppingCartService shoppingCartService, @NonNull @Lazy OrderService orderService,
                          @NonNull @Lazy PaymentService paymentService) {
        this.userService = userService;
        this.bookService = bookService;
        this.cartItemService = cartItemService;
        this.shoppingCartService = shoppingCartService;
        this.billingService = billingService;
        this.orderService = orderService;
        this.paymentService = paymentService;
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
        return "redirect:/bookAction/bookDetail?id=" + id.toString();
    }

    @GetMapping("/bookItems/{id}")
    public String bookItems(@PathVariable(name = "id") Long id, Model model){
        ShoppingCart shoppingCart = shoppingCartService.findById(id);
        Billing billing = new Billing();
        model.addAttribute("billing",billing);
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("userBillings",shoppingCart.getUser().getBillingList());

        return "booking_items";
    }

    @PostMapping("/bookItems/addAnAddress")
    public String enterShippingAddress(@ModelAttribute("billing") Billing billing, Principal principal,Model model){
        User user = userService.findUserByEmail(principal.getName());
        billing.setUser(user);
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setBilling(billing);
        billingService.save(billing);
        model.addAttribute("payment",payment);
        model.addAttribute("userPayments",user.getUserPayments());
        return "pay";
    }

    @PostMapping("/bookItems/useExistingAddress")
    public String enterExistingAddress(@RequestParam(name = "id")Long id,Principal principal,Model model){
        User user = userService.findUserByEmail(principal.getName());
        Billing newBilling = new Billing();
        for(Billing billing : user.getBillingList()){
            if(billing.getId().equals(id)){
                newBilling = billing;
                break;
            }
        }
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setBilling(newBilling);
        model.addAttribute("billingId",id);
        model.addAttribute("payment",payment);
        model.addAttribute("userPayments",user.getUserPayments());
        return "pay";
    }

    @PostMapping("/bookItems/checkPayment")
    public String checkPayment(@ModelAttribute("payment") Payment payment, @RequestParam(name = "billingId") Long id,
                               Model model, Principal principal){
        boolean error = false;
        String message = "";
        if (payment.getExpiryYear() < 2022) {
            error = true;
            message = "Wrong expiry year";
        } else if (!(payment.getExpiryMonth() > 0 && payment.getExpiryMonth() < 13)) {
            error = true;
            message = "Wrong expiry month";
        } else if (!(payment.getCvc() > 99 && payment.getCvc() < 1000)) {
            error = true;
            message = "Invalid cvc";
        } else if (!CardValidation.isValid(payment.getCardNumber())) {
            error = true;
            message = "Invalid card number";
        }
        model.addAttribute("payment", new Payment());
        if(error){
            model.addAttribute("error",message);
        }
        else{
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,3);
            User user = userService.findUserByEmail(principal.getName());
            Billing billing = billingService.findById(id);
            payment.setUser(user);
            payment.setBilling(billing);
            List<CartItem> itemList = new ArrayList<>(user.getShoppingCart().getCartItemList());
            Order order = new Order();
            order.setPayment(payment);
            order.setBilling(billing);
            order.setUser(user);
            order.setBookedCartItemList(itemList);
            order.setOrderDate(date);
            order.setOrderStatus("ordered");
            order.setTotalOrder(user.getShoppingCart().getTotal());
            order.setShippingDate(calendar.getTime());
            order = orderService.save(order);
            payment.setOrder(order);
            paymentService.save(payment);
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCartService.updateShoppingCart(shoppingCart);
            user.setShoppingCart(shoppingCart);
            userService.save(user);
            model.addAttribute("success","You paid successfuly");
        }
        return "home";
    }

    @PostMapping("/bookItems/checkPaymentWithExistingCard/{billingId}")
    public String checkPaymentWithExistingCard(@PathVariable(name = "billingId") Long id,
                                               @RequestParam(name = "paymentId") Long paymentId,
                                               Model model, Principal principal){
        Payment payment = paymentService.findById(paymentId);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,3);
        User user = userService.findUserByEmail(principal.getName());
        Billing billing = billingService.findById(id);
        payment.setUser(user);
        payment.setBilling(billing);
        List<CartItem> itemList = new ArrayList<>(user.getShoppingCart().getCartItemList());
        Order order = new Order();
        order.setPayment(payment);
        order.setBilling(billing);
        order.setUser(user);
        order.setBookedCartItemList(itemList);
        order.setOrderDate(date);
        order.setOrderStatus("ordered");
        order.setTotalOrder(user.getShoppingCart().getTotal());
        order.setShippingDate(calendar.getTime());
        order = orderService.save(order);
        payment.setOrder(order);
        paymentService.save(payment);
        ShoppingCart shoppingCart = user.getShoppingCart();
        shoppingCart.setCartItemList(new ArrayList<>());
        shoppingCartService.updateShoppingCart(shoppingCart);
        shoppingCartService.save(shoppingCart);
        user.setShoppingCart(shoppingCart);
        userService.save(user);
        model.addAttribute("success","You paid successfuly");
        return "home";
    }





}
