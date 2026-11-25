
package com.banking.dao;

import com.banking.database.DBConnection;
import com.banking.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerDAO {

    public Optional<Customer> findById(long customerId) {

        String sql = """
                SELECT customer_id, name, email, phone
                FROM customer
                WHERE customer_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Customer customer = new Customer(
                            resultSet.getLong("customer_id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone")
                    );

                    return Optional.of(customer);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error retrieving customer with ID: " + customerId, e
            );
        }

        return Optional.empty();
    }
}
