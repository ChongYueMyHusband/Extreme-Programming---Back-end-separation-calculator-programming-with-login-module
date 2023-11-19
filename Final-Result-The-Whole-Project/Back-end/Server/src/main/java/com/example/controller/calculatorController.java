package com.example.controller;



import com.example.DAO.calculatorHistoryDAO;
import com.example.Object.CalculationHistory;
import com.example.Object.Result;
import com.example.Object.User;
import com.example.Service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.metamodel.mapping.MappingModelCreationLogger.LOGGER;

@RequestMapping("calculateResult")
@RestController
public class calculatorController {
    public static final String SESSION_NAME = "userInfo";

   private final JdbcTemplate jdbcTemplate;
    @Autowired
    private CalculatorService calculatorService;
    @Autowired
    private calculatorHistoryDAO calculatorHistory;
    @Autowired
    public calculatorController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping  ("/saveHistory")
    public Result<CalculationHistory> calculateExpression(@RequestBody CalculationHistory calculationHistory) {
        LOGGER.info("接收到一条计算结果");
        Result<CalculationHistory> result;
        result= calculatorService.SaveHistory(calculationHistory);
        result.setResultSuccess("receive calculation result successfully");
        return result;
    }/*
    public void saveHistory(double result) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM history", Integer.class);
        if (count >= 10) {
            jdbcTemplate.update("DELETE FROM history ORDER BY timestamp LIMIT 1");
        }
        jdbcTemplate.update("INSERT INTO history (result) VALUES (?)", result);
    }*/
    @PostMapping("/history")
    public String getHistory(@RequestBody User user) {
        LOGGER.info("向"+user.getAccount()+"发送历史记录");
        String account = user.getAccount();
        List<String> results=jdbcTemplate.queryForList("SELECT result FROM history WHERE Account = ? ORDER BY timestamp DESC", String.class, account);
        return results.toString();
    }

    @PostMapping("/delete")
    public void delete(@RequestBody User user){

        calculatorService.deleteHistory(user.getAccount());
    }


}