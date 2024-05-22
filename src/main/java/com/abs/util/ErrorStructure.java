package com.abs.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorStructure {

	private int statuscode;
	private String message;
	private Object data;
}
