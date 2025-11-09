package com.example.lostfound.controller;

import com.example.lostfound.model.Item;
import com.example.lostfound.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")  // allows frontend requests (use specific domain in production)
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Optional<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        Optional<Item> existingItemOpt = itemService.getItemById(id);
        if (existingItemOpt.isPresent()) {
            Item existingItem = existingItemOpt.get();
            existingItem.setItemName(item.getItemName());
            existingItem.setDescription(item.getDescription());
            existingItem.setLocation(item.getLocation());
            existingItem.setStatus(item.getStatus());
            existingItem.setContactInfo(item.getContactInfo());
            return itemService.saveItem(existingItem);
        } else {
            throw new RuntimeException("Item with ID " + id + " not found.");
        }
    }

    @PutMapping("/{id}/claim")
    public String markAsClaimed(@PathVariable Long id) {
        itemService.markAsClaimed(id);
        return "Item with ID " + id + " marked as claimed.";
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "Item with ID " + id + " deleted successfully.";
    }

    @GetMapping("/lost")
    public List<Item> getLostItems() {
        return itemService.getLostItems();
    }

    @GetMapping("/found")
    public List<Item> getFoundItems() {
        return itemService.getFoundItems();
    }
}
