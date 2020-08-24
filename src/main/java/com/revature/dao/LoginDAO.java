package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class LoginDAO {
	
	public User getUser(LoginDTO l){
		User u = new User();
		String sql = "Select * from ers_users where username = ?, password = ?;";
		try
		{
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(0, l.userName);
			statement.setString(1, l.password);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				//finish this
				u.setUserID(result.getInt(0));
				u.setRoleID(result.getInt(6));
				u.setUserName(result.getString(1));
				u.setPassword(result.getString(2));
				u.setFirstName(result.getString(3));
				u.setLastName(result.getString(4));
				u.setEmail(result.getString(5));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return u;
	}
}
