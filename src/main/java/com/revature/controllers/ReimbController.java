package com.revature.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.LoginDTO;
import com.revature.models.Reimb;
import com.revature.service.ReimbService;

public class ReimbController {

	private static ReimbService rs = new ReimbService();

	public Reimb getReimb(int id, HttpServletRequest req) {
		Boolean success = false;
		HttpSession ses = req.getSession();
		LoginDTO l = (LoginDTO) ses.getAttribute("user");

		Reimb r = rs.getByID(id);
		// check user type to determine whether they have access to this record
		if (l.type == 0)// Employee
		{
			if (r.getAuthor().getUserID() == (l.userID))
				success = true;
		} else if (l.type == 1)// Manager
			success = true;
		if (success)
			return r;
		else
			return null;
	}
	public List<Reimb> listRecords(HttpServletRequest req){
		List<Reimb> result;
		HttpSession ses = req.getSession();
		LoginDTO l = (LoginDTO) ses.getAttribute("user");
		switch(ses.getAttribute("user_role").toString())
		{
		case "2":
			result = rs.getAll();
			break;
		case "1":
			result = rs.getByUser(l.userName);
			break;
		default:
			result = null;
		}
		return result;
	}
}
