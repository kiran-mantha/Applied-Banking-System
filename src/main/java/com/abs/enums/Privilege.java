package com.abs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Privilege {

	CREATE("create"),
	UPDATE("update"),
	DELETE("delete"),
	READ("read");
	
	@Getter
	private final String privilege;
}
