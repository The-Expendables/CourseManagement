package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyDAO {
	protected static Connection conn;
	protected static PreparedStatement pst;
	protected static String sqlCommand;
	protected static ResultSet rs;
	protected static void init(){ conn=null; sqlCommand=null; rs=null; pst=null; }
}
