package khaing.thymeleaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import khaing.thymeleaf.dao.EmployeeRepo;
import khaing.thymeleaf.entity.EmployeeEntity;

@Controller
@RequestMapping("testing")
public class TestingController {
    private EmployeeRepo employeeRepo;

    @Autowired
    public TestingController(EmployeeRepo thEmployeeRepo){
        this.employeeRepo = thEmployeeRepo;
    }
    // @GetMapping("/login")
    // public String login(){
    //     return "loginRegisterTemplate";
    // }

    // @GetMapping("/employees")
    // public String admin(Model model){
    //     return "Admin";
    // }
    @GetMapping("/list")
    public String listEmployees(Model theModel){
        List<EmployeeEntity> theEmployees = employeeRepo.findAll();
        theModel.addAttribute("EmployeesList", theEmployees);
        return "employees/listEmployees";
    }

    @GetMapping("/add")
    public String add (Model theModel){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        theModel.addAttribute("employee", employeeEntity);
        return "employees/createForm";
        
    }

   
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") EmployeeEntity employeeEntity){
        employeeRepo.save(employeeEntity);
        return "redirect:/testing/list";

    }
}   
