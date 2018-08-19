package com.important.HelloWorld;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.important.entity.User;

//@SessionAttributes(value = { "user" }, types = { String.class })
@RequestMapping("/springmvc")
@Controller
public class TestRequestMapping {

	private static final String SUCCESS = "success";
	
	/**
	 * 1、有 @ModelAttribute 标记的方法, 会在每个目标方法执行之前被 SpringMVC 调用! 
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		System.out.println("modelAttribute");
		if (id != null) {
			// 模拟从数据库中获取对象
			User user = new User(1, "Tom", "123456", "tom@atguigu.com", 12);
			System.out.println("从数据库中获取一个对象: " + user);
			map.put("abc", user);
			System.out.println(map);
		}
	}

	/**
	 * 运行流程： 1. 执行 @ModelAttribute 注解修饰的方法: 从数据库中取出对象, 把对象放入到了 Map 中. 键为: user
	 * 2.SpringMVC 从 Map 中取出 User 对象, 并把表单的请求参数赋给该 User 对象的对应属性. 3.SpringMVC
	 * 把上述对象传入目标方法的参数.
	 * 
	 * 4、注意: 在 @ModelAttribute 修饰的方法中, 放入到 Map 时的键需要和目标方法入参类型的第一个字母小写的字符串一致!
	 * 
	 * 
	 * 源代码分析的流程： 
	 * 1. 调用 @ModelAttribute 注解修饰的方法. 实际上把 @ModelAttribute 方法中 Map 中的数据放在了 implicitModel 中. 
	 * 2. 解析请求处理器的目标参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性
	 * 1).创建 WebDataBinder 对象: 
	 *   	①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则objectName 为类名第一个字母小写. 
	 *      >注意: attrName. 若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰, 则 attrName 值即为 @ModelAttribute 的 value 属性值
	 * 		②. 确定 target 属性: 
	 * 		> 在 implicitModel 中查找 attrName 对应的属性值. 若存在, ok 
	 * 		> *若不存在:则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中 获取 attrName 所对应的属性值. 
	 * 		若 session 中没有对应的属性值, 则抛出异常. 
	 * 		> 若Handler 没有使用 @SessionAttributes 进行修饰, 或 @SessionAttributes 中没有使用 value 值指定的 key 和 attrName 相匹配, 
	 * 		则通过反射创建 POJO 对象
	 * 
	 * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性. 
	 * 3). *SpringMVC 会把WebDataBinder 的 attrName 和 target 给到 implicitModel. 近而传到 request 域对象中.
	 * 4). 把WebDataBinder 的 target 作为参数传递给目标方法的入参.
	 * 
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(User user) {
		System.out.println("修改：" + user);
		return SUCCESS;
	}
	
	
	/**
	 * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外(实际上使用的是 value 属性值),
	 *                    还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中(实际上使用的是 types 属性值)
	 *                    注意：该注解只能放在类的上面. 而不能修饰方法.
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		User user = new User("Tom", "123456", "tom@atguigu.com", 15);
		map.put("user", user);
		map.put("school", "atguigu");
		return SUCCESS;
	}
	
	
	/**
	 * 目标方法可以添加 Map 类型(实际上也可以是 Model 类型或 ModelMap 类型)的参数. 
	 */
	@RequestMapping("testMap")
	public String testMap(Map<String, Object> map) {
		System.out.println(map.getClass().getName());
		map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));
		return SUCCESS;
	}
	

	/**
	 * 目标方法的返回值可以是 ModelAndView 类型。 其中可以包含视图和模型信息 
	 * SpringMVC会把 ModelAndView 的 model中数据放入到 request 域对象中.
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		ModelAndView modelAndView = new ModelAndView(SUCCESS);
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}
	
	
	/**
	 * 可以使用 Serlvet 原生的 API 作为目标方法的参数 具体支持以下类型
	 * 
	 * HttpServletRequest 
	 * HttpServletResponse 
	 * HttpSession
	 * java.security.Principal 
	 * Locale InputStream 
	 * OutputStream 
	 * Reader 
	 * Writer
	 */
	@RequestMapping("/testServletApi")
	public String testServletApi(HttpServletRequest request, HttpServletResponse response, Writer out) throws IOException {
		System.out.println(request);
		System.out.println(response);
//		out.write("aaa");
		return SUCCESS;
	}
	
	/**
	 * Spring MVC 会按请求参数名和 POJO 属性名进行自动匹配， 自动为该对象填充属性值。支持级
	 * 联属性。 如：dept.deptId、dept.address.tel 等
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user) {
		System.out.println(user);
		return SUCCESS;
	}
	
	/**
	 * 映射一个 Cookie 值
	 */
	@RequestMapping("/testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
		System.out.println("testCookieValue：" + sessionId);
		return SUCCESS;
	}

	/**
	 * 了解: 映射请求头信息
	 */
	@RequestMapping("/testRequestHeader")
	public String testRequestHeader(@RequestHeader(value = "Accept-Language") String al) {
		System.out.println("testRequestHeader, Accept-Language: " + al);
		return SUCCESS;
	}
	
	
	/**
	 * @RequestParam：映射请求参数
	 * 
	 * 超链接，表单都可以
	 */
	@RequestMapping("/testRequestParam")
	public String testRequestParam(@RequestParam(value = "username") String un,
			@RequestParam(value = "age", required = false, defaultValue = "0") Integer age) {
		System.out.println("testRequestParam username: " + un + " age: " + age);
		return SUCCESS;
	}
	
	
	/**
	 * 如何发送 PUT 请求和 DELETE 请求呢 ?
	 * 1. 需要在web.xml中配置 HiddenHttpMethodFilter
	 * 2. 需要发送 POST 请求
	 * 3. 需要在发送 POST 请求时携带一个 name="_method" 的隐藏域, 值为 DELETE 或 PUT
	 * 
	 * Tomcat8及以上会报错：HTTP Status 405 - JSPs only permit GET POST or HEAD
	 * 因为页面靠请求转发来实现，如果是DELETE请求则按照DELETE请求去请求success.jsp页面，而jsp页面不支持PUT和DELETE
	 * 解决方法：加上@ResponseBody就可以
	 */
	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String testRestPut(@PathVariable Integer id) {
		System.out.println("testRest Put: " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.DELETE)
	public String testRestDelete(@PathVariable Integer id) {
		System.out.println("testRest Delete: " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest", method = RequestMethod.POST)
	public String testRest() {
		System.out.println("testRest POST");
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.GET)
	public String testRest(@PathVariable Integer id) {
		System.out.println("testRest GET: " + id);
		return SUCCESS;
	}
	
	/**
	 * @PathVariable 可以来映射 URL 中的占位符到目标方法的参数中.
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id) {
		System.out.println("testPathVariable: " + id);
		return SUCCESS;
	}
	
	/**
	 * 了解：Ant风格的匹配
	 * ?：匹配文件名中的一个字符
	 * *：匹配文件名中的任意字符
	 * **：**匹配多层路径
	 */
	@RequestMapping("/testAntPath/*/abc")
	public String testAntPath() {
		System.out.println("testAntPath");
		return SUCCESS;
	}
	
	/**
	 * 了解：可以使用params和headers来更加精确的映射请求
	 */
	@RequestMapping(value = "/testParamsAndHeaders", params = { "username", "age != 10" }, headers = {
			"Accept-Language=zh-CN,zh;q=0.8" })
	public String testParamsAndHeaders() {
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}
	
	/**
	 * 使用method指定请求方式
	 */
	@RequestMapping(value = "/testMethod", method = RequestMethod.POST)
	public String testMethod() {
		System.out.println("testMethod");
		return SUCCESS;
	}
	
	/**
	 * @RequestMapping除了修饰方法，还可以修饰类
	 * 
	 * 2. 
	 * 1). 类定义处: 提供初步的请求映射信息。相对于 WEB 应用的根目录
	 * 2). 方法处: 提供进一步的细分映射信息。 相对于类定义处的 URL。
	 *   若类定义处未标注 @RequestMapping，则方法处标记的 URL相对于 WEB 应用的根目录
	 */
	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		System.out.println("testRequestMapping");
		return SUCCESS;
	}

}
