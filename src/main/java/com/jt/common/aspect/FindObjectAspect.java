package com.jt.common.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * 面向切面 查找功能切入
 * @author Administrator
 *
 */
@Aspect
@Service
public class FindObjectAspect {
	
	@Pointcut("@annotation(com.jt.common.anno.FindObjectFiter)")
	public  void doFindObject(){}
	
//	public Object around(){
//		
//	}

}
