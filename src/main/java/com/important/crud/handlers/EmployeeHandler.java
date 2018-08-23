package com.important.crud.handlers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	@Autowired
	private DepartmentDao departmentDao;

	@ModelAttribute
	public void modelAttribute(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (id != null) {
			map.put("employee", employeeDao.get(id));
		}
	}

	/**
	 * 修改员工信息
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.PUT)
	public String update(Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}

	/**
	 * 显示员工信息
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		map.put("employee", employeeDao.get(id));
		map.put("departments", departmentDao.getDepartments());
		return "input";
	}

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
	 * 
	 * BindingResult：如果类型转换出错，结果会放在BindingResult中
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String save(@Valid Employee employee, BindingResult result, Map<String, Object> map) {
		System.out.println("save:" + employee);
		if (result.getErrorCount() > 0) {
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(error.getField() + ":" + error.getDefaultMessage());
			}
			
			// 若验证出错，则转向定制的页面
			map.put("departments", departmentDao.getDepartments());
			return "input";
		}
		employeeDao.save(employee);
		return "redirect:/emps";
	}

	/**
	 * 显示部门
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("departments", departmentDao.getDepartments());
		map.put("employee", new Employee());
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
	
	
	/**
	 * 1.@InitBinder标识的方法，可以对WebDataBinder对象进行初始化。
	 * 2.WebDataBinder是DataBinder的子类，用于完成由表单字段到JavaBean属性的绑定
	 * 3.@InitBinder方法不能有返回值，必须声明为void
	 */
//	@InitBinder
//	public void testInitBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("lastName");
//	}

}
