package com.revature.service;

import com.revature.dao.ReimbDAO;
import com.revature.models.Reimb;

public class ReimbService {

	private static ReimbDAO rd = new ReimbDAO();
	
	public Reimb getByID(int id) {
		return rd.getSingleReimb(id);
	}

}
