package com.aditya.microservices.loans.service;

import com.aditya.microservices.loans.dto.LoanDto;

public interface ILoanService {
    public void createLoan(String mobileNumber);

    public LoanDto getLoanUsingMobileNumber(String mobileNumber);

    public boolean updateLoan(LoanDto customerDto);

    public boolean deleteLoanUsingLoanNumber(long loanNumber);
}
