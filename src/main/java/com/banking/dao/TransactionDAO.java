
package com.banking.dao;

import com.banking.database.DBConnection;
import com.banking.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionDAO {

    public void save(Transaction transaction) {

        String sql = """
                INSERT INTO transactions (
                    transaction_id,
                    source_account_id,
                    destination_account_id,
                    amount,
                    transaction_type,
                    status,
                    created_at,
                    processed_at,
                    failure_reason
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getTransactionId());

            if (transaction.getSourceAccountId() != null) {
                statement.setLong(2, transaction.getSourceAccountId());
            } else {
                statement.setNull(2, java.sql.Types.BIGINT);
            }

            if (transaction.getDestinationAccountId() != null) {
                statement.setLong(3, transaction.getDestinationAccountId());
            } else {
                statement.setNull(3, java.sql.Types.BIGINT);
            }

            statement.setBigDecimal(4, transaction.getAmount());
            statement.setString(5, transaction.getTransactionType());
            statement.setString(6, transaction.getStatus());

            statement.setTimestamp(
                    7, Timestamp.valueOf(transaction.getCreatedAt()));

            if (transaction.getProcessedAt() != null) {
                statement.setTimestamp(
                        8, Timestamp.valueOf(transaction.getProcessedAt()));
            } else {
                statement.setNull(8, java.sql.Types.TIMESTAMP);
            }

            statement.setString(9, transaction.getFailureReason());

            statement.executeUpdate();

            System.out.println("Transaction saved successfully!");

        } catch (SQLException e) {
            throw new RuntimeException("Error saving transaction", e);
        }
    }
}
