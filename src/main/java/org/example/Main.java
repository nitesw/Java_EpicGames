package org.example;

import org.example.entities.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var session = HibernateUtil.getSession();

        try {
            session.beginTransaction();

//            var user = new User();
//            user.setUsername("test_user");
//            user.setEmail("test@example.com");
//            user.setPassword_hash("password123");
//            user.setCreated_at(LocalDate.now());
//            session.persist(user);

//            Genre genre = session.createQuery("FROM Genre WHERE name = :name", Genre.class)
//                    .setParameter("name", "Action")
//                    .uniqueResult();
//            if (genre == null) {
//                genre = new Genre();
//                genre.setName("Action");
//                session.persist(genre);
//            }
//            var game = new Game();
//            game.setTitle("test game");
//            game.setPrice(BigDecimal.valueOf(10));
//            game.setDeveloper("test dev");
//            game.setPublisher("test publisher");
//            game.setRelease_date(LocalDate.now());
//            game.setFree(false);
//            game.setGenre(genre);
//            session.persist(game);

//            User user = session.createQuery("FROM User WHERE username = :username", User.class)
//                    .setParameter("username", "admin")
//                    .uniqueResult();
//            Game game = session.createQuery("FROM Game WHERE title = :title", Game.class)
//                    .setParameter("title", "test game")
//                    .uniqueResult();
//
//            Purchase purchase = new Purchase();
//            purchase.setUser(user);
//            purchase.setGame(game);
//            purchase.setPrice(BigDecimal.valueOf(10));
//            purchase.setObtainedForFree(false);
//            purchase.setPurchaseDate(LocalDate.now());
//            session.persist(purchase);

//            User user = session.createQuery("FROM User WHERE username = :username", User.class)
//                    .setParameter("username", "admin")
//                    .uniqueResult();
//            Game game = session.createQuery("FROM Game WHERE title = :title", Game.class)
//                    .setParameter("title", "test game")
//                    .uniqueResult();
//            Review review = new Review();
//            review.setUser(user);
//            review.setGame(game);
//            review.setRating(4);
//            review.setReviewText("This is a great game! I loved the gameplay and graphics.");
//            review.setReviewDate(LocalDate.now());
//            session.persist(review);

//            User user = session.createQuery("FROM User WHERE username = :username", User.class)
//                    .setParameter("username", "admin")
//                    .uniqueResult();
//
//            Game game = session.createQuery("FROM Game WHERE title = :title", Game.class)
//                    .setParameter("title", "test game")
//                    .uniqueResult();
//
//            Wishlist wishlist = new Wishlist();
//            wishlist.setUser(user);
//            wishlist.setGame(game);
//            wishlist.setAddedAt(LocalDateTime.now());
//            session.persist(wishlist);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
        }
    }
}