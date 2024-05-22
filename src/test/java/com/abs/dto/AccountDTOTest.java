package com.abs.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abs.enums.AccountType;

@ExtendWith(MockitoExtension.class)
class AccountDTOTest {

	@InjectMocks
	private AccountDTO dto;
	
	@Test
	void test_Object() {
		
		dto = new AccountDTO();
		
		// Initialising the values using builder
		dto = AccountDTO.builder()
						.accountNumber(123)
						.accountBalance(2500)
						.accountType(AccountType.SAVINGS)
						.build();
		
		// asserting the data in the account object
		assertEquals(123, dto.getAccountNumber());
		assertEquals(2500, dto.getAccountBalance());
		assertEquals(AccountType.SAVINGS, dto.getAccountType());
		
	}
	
}
