package com.jt.common.aspect;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.anno.RequiresLog;
import com.jt.common.util.IPUtils;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
import com.jt.sys.entity.SysUser;


/**
 * 面向切面
 * @author Administrator
 *
 */
@Aspect//此注解用于描述这个类是一个切面类
@Service
public class SysLogAspect {
	//在哪些方法执行时执行该方法？
	/**
	 * 通过此方法定义一个切入点（PointCut）
	 * bean(sysUserServiceImpl)为一个切入点表达式
	 * 只能实现粗粒度的切入点设计
	 * 
	 * 
	 */
	//@Pointcut("bean(sysUserServiceImpl)")
	//@Pointcut("bean(*ServiceImpl)")
	@Pointcut("@annotation(com.jt.common.anno.RequiresLog)")
	public void doLogPointCut(){
		
	}
	
	//可以将此方法理解为一个通知（在目标方法上扩展业务）
	@Around("doLogPointCut()")//环绕通知
	public Object around(ProceedingJoinPoint jp)throws Throwable{
		//记录时间
		long t1=System.currentTimeMillis();
		//执行目标方法  如何获取目标方法及目标对象
		Object result = jp.proceed();
		//记录时间
		long t2=System.currentTimeMillis();
		
		//将日志信息写入到数据库
		saveObject(jp,(t2-t1));
		
		
		return result;
	}

	private void saveObject(ProceedingJoinPoint jp, long time) throws Exception, Exception {
		//1获取目标对象类型
		Class<?> targetCls = jp.getTarget().getClass();
		//2获取目标对象方法
		//2.1获取方法签名（封装方法的相关信息）
		MethodSignature ms=(MethodSignature)jp.getSignature();
		
		//2.2获取目标方法
		Method method=
		targetCls.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
		String methodName=targetCls.getName()+"."+method.getName();
		System.out.println(methodName+".totalTime="+time);
		
		//3.获取目标方法执行时传递的实际参数
		Object[] args = jp.getArgs();
		System.out.println("args="+Arrays.toString(args));
		
		
		//4.获取方法对应的操作
		//4.1获取方法上定义的注解
		RequiresLog requiresLog = method.getDeclaredAnnotation(RequiresLog.class);
		String operation = requiresLog.value();
		//5 获取操作的用户
		SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
			String username=user.getUsername();
		System.out.println(operation);
		//6.获取IP地址
		String ip = IPUtils.getIpAddr();
		//7.封装用户行为日志
		SysLog log = new SysLog();
		log.setUsername(username);
		log.setOperation(operation);
		log.setIp(ip);
		log.setTime(time);
		log.setMethod(methodName);
		log.setCreatedTime(new Date());//now（） xx 不用这个
		log.setParams(Arrays.toString(args));
		//8.将数据存入数据库
		
		sysLogDao.insertObject(log);
		
		
		
		
		
		
	}
	@Autowired
	private SysLogDao sysLogDao;
}
