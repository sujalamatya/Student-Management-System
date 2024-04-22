package com.sea.main;



import com.sea.gui.Login;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args)throws Exception{
        Login ld=new Login();
        ld.setTitle("Subarashii Education Academy");
        ld.setResizable(false);
        ld.setLocationRelativeTo(null);
        ld.setVisible(true);
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("de.javasoft.synthetica,simple2d.syntheticasimple2DLookAndFeel");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
