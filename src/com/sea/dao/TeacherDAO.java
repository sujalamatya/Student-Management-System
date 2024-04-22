
package com.sea.dao;

import com.sea.db.ConnectionFactory;
import com.sea.dto.StudentDTO;
import com.sea.dto.StudentDTO;
import com.sea.dto.TeacherDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TeacherDAO {
    Connection con=null;
    Statement stmt=null;
    ResultSet rs;
    PreparedStatement pstmt=null;
    String[] searchColumnNames={"Tid","Tname","course"};
    public TeacherDAO(){
       
            con=new ConnectionFactory().getConnection();
    }
    public void addStudent(TeacherDTO teacher){
            String query="Insert into teacher values(?,?,?)";
            try {
                pstmt=con.prepareStatement(query);
                pstmt.setInt(1, teacher.getT_id());
                pstmt.setString(2, teacher.getTname());
                pstmt.setString(3, teacher.getCourse());
                
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "your record inserted sucessfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public ResultSet getQueryResult(){
            String sql="SELECT * FROM teacher";
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
