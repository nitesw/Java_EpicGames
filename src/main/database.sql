CREATE DATABASE epic_games;

\c epic_games;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE games (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    developer VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    release_date DATE NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    is_free BOOLEAN DEFAULT FALSE

CREATE TABLE purchases (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    game_id INT REFERENCES games(id) ON DELETE CASCADE,
    purchase_date TIMESTAMP DEFAULT NOW(),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    obtained_for_free BOOLEAN DEFAULT FALSE,
    UNIQUE(user_id, game_id)
);

CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    game_id INT REFERENCES games(id) ON DELETE CASCADE,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    review_text TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(user_id, game_id)
);

CREATE TABLE wishlist (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    game_id INT REFERENCES games(id) ON DELETE CASCADE,
    added_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(user_id, game_id)
);

CREATE TABLE game_library (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    game_id INT REFERENCES games(id) ON DELETE CASCADE,
    added_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(user_id, game_id)
);

CREATE TABLE promotions (
    id SERIAL PRIMARY KEY,
    game_id INT REFERENCES games(id) ON DELETE CASCADE,
    discount_percentage INT CHECK (discount_percentage BETWEEN 0 AND 100),
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    is_free_event BOOLEAN DEFAULT FALSE
);

CREATE TABLE friends (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    friend_id INT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW(),
    CHECK (user_id <> friend_id),
    UNIQUE(user_id, friend_id)
);
