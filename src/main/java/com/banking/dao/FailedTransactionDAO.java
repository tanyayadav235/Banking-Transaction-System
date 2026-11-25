
package com.banking.dao;

import com.banking.database.DBConnection;
import com.banking.model.FailedTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FailedTransactionDAO {

    public void save(FailedTransaction failure) {

        String sql = """
                INSERT INTO failed_transaction (
                    transaction_id,
                    account_id,
                    failure_reason,
                    error_category,
                    failure_timestamp
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, failure.getTransactionId());

            if (failure.getAccountId() != null) {
                statement.setLong(2, failure.getAccountId());
            } else {
                statement.setNull(2, java.sql.Types.BIGINT);
            }

            statement.setString(3, failure.getFailureReason());
            statement.setString(4, failure.getErrorCategory());

            statement.setTimestamp(
                    5, Timestamp.valueOf(failure.getFailureTimestamp()));

            statement.executeUpdate();

            System.out.println("Failed transaction record saved!");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error saving failed transaction record", e);
        }
    }
}
