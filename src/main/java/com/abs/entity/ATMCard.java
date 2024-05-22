package com.abs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "atm_card")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ATMCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cardId;
	// 8 digit card number
	private int cardNumber; 
	private String cardHolder;
	// ATM pin
	private int pin;
	// Current year + 8
	@Column(name = "_year")
	private int year;
	@Column(name = "_month")
	private int month;
	
	@OneToOne
	private Account account;
}
