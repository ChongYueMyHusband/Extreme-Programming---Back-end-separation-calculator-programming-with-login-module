package com.example.Service;

import com.example.Object.CalculationHistory;
import com.example.Object.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CalculatorService {

    Result<CalculationHistory> SaveHistory(CalculationHistory calculationHistory);

    List<String> readHistory(String Account);

    void deleteHistory(String Account);
}
