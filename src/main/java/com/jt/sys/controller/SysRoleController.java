package com.jt.sys.controller;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.sys.common.vo.JsonResult;
import com.jt.sys.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;


/**
 * Spring MVC 中的控制层对象（后端控制器）
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/role/")
public class SysRoleController {
	@Resource
	
	private SysRoleService  sysRoleService;
	
	//用户模块中嵌套角色数据
	  @RequestMapping("doFindRoles")
	  @ResponseBody
	  public JsonResult doFindRoles(){
	  	 return new JsonResult(sysRoleService.findObjects());
	  }

	
	
	
	
	@RequestMapping("doRoleListUI")
	public String doLogListUI(){
//		return "starter";
		return "sys/role_list";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String name,Integer pageCurrent){
		System.out.println(123);
		PageObject<SysRole> pageObject=null;
		try {
			Thread.sleep(1);
			
			pageObject = sysRoleService.findPageObjects(name, pageCurrent);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		return new JsonResult(pageObject);
		
	}//借助JsonResult对象封装控制层要返回的结果
	
//删除操作
	  @RequestMapping("doDeleteObjects")
	  @ResponseBody
	  public JsonResult doDeleteObjects(Integer...ids){
		  sysRoleService.deleteObjects(ids);
		  JsonResult result = new JsonResult("delete ok");
		  System.out.println(result.toString());
		  return result;
	  }
	  //添加修改
	  @RequestMapping("doRoleEditUI")
	  public String doRoleEditUI(){
	  		return "sys/role_edit";
	  }
	  
	  @RequestMapping("doSaveObject")
	  @ResponseBody
	  public JsonResult doSaveObject(
	      		SysRole entity,Integer[] menuIds){
	      	sysRoleService.saveObject(entity,menuIds);
	  return new JsonResult("save ok");    
	  }

	  
	  
	  @RequestMapping("doFindObjectById")
	  @ResponseBody
	   public JsonResult doFindObjectById(Integer id){
	      	Map<String,Object> map=
	      	sysRoleService.findObjectById(id);
	      	return new JsonResult(map);
	   }

	  
	  @RequestMapping("doUpdateObject")
	  @ResponseBody
	  public JsonResult doUpdateObject(SysRole entity,
Integer[] menuIds){
		  System.out.println(Arrays.toString(menuIds));
		  System.out.println(entity.toString());
		  sysRoleService.updateObject(entity, menuIds);
	 return new JsonResult("update ok");
	 

	  
	  }
	  
	  
	  /**删除*/
	  @RequestMapping("doDeleteObject")
	  @ResponseBody
	  public JsonResult doDeleteObject(Integer id){
		  System.out.println("id="+id);
	   sysRoleService.deleteObject(id);
	  return new JsonResult("delete Ok");
	  }

	  
	  
	  
	  
	  
	  
}
