package com.base.atlas.oauth2.controller;

import com.base.atlas.oauth2.dto.School;
import com.base.atlas.oauth2.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 假定为受保护的资源
 *
 * 用户中心 - Controller
 * @author ShengMiezhi
 */
@RestController
@RequestMapping(value = "/api/school")
public class SchoolController {

  @Resource
  private SchoolRepository schoolRepository;


  @RequestMapping(value = "/", method= RequestMethod.GET)
  @ResponseBody
  public School index(@RequestParam("id") Long schoolId) {

    School school = schoolRepository.findSchoolById(schoolId);

    return school;
  }

}
