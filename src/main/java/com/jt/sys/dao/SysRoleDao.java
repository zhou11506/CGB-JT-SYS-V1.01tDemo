package com.jt.sys.dao;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysRole;

import com.jt.sys.common.vo.CheckBox;



public interface SysRoleDao {
int getRowCount(@Param("name")String name);
	
	List<SysRole> findPageObject(@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
	int deleteObjects(@Param("ids")Integer...ids);
	
	
	int insertObject(SysRole entity);
	
	SysRole findObjectById(Integer id);
	
	
	int updateObject(SysRole entity);
	
	
	int deleteObject(Integer id);
	
	
	//只查询id,name
	List<CheckBox> findObjects();
	
}
