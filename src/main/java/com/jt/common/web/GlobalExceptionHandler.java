package com.jt.common.web;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jt.sys.common.vo.JsonResult;

/**全局异常处理类*/
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	
	
	
	
	
	
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(ShiroException e){
		e.printStackTrace();
		JsonResult r = new JsonResult();
		r.setState(0);
		if(e instanceof UnknownAccountException){
			r.setMessage("用户不存在");
		}else if(e instanceof LockedAccountException) {
		
			r.setMessage("用户已被禁用");
		}else if(e instanceof IncorrectCredentialsException){
			r.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException){
			r.setMessage("权限不足，请联系系统管理者");
		}else{
			r.setMessage(e.getMessage());
		}
		
		return r;
	}
	
	
	
	
	
	
	
	//定义处理的异常类型
	@ExceptionHandler(RuntimeException.class)
	
	@RequestMapping("exception")
	public ModelAndView doHandleRuntimeException(RuntimeException e){
		e.printStackTrace();
		String msg ="页面不存在";
		ModelAndView mv = new ModelAndView();
//		mv.addObject("msg",msg);
		mv.setViewName("exception");
		return mv;
	}
	
	
}
