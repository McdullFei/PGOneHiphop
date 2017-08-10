package com.atlas.business.repository;

import com.atlas.business.dto.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 根据Id查询User
     * @return
     */
    User findUserById(Long id);

    /**
     *
     * @param username
     * @param password
     * @return
     */
    User findUserByUsernameAndPassword(String username, String password);
}
