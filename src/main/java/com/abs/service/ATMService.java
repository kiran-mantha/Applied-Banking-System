package com.abs.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.dto.ATMCardDTO;
import com.abs.dto.AccountDTO;
import com.abs.entity.ATMCard;
import com.abs.entity.Account;
import com.abs.entity.Transaction;
import com.abs.enums.TransactionType;
import com.abs.exception.ATMCardNotFoundByNumberException;
import com.abs.exception.AccountNotFoundByAccountNumberException;
import com.abs.exception.CardDetailsNotFoundException;
import com.abs.exception.IncorrectPinNumberException;
import com.abs.exception.InsufficientBalanceException;
import com.abs.repository.ATMCardRepository;
import com.abs.repository.AccountRepository;
import com.abs.repository.TransactionRepository;

@Service
public class ATMService {
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ATMCardRepository atmCardRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	
	private final Logger LOGGER = LoggerFactory.getLogger(ATMService.class);

	public ATMCardDTO issueCard(ATMCardDTO atmCardDTO, int accountNumber) {
		
		LOGGER.info("Inside ATM service");
		Optional<Account> optional = accountRepository.findByAccountNumber(accountNumber);
		
		if(optional.isPresent()) {
			
			Account account = optional.get();
			ATMCard card = mapToATMCard(atmCardDTO);
			card.setAccount(account);
			
			return mapToATMCardDTO(atmCardRepository.save(card));
		}
		else {
			LOGGER.error("Account not Found!!");
			throw new AccountNotFoundByAccountNumberException("The Account is not found by this number: " + accountNumber);
		}
		
	}
	
	public AccountDTO debitCredit(ATMCardDTO atmCardDTO, double amount, TransactionType type, char operator) {
		
		LOGGER.info("Depositing/Crediting the amount through atm. Number: {}", atmCardDTO.getCardNumber());
		int cardNumber = atmCardDTO.getCardNumber();
		int pin = atmCardDTO.getPin();
		
		Optional<ATMCard> optional = atmCardRepository.findByCardNumber(cardNumber);
		
		if(optional.isPresent()) 
		{
			ATMCard card = optional.get();
			
			if(pin == card.getPin()) 
			{
				Account account = card.getAccount();
				
				if((account.getAccountBalance()<0 && operator == '-') || (account.getAccountBalance()-amount<=0 && operator == '-')) 
				{
					LOGGER.error("Insufficient balance in the account");
					throw new InsufficientBalanceException("You do not have sufficient balance");
				}
				
				account.setAccountBalance(operate(account.getAccountBalance(), amount, operator));
				Transaction transaction = getTransaction(amount, account, type);
				
				transactionRepository.save(transaction);
				
				return mapToAccountDTO(accountRepository.save(account));
			}
			else 
			{
				LOGGER.error("Wrong pin Entered");
				throw new IncorrectPinNumberException("The pin for this card is incorrect");
			}
			
		}
		else 
		{
			LOGGER.error("ATM Card is not found");
			throw new ATMCardNotFoundByNumberException("Atm card is not found");
		}
		
	}
	
	public AccountDTO sendMoney(ATMCardDTO atmCardDTO, int accountNumber, double amount) {
		
		LOGGER.info("-------Entered into fund transfer service-------");
		int cardNumber = atmCardDTO.getCardNumber();
		int pin = atmCardDTO.getPin();
		
		// get atmcard using card number
		Optional<ATMCard> opt1 = atmCardRepository.findByCardNumber(cardNumber);
		// find the account using account number
		Optional<Account> opt2 = accountRepository.findByAccountNumber(accountNumber);
		
		if(opt1.isPresent() && opt2.isPresent()) 
		{
			ATMCard card = opt1.get();
			// check for correct pin
			if(card.getPin() == pin) 
			{
				Account account1 = card.getAccount();
				Account account2 = opt2.get();
				// check if you have sufficient balance
				if(account1.getAccountBalance()-amount>0)
				{
					// subtract the amount in acc1 and add the amount in acc2
					account1.setAccountBalance(account1.getAccountBalance()-amount);
					account2.setAccountBalance(account2.getAccountBalance()+amount);
					
					// get transactions for both accounts
					Transaction transaction1 = getTransaction(amount, account1, TransactionType.DEBIT);
					Transaction transaction2 = getTransaction(amount, account2, TransactionType.CREDIT);
					
					// save them
					accountRepository.saveAll(Arrays.asList(account1, account2));
					transactionRepository.saveAll(Arrays.asList(transaction1, transaction2));
					
					// return atm user's account entity
					return mapToAccountDTO(account1);
				}
				else
				{
					LOGGER.error("Insufficient Balance found in the account: {}", account1.getAccountNumber());
					throw new InsufficientBalanceException("You do not have enough balance to perform this transaction");
				}
			}
			else 
			{
				LOGGER.error("Incorrect Pin number");
				throw new IncorrectPinNumberException("The PIN you entered is incorrect");
			}
		}
		else
		{
			LOGGER.error("Account is not found by this number: {}", accountNumber);
			throw new AccountNotFoundByAccountNumberException("Account is not found By this number: "+accountNumber);
		}
	}
	
	public AccountDTO balanceEnquiry(ATMCardDTO atmCardDTO) {
		
		//logger
		LOGGER.info("-------Entered into balance enquiry service");
		
		int cardNumber = atmCardDTO.getCardNumber();
		int pin = atmCardDTO.getPin();
		// find the atm card
		Optional<ATMCard> optional = atmCardRepository.findByCardNumber(cardNumber);
		
		if(optional.isPresent())
		{
			ATMCard card = optional.get();
			// check for the pin
			if(card.getPin() == pin)
			{
				// find the account
				Account account = card.getAccount();
				
				// return account details
				return mapToAccountDTO(account);
			}
			else
			{
				LOGGER.error("Incorrect Pin number");
				throw new IncorrectPinNumberException("The PIN you entered is incorrect");
			}
		}
		else
		{
			LOGGER.error("Incorrect Card Details");
			throw new CardDetailsNotFoundException("Incorrect Card Details");
		}
	}
	
	private ATMCard mapToATMCard(ATMCardDTO atmCardDTO) {
		
		return ATMCard.builder().cardNumber(new Random().nextInt(10000000, 99999999))
				.cardHolder(atmCardDTO.getCardHolder())
				.pin(atmCardDTO.getPin())
				.year(LocalDateTime.now().getYear()+8)
				.month(12)
				.build();
	}
	
	private ATMCardDTO mapToATMCardDTO(ATMCard atmCard) {
		
		return ATMCardDTO.builder().cardNumber(atmCard.getCardNumber())
								   .cardHolder(atmCard.getCardHolder())
								   .pin(atmCard.getPin())
								   .year(atmCard.getYear())
								   .month(atmCard.getMonth())
								   .account(mapToAccountDTO(atmCard.getAccount()))
								   .build();
	}

	private AccountDTO mapToAccountDTO(Account account) {
		
		return AccountDTO.builder().accountNumber(account.getAccountNumber())
								   .accountBalance(account.getAccountBalance())
								   .accountType(account.getAccountType())
								   .build();
	}
	
	private Transaction getTransaction(double amount, Account account, TransactionType type) {
		
		return Transaction.builder().type(type)
									.amount(amount)
									.timeStamp(LocalDateTime.now())
									.account(account)
									.build();
	}
	
	private double operate(double x, double y, char ch) {
		
		if(ch=='+') return x + y;
		else		return x - y;
	}

}
