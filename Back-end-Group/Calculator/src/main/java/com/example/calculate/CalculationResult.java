package com.example.calculate;

import org.springframework.jdbc.datasource.init.ScriptException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationResult {
    private double result;

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double evaluateExpression(String expression) {
        //Replace elements in an expression so that ScriptEngine can follow a reasonable expression
        expression = expression.replaceAll("exp","Math.exp");
        expression = expression.replaceAll("ln", "Math.log");
        expression = expression.replaceAll("tan", "Math.tan");
        expression = expression.replaceAll("cos", "Math.cos");
        expression = expression.replaceAll("sin", "Math.sin");
        expression = expression.replaceAll("π", "Math.PI");
        System.out.println(expression);
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object result = engine.eval(expression);
            if (result instanceof Number) {
                BigDecimal bd = new BigDecimal(((Number) result).doubleValue()).setScale(7, RoundingMode.HALF_UP);
                return bd.doubleValue();
            } else {
                throw new RuntimeException("Invalid expression result");
            }
        } catch (ScriptException | javax.script.ScriptException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to evaluate expression: " + expression, e);
        }
    }

}
