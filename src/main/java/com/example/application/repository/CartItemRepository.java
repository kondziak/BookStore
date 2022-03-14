package com.example.application.repository;

import com.example.application.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
