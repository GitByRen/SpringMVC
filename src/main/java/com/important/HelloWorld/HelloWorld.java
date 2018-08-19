package com.important.HelloWorld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {

	/**
	 * 1、@RequestMapping注解来映射请求的URL
	 * 2、返回值会通过视图解析器解析实际的物理视图
	 *    通过：prefix + returnVal + suffix 的方式得到实际的物理视图，然后做转发操作
	 * 3、webapp/HelloWorld/index.jsp映射的URL要加上文件名
	 */
	@RequestMapping("/HelloWorld/helloWorld")
	public String hello() {
		System.out.println("hello world!");
		return "success";
	}

}
