package com.abs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.abs.dto.ATMCardDTO;
import com.abs.dto.AccountDTO;
import com.abs.entity.ATMCard;
import com.abs.entity.Account;
import com.abs.enums.AccountType;
import com.abs.repository.ATMCardRepository;
import com.abs.utility.RestTemplateConfiguration;

/**
 * - Integration test for ATM services
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Sql(scripts = {
		"classpath:TestDB/schema.sql",
		"classpath:TestDB/InsertStatements.sql"
})
class ATMControllerTest {

	@LocalServerPort
	private int port;
	private final Logger LOGGER = LoggerFactory.getLogger(ATMControllerTest.class);
	private static RestTemplate restTemplate;
	
	private String baseURL = "http://localhost:";
	
	@Autowired
	private ATMCardRepository atmCardRepository;
	
	private static ATMCardDTO cardDTO;
	private final static int CARD_NUMBER = 96325878;
	private final static int PIN = 3344;//3344 - correct pin
	private final static String JWT_TOKEN ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGFudWFhcmFkeWFAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJyZWFkLHVwZGF0ZSxjcmVhdGUsUk9MRV9NQU5BR0VSLGRlbGV0ZSIsImlhdCI6MTcxNjI4OTg4NCwiZXhwIjoxNzE2Mzc2Mjg0fQ.CT6A2giWEtSrub9SNhCl5o7fDslFCMjHJ24yuyXQ1Wc";
	
	@BeforeAll
	public static void init() {
		RestTemplateConfiguration restTemplateConfiguration = new RestTemplateConfiguration();
		restTemplate = restTemplateConfiguration.restTemplateWithJwt(JWT_TOKEN);
		
		cardDTO = ATMCardDTO.builder()
				   .cardNumber(CARD_NUMBER)
				   .pin(PIN)
				   .build();
	}
	
	@Test
	void balanceEnquiry_Test_PositiveCase() {
		
		LOGGER.info("--------Entered into the balance Enquiry Test case");
		
		baseURL = baseURL.concat( port + "/atm-service/balanceEnquiry" );
		
		Optional<ATMCard> optional = atmCardRepository.findByCardNumber(CARD_NUMBER);
		Account account = optional.get().getAccount();
		
		/*
		 *  Calling the API
		 *  /atm-service/balanceEnquiry
		 */
		ResponseEntity<AccountDTO> entity = restTemplate.postForEntity(baseURL, cardDTO, AccountDTO.class);
		
		// checking whether we are getting the response or not
		assertNotNull(entity);
		
		// asserting the balance of the accountDTO and actual account 
		assertEquals(account.getAccountBalance(), entity.getBody().getAccountBalance());
		
		// asserting the account number of accountDTO and account object
		assertEquals(account.getAccountNumber(), entity.getBody().getAccountNumber());
	}
	
	@Test
	void creditAmount_Test() {
		
		LOGGER.info("--------Entered into credit amount Test case");
		
		baseURL = baseURL.concat( port + "/atm-service/deposit/200" );
		
		// fetching the account object from the repository using ATMCard
		Optional<ATMCard> optional = atmCardRepository.findByCardNumber(CARD_NUMBER);
		Account account = optional.get().getAccount();
		
		/*
		 *  Calling the API
		 *  /atm-service/deposit/{amount}
		 */
		ResponseEntity<AccountDTO> entity = restTemplate.postForEntity(baseURL, cardDTO, AccountDTO.class);
		
		// checking whether we are getting the response or not
		assertNotNull(entity);
		
		// asserting the actual balance before depositing the amount
		assertEquals(account.getAccountBalance(), entity.getBody().getAccountBalance()-200);
		
	}
	
	@Test
	void debitAmount_Test() {
		
		LOGGER.info("--------Entered into debit amount Test Case");
		
		baseURL = baseURL.concat( port + "/atm-service/withdraw/299" );
		
		/*
		 *  Calling the API
		 *  /atm-service/withdraw/{amount}
		 */
		ResponseEntity<AccountDTO> entity = restTemplate.postForEntity(baseURL, cardDTO, AccountDTO.class);

		//
		assertNotNull(entity);
		
		assertEquals(2500, entity.getBody().getAccountBalance()+299);
	}
	
	@Test
	void fundTransfer_Test() {
		
		LOGGER.info("--------Entered into fund transfer Test Case");
		
		baseURL = baseURL.concat( port + "/atm-service/fundTransfer/36925847/25");
		
		ResponseEntity<AccountDTO> entity = restTemplate.postForEntity(baseURL, cardDTO, AccountDTO.class);
		
		assertNotNull(entity.getBody());
		
		assertEquals(AccountType.SAVINGS, entity.getBody().getAccountType());
	}
}
