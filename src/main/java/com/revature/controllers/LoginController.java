package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginDTO;
import com.revature.service.LoginService;

public class LoginController {
	
	private static LoginService ls = new LoginService();
	private static ObjectMapper om = new ObjectMapper();
	
	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException{

		if(req.getMethod().equals("POST")) {

			BufferedReader reader = req.getReader();
			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();
			
			while(line != null)
			{
				builder.append(line);
				line = reader.readLine();
			}
			String body = builder.toString();
			LoginDTO l = om.readValue(body, LoginDTO.class);

			if(ls.login(l))
			{
				HttpSession ses = req.getSession();
				ses.setAttribute("user", l);
				ses.setAttribute("loggedin", true);
				res.setStatus(200);
				res.getWriter().println("Login Successful");
			}else {
				HttpSession ses = req.getSession(false);
				if(ses != null)
					ses.invalidate();
				res.setStatus(401);
				res.getWriter().println("Login failed");
			}
		}
	}
	public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException{
		HttpSession ses = req.getSession(false);
		if(ses != null) {
			LoginDTO l = (LoginDTO)ses.getAttribute("user");
			ses.invalidate();
			res.setStatus(200);
			res.getWriter().println(l.userName + " has logged out successfully");
		}else
		{
			res.setStatus(400);
			res.getWriter().println("You must be logged in to log out");
		}
		
	}
}
