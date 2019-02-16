package com.jt.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.sys.common.exception.ServiceException;
import com.jt.sys.common.vo.PageObject;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
import com.jt.sys.service.SysLogService;
@Service
public class SysLogServiceImpl implements SysLogService{
	
	@Resource
	private SysLogDao sysLogDao;

	@Override
	public PageObject<SysLog> findPageObjects(String name, Integer pageCurrent) {
		//1判断pageCurrent参数的合法性
		if(pageCurrent== null || pageCurrent<1)throw
		new IllegalArgumentException("页码不正确");
		//2基于用户名统计日志记录总数
		int rowCount=sysLogDao.getRowCount(name);
		//3对日志记录总数进行验证（总数为0就没有必要显示）
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//4查询当前页要显示的记录
		Integer pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysLog> records = sysLogDao.findPageObject(name, startIndex, pageSize);
		//5对象查询的记录，总记录数以及相关分页信息
		PageObject<SysLog> po = new PageObject<>();
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
	public int deleteObjects(Integer[] ids) {
		if(ids==null||ids.length==0){
			throw new IllegalArgumentException("请先选中数据");
		}
		int rows=sysLogDao.deleteObjects(ids);
		if(rows==0){
			throw new ServiceException("记录可能不存在");
		}
		return rows;
	}

}
