package com.important.crud.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping("/testConversionServiceConverer")
	public String testConverter(@RequestParam("employee") Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}

}
