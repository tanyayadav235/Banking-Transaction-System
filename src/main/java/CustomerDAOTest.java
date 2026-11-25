
import com.banking.dao.CustomerDAO;
import com.banking.model.Customer;

import java.util.Optional;

public class CustomerDAOTest {

    public static void main(String[] args) {

        CustomerDAO customerDAO = new CustomerDAO();

        Optional<Customer> result = customerDAO.findById(1);

        if (result.isPresent()) {

            Customer customer = result.get();

            System.out.println("Customer Found!");
            System.out.println("ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone: " + customer.getPhone());

        } else {
            System.out.println("Customer not found!");
        }
    }
}
