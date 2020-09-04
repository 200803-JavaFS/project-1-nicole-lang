package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.service.LoginService;

public class LoginController {

	private static LoginService ls = new LoginService();
	private static ObjectMapper om = new ObjectMapper();

	public void login(HttpServletRequest req, HttpServletResponse res, String reqBody) throws Exception {
		if(reqBody.contains("username")) {

			LoginDTO l = om.readValue(reqBody, LoginDTO.class);
			// check if user exists and password is correct
			if (ls.getUser(l.username) != null)
			{
				System.out.println("user found");
				if (ls.login(l)) {// success
					HttpSession ses = req.getSession();
					User currentUser = ls.getUser(l.username);
					l.userID = currentUser.getUserID();
					l.typeID = currentUser.getRole().getRoleID();
					ses.setAttribute("user", l);
					ses.setAttribute("loggedin", true);
					res.setStatus(200);
					String json = om.writeValueAsString(l);
					System.out.println(json);
					res.getWriter().println(json);
				} else {
					// wrong password
					System.out.println("wrong password");
					HttpSession ses = req.getSession(false);
					if (ses != null)
						ses.invalidate();
					res.setStatus(403);
				}
					
			}else
				res.setStatus(404);
		}else
			res.setStatus(400);

	}

	public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// dispose of the current session
		HttpSession ses = req.getSession(false);
		if (ses != null) {
			// success
			// get user info to print their username after the session is invalidated
			LoginDTO l = (LoginDTO) ses.getAttribute("user");
			ses.invalidate();
			res.setStatus(200);
			res.getWriter().println(l.username + " has logged out successfully");
		} else {// 400 bad request
			res.setStatus(400);
			res.getWriter().println("You must be logged in to log out");
		}

	}
}
