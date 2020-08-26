package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.revature.models.Reimb;
import com.revature.utils.ConnectionUtil;

public class ReimbDAO {
	
	//retrieve 1 reimbursement record by given ID
	public Reimb getSingleReimb(int id) {
		Reimb r = new Reimb();
		String sql = "Select * from ers_reimbursements where reimb_id = ?;";

		try
		{
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				int i = 0;
				r.setReimbID(result.getInt(++i));
				r.setAmt(result.getDouble(++i));
				r.setSubmittedDate(result.getTimestamp(++i));
				r.setResolvedDate(result.getTimestamp(++i));
				r.setDesc(result.getString(++i));
				r.setAuthorID(result.getInt(++i));
				r.setResolverID(result.getInt(++i));
				r.setStatusID(result.getInt(++i));
				r.setTypeID(result.getInt(++i));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return r;	
	}
	public boolean updateStatus(boolean approve, int reimbID, int resolverID) {
		//set the resolver and resolve date of a reimbursement (approve or deny)
		String sql = "Update ers_reimbursements Set reimb_resolved = ?, reimb_resolver = ?, reimb_status = ? where reimb_id = ?;";

		try
		{
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setTimestamp(1, getTimeStamp());
			statement.setInt(2, resolverID);
			statement.setInt(3,  reimbID);
			if(approve)
				statement.setInt(4, 2);//approved
			else
				statement.setInt(4, 3);//denied
			statement.execute();
			return true;
			
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}	
	}
	//get current timestamp for insertion into new record or newly approved record
		private Timestamp getTimeStamp() {
			return new Timestamp(System.currentTimeMillis());
		}
}
