package com.abs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ATMCardDTO {

	private int cardNumber;
	private String cardHolder;
	private int pin;
	private int year;
	private int month;
	private AccountDTO account;
}
