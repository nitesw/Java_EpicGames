package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:postgresql://ep-plain-heart-a2rnsz4v-pooler.eu-central-1.aws.neon.tech:5432/neondb";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_rDz5s2WvKcXA";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        var con = connect();
//        String sql = """
//                CREATE TABLE IF NOT EXISTS games (
//                    id SERIAL PRIMARY KEY,
//                    title VARCHAR(100) NOT NULL,
//                    developer VARCHAR(100) NOT NULL,
//                    publisher VARCHAR(100) NOT NULL,
//                    release_date DATE NOT NULL,
//                    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
//                    is_free BOOLEAN DEFAULT FALSE
//                );
//                """;
//
//        try(var command = con.createStatement()) {
//            int rows = command.executeUpdate(sql);
//            System.out.println("Table has been created. Rows affected: " + rows);
//        } catch (SQLException ex) {
//            System.out.println("Something went wrong. " + ex.getMessage());
//        }

        System.out.println("\t----------Games CRUD----------");
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {

            System.out.println("\t1. Display all Games");
            System.out.println("\t2. Display Game by ID");
            System.out.println("\t3. Create Game");
            System.out.println("\t4. Edit Game");
            System.out.println("\t5. Delete Game");
            System.out.println("\t0. Exit");
            System.out.print("\tEnter your choice: ");
            choice = scanner.nextInt();
            switch (choice){
                case 1: {
                    String sql = """
                            SELECT * FROM games
                            """;
                    try(var command = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY)) {
                        ResultSet rows = command.executeQuery(sql);

                        rows.last();
                        int rowCount = rows.getRow();

                        if(rowCount == 0) {
                            System.out.println("\n\n\n\tNo Games to display.\n\n\n");
                            break;
                        }

                        rows.beforeFirst();

                        System.out.println("\n\n\n\n\n\tID | Title | Developer | Publisher | Release Date | Price");
                        System.out.println("\t---------------------------------------------------------");

                        while (rows.next()){
                            int id = rows.getInt("id");
                            String title = rows.getString("title");
                            String developer = rows.getString("developer");
                            String publisher = rows.getString("publisher");
                            String releaseDate = rows.getString("release_date");
                            double price = rows.getDouble("price");

                            System.out.printf("\t%d | %s | %s | %s | %s | %.2f%n",
                                    id, title, developer, publisher, releaseDate, price);
                        }
                    } catch (SQLException ex) {
                        System.out.println("Something went wrong. " + ex.getMessage());
                    }

                    System.out.println("\n\n\n\n\n");
                    break;
                }
                case 2: {
                    System.out.print("\n\n\n\tEnter Game ID to display: ");
                    int gameId = scanner.nextInt();

                    String sql = "SELECT * FROM games WHERE id = ?";
                    try (PreparedStatement statement = con.prepareStatement(sql)) {
                        statement.setInt(1, gameId);
                        ResultSet rows = statement.executeQuery();

                        if(rows.next()){
                            System.out.println("\n\tGame Details");
                            System.out.println("\tID: " + rows.getInt("id"));
                            System.out.println("\tTitle: " + rows.getString("title"));
                            System.out.println("\tDeveloper: " + rows.getString("developer"));
                            System.out.println("\tPublisher: " + rows.getString("publisher"));
                            System.out.println("\tRelease Date: " + rows.getString("release_date"));
                            System.out.println("\tPrice: " + rows.getString("price") + "$");
                        } else {
                            System.out.println("\n\tCan't find game with id of " + gameId + ".\n\n\n");
                            break;
                        }
                    } catch (SQLException ex) {
                        System.out.println("Something went wrong. " + ex.getMessage());
                    }

                    System.out.println("\n\n\n\n\n");
                    break;
                }
                case 3: {
                    System.out.println("\n\n\n\tCreate Game");

                    System.out.print("\tEnter Game Title: ");
                    scanner.nextLine();
                    String title = scanner.nextLine();

                    System.out.print("\tEnter Developer: ");
                    String developer = scanner.nextLine();

                    System.out.print("\tEnter Publisher: ");
                    String publisher = scanner.nextLine();

                    System.out.print("\tEnter Release Date (YYYY-MM-DD): ");
                    String releaseDate = scanner.nextLine();

                    System.out.print("\tEnter Price: ");
                    double price = scanner.nextDouble();

                    String sql = "INSERT INTO games (title, developer, publisher, release_date, price) VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement statement = con.prepareStatement(sql)) {
                        statement.setString(1, title);
                        statement.setString(2, developer);
                        statement.setString(3, publisher);
                        statement.setDate(4, Date.valueOf(releaseDate));
                        statement.setDouble(5, price);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("\n\tGame successfully created!\n");
                        }
                    } catch (SQLException ex) {
                        System.out.println("Something went wrong. " + ex.getMessage());
                    }

                    System.out.println("\n\n\n\n\n");
                    break;
                }
                case 4: {
                    System.out.print("\n\n\n\tEnter Game ID to edit: ");
                    int gameId = scanner.nextInt();
                    scanner.nextLine();

                    String checkSql = "SELECT * FROM games WHERE id = ?";
                    try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                        checkStmt.setInt(1, gameId);
                        ResultSet result = checkStmt.executeQuery();

                        if (!result.next()) {
                            System.out.println("\n\n\n\tCan't find game with ID " + gameId + ".\n\n\n");
                            break;
                        }

                        System.out.println("\n\tGame found: " + result.getString("title"));
                        System.out.println("\tWhat do you want to edit?");
                        System.out.println("\t1. Title");
                        System.out.println("\t2. Developer");
                        System.out.println("\t3. Publisher");
                        System.out.println("\t4. Release Date");
                        System.out.println("\t5. Price");
                        System.out.println("\t0. Cancel");
                        System.out.print("\tEnter your choice: ");

                        int editChoice = scanner.nextInt();
                        scanner.nextLine();

                        String updateSql = null;
                        String newValue = null;
                        double newPrice = 0;

                        switch (editChoice) {
                            case 1: {
                                String currentTitle = result.getString("title");
                                System.out.println("\n\n\n\tCurrent Title: " + currentTitle);
                                System.out.print("\tEnter new Title: ");
                                newValue = scanner.nextLine();
                                updateSql = "UPDATE games SET title = ? WHERE id = ?";
                                break;
                            }
                            case 2: {
                                String currentDeveloper = result.getString("developer");
                                System.out.println("\n\n\n\tCurrent Developer: " + currentDeveloper);
                                System.out.print("\tEnter new Developer: ");
                                newValue = scanner.nextLine();
                                updateSql = "UPDATE games SET developer = ? WHERE id = ?";
                                break;
                            }
                            case 3: {
                                String currentPublisher = result.getString("publisher");
                                System.out.println("\n\n\n\tCurrent Publisher: " + currentPublisher);
                                System.out.print("\tEnter new Publisher: ");
                                newValue = scanner.nextLine();
                                updateSql = "UPDATE games SET publisher = ? WHERE id = ?";
                                break;
                            }
                            case 4: {
                                String currentReleaseDate = result.getString("release_date");
                                System.out.println("\n\n\n\tCurrent Release Date: " + currentReleaseDate);
                                System.out.print("\tEnter new Release Date (YYYY-MM-DD): ");
                                newValue = scanner.nextLine();
                                updateSql = "UPDATE games SET release_date = ? WHERE id = ?";
                                break;
                            }
                            case 5: {
                                double currentPrice = result.getDouble("price");
                                System.out.println("\n\n\n\tCurrent Price: " + currentPrice + "$");
                                System.out.print("\tEnter new Price: ");
                                newPrice = scanner.nextDouble();
                                scanner.nextLine();
                                updateSql = "UPDATE games SET price = ? WHERE id = ?";
                                break;
                            }
                            case 0:
                                System.out.println("\n\n\n\tEdit cancelled.\n\n\n");
                                break;
                            default:
                                System.out.println("\n\n\n\tInvalid choice.\n\n\n");
                                continue;
                        }

                        if (updateSql != null) {
                            try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
                                if (editChoice == 5) {
                                    updateStmt.setDouble(1, newPrice);
                                } else {
                                    updateStmt.setString(1, newValue);
                                }
                                updateStmt.setInt(2, gameId);

                                int rowsUpdated = updateStmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("\n\tGame updated successfully!\n");
                                }
                            } catch (SQLException ex) {
                                System.out.println("Something went wrong. " + ex.getMessage());
                            }
                        }
                    } catch (SQLException ex) {
                        System.out.println("Something went wrong. " + ex.getMessage());
                    }

                    System.out.println("\n\n\n\n\n");
                    break;
                }
                case 5: {
                    System.out.print("\n\n\n\tEnter Game ID to delete: ");
                    int gameId = scanner.nextInt();

                    String sql = "DELETE FROM games WHERE id = ?";
                    try (PreparedStatement statement = con.prepareStatement(sql)) {
                        statement.setInt(1, gameId);

                        int rowsDeleted = statement.executeUpdate();

                        if (rowsDeleted > 0) {
                            System.out.println("\n\tGame deleted successfully!\n");
                        } else {
                            System.out.println("\n\tCan't find game with ID " + gameId + ".\n\n\n");
                        }
                    } catch (SQLException ex) {
                        System.out.println("Something went wrong. " + ex.getMessage());
                    }

                    System.out.println("\n\n\n\n\n");
                    break;
                }
                case 0:
                    System.out.println("\n\n\n\tBye Bye!\n\n\n");
                    break;
                default:
                    System.out.println("\n\n\n\tInvalid choice.\n\n\n");
                    continue;
            }
        }while(choice != 0);
    }
}