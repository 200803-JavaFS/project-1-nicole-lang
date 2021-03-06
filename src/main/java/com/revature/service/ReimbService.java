package com.revature.service;

import java.util.List;

import com.revature.dao.ReimbDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Reimb;
import com.revature.models.ReimbDTO;

public class ReimbService implements ReimbDAO, UserDAO{
	
	//service class for reimbursement handling
	public Reimb getByID(int id) {
		return ReimbDAO.selectById(id);
	}
	public List<Reimb> getAll(){
		return ReimbDAO.findAll();
	}
	public List<Reimb> getByUser(String userName) {
		
		return ReimbDAO.selectByUserName(userName);
	}
	public boolean addReimb(ReimbDTO request) {
		return ReimbDAO.insert(request);
	}
	public boolean updateStatus(ReimbDTO r) {
		return ReimbDAO.update(r);
	}
}
