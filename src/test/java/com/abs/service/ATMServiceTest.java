/**
 * 
 */
package com.abs.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abs.dto.ATMCardDTO;
import com.abs.dto.AccountDTO;
import com.abs.entity.ATMCard;
import com.abs.entity.Account;
import com.abs.entity.Transaction;
import com.abs.enums.AccountType;
import com.abs.enums.TransactionType;
import com.abs.exception.ATMCardNotFoundByNumberException;
import com.abs.exception.AccountNotFoundByAccountNumberException;
import com.abs.exception.CardDetailsNotFoundException;
import com.abs.exception.IncorrectPinNumberException;
import com.abs.exception.InsufficientBalanceException;
import com.abs.repository.ATMCardRepository;
import com.abs.repository.AccountRepository;
import com.abs.repository.TransactionRepository;

/**
 * - JUnit tests for all the methods in ATMService class
 */
@ExtendWith(MockitoExtension.class)
class ATMServiceTest {

	@InjectMocks
	private ATMService atmService;

	@Mock
	private ATMCardRepository atmCardRepository;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private TransactionRepository transactionRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(ATMServiceTest.class);

	private final char POSITIVE = '+';
	private final char NEGATIVE = '-';
	private final int ID = 1;
	private final int AMOUNT = 250;
	private final int PIN = 2200;
	private final int ACCOUNT_NUMBER = 123;
	private final int INVALID_ACCOUNT_NUMBER = 1234;
	private final int CARD_NUMBER = 25;
	private final int MONEY = 2500;
	private final String HOLDER_NAME = "Kiran";
	
	/**
	 * - Checking whether the card is issued properly.
	 * - asserting the card object to not null
	 * - asserting for correct pin number
	 */
	@Test
	void issueCard_Test_PositiveCase() {
		
		when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.of(mockObjectForAccount()));
		
		when(atmCardRepository.save(ArgumentMatchers.any(ATMCard.class))).thenReturn(mockObjectForATMCard());
		
		// Calling the issueCard() service method
		ATMCardDTO cardDTO = atmService.issueCard(mockObjectForATMCardDTO(), ACCOUNT_NUMBER);
		
		// checking the cardDTO is not null
		assertNotNull(cardDTO);
		
