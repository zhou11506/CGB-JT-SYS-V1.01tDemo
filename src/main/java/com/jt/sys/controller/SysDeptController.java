package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.sys.common.vo.JsonResult;
import com.jt.sys.entity.SysDept;
import com.jt.sys.service.SysDeptService;

@Controller
@RequestMapping("/dept/")
public class SysDeptController {
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@RequestMapping("doDeptListUI")
	public String doDeptListUI(){
		return "sys/dept_list";
	}
	@RequestMapping("doDeptEditUI")
	public String doDeptEditUI(){
		return "sys/dept_edit";
	}
	
	@RequestMapping("doFindZTreeNodes")
	@ResponseBody
	public JsonResult doFindZTreeNodes(){
		return new JsonResult(
		sysDeptService.findZTreeNodes());
	}
	
	
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects(){
		return new JsonResult(sysDeptService.findObjects());
	}	
	
	
}
