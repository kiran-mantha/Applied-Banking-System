package com.abs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abs.entity.ATMCard;

@Repository
public interface ATMCardRepository extends JpaRepository<ATMCard, Integer> {

	Optional<ATMCard> findByCardNumber(int cardNumber);
}
