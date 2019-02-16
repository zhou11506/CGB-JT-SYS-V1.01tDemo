package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.common.vo.Node;
import com.jt.sys.entity.SysMenu;

public interface SysMenuDao {
	/**查询菜单*/
	List<Map<String,Object>> findObjects();
	
	/**根据菜单id统计子菜单的个数*/
	int getChildCount( @Param("id")Integer id);
	
	/**根据id删除菜单*/
	int deleteObject( @Param("id")Integer id);
	
	
	/**获取菜单节点信息（id,name,parentId）*/
	List<Node> findZTreeMenuNodes();
	
	/**添加插入数据的方法*/
	int insertObject(SysMenu entity);
	
	/**修改数据的方法*/
	int updataObject(SysMenu entity);
	
	
	/**基于菜单id找菜单对应的授权标识*/
	List<String> findPermissions(
			@Param("menuIds")
			Integer...menuIds);

}
