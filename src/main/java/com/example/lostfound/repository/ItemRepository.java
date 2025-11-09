package com.example.lostfound.repository;

import com.example.lostfound.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    long countByStatus(String status);

    long countByClaimed(boolean claimed);

    @Query("SELECT i.location, COUNT(i) FROM Item i GROUP BY i.location ORDER BY COUNT(i) DESC")
    List<Object[]> countByLocation();
}
