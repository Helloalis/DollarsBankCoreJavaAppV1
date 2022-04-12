package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.cognixia.jump.model.Transaction;

public class TransactionDAOClass implements TransactionDAO<Transaction> {

	private Connection conn = null;
	
	@Override
	public List<Transaction> getAllTransactions() {
		
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(
					"select * from transaction"
					);
			rs = pstmt.executeQuery();
			
			List<Transaction> deptList = new ArrayList<>();
			
			while(rs.next()) {
				Transaction dept = new Transaction(
						rs.getInt("dept_id"), 
						rs.getString("dept_name"), 
						rs.getString("dept_phone"));
				
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
	public boolean addTransaction(Transaction transaction) {
		
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
	
		int query = 0;
		
		try {
			Instant instant = transaction.getTime().atZone(ZoneId.systemDefault()).toInstant();
			Date date = Date.from(instant);
			pstmt = conn.prepareStatement(
					"insert into transactions(date, dept_name, dept_phone) "
					+ "values(?, ?, ?)"
					);
			pstmt.setDate(1, new java.sql.Date(date.getTime()));
			pstmt.set
			
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
	public boolean deleteTransactionById(int id) {
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		boolean retVal = false;
		
		try {
			pstmt = conn.prepareStatement("DELETE * FROM transactions WHERE dept_id = ?");
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
	public boolean deleteTransaction(Transaction transaction) {
		
		String name = transaction.getName();
		String phone = transaction.getPhone();
		int id = -1;
		
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean success = false;
		
		try {
			pstmt = conn.prepareStatement(
					"select * from transaction "
					+ "where dept_name = ? and dept_phone = ?"
					);
			
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			
			rs = pstmt.executeQuery();
			rs.first();
			id = rs.getInt("dept_id");
			
			if (id != -1 ) {
				pstmt = conn.prepareStatement(
						"delete from transaction "
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
	public boolean updateTransaction(Transaction transaction) {
		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		boolean retVal = false;
		
		try {
			pstmt = conn.prepareStatement("UPDATE transaction SET dept_name = ?, dept_phone = ? WHERE dept_id = ?");
			pstmt.setString(1,transaction.getName());
			pstmt.setString(2,transaction.getPhone());
			pstmt.setInt(3, transaction.getId());
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
