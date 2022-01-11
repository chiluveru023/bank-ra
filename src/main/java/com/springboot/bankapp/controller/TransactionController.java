package com.springboot.bankapp.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankapp.dto.Transfer;
import com.springboot.bankapp.model.Transaction;
import com.springboot.bankapp.service.TransactionService;

@RestController
public class TransactionController {
@Autowired
private TransactionService transactionService;
/*
 * beneficiary account  to acct no 
 * username:extract (from account no)
 * amount{
 * toAccountNumber :""
 * username:""
 * amount:""
 * }:request body
 * transfer ?toAaccountNumber=___&username=__&amount=__:request param
 * transfer/to AaccountNumber=___&username=__&amount=__:pathvariable
 * */

@PostMapping("/transfer")
public Transaction doTransfer( Principal principal,@RequestBody Transfer transfer) {
String username = principal.getName();
/*
 Step:1 
 Fetch details of fromAccount
 1.1 fetch fromAccountNumber from username
 
 Step:2 
 Debit the amount from fromAccountNumber /update the balance
 credit the amount to toAccountNumber /update the balance
 Step:3
 3.1 insert the entry of tranfer in transaction table*/
//1.1
String fromAccountNumber = transactionService.fetchFromAccountNumber(username);
//2.1
transactionService.updateBalance(fromAccountNumber, transfer.getAmount());
transactionService.creditAmount(transfer.getToAccountNumber(), transfer.getAmount());
//3.1
Transaction transaction = new Transaction();
transaction.setAccountFrom(fromAccountNumber);
transaction.setAccountTo(transfer.getToAccountNumber());
transaction.setAmount(transfer.getAmount());
transaction.setOperationType("Transfer");
transaction.setDateOfTransaction(new Date());
return transactionService.saveTransaction(transaction);
}
}