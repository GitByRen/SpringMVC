package com.important.crud.handlers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.important.crud.dao.DepartmentDao;
import com.important.crud.dao.EmployeeDao;
import com.important.crud.entities.Employee;

/**
 * 1.Restful风格的crud
 * 2.解析静态资源
 */
@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;

	private DepartmentDao departmentDao;

	/**
	 * 删除
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {
		employeeDao.delete(id);
		return "redirect:/emps";
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String save(Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}

	/**
	 * 显示部门
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("departments", departmentDao.getDepartments());
		map.put("employee", employeeDao.getAll());
		return "input";
	}

	/**
	 * 主页面显示
	 */
	@RequestMapping("/emps")
	public String emps(Map<String, Object> map) {
		map.put("employees", employeeDao.getAll());
		return "list";
	}

}
