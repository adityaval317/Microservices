package com.aditya.microservices.loans.service.impl;

import com.aditya.microservices.loans.dto.LoanDto;
import com.aditya.microservices.loans.entities.Loan;
import com.aditya.microservices.loans.exception.ResourceNotFoundException;
import com.aditya.microservices.loans.mapper.LoanMapper;
import com.aditya.microservices.loans.repositories.LoanRepository;
import com.aditya.microservices.loans.service.ILoanService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements ILoanService {

    private LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void createLoan(String mobileNumber) {
        Loan loan = addNewLoan(mobileNumber);
        loanRepository.save(loan);
    }

    @Override
    public LoanDto getLoanUsingMobileNumber(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", String.valueOf(mobileNumber)));
        LoanDto loanDto = LoanMapper.toLoanDto(loan, new LoanDto());
        return loanDto;
    }

    private Loan addNewLoan(String mobileNumber) {
        Loan loan = new Loan();
        loan.setMobileNumber(mobileNumber);

        long loanNumber = 1000000000L + new Random().nextInt(90000000);
        loan.setLoanNumber(loanNumber);
        loan.setLoanType("Home");
        loan.setAmountPaid(0);
        loan.setOutstandingAmount(1_00_000);

        return loan;
    }

    @Override
    public boolean updateLoan(LoanDto loanDto) {
        boolean updated = false;
        Loan existingLoan = loanRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(()->
                new ResourceNotFoundException("Loan", "loanNumber", String.valueOf(loanDto.getLoanNumber())));

        if (existingLoan != null) {
            loanDto.setOutstandingAmount(existingLoan.getOutstandingAmount()-loanDto.getAmountPaid());
            Loan updatedLoan = LoanMapper.toLoan(loanDto, existingLoan);
            loanRepository.save(updatedLoan);
            updated = true;
        }
        return updated;
    }


    @Override
    public boolean deleteLoanUsingLoanNumber(long loanNumber) {
        boolean deleted = false;
        Loan loan = loanRepository.findByLoanNumber(loanNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", String.valueOf(loanNumber)));
        if(loan!=null){
            loanRepository.delete(loan);
            deleted = true;
        }
        return deleted;
    }

}
