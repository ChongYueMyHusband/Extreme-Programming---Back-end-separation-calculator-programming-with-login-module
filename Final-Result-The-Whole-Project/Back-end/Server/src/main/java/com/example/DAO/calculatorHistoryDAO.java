package com.example.DAO;

import com.example.Object.CalculationHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface calculatorHistoryDAO {
    @Insert("INSERT INTO history (Account, result) VALUES (#{Account}, #{result})")
    int add(CalculationHistory calculationHistory);

    @Select("SELECT result FROM history WHERE Account = #{Account}")
    @ResultType(String.class)
    List<String> getHistoryByAccount(String Account);

    @Delete("Delete FROM history WHERE Account = #{Account}")
    void deleteHistory(String Account);
}
