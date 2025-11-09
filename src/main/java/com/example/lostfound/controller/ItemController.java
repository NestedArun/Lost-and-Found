package com.example.lostfound.controller;

import com.example.lostfound.model.Item;
import com.example.lostfound.repository.ItemRepository;
import com.example.lostfound.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;


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

    @PutMapping("/{id}/request-claim")
    public ResponseEntity<?> requestClaim(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String email = body.get("email");
        Item item = itemRepository.findById(id).orElse(null);

        if (item == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Item not found"));
        }

        if (email.equals(item.getOwnerEmail())) {
            return ResponseEntity.status(403).body(Map.of("message", "You cannot claim your own found item."));
        }

        if (item.isClaimRequested()) {
            return ResponseEntity.status(400).body(Map.of("message", "Claim already requested."));
        }

        if (item.isClaimed()) {
            return ResponseEntity.status(400).body(Map.of("message", "Item already claimed."));
        }

        item.setClaimRequested(true);
        item.setClaimRequestedBy(email);
        itemRepository.save(item);

        return ResponseEntity.ok(Map.of("message", "Claim request sent for admin approval."));
    }


    @PutMapping("/{id}/approve-claim")
    public ResponseEntity<?> approveClaim(@PathVariable Long id, @RequestParam String adminKey) {
        if (!"secretKey123".equals(adminKey)) {
            return ResponseEntity.status(403).body(Map.of("message", "Unauthorized"));
        }

        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Item not found"));
        }

        item.setClaimRequested(false);
        item.setClaimed(true);
        item.setClaimRequestedBy(null); // Clear claim request info
        itemRepository.save(item);

        return ResponseEntity.ok(Map.of("message", "Claim approved successfully"));
    }



}
