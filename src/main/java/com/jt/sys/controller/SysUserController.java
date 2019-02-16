package com.jt.sys.controller;


import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.sys.common.vo.JsonResult;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;


@RequestMapping("/user/")
@Controller
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	
	
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username,String password){
		System.out.println(username);
		System.out.println(password);
		//1.对用户进行封装
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		//2.提交用户信息到SecurityManager
		//2.1 获取用户主体对象
		Subject subject = SecurityUtils.getSubject();
		//2.2提交用户信息（执行登陆认证操作）
		subject.login(token);
		
		return new JsonResult("login ok");
	}
	
	
	@RequestMapping("doUserListUI")
	public String doUserListUI(){
		return "sys/user_list";
	}
	@RequestMapping("doUserEditUI")
	public String doUserEditUI(){
		return "sys/user_edit";
	}
	/**
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username,String password){
		//1.封装用户身份和凭证
		UsernamePasswordToken token=
		new UsernamePasswordToken(username,password);
	    //2.执行登录认证操作
		Subject subject=
	    SecurityUtils.getSubject();
		subject.login(token);//认证失败此位置会抛出异常
		//如上信息提交给谁?SecurityManager
		//SecurityManager调用认证管理器完成认证操作
		return new JsonResult("login ok");
	}
	*/


	
	
	/**用户管理模块修改页面数据回现*/
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(
			Integer id){
		return new JsonResult(
	    sysUserService.findObjectById(id));
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(
			SysUser entity,
			Integer ...roleIds){
		sysUserService.updateObject(entity,roleIds);
		return new JsonResult("update ok");
	}
	/**
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(
			SysUser entity,
			Integer ...roleIds){
		sysUserService.saveObject(entity,roleIds);
		return new JsonResult("save ok");
	}
	*/
	/**禁用用户*/
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id,
			Integer valid){
		//获取用户信息
		SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
		sysUserService.validById(id, valid,
				user.getUsername());//admin 假数据
		return new JsonResult("update ok");
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String username,Integer pageCurrent){
		return new JsonResult(
		sysUserService.findPageObjects(
		username, pageCurrent));
	}
	
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(
			SysUser entity,
			Integer[] roleIds){
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("save ok");
	}

	
}
