/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outils;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Abdelhamid
 */
public class DB_Connect {
    static String url="jdbc:mysql://localhost/impots";
    static String username="root";
    static String password ="";
    static Connection con=null;
    static String unicode="?useUnicode=yes&characterEncoding=UTF-8";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con =  DriverManager.getConnection(url, username,password);
        con =  DriverManager.getConnection(url+unicode,username,password);

        if(con==null){
            return null;
        }else{
            return con;
        }
    }


    public static void main(String[]args){
        try {
            com.mysql.jdbc.Connection connection;
            connection=(com.mysql.jdbc.Connection) DB_Connect.getConnection();

            String SQL="Select * from beneficiaire";
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(SQL);
            ResultSetImpl resultSet = (ResultSetImpl) statement.executeQuery();

            while (resultSet.next()){
                System.out.println(
                        resultSet.getInt(1)+
                                resultSet.getString(2)+
                                resultSet.getDate(3)+
                                "Lardjam"+
                                resultSet.getString(4)+
                                resultSet.getString(5)+
                                resultSet.getString(6));
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
