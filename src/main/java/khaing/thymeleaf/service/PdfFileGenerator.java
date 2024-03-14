package khaing.thymeleaf.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import khaing.thymeleaf.entity.EmployeeEntity;
@Component
public class PdfFileGenerator {
public void writeEmployeesToPdf(List <EmployeeEntity> employeeEntities, HttpServletResponse response) throws DocumentException, IOException {
    // Creating the Object of Document
    Document document = new Document(PageSize.A4);
    // Getting instance of PdfWriter
    PdfWriter.getInstance(document, response.getOutputStream());
    // Opening the created document to change it
    document.open();
    // Creating font
    // Setting font style and size
    Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    fontTiltle.setSize(20);
    // Creating paragraph
    Paragraph paragraph1 = new Paragraph("Employees List", fontTiltle);
    // Aligning the paragraph in the document
    paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
    // Adding the created paragraph in the document
    document.add(paragraph1);
    // Creating a table of the 4 columns
    PdfPTable table = new PdfPTable(4);
    // Setting width of the table, its columns and spacing
    table.setWidthPercentage(100f);
    table.setWidths(new int[] {3,3,3,5});
    table.setSpacingBefore(5);
    // Create Table Cells for the table header
    PdfPCell cell = new PdfPCell();
    // Setting the background color and padding of the table cell
    CMYKColor redCmyk = new CMYKColor(0f, 100f, 100f, 0f);
    cell.setBackgroundColor(redCmyk);
    cell.setPadding(5);
    // Creating font
    // Setting font style and size
    Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    font.setColor(CMYKColor.WHITE);
    // Adding headings in the created table cell or  header
    // Adding Cell to table
    cell.setPhrase(new Phrase("ID", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("First Name", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Last Name", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Email", font));
    table.addCell(cell);

    for (EmployeeEntity employee: employeeEntities) {
 
      table.addCell(String.valueOf(employee.getId()));
   
      table.addCell(employee.getFirstName());

      table.addCell(employee.getLastName());

      table.addCell(employee.getEmail());
    }

    document.add(table);

    document.close();
  }
}