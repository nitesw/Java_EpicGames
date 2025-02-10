package org.example.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_review_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_review_game"))
    private Game game;

    @Column(nullable = false)
    private int rating;

    @Column(length = 1000)
    private String reviewText;

    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
