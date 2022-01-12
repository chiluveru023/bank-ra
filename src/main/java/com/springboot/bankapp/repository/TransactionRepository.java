package com.springboot.bankapp.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankapp.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("select t from Transaction t where t.accountFrom = ?1 OR t.accountTo=?1 order by t.dateOfTransaction DESC")
	List<Transaction> fetchTransactionsByAccountNumber(String accountNumber);


	
}
