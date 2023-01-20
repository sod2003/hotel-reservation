package model;

import java.util.regex.Pattern;

public final class Customer {
	private final String firstName;
	private final String lastName;
	private String email;
	
	public Customer(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		try {
			validateEmail(email);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getLocalizedMessage());
		}
	}

	public final String getEmail() {
		return email;
	}

	public final String getFirstName() {
		return firstName;
	}

	public final String getLastName() {
		return lastName;
	}
	
	public void validateEmail(String email) throws IllegalArgumentException {
		String emailRegEx = "^(.+)@(.+).(.+)";
		Pattern pattern = Pattern.compile(emailRegEx);
		if (pattern.matcher(email).matches()) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("Not a valid e-mail!");
		}
	}
	
	public String fullName() {
		return firstName + " " + lastName;
	}
	
	@Override
	public String toString() {
		return "Name: " + firstName + " " + lastName + "\nE-mail: " + email;
	}
	
	@Override
    public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
		
		Customer other = (Customer) obj;
        if (this.fullName() != other.fullName()) {
            return false;
        }
        
        if (this.email != other.email) {
        	return false;
        }
        
        return true;
	}
	
	@Override
	public int hashCode() {
		return email.hashCode();
	}
}