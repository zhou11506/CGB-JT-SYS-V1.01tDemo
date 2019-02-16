package com.jt.sys.controller;


import com.jt.sys.common.vo.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;






/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class PageController {
	@RequestMapping("doIndexUI")
	public String doIndexUI(){
		return "starter";
	}
	
	@RequestMapping("doPageUI")
	public String doPageUI(){
		return "common/page";
	}
	
	
	@RequestMapping("doLoginUI")
	public String doLoginUI(){

		return "login";
	}
	
	
	@RequestMapping("doLogOut")
	@ResponseBody
	public JsonResult doLogOut(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return  new JsonResult("您已退出系统");
	}
	@RequestMapping("demo")
	public String dodemo(){
		return "demo";
	}
	
	
	
	
}
