package com.abs.enums;

import static com.abs.enums.Privilege.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

	MANAGER(
			Set.of(READ, CREATE, UPDATE, DELETE)
			)
	,
	HELP_DESK(
			Set.of(CREATE, READ)
			),
	LOAN_OFFICER(
			Set.of()
			),
	TELLER(
			Set.of()
			);
	
	@Getter
	private final Set<Privilege> privileges;
	
	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPrivileges().stream()
									     .map(authority -> new SimpleGrantedAuthority(authority.getPrivilege()))
									     .collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
}
