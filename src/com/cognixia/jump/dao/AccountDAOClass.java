package com.cognixia.jump.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cognixia.jump.model.Account;

public class AccountDAOClass implements AccountDAO<Account> {

	private Connection conn = null;
	
	@Override
	public List<Account> getAllAccounts() {
		
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(
					"select * from accounts"
					);
			rs = pstmt.executeQuery();
			
			List<Account> deptList = new ArrayList<>();
			
			while(rs.next()) {
				Account dept = new Account(
						rs.getInt("accountId"), 
						rs.getDouble("amount"),
						rs.getString("password"), 
						Arrays.asList(rs.getArray("dept_phone")));
				
				deptList.add(dept);
			}
			
			return deptList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
//				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public Account getAccountById(int id) {
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Account retVal = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM accounts WHERE dept_id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			retVal = new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return retVal;
	}


	@Override
	public boolean addAccount(Account account) {
		
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
	
		int query = 0;
		
		try {
			pstmt = conn.prepareStatement(
					"insert into accounts(dept_id, dept_name, dept_phone) "
					+ "values(?, ?, ?)"
					);
			pstmt.setInt(1, 0);
			pstmt.setString(2, account.getName());
			pstmt.setString(3, account.getPhone());
			
			query = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				pstmt.close();
//				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if(query > 0) return true;
		
		return false;
	}

	@Override
	public boolean deleteAccountById(int id) {
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		boolean retVal = false;
		
		try {
			pstmt = conn.prepareStatement("DELETE * FROM accounts WHERE dept_id = ?");
			pstmt.setInt(1, id);
			retVal = pstmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return retVal;
	}

	@Override
	public boolean deleteAccount(Account account) {
		
		String name = account.getName();
		String phone = account.getPhone();
		int id = -1;
		
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean success = false;
		
		try {
			pstmt = conn.prepareStatement(
					"select * from accounts "
					+ "where dept_name = ? and dept_phone = ?"
					);
			
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			
			rs = pstmt.executeQuery();
			rs.first();
			id = rs.getInt("dept_id");
			
			if (id != -1 ) {
				pstmt = conn.prepareStatement(
						"delete from accounts "
						+ "where dept_id = ?"
						);
				pstmt.setInt(1, id);
				success = pstmt.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
//				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return success;
	}

	@Override
	public boolean updateAccount(Account account) {
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		boolean retVal = false;
		
		try {
			pstmt = conn.prepareStatement("UPDATE accounts SET dept_name = ?, dept_phone = ? WHERE dept_id = ?");
			pstmt.setString(1,account.getName());
			pstmt.setString(2,account.getPhone());
			pstmt.setInt(3, account.getId());
			retVal = pstmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return retVal;
	}

}
