package khaing.thymeleaf.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import khaing.thymeleaf.dao.EmployeeRepo;
import khaing.thymeleaf.entity.EmployeeEntity;
import khaing.thymeleaf.service.EmployeeRestId;

@Controller
@RequestMapping("/api")
public class TestingController {

    private EmployeeRepo employeeRepo;
    private EmployeeRestId employeeRestId;

    @Autowired
    public TestingController(EmployeeRepo thEmployeeRepo, EmployeeRestId theEmployeeRestId) {
        this.employeeRepo = thEmployeeRepo;
        this.employeeRestId = theEmployeeRestId;

    }

    @GetMapping("/list")
    public String listEmployees(Model theModel) {
        employeeRestId.resetEmployeeId();
        List<EmployeeEntity> theEmployees = employeeRepo.findAll();
        theModel.addAttribute("EmployeesList", theEmployees);
        return "employees/listEmployees";
    }

    @GetMapping("/add")
    public String add(Model theModel) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        theModel.addAttribute("employee", employeeEntity);
        return "employees/createForm";

    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") EmployeeEntity employeeEntity) {
        employeeRepo.save(employeeEntity);
        return "redirect:/api/list";
    }

    @GetMapping("/update")
    public String updateEmployee(@RequestParam("employeeId") int theId, Model model) {
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(theId);
        model.addAttribute("employee", employeeEntity);
        return "employees/updateForm";
    }

    @GetMapping("/delete")
    public String deleteEmplyoee(@RequestParam("employeeId") int theId) {
        employeeRepo.deleteById(theId);
        return "redirect:/api/list";

    }

    @GetMapping("/showLogin")
    public String loginPage() {
        return "loginRegister/loginRegisterTemplate";
    }

    @GetMapping("/roleAccessDenied")
    public String accessDeniedPage() {
        return "exceptionHandlerTemplate/accessDeniedPage";
    }

}