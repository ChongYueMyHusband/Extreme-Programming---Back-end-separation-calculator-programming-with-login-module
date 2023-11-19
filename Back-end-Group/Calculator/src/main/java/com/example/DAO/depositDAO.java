package com.example.DAO;

import com.example.Object.depositInterest;
import org.apache.ibatis.annotations.*;

@Mapper
public interface depositDAO {

    @Insert("INSERT INTO depositinterest(rate, Period) VALUES (#{rate}, #{Period})")
    int add(depositInterest depositInterest);

    @Select("SELECT rate FROM depositinterest WHERE Period= #{Period}")
    depositInterest getPeriod(String Period);

    @Update("UPDATE depositinterest SET rate = #{rate} WHERE Period = #{Period}")
    int update(depositInterest depositInterest);

    @Delete("DELETE FROM interest_rates WHERE rate_id = #{rateId}")
    int delete(Long rateId);

}
