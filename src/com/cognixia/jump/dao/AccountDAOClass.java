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
import com.cognixia.jump.model.Transaction;

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
			
			
			
			
			ArrayList<Account> accList = new ArrayList<>();
			
			while(rs.next()) {
				ArrayList<Transaction> transList = new ArrayList<>();
			
				PreparedStatement psRs = conn.prepareStatement("select * from transactions WHERE root_id = ? ORDER BY timeval DESC"
						);
				psRs.setInt(1, rs.getInt("accountId"));
				ResultSet trs = null;
				trs = psRs.executeQuery();
				int i = 0;
				while(trs.next() && i < 5) {
					Transaction trans = new Transaction(
							rs.getDate("date"),
							rs.getDouble("trans_val"),
							rs.getInt("root_id"), 
							rs.getInt("dest"));
					transList.add(trans);
					i++;
				}
				Account acc = new Account(
						rs.getInt("accountId"), 
						rs.getDouble("amount"),
						rs.getString("password"));
				acc.setTrans(transList);
				
				accList.add(acc);
			}
			
			return accList;
			
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
			pstmt = conn.prepareStatement("SELECT * FROM accounts WHERE accountId = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			ArrayList<Transaction> transList = new ArrayList<>();
			
			PreparedStatement psRs = conn.prepareStatement("select * from transactions WHERE root_id = ? ORDER BY timeval DESC"
					);
			psRs.setInt(1, rs.getInt("accountId"));
			ResultSet trs = null;
			trs = psRs.executeQuery();
			int i = 0;
			while(trs.next() && i < 5) {
				Transaction trans = new Transaction(
						rs.getDate("date"),
						rs.getDouble("trans_val"),
						rs.getInt("root_id"), 
						rs.getInt("dest"));
				transList.add(trans);
				i++;
			}
			
			Account acc = new Account(
					rs.getInt("accountId"), 
					rs.getDouble("amount"),
					rs.getString("password"));
			acc.setTrans(transList);
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
					"insert into accounts(accountid, amount, password) "
					+ "values(?, ?, ?)"
					);
			pstmt.setInt(1, 0);
			pstmt.setDouble(2, account.getAmount());
			pstmt.setString(3, account.getPassword());
			query = pstmt.executeUpdate();
			
			ArrayList<Transaction> trans = account.getTrans();
			
			for(int i = 0; i < trans.size(); i ++) {
				Transaction transaction = trans.get(i);
				java.sql.Date sqlDate=new java.sql.Date(transaction.getTime().getTime());
				pstmt = conn.prepareStatement(
						"insert into transactions(time_val, trans_val, root_id, dest_id) " + "values(?, ?, ?, ?)");
				pstmt.setTimestamp(1, new java.sql.Timestamp(sqlDate.getTime()));
				pstmt.setDouble(2, transaction.getTransVal());
				pstmt.setInt(3, transaction.getRootId());
				pstmt.setInt(4, transaction.getDest());

				query = pstmt.executeUpdate();
			}

			
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
			pstmt = conn.prepareStatement("DELETE * FROM accounts WHERE accountId = ?");
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
