package com.abs.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abs.enums.AccountType;

@ExtendWith(MockitoExtension.class)
class AccountTest {

	@InjectMocks
	private Account account;
	
	@Test
	void test_Object() {
		
		account = new Account();
		
		// Initialising the values through setters
		account.setAccountId(1);
		account.setAccountNumber(1234);
		account.setAccountBalance(25000);
		account.setAccountType(AccountType.SAVINGS);
		account.setOpenedDate(LocalDateTime.now());
		account.setCustomer(new Customer());
		
		// asserting the values present in the object
		assertEquals(1, account.getAccountId());
		assertEquals(1234, account.getAccountNumber());
		assertEquals(25000, account.getAccountBalance());
		assertEquals(AccountType.SAVINGS, account.getAccountType());
		assertEquals(LocalDateTime.now().getMonth(), account.getOpenedDate().getMonth());
		assertEquals(null, account.getCustomer().getCustomerFirstName());
	}
	
	@Test
	void test_Builder() {
		
		// initialising the values through builder
		account = Account.builder()
						 .accountId(1)
						 .accountNumber(1234)
						 .accountBalance(25000)
						 .accountType(AccountType.SAVINGS)
						 .openedDate(LocalDateTime.now())
						 .customer(new Customer())
						 .build();
		
		// asserting the values present in the object
		assertEquals(1, account.getAccountId());
		assertEquals(1234, account.getAccountNumber());
		assertEquals(25000, account.getAccountBalance());
		assertEquals(AccountType.SAVINGS, account.getAccountType());
		assertEquals(LocalDateTime.now().getMonth(), account.getOpenedDate().getMonth());
		assertEquals(null, account.getCustomer().getCustomerFirstName());
		
		new Account.AccountBuilder().toString();
	}
}
