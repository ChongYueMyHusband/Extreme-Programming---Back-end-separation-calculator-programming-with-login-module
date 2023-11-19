package com.example.Service.ServiceImp;

import com.example.DAO.calculatorHistoryDAO;
import com.example.Object.CalculationHistory;
import com.example.Object.Result;
import com.example.Service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculatorServiceImp implements CalculatorService {
    @Autowired
    private calculatorHistoryDAO calculatorHistoryDAO;
    @Override
    public Result<CalculationHistory> SaveHistory(CalculationHistory calculationHistory) {
        Result<CalculationHistory> result=new Result<>();
         calculatorHistoryDAO.add(calculationHistory);
        result.setResultSuccess("save successfully", calculationHistory);
        return result;
    }

    @Override
    public List<String> readHistory(String Account) {
        return calculatorHistoryDAO.getHistoryByAccount(Account);
    }

    @Override
    public void deleteHistory(String Account) {
        calculatorHistoryDAO.deleteHistory(Account);
    }
}
