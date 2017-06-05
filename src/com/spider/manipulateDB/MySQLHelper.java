package com.spider.manipulateDB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLHelper {
	
	static{
		InputStream stream = ClassLoader.getSystemResourceAsStream("config/jdbc.properties");
		Properties properties=new Properties();
		try {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		url = properties.getProperty("jdbc.databaseurl");
		name= properties.getProperty("jdbc.driverClassName");
		user= properties.getProperty("jdbc.username");
		password= properties.getProperty("jdbc.password");
	}
	
	public static final String url;
    public static final String name;
    public static final String user;
    public static final String password;
    public Connection conn = null;
    public PreparedStatement pst = null;
    public Statement st = null;
    public ResultSet rs = null;
    
    /**
     * open connection and return PreparedStatement
     * @param sql
     * @return PreparedStatement
     */
    public PreparedStatement getPst(String sql){
    	getConnection();
    	try {
			pst = conn.prepareStatement(sql);
		} catch (SQLException e) {
			try {
				pst.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	return pst;
    }
    /**
     * open connection
     * @return Connection
     */
    public Connection getConnection(){
    	try {
			Class.forName(name);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    	try {
	    	if(this.conn == null || this.conn.isClosed()){
	    		this.conn = DriverManager.getConnection(url, user, password);
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	return conn;
    }
    
    /**
     * close Connection,PreparedStatement,Statement and ResultSet
     */
    public void closeConnection(){
    	if(this.conn != null){
    		try {
    			if(rs != null){
    				rs.close();
    			}
    			if(pst != null){
    				pst.close();
    			}
    			if(st != null){
    				st.close();
    			}
    			if(conn != null){
					conn.close();
    			}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
    	}
    }
    
    
    
    
    //demo
    public ResultSet addOrUpdateOrDelete(String sql){
    	
    	int count = 0;
    	getConnection();//conn = DriverManager.getConnection(url, user, password);
    	try {
			pst = conn.prepareStatement(sql);
			count = pst.executeUpdate();
			System.out.println("***************************************************************");
			System.out.println("success number : " + count);
			System.out.println("***************************************************************");
		} catch (SQLException e) {
			try {
				pst.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
    	return this.rs;
    }
    public ResultSet executeQuery(String sql){
    	
    	getConnection();//conn = DriverManager.getConnection(url, user, password);
    	try {
			pst = conn.prepareStatement(sql);
		} catch (SQLException e) {
			try {
				pst.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
    	try {
			this.rs = pst.executeQuery();
		} catch (SQLException e) {
			try {
				if(this.rs != null){
					this.rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
    	return this.rs;
    }
    public ResultSet executeStatementQuery(String sql){
    	
    	getConnection();//conn = DriverManager.getConnection(url, user, password);
    	try {
			st = conn.createStatement();
		} catch (SQLException e) {
			try {
				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
    	try {
    		this.rs = st.executeQuery(sql);
		} catch (SQLException e) {
			try {
				this.rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
    	return this.rs;
    }
    
    

}
