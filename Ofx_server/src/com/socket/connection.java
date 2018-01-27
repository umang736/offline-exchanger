/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.socket;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author umang gupta
 */
public class connection {
    
 static final String JDBC_DRIVER="com.mysql.jdbc.Driver"; 
static final String DB_URL = "jdbc:mysql://localhost:3306/db";
static final String USER="root"; 
static final String PASS ="umang736";
public static Connection conn=null;
public static Statement stmt=null;

public connection() throws SQLException, ClassNotFoundException{
    //Register JDBC driver
     Class.forName(JDBC_DRIVER);//communication channel open kar diya
    
        //Open a connection   
      System.out.println("Connecting to a selected database...");
     conn=(Connection) DriverManager.getConnection(DB_URL,USER,PASS);
     System.out.println("Connected database successfully...");
        stmt=(Statement) conn.createStatement();
} 
    
}
