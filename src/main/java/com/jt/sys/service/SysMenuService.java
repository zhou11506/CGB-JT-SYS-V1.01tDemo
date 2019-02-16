package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.sys.common.vo.Node;
import com.jt.sys.entity.SysMenu;

public interface SysMenuService {
	
	List<Map<String,Object>> findObjects();
	/**删除菜单以及菜单和教室关系的数据*/
	int deleteObject (Integer id);
	
	/**查询menu树节点信息*/
	List<Node> findZTreeMenuNodes();

	/**添加menu信息*/
	int insertObject(SysMenu entity);
	
	/**修改menu信息*/
	int updateObject(SysMenu entity);
}
