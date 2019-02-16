package com.jt.sys.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.sys.common.vo.JsonResult;
import com.jt.sys.common.vo.PageObject;
import com.jt.sys.entity.SysLog;
import com.jt.sys.service.SysLogService;
/**
 * Spring MVC 中的控制层对象（后端控制器）
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/log/")
public class SlsLogController {
	@Resource
	
	private SysLogService  sysLogService;
	
	@RequestMapping("doLogListUI")
	public String doLogListUI(){
//		return "starter";
		return "sys/log_list";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String username,Integer pageCurrent){
		System.out.println(123);
		PageObject<SysLog> pageObject=null;
		try {
			Thread.sleep(1);
			
			pageObject = sysLogService.findPageObjects(username, pageCurrent);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		return new JsonResult(pageObject);
		
	}//借助JsonResult对象封装控制层要返回的结果
	
//删除操作
	  @RequestMapping("doDeleteObjects")
	  @ResponseBody
	  public JsonResult doDeleteObjects(Integer...ids){
		  sysLogService.deleteObjects(ids);
		  JsonResult result = new JsonResult("delete ok");
		  System.out.println(result.toString());
		  return result;
	  }

	
}
