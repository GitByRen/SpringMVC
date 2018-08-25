package com.important.crud.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.important.crud.dao.EmployeeDao;
import com.important.crud.entities.Employee;

/**
 * 1.自定义类型转换器
 * 2.annotation-driven会自动注册
 * 	RequestMappingHandlerMapping,
 *	RequestMappingHandlerAdapter 与
 *	ExceptionHandlerExceptionResolver 三个bean
 * 
 */
@Controller
public class SpringMVCTest {

	@Autowired
	private EmployeeDao employeeDao;

	// 可以在 bean 中获取国际化资源文件 Locale 对应的消息
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@RequestMapping("/testSimpleMappingExceptionResolver")
	public String testSimpleMappingExceptionResolver(@RequestParam("i") int i){
		String [] vals = new String[10];
		System.out.println(vals[i]);
		return "success";
	}
	
	/**
	 * 处理SpringMVC特定的异常，具体参看DefaultHandlerExceptionResolver
	 */
	@RequestMapping(value="/testDefaultHandlerExceptionResolver",method=RequestMethod.POST)
	public String testDefaultHandlerExceptionResolver(){
		System.out.println("testDefaultHandlerExceptionResolver...");
		return "success";
	}
	
	/**
	 * testResponseStatus
	 * @param i
	 * @return
	 */
	@ResponseStatus(reason = "测试", value = HttpStatus.NOT_FOUND)
	@RequestMapping("/testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("i") int i) {
		if (i == 13) {
			throw new UserNameNotMatchPasswordException();
		}
		System.out.println("testResponseStatusExceptionResolver...");
		return "success";
	}
	
	
	/**
	 * 1. 在 @ExceptionHandler 方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
	 * 2. @ExceptionHandler 方法的入参中不能传入 Map. 若希望把异常信息传导页面上, 需要使用 ModelAndView 作为返回值
	 * 3. @ExceptionHandler 方法标记的异常有优先级的问题，优先使用匹配度高的
	 * 4. @ControllerAdvice: 如果在当前 Handler 中找不到 @ExceptionHandler 方法来出来当前方法出现的异常, 
	 * 则将去 @ControllerAdvice 标记的类中查找 @ExceptionHandler 标记的方法来处理异常. 
	 */
//	@ExceptionHandler({ArithmeticException.class})
//	public ModelAndView handleArithmeticException(Exception ex){
//		System.out.println("出异常了: " + ex);
//		ModelAndView mv = new ModelAndView("error");
//		mv.addObject("exception", ex);
//		return mv;
//	}
	
	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver(@RequestParam("i") int i) {
		System.out.println("result" + (10 / i));
		return "success";
	}
	
	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param desc
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/testFileUpload")
	public String testFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("desc") String desc)
			throws IOException {
		System.out.println("desc:" + desc);
		System.out.println("original:" + file.getOriginalFilename());
		System.out.println("inputStream:" + file.getInputStream());
		return "success";
	}
	
	/**
	 * 测试国际化
	 */
	@RequestMapping("/i18n")
	public String testI18n(Locale locale) {
		String val = messageSource.getMessage("i18n.user", null, locale);
		System.out.println(val);
		return "i18n";
	}
	
	
	@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpServletRequest request) throws IOException {
		byte[] body = null;
		
		ServletContext sc = request.getServletContext();
		InputStream in = sc.getResourceAsStream("/files/abc.txt");
		body = new byte[in.available()];
		in.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=abc.txt");
		
		HttpStatus status = HttpStatus.OK;
		
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, status);
		return response;
	}
	
	/**
	 * HttpMessageConverter将请求信息转化并绑定到处理方法的入参中或者将响应结果转换为响应信息
	 */
	@ResponseBody
	@RequestMapping("/testHttpMessageConverter")
	public String testHttpMessageConverter(@RequestBody String body) {
		System.out.println(body);
		return "helloworld " + new Date();
	}
	
	/**
	 * ajax
	 */
	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson() {
		return employeeDao.getAll();
	}
	
	/**
	 * 测试自定义类型转换
	 */
	@RequestMapping("/testConversionServiceConverer")
	public String testConverter(@RequestParam("employee") Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}

}
