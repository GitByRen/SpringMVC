package com.important.crud.handlers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.important.crud.dao.EmployeeDao;

@Controller
public class EmployeeHandler {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping("/input")
    public String input() {
        
        return "input";
    }

    @RequestMapping("/emps")
    public String emps(Map<String, Object> map) {
        map.put("employees", employeeDao.getAll());
        return "list";
    }

}
