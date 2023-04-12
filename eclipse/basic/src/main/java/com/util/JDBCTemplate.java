package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTemplate {

	private static JDBCTemplate instance;

	private JDBCTemplate() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static JDBCTemplate getInstance() {
		if (instance == null)
			instance = new JDBCTemplate();
		return instance;
	}

	public Connection getConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl11";
		String user = "hr";
		String password = "tiger";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public void commit(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close(ResultSet rset) {
		try {
			rset.close();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close(ResultSet rset, Statement stmt) {
		close(rset);
		close(stmt);
	}
	public void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(con != null) con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
	public static void main(String args[]) {
		JDBCTemplate jt = JDBCTemplate.getInstance();
		Connection con = jt.getConnection();
		String sql = "SELECT deptno, dname, loc FROM dept";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println(rs.getString("dname"));
			}
			jt.close(rs,stmt);
			jt.close(con);
			String sql2 = "SELECT dname, loc FROM dept";
			rs = stmt.executeQuery(sql2);
			while(rs.next()) {
				System.out.println(rs.getString("loc"));
			}			
		} catch (Exception e) {
			System.out.println("[[Exception]]"+e.toString());
		} finally {
			System.out.println("====================================");
			jt.close(con, stmt, rs);
		
		}
		
		
	}
}
