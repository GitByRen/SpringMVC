package com.important.crud.test;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.important.crud.dao.DepartmentDao;
import com.important.crud.entities.Department;

@Controller
public class TestJson {

	@Autowired
	private DepartmentDao dept;
	
	@ResponseBody
	@RequestMapping(value = "/testJson2/testAppJson", method = RequestMethod.POST)
	public Collection<Department> testAppJson(@RequestBody Department department) {
		System.out.println("department:"+department);
		return dept.getDepartments();
	}
	
	@ResponseBody
	@RequestMapping(value = "/testJson2/testAppWww", method = RequestMethod.POST)
	public Collection<Department> testAppWww(@RequestParam Map map) {
		System.out.println("department:"+map);
		return dept.getDepartments();
	}
	
	@ResponseBody
	@RequestMapping("/testJson2/ajaxGet")
	public Collection<Department> getDepartments(@RequestParam Map map, String departmentName) {
		System.out.println("map:" + map);
		System.out.println(departmentName);
		return dept.getDepartments();
	}
	
	@ResponseBody
	@RequestMapping("/testJson2/ajaxGetDto")
	public Collection<Department> getDepartmentDto(Department map, String departmentName) {
		System.out.println("map:" + map);
		System.out.println(departmentName);
		return dept.getDepartments();
	}
}
