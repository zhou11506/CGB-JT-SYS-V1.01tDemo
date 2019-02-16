package com.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * 测试基类（测试的共性代码）
 * @author Administrator
 *
 */
public class TestBase {
	protected ClassPathXmlApplicationContext ctx;
	
	@Before
	public void init(){//初始化（@Terst之前）
		//初始化 spring容器
	
		String cfg="spring-configs.xml";
		 ctx = new ClassPathXmlApplicationContext(cfg);
		
	}
	@After
	public void close(){//初始化（@Terst之后）
		//释放资源
		ctx.close();
	}
}
