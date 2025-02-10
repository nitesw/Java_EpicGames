package org.example;

import org.example.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    // Will call by itself when this class is being used
    static {
        try {
            var config = new Configuration()
                    .configure(); // Auto reading of file hibernate.cfg.xml
            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(Genre.class);
            config.addAnnotatedClass(Game.class);
            config.addAnnotatedClass(Purchase.class);
            config.addAnnotatedClass(Review.class);
            config.addAnnotatedClass(Wishlist.class);
            sessionFactory = config.buildSessionFactory();
            System.out.println("-----Successfully connected to DB-----");
        } catch (Exception e) {
            System.out.println("Error connecting to DB!\n" + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void shutdownSession() {
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
