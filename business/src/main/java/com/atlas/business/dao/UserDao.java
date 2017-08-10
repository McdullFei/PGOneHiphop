package com.atlas.business.dao;

import com.atlas.business.dto.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by renfei on 17/6/5.
 */
@Component
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getAll(){

        List<User> list = jdbcTemplate.query("select id,user_name as userName,age from user", resultSet -> {
            List<User> list1 = Lists.newArrayList();
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("userName"));
                user.setAge(resultSet.getInt("age"));

                list1.add(user);
            }

            return list1;
        });

        return list;
    }
}
