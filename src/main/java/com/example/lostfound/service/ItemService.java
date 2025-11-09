package com.example.lostfound.service;

import com.example.lostfound.model.Item;
import com.example.lostfound.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    // Automatically injects the repository so we can access database methods
    @Autowired
    private ItemRepository itemRepository;

    // ---------- Create or Update an Item ----------
    public Item saveItem(Item item) {
        return itemRepository.save(item);  // JPA handles insert/update automatically
    }

    // ---------- Get All Items ----------
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // ---------- Get Item by ID ----------
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    // ---------- Delete an Item ----------
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    // ---------- Mark Item as Claimed ----------
    public void markAsClaimed(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setClaimed(true);
            itemRepository.save(item); // Save updated item back to DB
        } else {
            throw new RuntimeException("Item with ID " + id + " not found.");
        }
    }

    // ---------- Get Only Lost Items ----------
    public List<Item> getLostItems() {
        // Custom query example if you added findByStatus() in repository
        // return itemRepository.findByStatus("LOST");
        return itemRepository.findAll().stream()
                .filter(item -> "LOST".equalsIgnoreCase(item.getStatus()))
                .toList();
    }

    // ---------- Get Only Found Items ----------
    public List<Item> getFoundItems() {
        // Custom query example if you added findByStatus() in repository
        // return itemRepository.findByStatus("FOUND");
        return itemRepository.findAll().stream()
                .filter(item -> "FOUND".equalsIgnoreCase(item.getStatus()))
                .toList();
    }
}
