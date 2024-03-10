package com.sda.finalbackend.service;

import com.sda.finalbackend.entity.Item;
import com.sda.finalbackend.entity.User;
import com.sda.finalbackend.errors.InvalidItemFieldsException;
import com.sda.finalbackend.errors.ItemNotFoundException;
import com.sda.finalbackend.errors.UserNotFoundException;
import com.sda.finalbackend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    public ItemService(ItemRepository itemRepository)
    {
        this.itemRepository=itemRepository;
    }

    public Item createItem(Item item)throws InvalidItemFieldsException
    {
        validateItemFields(item);
        return this.itemRepository.save(item);

    }

    public Item updateItem(Item item)throws InvalidItemFieldsException, ItemNotFoundException
    {
        Optional<Item> itemDB= itemRepository.findById(item.getId());

        if(itemDB.isEmpty())
        {
            throw  new ItemNotFoundException("Item-ul nu a fost gasit.");
        }

        validateItemFields(item);

        return this.itemRepository.save(item);

    }

    public void deleteItem(Integer id) throws ItemNotFoundException
    {
        Optional<Item> itemDB = this.itemRepository.findById(id);

        if (itemDB.isEmpty()) {
            throw new ItemNotFoundException("Item not found in database.");
        }

        this.itemRepository.deleteById(id);
    }

    public List<Item> getAllItems()
    {
        return this.itemRepository.findAll();
    }

    private void validateItemFields(Item item)throws InvalidItemFieldsException
    {
        if(item.getTitle()==null||item.getTitle().isEmpty())
        {
            throw new InvalidItemFieldsException("Titlul este invalid.");
        }

        if(item.getCategory()==null)
        {
            throw new InvalidItemFieldsException("Categoria este invalid.");
        }

        if(item.getPrice()==null||item.getPrice()<=0.0d)
        {
            throw new InvalidItemFieldsException("Pretul este invalid.");
        }

        if(item.getImageUrl()==null||item.getImageUrl().isEmpty())
        {
            throw new InvalidItemFieldsException("url Image  este invalid.");
        }

        if(item.getDescription()==null||item.getDescription().isEmpty())
        {
            throw new InvalidItemFieldsException("Descrierea  lipseste.");
        }
    }


}
