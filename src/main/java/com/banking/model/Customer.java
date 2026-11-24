
package com.banking.model;

public class Customer {

    private long customerId;
    private String name;
    private String email;
    private String phone;

    // Constructor
    public Customer(long customerId, String name,
                    String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
