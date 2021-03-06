package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.controllers.LoginController;
import com.revature.controllers.ReimbController;

public class MasterServlet extends HttpServlet {
	
	//master servlet class; handles all HTTP requests from the client
	
	private static final long serialVersionUID = 1L;
	private static LoginController lc = new LoginController();
	private static ReimbController rc = new ReimbController();

	public MasterServlet() {
		//call HTTPServlet constructor
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/project1/", "");
		// JSON content type
		res.setContentType("application/json");
		// tomcat sends a success code by default if it finds a servlet method so we
		// need to set the status explicitly
		res.setStatus(404);

		String[] portions = URI.split("/");

		System.out.println(Arrays.toString(portions));

		// get can retrieve one of the following:
		// 1: a list of reimbursement requests submitted by the logged-in employee
		// 2: a list of all reimbursement requests (for managers) ordered by status (pending first)
		// 3: a single reimbursement record (reimb/#), for update form

		if (portions[0].equals("reimb")) {
			if (req.getSession(false) != null) {
				if (portions.length == 2)
					try {//get single reimb
						rc.getReimb(Integer.parseInt(portions[1]), req, res);
					} catch (Exception e) {
						e.printStackTrace();
					}
				else {
					try {//get list of reimbs
						rc.listRecords(req, res);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
			} else {
				res.setStatus(403);
				res.getWriter().println("You must be logged in to do that!");
			}
		} else {
			res.setStatus(400);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/project1/", "");
		System.out.println(URI);

		// JSON content type
		res.setContentType("application/json");

		BufferedReader reader = req.getReader();

		StringBuilder sb = new StringBuilder();

		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		String body = new String(sb);
		// post is used for
		// 1: login/logout
		// 2: adding new reimbursement requests
		// tomcat sends a success code by default if it finds a servlet method so we
		// need to set the status explicitly
		res.setStatus(404);
		System.out.println(body);
		
		switch (URI) {
		case "login":
			try {
				lc.login(req, res, body);					
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "reimb":
			if (rc.addReimb(body)) {
				res.setStatus(201);
				res.getWriter().println("Reimbursement request submitted successfully.");
			}else
			{
				res.setStatus(401);
				res.getWriter().println("Failed to submit request");
			}
			break;
		case "logout":
			lc.logout(req, res);
			break;
		default:
			res.setStatus(400);
			res.getWriter().println("Invalid post request");
			break;
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// put is used to update a reimbursement's status, resolver, and resolve date
		// status will be set to either 2 (Approved) or 3 (Denied)
		final String URI = req.getRequestURI().replace("/project1/", "");
		System.out.println(URI);

		// JSON content type
		res.setContentType("application/json");

		String body = req.getReader().readLine();
		if(URI.equals("reimb")) {
			if(rc.updateReimb(body)) {
				res.setStatus(202);
			}else
				res.setStatus(401);
		}else
			res.setStatus(400);
	}

}
