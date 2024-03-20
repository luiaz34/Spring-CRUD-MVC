package khaing.thymeleaf.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import khaing.thymeleaf.dao.EmployeeRepo;
import khaing.thymeleaf.entity.EmployeeEntity;
import khaing.thymeleaf.service.CacheService;
import khaing.thymeleaf.service.CsvFileGenerator;
import khaing.thymeleaf.service.EmployeeRestId;
import khaing.thymeleaf.service.PdfFileGenerator;

@Controller
@RequestMapping("/api")
public class TestingController {

    private EmployeeRepo employeeRepo;
    private EmployeeRestId employeeRestId;
    private CsvFileGenerator csvFileGenerator;
    private PdfFileGenerator pdfFileGenerator;
    private CacheService cacheService;

    @Autowired
    public TestingController(EmployeeRepo thEmployeeRepo, EmployeeRestId theEmployeeRestId,
            CsvFileGenerator theCsvFileGenerator, PdfFileGenerator thPdfFileGenerator,CacheService theCacheService) {
        this.employeeRepo = thEmployeeRepo;
        this.employeeRestId = theEmployeeRestId;
        this.csvFileGenerator = theCsvFileGenerator;
        this.pdfFileGenerator = thPdfFileGenerator;
        this.cacheService = theCacheService;

    }

    @GetMapping("/home")
    public String home(){
        return "employees/homepage";
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

    @GetMapping("/export-to-csv")
    public void exportIntoCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"employees.csv\"");
        csvFileGenerator.writeEmployeesToCsv(employeeRepo.findAll(), response.getWriter());

    }

    @GetMapping("/export-to-pdf")
    public void generatePdfFile(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=Employees_list" + ".pdf";
        response.setHeader(headerkey, headervalue);

        List<EmployeeEntity> employeeEntities = employeeRepo.findAll();
        pdfFileGenerator.writeEmployeesToPdf(employeeEntities, response);
    }
}