package com.example.lostfound.controller;

import com.example.lostfound.repository.ItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final ItemRepository itemRepository;

    public StatsController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", itemRepository.count());
        stats.put("lost", itemRepository.countByStatus("LOST"));
        stats.put("found", itemRepository.countByStatus("FOUND"));
        stats.put("claimed", itemRepository.countByClaimed(true));
        stats.put("unclaimed", itemRepository.countByClaimed(false));

        List<Object[]> locs = itemRepository.countByLocation();
        int limit = Math.min(5, locs.size());
        Map<String, Long> topLocations = new HashMap<>();
        for (int i = 0; i < limit; i++) {
            Object[] row = locs.get(i);
            String location = (String) row[0];
            Long count = (Long) row[1];
            topLocations.put(location, count);
        }
        stats.put("topLocations", topLocations);

        return stats;
    }
}
