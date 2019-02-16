package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuDao {
	
	int deleteObjectsByMenuId(Integer menuId);
	
	int insertObject(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
	
	
	List<Integer> findMenuIdsByRoleId(Integer roleId);
	
	
	int deleteObjectsByRoleId(Integer roleId);
	
	/**基于角色id查找菜单id集合*/
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds")Integer...roleIds);

}
