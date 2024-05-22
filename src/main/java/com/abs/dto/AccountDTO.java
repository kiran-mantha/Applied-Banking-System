package com.abs.dto;

import com.abs.enums.AccountType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountDTO {

	private int accountNumber;
	private double accountBalance;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
}
