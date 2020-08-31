package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginDTO;
import com.revature.service.LoginService;

public class LoginController {

	private static LoginService ls = new LoginService();
	private static ObjectMapper om = new ObjectMapper();

	public void login(HttpServletRequest req, HttpServletResponse res) throws Exception {

		BufferedReader reader = req.getReader();
		StringBuilder builder = new StringBuilder();
		// read the request body
		String line = reader.readLine();
		while (line != null) {
			builder.append(line);
			line = reader.readLine();
		}
		String body = builder.toString();
		System.out.println(body);
		LoginDTO l = om.readValue(body, LoginDTO.class);

		// check if user exists and password is correct

		if (ls.login(l)) {// success
			HttpSession ses = req.getSession();
			ses.setAttribute("user", l);
			ses.setAttribute("loggedin", true);
			ses.setAttribute("user_role", ls.getUser(l.userName).getRole());
			res.setStatus(200);
			res.getWriter().println("Login Successful");
		} else {
			// failure
			HttpSession ses = req.getSession(false);
			if (ses != null)
				ses.invalidate();
			res.setStatus(401);
			res.getWriter().println("Login failed");
		}
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
			res.getWriter().println(l.userName + " has logged out successfully");
		} else {// 400 bad request
			res.setStatus(400);
			res.getWriter().println("You must be logged in to log out");
		}

	}
}
