package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.sys.common.vo.JsonResult;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
	//绑定Service 菜单接口
	@Autowired
	private SysMenuService sysMenuService;
	
	//修改菜单信息
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu entity){
		System.out.println(entity.toString());
		int updateObject = sysMenuService.updateObject(entity);
		System.out.println(updateObject);
		return new JsonResult("update ok");
	}
	
	//返回菜单页面
	@RequestMapping("doMenuListUI")
	public String  doMenuListUI() {
		
		return "sys/menu_list";
	}
	

	//实现查找方法
	@RequestMapping("doFindObjects")
	@ResponseBody
	public  JsonResult doFindObjects(){
		
		return new JsonResult(sysMenuService.findObjects());
	}
	
	
	/**实现删除菜单*/
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer id){
		sysMenuService.deleteObject(id);
		return new JsonResult("Delete OK");
	}
	
	
	/**添加菜单*/
	 @RequestMapping("doMenuEditUI")
	 public String doMenuEditUI(){
		 return "sys/menu_edit";
	 }
	 
	 
	 /**查找menu树节点信息*/
	 @RequestMapping("doFindZTreeMenuNodes")
	 @ResponseBody
	 public JsonResult doFindZTreeMenuNodes(){
		 return new JsonResult(sysMenuService.findZTreeMenuNodes());
	 }

	 @RequestMapping("doInsertObject")
	 @ResponseBody
	 public JsonResult doInsertObject(SysMenu entity){
		 System.out.println(entity.toString());
		 sysMenuService.insertObject(entity);
		 return new JsonResult("save ok");
	 }
	 
}
