package com.sea.gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sea.db.ConnectionFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PDFGenerator {
    Connection con = null;

    public void generatePDF() {
        con = new ConnectionFactory().getConnection();

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                Document document = new Document();

                PdfWriter.getInstance(document, new FileOutputStream(fileToSave.getAbsolutePath() + ".pdf"));
                document.open();

                PdfPTable table = new PdfPTable(7); // 6 columns for id, fullname, dob, gender, faculty, section
                addTableHeader(table);
                addRows(table);

                document.add(table);

                document.close();

                System.out.println("PDF generated successfully!");

            } else {
                System.out.println("User canceled the operation. PDF not generated.");
            }

        } catch (DocumentException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addTableHeader(PdfPTable table) {
        String[] header = {"Name", "Address", "Phone", "Email", "GPA", "Percentage","Course"};
        for (String columnHeader : header) {
            PdfPCell cell = new PdfPCell();
            cell.setGrayFill(0.7f);
            cell.setPhrase(new Phrase(columnHeader));
            table.addCell(cell);
        }
    }

    private void addRows(PdfPTable table) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM student");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String phone = resultSet.getString("phone");
            String email = resultSet.getString("email");
            String gpa = resultSet.getString("GPA");
            String percentage = resultSet.getString("percentage");
            String course = resultSet.getString("course");
            

            table.addCell(String.valueOf(name));
            table.addCell(address);
            table.addCell(phone);
            table.addCell(email);
            table.addCell(gpa);
            table.addCell(percentage);
            table.addCell(course);
        }
    }
}
