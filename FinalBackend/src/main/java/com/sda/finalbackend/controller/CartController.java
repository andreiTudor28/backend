package com.sda.finalbackend.controller;

import com.sda.finalbackend.dto.CartDto;
import com.sda.finalbackend.entity.Cart;
import com.sda.finalbackend.errors.CartNotFoundException;
import com.sda.finalbackend.service.CartService;
import com.sda.finalbackend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CartController {

    @Autowired
    private CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse> createCart(@RequestBody CartDto cartDtoBody) {
        try {
            Cart cart = this.cartService.createCart(cartDtoBody);

            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Cart create cu succes.")
                    .data(cart)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity
                    .status(400)
                    .body(response);
        }
    }

    @GetMapping("/carts")
    public ResponseEntity<ApiResponse> getAllCarts() {
        List<Cart> cartList = this.cartService.getAllCarts();

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Lista cu toate carturile active.")
                .data(cartList)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<ApiResponse> deleteCartById(@PathVariable("id") Integer id) {
        try {

            this.cartService.deleteCart(id);
            ApiResponse response = new ApiResponse
                    .Builder()
                    .status(200)
                    .message("Cart-ul a fost sters cu succes.")
                    .data(null)
                    .build();

            return ResponseEntity.ok(response);

        } catch (CartNotFoundException cartNotFoundException) {

            ApiResponse response = new ApiResponse
                    .Builder()
                    .status(400)
                    .message(cartNotFoundException.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity
                    .status(400)
                    .body(response);

        }
    }

}
