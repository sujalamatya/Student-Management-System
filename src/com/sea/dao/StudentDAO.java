
package com.sea.dao;

import com.sea.db.ConnectionFactory;
import com.sea.dto.StudentDTO;
import com.sea.dto.StudentDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class StudentDAO {
    Connection con=null;
    Statement stmt=null;
    ResultSet rs;
    PreparedStatement pstmt=null;
    String[] searchColumnNames={"ID","Full name","Dob","Gender","Faculty","Section"};
    public StudentDAO(){
       
            con=new ConnectionFactory().getConnection();
    }
    public void addStudent(StudentDTO student){
            String query="Insert into student values(?,?,?,?,?,?,?,?)";
            try {
                pstmt=con.prepareStatement(query);
                pstmt.setString(1, student.getName());
                pstmt.setString(2, student.getAddress());
                pstmt.setString(3, student.getPhone());
                pstmt.setString(4, student.getEmail());
                pstmt.setString(5, student.getGPA());
                pstmt.setString(6, student.getPercentage());
                pstmt.setString(7, student.getCourse());
                pstmt.setInt(8,student.getAttendance());
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "your record inserted sucessfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public ResultSet getQueryResult(){
            String sql="SELECT * FROM student";
            try {
                stmt=con.createStatement();
                rs=stmt.executeQuery(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
         return rs;
            
        }
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException{
            ResultSetMetaData metaData=rs.getMetaData();
            Vector<String> columnNames=new Vector<String>();
            int columnCount=metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));   
            }
            Vector<Vector<Object>> data=new Vector<Vector<Object>>();
            while(rs.next()){
                Vector<Object> vector=new Vector<Object>();
            
            for(int i=1;i<=columnCount;i++){
                vector.add(rs.getObject(i));
             }
            data.add(vector);
            }
            return new DefaultTableModel(data,columnNames);
            
        }
}