		// asserting the pin number
		assertEquals(PIN, cardDTO.getPin());
	}

	/**
	 * - Writing negative case for the issueCard() 
	 * - If the account is not found by the account number it should throw AccountNotFoundException
	 */
	@Test
	void issueCard_Test_NegativeCase_AccountNotFoundException() {
		
		when(accountRepository.findByAccountNumber(INVALID_ACCOUNT_NUMBER)).thenReturn(Optional.empty());
		
		try {
		atmService.issueCard(mockObjectForATMCardDTO(), INVALID_ACCOUNT_NUMBER);
		}catch(AccountNotFoundByAccountNumberException e) {
			
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
		
	}

	/**
	 * - Checking for the credit of the balance in the account
	 * - asserting the object for not null
	 * - asserting whether the balance is credited in the account or not
	 */
	@Test
	void credit_Test_PositiveCase() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));
		
		when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(mockObjectForTransaction(TransactionType.CREDIT));
		
		when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(mockObjectForAccount());
		
		// calling the debitCredit() service
		AccountDTO accountDTO = atmService.debitCredit(mockObjectForATMCardDTO(), AMOUNT, TransactionType.CREDIT, POSITIVE);
		
		// checking the object for not null
		assertNotNull(accountDTO);
		
		// checking the account balance after the transaction
		assertNotEquals(2250, accountDTO.getAccountBalance());
	}

	
	@Test
	void credit_Test_NegativeCase_ATMCardNotFoundException() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.empty());
		
		try {
			atmService.debitCredit(mockObjectForATMCardDTO(), AMOUNT, TransactionType.CREDIT, POSITIVE);
		}catch(ATMCardNotFoundByNumberException e) {
			
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	void credit_Test_NegativeCase_IncorrectPinNumberException() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));
		
		try {
			ATMCardDTO mock = mockObjectForATMCardDTO();
			mock.setPin(mock.getPin()-1);
			atmService.debitCredit(mock, AMOUNT, TransactionType.CREDIT, POSITIVE);
		}catch(IncorrectPinNumberException e) {
			
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * - Checking whether the amount is withdrawn properly 
	 * - asserting the object for not null 
	 * - asserting whether amount is debit from the account
	 */
	@Test
	void debit_Test_PositiveCase() {

		TransactionType type = TransactionType.DEBIT;

		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));

		when(transactionRepository.save(ArgumentMatchers.any(Transaction.class)))
				.thenReturn(mockObjectForTransaction(type));

		when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(mockObjectForAccount());

		// Calling the debitCredit() from atmService
		AccountDTO accountDTO = atmService.debitCredit(mockObjectForATMCardDTO(), AMOUNT, type, NEGATIVE);

		// checking the object for not null
		assertNotNull(accountDTO);

		// asserting the account balance
		assertEquals(2500, accountDTO.getAccountBalance());
	}

	@Test
	void debit_Test_NegativeCase_InsufficientBalanceException() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));
		
		TransactionType type = TransactionType.DEBIT;
		try {			
			atmService.debitCredit(mockObjectForATMCardDTO(), MONEY, type, NEGATIVE);
		}catch(InsufficientBalanceException e) {
						
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * - Verifying whether the amount is able to credit into another account properly
	 */
	@Test
	void fundTransfer_Test_PositiveCase() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));
		when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.of(mockObjectForAccount()));
		
		when(accountRepository.saveAll(anyList())).thenReturn(null);
		when(transactionRepository.saveAll(anyList())).thenReturn(null);
		
		Account account = accountRepository.findByAccountNumber(ACCOUNT_NUMBER).get();
		
		// Calling the sendMoney() in ATMService
		AccountDTO accountDTO = atmService.sendMoney(mockObjectForATMCardDTO(), ACCOUNT_NUMBER, AMOUNT);
		
		// checking we are getting the object from the method
		assertNotNull(accountDTO);
		
		// verifying we are getting the correct account number from the method
		assertEquals(account.getAccountNumber(), accountDTO.getAccountNumber());
	}

	@Test
	void fundTransfer_Test_NegativeCase_InsufficientBalanceException() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));
		when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.of(mockObjectForAccount()));
		
		try {
		atmService.sendMoney(mockObjectForATMCardDTO(), ACCOUNT_NUMBER, MONEY);
		}catch(InsufficientBalanceException e) {
			
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	void fundTransfer_Test_NegativeCase_IncorrectPinNumberException() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));
		when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.of(mockObjectForAccount()));
		
		try {
			ATMCardDTO mock = mockObjectForATMCardDTO();
			mock.setPin(mock.getPin()-1); 
			
			atmService.sendMoney(mock, ACCOUNT_NUMBER, AMOUNT);
		}catch(IncorrectPinNumberException e) {
			
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	void fundTransfer_Test_NegativeCase_AccountNotFoundByAccountNumberException() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.empty());
		when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.of(mockObjectForAccount()));
		
		try {
		atmService.sendMoney(mockObjectForATMCardDTO(), ACCOUNT_NUMBER, MONEY);
		}catch(AccountNotFoundByAccountNumberException e) {
			
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * - method to test the balanceEnquiry() 
	 */
	@Test
	void balanceEnquiry_Test_PositiveCase() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));
		
		ATMCardDTO cardDTO = mockObjectForATMCardDTO();
		
		// calling the balanceEnquiry() from ATMService
		AccountDTO accountDTO = atmService.balanceEnquiry(cardDTO);
		
		// asserting whether we are getting the object from the method
		assertNotNull(accountDTO);
		
		// asserting whether we are getting the account balance properly
		assertNotNull(accountDTO.getAccountBalance());
	}
	
	@Test
	void balanceEnquiry_Test_NegativeCase_IncorrectPinNumberException() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.of(mockObjectForATMCard()));
		
		try {
			ATMCardDTO mock = mockObjectForATMCardDTO();
			mock.setPin(mock.getPin()-1); 
			
			atmService.balanceEnquiry(mock);
		}catch(IncorrectPinNumberException e) {
			
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	void balanceEnquiry_Test_NegativeCase_CardDetailsNotFoundException() {
		
		when(atmCardRepository.findByCardNumber(CARD_NUMBER)).thenReturn(Optional.empty());
		
		try {
			atmService.balanceEnquiry(mockObjectForATMCardDTO());
		}catch(CardDetailsNotFoundException e) {
			
			LOGGER.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**
	 * - Creating the mock object and fetching them through methods
	 * -------------------------------
	 * Mock Objects that are created:
	 * - Account
	 * - ATMCardDTO
	 * - ATMCardDTO
	 * - Transaction
	 */
	Account mockObjectForAccount() {

		return Account.builder()
					  .accountId(ID)
					  .accountNumber(ACCOUNT_NUMBER)
					  .accountBalance(MONEY)
					  .accountType(AccountType.SAVINGS)
					  .openedDate(LocalDateTime.now())
					  .customer(null)
					  .build();
	}

	ATMCardDTO mockObjectForATMCardDTO() {

		return ATMCardDTO.builder()
						 .cardNumber(CARD_NUMBER)
						 .cardHolder(HOLDER_NAME)
						 .pin(PIN)
						 .build();
	}

	ATMCard mockObjectForATMCard() {

		ATMCardDTO atmCardDTO = mockObjectForATMCardDTO();

		return ATMCard.builder()
					  .cardNumber(CARD_NUMBER)
					  .cardHolder(atmCardDTO.getCardHolder())
					  .pin(atmCardDTO.getPin())
					  .year(LocalDateTime.now().getYear() + 8)
					  .account(mockObjectForAccount())
					  .month(12)
					  .build();
	}

	Transaction mockObjectForTransaction(TransactionType type) {

		return Transaction.builder()
						  .account(mockObjectForAccount())
						  .amount(AMOUNT)
						  .timeStamp(LocalDateTime.now())
						  .transactionId(ID)
						  .type(type)
						  .build();
	}

}
