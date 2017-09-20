package com.base.atlas.oauth2.controller;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用的异常处理Controller
 *
 * @author renfei
 */
@Controller
@RequestMapping("/errors")
public class ErrorsController extends BasicErrorController {

  public ErrorsController(ServerProperties serverProperties) {
    super(new DefaultErrorAttributes(), serverProperties.getError());
  }

  @RequestMapping(value = "/401", method = {RequestMethod.POST, RequestMethod.GET},
      produces = "text/html;charset=UTF-8")
  public String unAuthorized(Model model) {
    model.addAttribute("message", "您没有权限访问这个功能。（错误码：401）");
    model.addAttribute("describe", "401");
    model.addAttribute("exception", "");
    model.addAttribute("showStackTrace", 0);
    return "errors";
  }

  /**
   * 覆盖方法，处理@ResponseBody请求
   * @param request HttpServletRequest
   * @return ResponseEntity
   */
  @Override
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    boolean showStackTrace = isIncludeStackTrace(request, MediaType.ALL);
    Map<String, Object> body = getErrorAttributes(request, showStackTrace);
    //输出自定义的Json格式
    Map<String, Object> map = new HashMap<>(9);
    map.put("success", false);
    map.put("message", "系统异常");// FIXME 这里可以统一错误码错误信息
    map.put("describe", "系统异常");// FIXME 这里可以统一错误码错误信息
    map.put("exception", body.get("exception"));
    //包含堆栈的时候进行输出
    if (showStackTrace) {
      map.put("trace", body.get("trace"));
    }
    //为适用bootgrid加入下面属性
    map.put("current", 1);
    map.put("rowCount", 10);
    map.put("total", 0);
    map.put("rows", Collections.EMPTY_LIST);
    //固定返回状态码200
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  /**
   * 覆盖方法，处理@RequestMapping(produces = "text/html")
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @return ModelAndView
   */
  @Override
  public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
    response.setStatus(500);// FIXME 这里可以统一错误码错误信息
    // 如果要显示错误堆栈，需要在request的参数中加入trace=true
    boolean showStackTrace = isIncludeStackTrace(request, MediaType.TEXT_HTML);
    Map<String, Object> body = getErrorAttributes(request, showStackTrace);
    ModelAndView model = new ModelAndView("/errors", body);
    model.addObject("message", "系统异常");// FIXME 这里可以统一错误码错误信息
    model.addObject("describe", "系统异常");// FIXME 这里可以统一错误码错误信息
    model.addObject("exception", body.get("exception"));
    model.addObject("showStackTrace", showStackTrace ? 1 : 0);
    if (showStackTrace) {
      model.addObject("trace", body.get("trace"));
    }
    //指定自定义的视图
    return model;
  }
}