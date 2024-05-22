package com.abs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abs.dto.AccountDTO;
import com.abs.dto.CustomerDTO;
import com.abs.response.CustomerResponse;
import com.abs.service.AccountService;
import com.abs.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CustomerService customerService;

	@PostMapping("/createCustomer")
	public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerDTO customerDto, HttpServletRequest request) {	
		
		return ResponseEntity.ok(customerService.addCustomer(customerDto,request));
	}

	@PostMapping("/createAccount/{customerEmail}")
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO, @PathVariable String customerEmail, HttpServletRequest request) {
		
		AccountDTO result = accountService.createAccount(accountDTO, customerEmail, request);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("deleteCustomer/{customerEmail}") 
	public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable String customerEmail) {
		
		return ResponseEntity.ok(customerService.deleteCustomer(customerEmail));
	}
}
