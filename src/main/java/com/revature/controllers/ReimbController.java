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

	public Reimb getReimb(String[] portions, HttpServletRequest req, HttpServletResponse res) throws IOException{
		Boolean success = false;
		Reimb r = new Reimb();
		HttpSession ses = req.getSession();
		LoginDTO l = (LoginDTO) ses.getAttribute("user");
		if(portions.length == 2)
		{
			int reimbID = Integer.parseInt(portions[1]);
			try {

				r = rs.getByID(reimbID);
				//check user type to determine whether they have access to this record
				if(l.type == 0)//Employee
				{
					if(r.getAuthor().getUserID() == (l.userID))
						success = true;
				}else if(l.type == 1)//Manager
					success = true;
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
			if(success)
				return r;
			else
				return null;

		}
		return r;
	}
}
