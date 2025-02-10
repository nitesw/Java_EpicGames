package org.example.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String developer;

    @Column(length = 100, nullable = false)
    private String publisher;

    @Column(name="release_date")
    private LocalDate release_date;

    @Column(nullable = false, precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) CHECK (price >= 0)")
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE", name = "is_free")
    private boolean isFree;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_game_genre", value = ConstraintMode.CONSTRAINT))
    private Genre genre;

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}