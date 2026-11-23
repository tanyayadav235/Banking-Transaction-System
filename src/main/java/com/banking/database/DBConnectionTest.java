
package com.banking.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnectionTest {

    public static void main(String[] args) {

        String query = """
                SELECT c.customer_id, c.name,
                       a.account_number, a.balance
                FROM customer c
                JOIN account a
                ON c.customer_id = a.customer_id
                ORDER BY a.account_id
                """;

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Database connected successfully!");
            System.out.println("--------------------------------");

            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("name") + " | " +
                                resultSet.getString("account_number") + " | ₹" +
                                resultSet.getBigDecimal("balance")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
