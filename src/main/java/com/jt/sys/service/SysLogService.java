package com.jt.sys.service;

import com.jt.sys.common.vo.PageObject;
import com.jt.sys.entity.SysLog;

public interface SysLogService {
	/**通过此方法实现分页查询*/
	PageObject<SysLog> findPageObjects(String name,Integer pageCurrent);
	/**基于id执行删除操作*/
	
	int deleteObjects(Integer[] ids);
}
