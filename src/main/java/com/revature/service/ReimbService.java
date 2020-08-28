package com.revature.service;

import java.util.List;

import com.revature.dao.ReimbDAO;
import com.revature.models.Reimb;

public class ReimbService {

	private static ReimbDAO rd = new ReimbDAO();
	
	public Reimb getByID(int id) {
		return rd.selectById(id);
	}
	public List<Reimb> getAll(){
		return rd.findAll();
	}

}
