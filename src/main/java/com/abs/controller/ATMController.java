package com.abs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abs.dto.ATMCardDTO;
import com.abs.dto.AccountDTO;
import com.abs.enums.TransactionType;
import com.abs.service.ATMService;

@RestController
@RequestMapping(value = "/atm-service")
public class ATMController {

	@Autowired
	private ATMService atmService;
	
	@PostMapping("/issueCard/{accountNumber}")
	public ResponseEntity<ATMCardDTO> issueCard(@RequestBody ATMCardDTO atmCardDTO, @PathVariable int accountNumber) {
		
		return ResponseEntity.ok(atmService.issueCard(atmCardDTO, accountNumber));
	}
	
	@PostMapping("/deposit/{amount}")
	public ResponseEntity<AccountDTO> credit(@RequestBody ATMCardDTO atmCardDTO, @PathVariable double amount) {
		
		return ResponseEntity.ok(atmService.debitCredit(atmCardDTO, amount, TransactionType.CREDIT, '+'));
	}
	
	@PostMapping("/withdraw/{amount}")
	public ResponseEntity<AccountDTO> debit(@RequestBody ATMCardDTO atmCardDTO, @PathVariable double amount) {
		
		return ResponseEntity.ok(atmService.debitCredit(atmCardDTO, amount, TransactionType.DEBIT, '-'));
	}
	
	@PostMapping("/fundTransfer/{accountNumber}/{amount}")
	public ResponseEntity<AccountDTO> sendMoneyThroughATM(@RequestBody ATMCardDTO atmCardDTO, @PathVariable int accountNumber, @PathVariable double amount) {
		
		return ResponseEntity.ok(atmService.sendMoney(atmCardDTO, accountNumber, amount));
	}
	
	@PostMapping("/balanceEnquiry")
	public ResponseEntity<AccountDTO> balanceEnquiry(@RequestBody ATMCardDTO atmCardDTO) {
		
		return ResponseEntity.ok(atmService.balanceEnquiry(atmCardDTO));
	}
}
