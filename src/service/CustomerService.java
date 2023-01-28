package service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import model.Customer;

public class CustomerService {
	private static CustomerService INSTANCE;
	private Set<Customer> customers;

	private CustomerService() {
		customers = new HashSet<Customer>();
	}
	
	public static CustomerService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CustomerService();
		}
		
		return INSTANCE;
	}
	
	public void addCustomer(String email, String firstName, String lastName) {
		Customer newCustomer = new Customer(firstName, lastName, email);
		if (customers.contains(newCustomer)) {
			System.out.println("This customer already exists");
		} else if (newCustomer.getEmail() == null) {
			System.out.println("I'm sorry. We can't add a customer account with an invalid e-mail.\n");
		} else {
			customers.add(newCustomer);
			System.out.println("Customer created");
		}
	}
	
	public Customer getCustomer(String customerEmail) {
		for (Customer customer : customers) {
			if (customer.getEmail().equals(customerEmail)) {
				return customer;
			}
		}
		
		System.out.println("This customer doesn't exist");
		return null;
	}
	
	public Collection<Customer> getAllCustomers() {
		return customers;
	}
	
}
