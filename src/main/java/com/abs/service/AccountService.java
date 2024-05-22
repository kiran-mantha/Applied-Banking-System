package com.abs.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.abs.dto.AccountDTO;
import com.abs.entity.Account;
import com.abs.entity.Customer;
import com.abs.repository.AccountRepository;
import com.abs.repository.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
	
	private CustomerRepository customerRepository;
	private AccountRepository accountRepository;

	public AccountDTO createAccount(AccountDTO accountDTO, String customerEmail,
			HttpServletRequest request) {

		Account account = mapToAccount(accountDTO);
		Customer customer = customerRepository.findByCustomerEmail(customerEmail).get();
		
		account.setCustomer(customer);
		
		customerRepository.save(customer);
		accountRepository.save(account);
		
		return mapToAccountDTO(accountRepository.save(account));
		
	}
	
	private Account mapToAccount(AccountDTO accountDTO) {
		
		return Account.builder().accountNumber(new Random().nextInt(10000, 99999999))
								.accountBalance(accountDTO.getAccountBalance())
								.accountType(accountDTO.getAccountType())
								.openedDate(LocalDateTime.now())
								.build();
	}
	
	private AccountDTO mapToAccountDTO(Account account) {
		
		return AccountDTO.builder().accountNumber(account.getAccountNumber())
								   .accountBalance(account.getAccountBalance())
								   .accountType(account.getAccountType())
								   .build();
	}

}
