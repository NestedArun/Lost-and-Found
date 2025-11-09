package com.example.lostfound.model;

import jakarta.persistence.*;

// The @Entity annotation marks this class as a database table
@Entity
@Table(name = "items")
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Auto-incremented ID

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private String location;

   @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String contactInfo;

    private boolean claimed = false;

    private String ownerEmail;

    private boolean claimRequested;


    public Item() {
    }

    public Item(String itemName, String description, String location, String status, String contactInfo) {
        this.itemName = itemName;
        this.description = description;
        this.location = location;
        this.status = status;
        this.contactInfo = contactInfo;
        this.claimed = false;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    public boolean isClaimRequested() { return claimRequested; }
    public void setClaimRequested(boolean claimRequested) { this.claimRequested = claimRequested; }

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", claimed=" + claimed +
                '}';
    }
}
