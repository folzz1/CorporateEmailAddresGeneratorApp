package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        EmailGenerator emailGenerator = new EmailGenerator(firstname, lastname, departament);
        emailGenerator.generateRandomPassword(12);
        emailGenerator.generateEmail();
    }
}