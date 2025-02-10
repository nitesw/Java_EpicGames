package org.example.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_purchase_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_purchase_game"))
    private Game game;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate = LocalDate.now();

    @Column(nullable = false, precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) CHECK (price >= 0)")
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean obtainedForFree;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isObtainedForFree() {
        return obtainedForFree;
    }

    public void setObtainedForFree(boolean obtainedForFree) {
        this.obtainedForFree = obtainedForFree;
    }
}
