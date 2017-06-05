package com.spider.manipulateDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecuteSQLDemo {
	
	
	
	/**
	 * main method for show demo
	 * @param args
	 */
	public static void main(String[] args){
		addOrUpdateOrDeleteTest1();
		queryTest1();
	}
	
	
	public static MySQLHelper mh = new MySQLHelper();
	
	//demo
	/**
	 * demo for add,update or delete
	 * @return
	 */
	public static int addOrUpdateOrDeleteTest1(){
		int count = 0;
		PreparedStatement pst = null;
		if(mh == null){
			mh = new MySQLHelper();
		}
		try {
			//pst = mh.getPst("delete from test1 where name=?");
			pst = mh.getPst("insert into test1(name) values(?)");
			//pst = mh.getPst("update test1 set name = ? where id= ?");
			pst.setString(1, "xds");
			count = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			mh.closeConnection();
		}
		return count;
	}
	
	/**
	 * demo for query
	 */
	public static void queryTest1(){
		ResultSet rs = null;
		if(mh == null){
			mh = new MySQLHelper();
		}
		rs = mh.executeQuery("select * from test1");
		String name = null;
		int id = 0;
		if(rs!=null){
			try {
				System.out.println("id"+"\t"+"name");
				while(rs.next()){
					name = rs.getString("name");
					id = rs.getInt("id");
					System.out.println(id+"\t"+name);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}finally{
				mh.closeConnection();
			}
		}
	}
}
