package com.abs.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ATMCardTest {

	@InjectMocks
	private ATMCard card;
	
	@Test
	void test_Object() {
		
		card = new ATMCard();
		
		// initialising the values through setters
		card.setCardId(1);
		card.setCardNumber(1234);
		card.setCardHolder("Kiran");
		card.setPin(1144);
		card.setYear(2024);
		card.setMonth(5);
		card.setAccount(new Account());
		
		// asserting the values present in the object
		assertEquals(1, card.getCardId());
		assertEquals(1234, card.getCardNumber());
		assertEquals("Kiran", card.getCardHolder());
		assertEquals(1144, card.getPin());
		assertEquals(2024, card.getYear());
		assertEquals(5, card.getMonth());
		assertEquals(0, card.getAccount().getAccountNumber());
	}
	
	@Test
	void test_Builder() {
		
		// injecting the values using builder
		card = ATMCard.builder()
					  .cardId(1)
					  .cardNumber(1234)
					  .cardHolder("Kiran")
					  .pin(1144)
					  .year(2024)
					  .month(5)
					  .account(new Account())
					  .build();
		
		// asserting the values present in the object
		assertEquals(1, card.getCardId());
		assertEquals(1234, card.getCardNumber());
		assertEquals("Kiran", card.getCardHolder());
		assertEquals(1144, card.getPin());
		assertEquals(2024, card.getYear());
		assertEquals(5, card.getMonth());
		assertEquals(0, card.getAccount().getAccountNumber());
		
		new ATMCard.ATMCardBuilder().toString();
	}
}
