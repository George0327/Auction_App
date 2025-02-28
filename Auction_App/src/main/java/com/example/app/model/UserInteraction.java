package com.example.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interaction_table")
public class UserInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuctionCategory category;

    private int buttonClicks = 0;
    private int filterUses = 0;

    public UserInteraction() {
    }

    public UserInteraction(User user, AuctionCategory category) {
        this.user = user;
        this.category = category;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuctionCategory getCategory() {
        return category;
    }

    public void setCategory(AuctionCategory category) {
        this.category = category;
    }

    public int getButtonClicks() {
        return buttonClicks;
    }

    public void setButtonClicks(int buttonClicks) {
        this.buttonClicks = buttonClicks;
    }

    public int getFilterUses() {
        return filterUses;
    }

    public void setFilterUses(int filterUses) {
        this.filterUses = filterUses;
    }
}
