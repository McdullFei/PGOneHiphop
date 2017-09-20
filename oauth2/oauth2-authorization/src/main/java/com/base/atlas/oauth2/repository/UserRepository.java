package com.base.atlas.oauth2.repository;

import com.base.atlas.oauth2.dto.User;
import org.springframework.data.repository.CrudRepository;

/**
 * 复制代码只做oauth2.0框架演示用，后面可以对business接入rpc框架提供service
 * （oauth2 module不引入business这个springboot module）
 *
 * @author renfei
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 根据Id查询User
     * @return
     */
    User findUserById(Long id);

    /**
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
