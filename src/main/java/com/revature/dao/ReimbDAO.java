package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Reimb;
import com.revature.utils.ConnectionUtil;

public class ReimbDAO {
	public Reimb getSingleReimb(int id) {
		Reimb r = new Reimb();
		String sql = "Select * from ers_reimbursements where reimb_id = ?;";

		try
		{
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(0, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				//finish this
				r.setReimbID(result.getInt(0));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return r;	
	}
}
