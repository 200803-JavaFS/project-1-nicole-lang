package com.revature.service;

import java.util.List;

import com.revature.dao.ReimbDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Reimb;

public class ReimbService implements ReimbDAO, UserDAO{
	
	public Reimb getByID(int id) {
		return ReimbDAO.selectById(id);
	}
	public List<Reimb> getAll(){
		return ReimbDAO.findAll();
	}
	public List<Reimb> getByUser(String userName) {
		
		return ReimbDAO.selectByUserName(userName);
	}

}
