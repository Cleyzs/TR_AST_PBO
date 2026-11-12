/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author THINKPAD
 */
public class Koneksi {
    
    public static Connection con;
    public static Statement stm;
    
     public void config(){
        try {
            // Variabel-variabel untuk koneksi
            String url = "jdbc:mysql://127.0.0.1/laundry_db";
            String user = "root";
            String pass = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Menggabungkan variabel tersebut menjadi String Connection
            con = DriverManager.getConnection(url,user,pass);
            
            // Membuat SQL Statement
            stm = (Statement)con.createStatement();
            System.out.println("Koneksi berhasil...");
        } catch (Exception e) {
            System.out.println("Koneksi gagal...");
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        Koneksi kn = new Koneksi();
        kn.config();
    }
}
