package com.aditya.microservices.accounts.mapper;

import com.aditya.microservices.accounts.dto.AccountDto;
import com.aditya.microservices.accounts.entities.Account;

public class AccountMapper {

    public static AccountDto toAccountDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

    public static Account toAccount(AccountDto accountDto, Account account) {
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        return account;
    }
}
