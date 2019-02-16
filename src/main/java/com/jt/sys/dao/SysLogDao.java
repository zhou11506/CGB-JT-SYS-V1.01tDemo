package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysLog;

public interface SysLogDao {
	
	int getRowCount(@Param("username")String username);
	
	List<SysLog> findPageObject(@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
	int deleteObjects(@Param("ids")Integer...ids);
	
	
	/**日记添加操作*/
	int insertObject(SysLog log);
}
