package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.sys.common.vo.PageObject;

import com.jt.sys.entity.SysRole;
import com.jt.sys.common.vo.CheckBox;

public interface SysRoleService {
	/**通过此方法实现分页查询*/
	PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
	/**基于id执行删除操作*/
	
	int deleteObjects(Integer[] ids);
	
	int saveObject(SysRole entity,Integer...menuIds);
	
	Map<String,Object> findObjectById(Integer id) ;
	
	
	
	 int updateObject(SysRole entity,Integer...menuIds);
	
	int deleteObject(Integer id) ;
	
	
	
	 List<CheckBox> findObjects();
	
}
