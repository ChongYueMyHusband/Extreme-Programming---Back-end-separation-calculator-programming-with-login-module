package com.example.Service.ServiceImp;


import com.example.DAO.calculatorHistoryDAO;
import com.example.DAO.depositDAO;
import com.example.DAO.loanIntDAO;
import com.example.Object.Result;
import com.example.Object.depositInterest;
import com.example.Object.loanInterest;
import com.example.Service.interestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class interestRateServiceImp implements interestRateService {
    @Autowired
    private depositDAO depositDAO;
    @Autowired
    private loanIntDAO loanIntDAO;
    @Override
    public Result<depositInterest> saveDepositInterestRate(depositInterest depositInterest) {
        Result<depositInterest> result=new Result<>();
        depositDAO.add(depositInterest);
        result.setResultSuccess("save successfully", depositInterest);
        return result;
    }

    @Override
    public Result<depositInterest> updateDepositInterestRate(depositInterest depositInterest) {
        Result<depositInterest> result=new Result<>();
        depositDAO.update(depositInterest);
        result.setResultSuccess("update successfully",depositInterest);
        return result;
    }

    @Override
    public Result<depositInterest> readDepositInterestRate(depositInterest depositInterest) {
        Result<depositInterest> result=new Result<>();
        depositDAO.getPeriod(depositInterest.getPeriod());
        result.setResultSuccess("read successfully",depositInterest);
        return result;
    }

    public Result<loanInterest> saveloanInterestRate(loanInterest loanInterest) {
        Result<loanInterest> result=new Result<>();
        loanIntDAO.add(loanInterest);
        result.setResultSuccess("save successfully", loanInterest);
        return result;
    }

    @Override
    public Result<loanInterest> updateloanInterestRate(loanInterest loanInterest) {
        Result<loanInterest> result=new Result<>();
        loanIntDAO.update(loanInterest);
        result.setResultSuccess("update successfully", loanInterest);
        return result;
    }

    @Override
    public Result<loanInterest> readloanInterestRate(loanInterest loanInterest) {
        Result<loanInterest> result=new Result<>();
        loanIntDAO.getPeriod(loanInterest.getPeriod());
        result.setResultSuccess("read successfully", loanInterest);
        return result;
    }

}
