package com.sda.finalbackend.controller;

import com.sda.finalbackend.entity.Item;
import com.sda.finalbackend.errors.InvalidItemFieldsException;
import com.sda.finalbackend.errors.ItemNotFoundException;
import com.sda.finalbackend.service.ItemService;
import com.sda.finalbackend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class ItemController {

    @Autowired
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/item")
    public ResponseEntity<ApiResponse> createItem(@RequestBody Item item) {
        try {

            Item itemDB = itemService.createItem(item);

            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Item-ul a fost adaugat cu succes.")
                    .data(itemDB)
                    .build();

            return ResponseEntity.ok(response);

        } catch (InvalidItemFieldsException e) {

            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }
    }

    @PatchMapping("/item")
    public ResponseEntity<ApiResponse> updateItem(@RequestBody Item item) {
        try {

            Item itemDB = itemService.updateItem(item);

            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Item-ul a fost modificat cu succes.")
                    .data(itemDB)
                    .build();

            return ResponseEntity.ok(response);

        } catch (InvalidItemFieldsException | ItemNotFoundException e) {

            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable("id") Integer id) {
        try {

            itemService.deleteItem(id);

            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Item-ul a fost sters cu succes.")
                    .data(null)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ItemNotFoundException e) {

            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }
    }

    @GetMapping("/items")
    public ResponseEntity<ApiResponse> getAllItems()
    {
        List<Item> items=itemService.getAllItems();

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Lista items")
                .data(items)
                .build();

        return ResponseEntity.ok(response);
    }


}
