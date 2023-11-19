package com.example.Service;

import com.example.Object.Result;
import com.example.Object.depositInterest;
import com.example.Object.loanInterest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface interestRateService {
    public Result<depositInterest> saveDepositInterestRate(depositInterest depositInterest);

    public Result<depositInterest> updateDepositInterestRate(depositInterest depositInterest);

    public Result<depositInterest> readDepositInterestRate(depositInterest depositInterest);

    public Result<loanInterest> saveloanInterestRate(loanInterest loanInterest);

    public Result<loanInterest> updateloanInterestRate(loanInterest loanInterest);

    public Result<loanInterest> readloanInterestRate(loanInterest loanInterest);

    public List<loanInterest> loanHistory();

    public List<depositInterest> depositHistory();
}
