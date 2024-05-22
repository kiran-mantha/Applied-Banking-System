package com.abs.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abs.enums.TransactionType;

@ExtendWith(MockitoExtension.class)
class TransactionTest {

	@InjectMocks
	private Transaction trans;
	
	@Test
	void test_Object() {
		
		trans = new Transaction();
		
		// injecting the values through setter
		trans.setTransactionId(1);
		trans.setType(TransactionType.CREDIT);
		trans.setAmount(15);
		trans.setTimeStamp(LocalDateTime.now());
		trans.setAccount(new Account());
		
		// asserting the values passed to the object
		assertEquals(1, trans.getTransactionId());
		assertEquals(TransactionType.CREDIT, trans.getType());
		assertEquals(15, trans.getAmount());
		assertEquals(LocalDateTime.now().getHour(), trans.getTimeStamp().getHour());
		assertEquals(0, trans.getAccount().getAccountNumber());
	}
	
	@Test
	void test_Builder() {
		
		// injecting the values through builder
		trans = Transaction.builder()
						   .transactionId(1)
						   .type(TransactionType.DEBIT)
						   .amount(15)
						   .timeStamp(LocalDateTime.now())
						   .account(new Account())
						   .build();
		
		// asserting the values passed to the object
		assertEquals(1, trans.getTransactionId());
		assertEquals(TransactionType.DEBIT, trans.getType());
		assertEquals(15, trans.getAmount());
		assertEquals(LocalDateTime.now().getHour(), trans.getTimeStamp().getHour());
		assertEquals(0, trans.getAccount().getAccountNumber());
		
		new Transaction.TransactionBuilder().toString();
	}
}
