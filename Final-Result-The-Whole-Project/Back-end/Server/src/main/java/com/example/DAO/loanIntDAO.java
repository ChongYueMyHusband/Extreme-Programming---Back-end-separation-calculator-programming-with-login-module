package com.example.DAO;

import com.example.Object.depositInterest;
import com.example.Object.loanInterest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface loanIntDAO {
    @Insert("INSERT INTO loaninterest (Rate,Period) VALUES (#{rate}, #{Period})")
    int add(loanInterest loanInterest);

    @Select("SELECT Rate FROM loaninterest WHERE Period= #{Period}")
    String getRate(String Period);

    @Update("UPDATE loaninterest SET Rate = #{Rate} WHERE Period = #{Period}")
    int update(loanInterest loanInterest);

    @Delete("DELETE FROM interest_rates WHERE rate_id = #{rateId}")
    int delete(Long rateId);

    @Select("SELECT * FROM depositinterest")
    List<loanInterest> loanHistory();
}
