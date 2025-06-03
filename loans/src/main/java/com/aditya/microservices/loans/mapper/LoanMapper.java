package com.aditya.microservices.loans.mapper;

import com.aditya.microservices.loans.dto.LoanDto;
import com.aditya.microservices.loans.entities.Loan;

public class LoanMapper {

    public static LoanDto toLoanDto(Loan loan, LoanDto loanDto) {
        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setOutstandingAmount(loan.getOutstandingAmount());
        loanDto.setTotalLoanAmount(loan.getAmountPaid() + loan.getOutstandingAmount());
        return loanDto;
    }

    public static Loan toLoan(LoanDto loanDto, Loan loan) {
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());
        return loan;
    }
}
