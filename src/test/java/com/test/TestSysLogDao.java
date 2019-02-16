package com.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jt.sys.common.vo.PageObject;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
import com.jt.sys.service.SysLogService;
import com.jt.sys.service.impl.SysLogServiceImpl;


public class TestSysLogDao extends TestBase{
	
	//编译时看=左边，运行时看=右边  多态继承
	
	
	
	@Test
	public void testsysLogServiceImpl(){
		//获取dao对象
	SysLogService logService	= ctx.getBean("sysLogServiceImpl", SysLogServiceImpl.class);
		//测试logService是否为空  断点测试
		Assert.assertNotEquals(null, logService);//（unexpected，actual）不期望出现的值  实际值
		//调用service对象方法
		PageObject<SysLog> po = logService.findPageObjects("admin", 3);
		//3输出结果
		System.out.println("rowCount="+po.getRowCount());
		System.out.println("pageCount="+po.getPageCount());
		for(SysLog log:po.getRecords()){
			System.out.println(log);
		}
	}
	
	
	@Test
	public void tetGetRowCount(){
		//获取dao对象
		SysLogDao dao = ctx.getBean("sysLogDao", SysLogDao.class);
		//调用的接口的方法
		int rows=	dao.getRowCount("admin");
		//输出结果
		System.out.println("rows="+rows);
	}
	
	@Test
	public void tetFindPageObjects(){
		//获取dao对象
		SysLogDao dao = ctx.getBean("sysLogDao", SysLogDao.class);
		//调用的接口的方法
		System.out.println(dao);
	List<SysLog> list=dao.findPageObject("admin",1,5);
	System.out.println(list);
		//输出结果
		for (SysLog s : list) {
			System.out.println(s);
			
		}
	}
	
	
}
