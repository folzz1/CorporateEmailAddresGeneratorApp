package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Employees";
        String user = "postgres";
        String password = "postgres";

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the Employees database!");
            Scanner scanner = new Scanner(System.in);
            boolean quit = false;
            while (!quit) {
                System.out.println("""
                                Меню
                        1. Добавить нового сотрудника
                        0. Выход
                        """);
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0 -> quit = true;
                    case 1 -> addEmployee(conn);
                    default -> System.out.println("Invalid choice!");
                }
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
    }

    public static void addEmployee(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите данные о сотруднике в формате: имя фамилия департамент");
        String firstname = scanner.next();
        String lastname = scanner.next();
        String departament = scanner.next();

        String countQuery = "SELECT COUNT(*) FROM Employees WHERE last_name = ?";
        PreparedStatement countStmt = conn.prepareStatement(countQuery);
        countStmt.setString(1, lastname);
        ResultSet rs = countStmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }

        EmailGenerator emailGenerator = new EmailGenerator(firstname, lastname, departament);
        emailGenerator.generateRandomPassword(12);

        if (count > 0) {
            emailGenerator.generateEmail(count + 1);
        } else {
            emailGenerator.generateEmail();
        }

        String insertQuery = "INSERT INTO Employees (first_name, last_name, email, department, password) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        insertStmt.setString(1, firstname);
        insertStmt.setString(2, lastname);
        insertStmt.setString(3, emailGenerator.getEmail());
        insertStmt.setString(4, departament);
        insertStmt.setString(5, emailGenerator.getPassword());

        int rowsInserted = insertStmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new employee was inserted successfully!");
        }
    }
}