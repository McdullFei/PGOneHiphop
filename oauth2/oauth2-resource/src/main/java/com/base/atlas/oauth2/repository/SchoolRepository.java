package com.base.atlas.oauth2.repository;

import com.base.atlas.oauth2.dto.School;
import org.springframework.data.repository.CrudRepository;

/**
 * 复制代码只做oauth2.0框架演示用，后面可以对business接入rpc框架提供service
 * （oauth2 module不引入business这个springboot module）
 *
 * @author renfei
 *
 */
public interface SchoolRepository extends CrudRepository<School, Long> {
    /**
     * 根据Id查询
     * @return
     */
    School findSchoolById(Long id);

}
