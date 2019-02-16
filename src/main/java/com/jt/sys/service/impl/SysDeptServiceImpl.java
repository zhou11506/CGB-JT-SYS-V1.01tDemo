package com.jt.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jt.sys.common.exception.ServiceException;
import com.jt.sys.common.vo.Node;
import com.jt.sys.dao.SysDeptDao;
import com.jt.sys.entity.SysDept;
import com.jt.sys.service.SysDeptService;
/***
 * @Transactional 注解可以应用在方法和类上
 * 1)定义在类上：所有方法都会开启事务
 * 2)定义在方法上：主要用于做事务特性设置
 * @author ta
 *
 */
@Transactional(timeout=30,rollbackFor=RuntimeException.class)
@Service
public class SysDeptServiceImpl implements SysDeptService {
	@Autowired
	private SysDeptDao sysDeptDao;
//	@Autowired
	//private SysUserDao sysUserDao;

	@Transactional(readOnly=true)//默认为false
	@Override
	public List<Node> findZTreeNodes() {
		return sysDeptDao.findZTreeNodes();
	}
	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> findObjects() {
		return sysDeptDao.findObjects();
	}


}
