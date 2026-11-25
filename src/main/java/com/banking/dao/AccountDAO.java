
package com.banking.dao;

import com.banking.database.DBConnection;
import com.banking.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class AccountDAO {

    public Optional<Account> findById(long accountId) {

        String sql = """
                SELECT account_id, customer_id, account_number,
                       balance, status, created_at
                FROM account
                WHERE account_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Timestamp timestamp =
                            resultSet.getTimestamp("created_at");

                    Account account = new Account(
                            resultSet.getLong("account_id"),
                            resultSet.getLong("customer_id"),
                            resultSet.getString("account_number"),
                            resultSet.getBigDecimal("balance"),
                            resultSet.getString("status"),
                            timestamp.toLocalDateTime()
                    );

                    return Optional.of(account);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error retrieving account with ID: " + accountId, e
            );
        }

        return Optional.empty();
    }
}
