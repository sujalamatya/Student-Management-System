
package com.sea.dao;

import com.sea.db.ConnectionFactory;
import com.sea.dto.UserDTO;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class UserDAO {
     Connection con=null;
    Statement stmt=null;
    ResultSet rs;
    PreparedStatement pstmt=null;
    String[] searchColumnNames={"ID","Full name","Username","Address","Contact","Email"};
        public UserDAO(){
       
            con=new ConnectionFactory().getConnection();
    }
        public void addUser(UserDTO user){
            String query="Insert into user values(?,?,?,?,?,?,?)";
            try {
                pstmt=con.prepareStatement(query);
                pstmt.setInt(1, user.getId());
                pstmt.setString(2, user.getFullname());
                pstmt.setString(3, user.getUsername());
                pstmt.setString(4, user.getPassword());
                pstmt.setString(5, user.getAddress());
                pstmt.setString(6, user.getContact());
                pstmt.setString(7, user.getEmail());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "your record inserted sucessfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public ResultSet getQueryResult(){
            String sql="SELECT * FROM user";
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
        public void delete(String value){
            try {
                String query="delete from user where id=?";
                pstmt=con.prepareStatement(query);
                pstmt.setString(1, value);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "One Record deleted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public UserDTO editUser(JTable table) {
         UserDTO userEdit=new UserDTO();
         userEdit.setId((Integer) table.getValueAt(table.getSelectedRow(), 0));
         userEdit.setFullname((String) table.getValueAt(table.getSelectedRow(), 1));
         userEdit.setUsername((String) table.getValueAt(table.getSelectedRow(), 2));
         userEdit.setPassword((String) table.getValueAt(table.getSelectedRow(), 3));
         userEdit.setAddress((String) table.getValueAt(table.getSelectedRow(), 4));
         userEdit.setContact((String) table.getValueAt(table.getSelectedRow(), 5));
         userEdit.setEmail((String) table.getValueAt(table.getSelectedRow(), 6));
         return userEdit;
        
    }

    public void updateUser(UserDTO userUpdate) {
        try {
            String query="update user set username=?,fullname=?,address=?,contact=?,email=? where id=?";
            pstmt=con.prepareStatement(query);
            pstmt.setInt(6, userUpdate.getId());
            pstmt.setString(1, userUpdate.getFullname());
            pstmt.setString(2, userUpdate.getUsername());
            pstmt.setString(3, userUpdate.getAddress());
            pstmt.setString(4, userUpdate.getContact());
            pstmt.setString(5, userUpdate.getEmail());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"One record updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String returnQueryToGetColumnNames(){
        String query= "SELECT id,fullname,username,"
                + "address,contact, email FROM user";
        return query;
    }
    public Vector<String> getColumnName(String query){
        Vector<String> columnNames=new Vector<String>();
        try {
            stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            ResultSetMetaData metaData=rs.getMetaData();
            int columnCount=metaData.getColumnCount();
            for(int column=1;column<=columnCount;column++){
                columnNames.add(metaData.getColumnName(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnNames;
    }

    public DefaultTableModel searchUser(String name, String value) throws Exception{
        String sql="";
        if("id".equalsIgnoreCase(name)){
            sql="SELECT id,fullname,username,address,contact,email FROM user WHERE "+name+"='"+Integer.parseInt(value)+"'";

        }else{
            sql="SELECT id,fullname,username,address,contact,email FROM user WHERE "+name+"='"+value+"'";
        }
        
        stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        
        ResultSetMetaData metaData=rs.getMetaData();
        Vector<String> columnNames=new Vector<String>();
        int columnCount=metaData.getColumnCount();
        for(int column=1;column<=columnCount;column++){
            columnNames.add(searchColumnNames[column-1]);
        }
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        if(!rs.next()){
            JOptionPane.showMessageDialog(null, "NO search found");
        }else{
//            rs.beforeFirst();
            while(rs.next()){
                Vector<Object> vector=new Vector<Object>();
                for(int columnIndex=1;columnIndex<=columnCount; columnIndex++){
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
        }
        return new DefaultTableModel(data, columnNames);
    }

    public Vector<String> getCName() {
        Vector<String> columnNames=new Vector<String>();
        columnNames.add("ID:");
        columnNames.add("Full Name:");
        columnNames.add("UserName:");
        columnNames.add("Address:");
        columnNames.add("Contact:");
        columnNames.add("Email:");
        return columnNames;
        
    }
}
