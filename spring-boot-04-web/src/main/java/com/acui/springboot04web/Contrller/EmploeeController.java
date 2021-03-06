package com.acui.springboot04web.Contrller;

import com.acui.springboot04web.dao.DepartmentDao;
import com.acui.springboot04web.dao.EmployeeDao;
import com.acui.springboot04web.entities.Department;
import com.acui.springboot04web.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@Controller
public class EmploeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;
    @GetMapping("/emps")
    public String list(Model model) {

        Collection<Employee> employees = employeeDao.getAll();
        //放在请求域中
        model.addAttribute("emps", employees);
        //thymeleaf 默认会拼串， classpath://templates/xxx.html
        return "emp/list";
    }
    //来到员工界面
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        //来到添加页面,并且查出所有部门，在页面显示
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);
        return "emp/add";
    }

    /**
     * 员工添加
     * springmvc自动将请求参数和入参对象的属性进行一一绑定；
     * 要求了请求参数的名字和javaBean入参的属性名一样
     * @param employee
     * @return
     */
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        //来到员工列表页面
        System.out.println("保存的员工信息："+employee.toString());
        //保存员工
        employeeDao.save(employee);
        //redirect:重定向到一个地址  /代表当前项目路径
        //forward:转发到一个地址
        return "redirect:/emps";
    }

    /**
     *来到修改页面，查出当前员工，在页面回显
     * @return
     */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp", employee);

        //查出所有部门，在页面显示
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);
        //回到修改页面（add是一个修改添加二合一的页面）
        return "emp/add";
    }

    //员工修改,需要提交员工id
    @PutMapping("/emp")
    public String updateEmploee(Employee employee) {
        System.out.println("修改的员工数据：" + employee.toString());
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //员工删除, 需要提交员工id
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }



}
