package com.example.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @Column(name = "subtotal",nullable = false)
    private BigDecimal subtotal;

    @OneToOne
    private Book book;

    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    private List<BookedCartItem> bookedCartItemList;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<BookedCartItem> getBookedCartItemList() {
        return bookedCartItemList;
    }

    public void setBookedCartItemList(List<BookedCartItem> bookedCartItemList) {
        this.bookedCartItemList = bookedCartItemList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
