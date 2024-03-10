package com.sda.finalbackend.dto;

import com.sda.finalbackend.entity.Item;
import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private Integer id;
    private Integer userId;
    private List<Item> items;
}
