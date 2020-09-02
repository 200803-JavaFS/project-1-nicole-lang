package com.revature.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.UserDAO;
import com.revature.models.LoginDTO;
import com.revature.models.Reimb;
import com.revature.models.ReimbDTO;
import com.revature.service.ReimbService;

public class ReimbController {

	private static ReimbService rs = new ReimbService();
	private static ObjectMapper om = new ObjectMapper();

	public void getReimb(int id, HttpServletRequest req, HttpServletResponse res) throws Exception{
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
		if (success) {
			String json = om.writeValueAsString(r);
			res.getWriter().println(json);
		}
	}
	public void listRecords(HttpServletRequest req, HttpServletResponse res) throws Exception{
		List<Reimb> result;
		HttpSession ses = req.getSession();
		LoginDTO l = (LoginDTO) ses.getAttribute("user");
		int roleID = Integer.parseInt(ses.getAttribute("user_role_id").toString());
		System.out.println("Role ID = "+ roleID);
		switch(roleID)
		{
		case 2: //Manager
			result = rs.getAll();
			break;
		case 1: //Employee
			result = rs.getByUser(l.username);
			break;
		default:
			result = null;
		}
		if(result != null) {
			String json = om.writeValueAsString(result);
			res.getWriter().println(json);
		}
	}
	public boolean addReimb(HttpServletRequest req, HttpServletResponse res, String body) throws IOException{
		ReimbDTO r = om.readValue(body, ReimbDTO.class);
		HttpSession ses = req.getSession();

		LoginDTO l = (LoginDTO) ses.getAttribute("user");

		return rs.addReimb(r); 
		
	}
	public boolean updateReimb(HttpServletRequest req, HttpServletResponse res, String body) throws IOException{
		ReimbDTO r = om.readValue(body, ReimbDTO.class);
		HttpSession ses = req.getSession();

		LoginDTO l = (LoginDTO) ses.getAttribute("user");
		r.resolver = UserDAO.selectByUsername(l.username);
		return rs.updateStatus(r);
	}
}
