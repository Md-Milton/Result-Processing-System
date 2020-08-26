package resultprocessing.export.pdf;


import alertmessage.AlertMaker;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.datatable.DataTable;
import be.quodlibet.boxable.line.LineStyle;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Cell;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/*
@author afsal-5502
 */

public class ListToPDF {

    public enum Orientation {
        PORTRAIT, LANDSCAPE
    };

    public boolean doPrintToPdf(List<List> list, File saveLoc, Orientation orientation,String sem) {
        try {
            if (saveLoc == null) {
                return false;
            }
            if (!saveLoc.getName().endsWith(".pdf")) {
                saveLoc = new File(saveLoc.getAbsolutePath() + ".pdf");
            }
            //Initialize Document
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            
            //Create a landscape page
            if (orientation == Orientation.LANDSCAPE) {
                page.setMediaBox(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
            } else {
                page.setMediaBox(new PDRectangle(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight()));
            }

           
            doc.addPage(page);
            //Initialize table
            float margin = 50;
            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
            float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
            float yStart = yStartNewPage;
            float bottomMargin = 300;

            BaseTable dataTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true,
                    true);
            Row<PDPage>row = dataTable.createRow(50);
            String s = loadSemester(sem);
            be.quodlibet.boxable.Cell<PDPage>cell = row.createCell(100, s);
            
            
            dataTable.addHeaderRow(row);
            
            
            DataTable t = new DataTable(dataTable, page);
            t.addListToTable(list, DataTable.HASHEADER);
            dataTable.draw();
            doc.save(saveLoc);
            doc.close();

            return true;
        } catch (IOException ex) {
            AlertMaker.showError("Error occurred during PDF export");
            
        }
        return false;
    }
private String loadSemester(String s) {
    String str = "";
          if(s.equals("1st"))
                    str = "First Year First Term Examination";      
          else if(s.equals("2nd"))
                    str="Semester: First year Second term";
          else if(s.equals("3rd"))
                    str="Semester: Second year First term";
          else if(s.equals("4th"))
                 str="Semester: Second year Second term";
          else if(s.equals("5th"))
                    str="Semester: Third year First term";
          else if(s.equals("6th"))
                   str="Semester: Third year Second term";
          else if(s.equals("7th"))
                   str="Semester: Fourth year First term";
          else if(s.equals("8th"))
                  str="Semester: Fourth year Second term";
         return str;
    }
}
