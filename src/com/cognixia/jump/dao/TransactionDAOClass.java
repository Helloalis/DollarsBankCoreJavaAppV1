package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
					"select * from transactions"
					);

			rs = pstmt.executeQuery();

			List<Transaction> transList = new ArrayList<>();

			while(rs.next()) {
				Timestamp ts = rs.getTimestamp("timeval");
				Transaction trans = new Transaction(
						new Date(ts.getTime()),
						rs.getDouble("trans_val"),
						rs.getInt("root_id"),
						rs.getInt("dest_id"));


						transList.add(trans);
			}

			return transList;

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
	public List<Transaction> getTransactionsByRoot(int id) {

		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(
					"select * from transactions WHERE root_id = ?"
					);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			List<Transaction> transList = new ArrayList<>();

			while(rs.next()) {
				Timestamp ts = rs.getTimestamp("timeval");
				Transaction trans = new Transaction(
						new Date(ts.getTime()),
						rs.getDouble("trans_val"),
						rs.getInt("root_id"),
						rs.getInt("dest_id"));



						transList.add(trans);
			}

			return transList;

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
			java.sql.Date sqlDate=new java.sql.Date(transaction.getTime().getTime());
			pstmt = conn.prepareStatement(
					"insert into transactions(time_val, trans_val, root_id, dest_id) " + "values(?, ?, ?, ?)");
			pstmt.setTimestamp(1, new java.sql.Timestamp(sqlDate.getTime()));
			pstmt.setDouble(2, transaction.getTransVal());
			pstmt.setInt(3, transaction.getRootId());
			pstmt.setInt(4, transaction.getDest());

			query = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				pstmt.close();
				// conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if (query > 0)
			return true;

		return false;
	}

	@Override
	public boolean deleteTransaction(Transaction transaction) {

		conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean success = false;

		java.sql.Date sqlDate=new java.sql.Date(transaction.getTime().getTime());
		Timestamp dat =  new java.sql.Timestamp(sqlDate.getTime());
		try {

			pstmt = conn.prepareStatement("select * from transactions " + "where timeval = ?");

			pstmt.setTimestamp(1, new java.sql.Timestamp(sqlDate.getTime()));

			rs = pstmt.executeQuery();
			rs.first();
			dat = rs.getTimestamp("date");

			if (dat.equals(new java.sql.Timestamp(sqlDate.getTime()))) {
				pstmt = conn.prepareStatement("delete from transactions " + "where timeval = ?");

				pstmt.setTimestamp(1, new java.sql.Timestamp(sqlDate.getTime()));
				success = pstmt.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				// conn.close();
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

			java.sql.Date sqlDate=new java.sql.Date(transaction.getTime().getTime());
			pstmt = conn.prepareStatement("UPDATE transactions SET trans_val = ?, root_id = ?, dest_id ? WHERE date = ?");

			pstmt.setTimestamp(4, new java.sql.Timestamp(sqlDate.getTime()));
			pstmt.setDouble(1, transaction.getTransVal());
			pstmt.setInt(2, transaction.getRootId());
			pstmt.setInt(3, transaction.getDest());


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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