package com.important.crud.test;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.important.crud.dao.DepartmentDao;
import com.important.crud.entities.Department;

@Controller
public class TestJson {

	@Autowired
	private DepartmentDao dept;
	
	@ResponseBody
	@RequestMapping("/testJson2/ajax")
	public Collection<Department> getDepartment(@RequestBody Department department) {
		System.out.println("department:"+department);
		return dept.getDepartments();
	}
	
	@ResponseBody
	@RequestMapping("/testJson2/ajaxGet")
	public Collection<Department> getDepartments(@RequestParam Map map, String departmentName) {
		System.out.println("map:" + map);
		System.out.println(departmentName);
		return dept.getDepartments();
	}
}
