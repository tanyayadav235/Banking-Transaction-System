
import com.banking.dao.FailedTransactionDAO;
import com.banking.model.FailedTransaction;

import java.time.LocalDateTime;

public class FailedTransactionDAOTest {

    public static void main(String[] args) {

        FailedTransactionDAO failedDAO =
                new FailedTransactionDAO();

        FailedTransaction failure = new FailedTransaction(
                0,
                "TEST_TXN_001",
                1L,
                "Insufficient balance",
                "BUSINESS_VALIDATION",
                LocalDateTime.now()
        );

        failedDAO.save(failure);
    }
}
