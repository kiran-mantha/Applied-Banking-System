package com.abs.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.abs.dto.CustomerDTO;
import com.abs.entity.Customer;
import com.abs.entity.User;
import com.abs.repository.CustomerRepository;
import com.abs.repository.UserRepository;
import com.abs.response.CustomerResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {

	private CustomerRepository customerRepository;
	private UserRepository userRepository;
	private JwtService jwtService;
	
	 
	public CustomerResponse addCustomer(CustomerDTO customerDto, HttpServletRequest request) {

		String email = extractEmail(request);
		
		User user = userRepository.findByEmail(email).get();
		
		Customer customer = mapToCustomer(customerDto);

		customer.setAddedBy(user.getFirstName() + " " + user.getLastName());
		customer.setUpdatedBy(user.getFirstName() + " " + user.getLastName());
		
		return mapToCustomerResponse(customerRepository.save(customer));
	}
	
	private String extractEmail(HttpServletRequest request) {
		
		final String jwt, authHeader = request.getHeader("Authorization");
		
		// Extract jwt token from the Authorization
		jwt = authHeader.substring(7);
		
		// Verify whether user is present in db and whether token is valid
		return jwtService.extractUsername(jwt);
	}

	private Customer mapToCustomer(CustomerDTO customerDto) {
		
		return Customer.builder().customerFirstName(customerDto.getCustomerFirstName())
				                 .customerLastName(customerDto.getCustomerLastName())
				                 .customerEmail(customerDto.getCustomerEmail())
				                 .customerPassword(customerDto.getCustomerPassword())
				                 .contact(customerDto.getContact())
				                 .addedOn(LocalDateTime.now())
				                 .updatedOn(LocalDateTime.now())
				                 .build();
	}
	
	private CustomerResponse mapToCustomerResponse(Customer customer) {
		
		return CustomerResponse.builder().customerId(customer.getCustomerId())
										 .customerEmail(customer.getCustomerEmail())
										 .build();
	}

	public CustomerResponse deleteCustomer(String customerEmail) {
		
		Optional<Customer> optional = customerRepository.findByCustomerEmail(customerEmail);
		
		if(optional.isPresent()) {
			Customer customer = optional.get();
			customerRepository.delete(customer);
			return mapToCustomerResponse(customer);
		}
		else
			throw new RuntimeException("Customer Not Found by email: " + customerEmail);
	}
		
}
