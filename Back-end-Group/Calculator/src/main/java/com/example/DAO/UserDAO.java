package com.example.DAO;


import com.example.Object.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDAO {

    @Insert("INSERT INTO user (Account, password) VALUES (#{Account}, #{password})")
    int add(User user);


    @Select("SELECT * FROM user WHERE Account = #{Account}")
    User getByAccount(String Account);

    @Update("UPDATE user SET userName = #{userName} ,idiograph=#{idiograph}, age =#{age}, Sex=#{Sex} WHERE Account = #{Account}")
    int update(User user);
}
