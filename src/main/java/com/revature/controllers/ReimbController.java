package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.LoginDTO;
import com.revature.models.Reimb;
import com.revature.service.ReimbService;

public class ReimbController {

	private static ReimbService rs = new ReimbService();
	
	public Reimb getReimb(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Boolean success = false;
		Reimb r = new Reimb();
		HttpSession ses = req.getSession();
		LoginDTO l = (LoginDTO) ses.getAttribute("user");
			try {
				int id = Integer.parseInt(req.getParameter("reimbid"));
				r = rs.getByID(id);
				if(l.type == 0)//Employee
				{
					if(r.getAuthorID() == (l.userID))
						success = true;
				}else if(l.type == 1)
				{
					if(r.getResolverID() == (l.userID) || r.getStatusID() == 0)
						success = true;
				}
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
			if(success)
				return r;
			else
				return null;
		}
}
