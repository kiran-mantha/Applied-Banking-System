package com.abs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {

	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private String customerPassword;
	private long contact;
}
