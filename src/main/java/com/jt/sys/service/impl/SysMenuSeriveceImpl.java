package com.jt.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.jt.sys.common.exception.ServiceException;
import com.jt.sys.common.vo.Node;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

@Service
public class SysMenuSeriveceImpl implements SysMenuService{

	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	/**修改menu菜单信息*/
	public int updateObject(SysMenu entity) {
		if(entity==null){
			throw new ServiceException("修改对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getName())){
			throw new ServiceException("修改的菜单名不能为空");
		}
		int rows=sysMenuDao.updataObject(entity);
		
		if(rows==0){
			throw new ServiceException("记录可能已经不存在");
		}
		return rows;
	};
	
	/**添加menu树节点信息*/
	@Override
	public int insertObject(SysMenu entity) {
		if(entity==null){
			throw new ServiceException("保存对象不能为空");
			
		}
		
		if(StringUtils.isEmpty(entity.getName())){
			throw new ServiceException("菜单名不能为空");
		}
		int rows=0;
		
		try {
			rows = sysMenuDao.insertObject(entity);
		} catch (Exception e) {
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	
	
	
	

	/**查询menu树节点信息*/
	@Override
	public List<Node> findZTreeMenuNodes() {
		
		List<Node> list=sysMenuDao.findZTreeMenuNodes();
		if(list==null || list.size()==0){
			throw new ServiceException("没有菜单信息");
		}
		return list;
	}
	
	
	
	
	
	
	//中间表没有Service
	
		@Override
		public int deleteObject(Integer id) {
			//合法性验证
			if(id==null ||id<=0){
				throw new ServiceException("请重新选择");
			}
			//基于id对子菜单查询
			int count = sysMenuDao.getChildCount(id);
			if(count>0){
				throw new ServiceException("请先删除子菜单");
			}
			//删除菜单元素
			int rows = sysMenuDao.deleteObject(id);
			if(rows==0){
				throw new ServiceException("此菜单可能已经不存在");
			}
			//删除角色、菜单关系数据(中间表)
			sysRoleMenuDao.deleteObjectsByMenuId(id);
			return rows;
		}
		
		
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list=sysMenuDao.findObjects();
		if(list==null || list.size()==0){
			throw new ServiceException("没有相关菜单记录");
		}
		
		return list;
	}

	

}
