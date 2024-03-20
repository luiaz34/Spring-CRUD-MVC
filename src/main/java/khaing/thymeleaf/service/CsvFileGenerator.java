package khaing.thymeleaf.service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import khaing.thymeleaf.entity.EmployeeEntity;

@Component
public class CsvFileGenerator {
    public void writeEmployeesToCsv(List<EmployeeEntity> employeeEntities, Writer writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            for (EmployeeEntity employee : employeeEntities) {
                printer.printRecord(employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail());
            }
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}