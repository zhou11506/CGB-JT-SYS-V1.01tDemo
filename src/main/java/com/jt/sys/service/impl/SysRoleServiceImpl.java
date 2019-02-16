package com.jt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.jt.sys.common.exception.ServiceException;
import com.jt.sys.common.vo.CheckBox;
import com.jt.sys.common.vo.PageObject;
import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService{
	
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	
	@Override
	public List<CheckBox> findObjects() {
		
		return sysRoleDao.findObjects();
	}
	
	
	
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1判断pageCurrent参数的合法性
		if(pageCurrent== null || pageCurrent<1)throw
		new IllegalArgumentException("页码不正确");
		//2基于用户名统计日志记录总数
		int rowCount=sysRoleDao.getRowCount(name);
		System.out.println("rowCount="+rowCount);
		//3对日志记录总数进行验证（总数为0就没有必要显示）
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//4查询当前页要显示的记录
		Integer pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysRole> records = sysRoleDao.findPageObject(name, startIndex, pageSize);
		//5对象查询的记录，总记录数以及相关分页信息
		PageObject<SysRole> po = new PageObject<>();
		po.setRecords(records);//当前页记录
		po.setRowCount(rowCount);//总页数
		po.setPageCurrent(pageCurrent);//当前页的页码值
		po.setPageSize(pageSize);//页面大小
		int pageCount=(rowCount-1)/pageSize+1;
//		int pageCount=rowCount/pageSize;
//		if(rowCount%pageSize!=0)pageCount++;
		po.setPageCount(pageCount);//总页数
		
		if(pageCurrent>pageCount)
			throw new RuntimeException("页码超出实际页码最大值，请重新输入");
		//6返回结果
		return po;
	}

	
	/**删除*/
	@Override
	public int deleteObjects(Integer[]ids) {
		if(ids==null||ids.length==0){
			throw new IllegalArgumentException("请先选中数据");
		}
		int rows=sysRoleDao.deleteObjects(ids);
		if(rows==0){
			throw new ServiceException("记录可能不存在");
		}
		return rows;
	}

	@Transactional(timeout=10,rollbackFor=Throwable.class,isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)//开启事务
	@Override
	public int saveObject(SysRole entity, Integer...menuIds) {
		
		    	//1.合法性验证
		    	if(entity==null)
		    throw new ServiceException("保存数据不能为空");
		    	if(StringUtils.isEmpty(entity.getName()))
		    	throw new ServiceException("角色名不能为空");
		    	//是否角色已经存在
		   	if(menuIds==null||menuIds.length==0)
		    	throw new ServiceException("必须为角色赋予权限");
		   	
		    	//2.保存数据
		    	int rows=sysRoleDao.insertObject(entity);
		    int menusrow=	sysRoleMenuDao.insertObject(
		    			entity.getId(),menuIds);
		    	if(menusrow>0){
		    		throw new ServiceException("menusrow不合法");
		    	}
		    	//3.返回结果
		    	return rows;
		 }


	@Override
	public Map<String, Object> findObjectById(Integer id) {
	  	//1.合法性验证
    	if(id==null||id<=0)
    	throw new ServiceException("id的值不合法");
    	//2.执行查询
    	SysRole role=sysRoleDao.findObjectById(id);
  	//3.验证结果并返回
    	if(role==null)
    	throw new ServiceException("此记录已经不存在");
    	List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleId(id);
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("role", role);
    	map.put("menuIds", menuIds);
    	return map;

	}


	@Override
	public int updateObject(SysRole entity, Integer...menuIds) {
		//1.合法性验证
    	if(entity==null)
    throw new ServiceException("更新的对象不能为空");
    	if(entity.getId()==null)
    	throw new ServiceException("id的值不能为空");
    	
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new ServiceException("角色名不能为空");
    	if(menuIds==null||menuIds.length==0)
    	throw new ServiceException("必须为角色指定一个权限");
    	
    	//2.更新数据
    	int rows=sysRoleDao.updateObject(entity);
    	System.out.println("rows="+rows);
    	if(rows==0)
    throw new ServiceException("对象可能已经不存在");
    	sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
    	sysRoleMenuDao.insertObject(entity.getId(),menuIds);
 
    	//3.返回结果
    	return rows;

	}


	
	
	@Override
	public int deleteObject(Integer id) {
			//1.验证参数的合法性
			if(id==null||id<1)
			throw new ServiceException("id的值不正确,id="+id);
			//2.执行dao操作
			int rows=sysRoleDao.deleteObject(id);
			if(rows==0)
			throw new ServiceException("数据可能已经不存在");
			//删除角色菜单关系数据
	         sysRoleMenuDao.deleteObjectsByRoleId(id);
	         //删除角色用户关系数据
	         sysUserRoleDao.deleteObjectsByRoleId(id);
			//3.返回结果
			return rows;
	}

	
	
	
	
	
	
	
	

}
