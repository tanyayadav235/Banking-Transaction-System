
import com.banking.dao.TransactionDAO;
import com.banking.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDAOTest {

    public static void main(String[] args) {

        TransactionDAO transactionDAO = new TransactionDAO();

        Transaction transaction = new Transaction(
                "TEST_TXN_001",
                1L,
                null,
                new BigDecimal("500.00"),
                "WITHDRAWAL",
                "PENDING",
                LocalDateTime.now(),
                null,
                null
        );

        transactionDAO.save(transaction);
    }
}
