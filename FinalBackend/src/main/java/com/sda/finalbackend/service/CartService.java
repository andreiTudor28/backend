package com.sda.finalbackend.service;

import com.sda.finalbackend.dto.CartDto;
import com.sda.finalbackend.entity.Cart;
import com.sda.finalbackend.entity.Item;
import com.sda.finalbackend.entity.User;
import com.sda.finalbackend.errors.CartNotFoundException;
import com.sda.finalbackend.errors.ItemNotFoundException;
import com.sda.finalbackend.errors.UserNotFoundException;
import com.sda.finalbackend.repository.CartRepository;
import com.sda.finalbackend.repository.ItemRepository;
import com.sda.finalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    public CartService(CartRepository cartRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Cart createCart(CartDto cartDto) throws Exception {

        if (cartDto.getUserId() == null || cartDto.getUserId() <= 0) {
            throw new Exception("Invalid user id.");
        }

        Optional<User> userOptional = userRepository.findById(cartDto.getUserId());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User nu a fost gasit.");
        }

        if (cartDto.getItems() == null || cartDto.getItems().isEmpty()) {
            throw new Exception("Invalid Items");
        }

        List<Item> listItems = cartDto.getItems();

        for (Item item : listItems) {
            if (item.getId() != null) {
                Optional<Item> itemOptional = this.itemRepository.findById(item.getId());
                if (itemOptional.isEmpty()) {
                    throw new ItemNotFoundException("Item-ul cu id: " + item.getId() + " nu a fost gasit.");
                }
            }
        }

        Cart cart = new Cart();
        cart.setUser(userOptional.get());
        cart.setItems(listItems);

        return this.cartRepository.save(cart);

    }

    public List<Cart> getAllCarts() {
        return this.cartRepository.findAll();
    }

    public void deleteCart(Integer id) throws CartNotFoundException {
        Optional<Cart> cartOptional = this.cartRepository.findById(id);

        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException("Cart-ul nu a fost gasit.");
        }

        this.cartRepository.deleteById(id);

    }
}
