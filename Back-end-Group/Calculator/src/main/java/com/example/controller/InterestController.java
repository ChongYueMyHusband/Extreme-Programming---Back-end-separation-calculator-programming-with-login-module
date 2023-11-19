package com.example.controller;

import com.example.Object.Result;
import com.example.Object.depositInterest;
import com.example.Object.loanInterest;
import com.example.Service.interestRateService;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.mybatis.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class InterestController {
    @Autowired
    private interestRateService interestRateService;

    @PostMapping("addDepositInterestRate")
    public Result<depositInterest> addDepositInterest(@RequestBody depositInterest depositInterest){
        Result<depositInterest> result;
        result=interestRateService.saveDepositInterestRate(depositInterest);
        return result;
    }

    @PostMapping("updateDepositInterestRate")
    public Result<depositInterest> updateDepositInterest(@RequestBody depositInterest depositInterest){
        System.out.println(depositInterest.getPeriod());
        Result<depositInterest> result;
        result=interestRateService.updateDepositInterestRate(depositInterest);
        return result;
    }
    @PostMapping("searchDepositInterestRate")
    public Result<depositInterest> searchDepositInterest(@RequestBody depositInterest depositInterest){
        Result<depositInterest> result;
        result=interestRateService.readDepositInterestRate(depositInterest);
        return result;
    }

    @PostMapping("addLoanInterestRate")
    public Result<loanInterest> addLoanInterestRate(@RequestBody loanInterest loanInterest){
        Result<loanInterest> result;
        result=interestRateService.saveloanInterestRate(loanInterest);
        return result;
    }

    @PostMapping("updateLoanInterestRate")
    public Result<loanInterest> updateLoanInterestRate(@RequestBody loanInterest loanInterest){
        Result<loanInterest> result;
        result=interestRateService.updateloanInterestRate(loanInterest);
        return result;
    }

    @PostMapping("searchLoanInterestRate")
    public Result<loanInterest> searchLoanInterestRate(@RequestBody loanInterest loanInterest){
        Result<loanInterest> result;
        result=interestRateService.readloanInterestRate(loanInterest);
        return result;
    }
}
