package com.revature.web;


import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.controllers.LoginController;
import com.revature.controllers.ReimbController;

public class MasterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static LoginController lc;
	private static ReimbController rc;
	public MasterServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/project1/", "");

		// example URI = avenger/1 to get avenger with ID 1 

		String[] portions = URI.split("/");

		System.out.println(Arrays.toString(portions));

		//get can retrieve one of the following:
		//1: a list of reimbursement requests submitted by the logged-in employee, 
		//either pending or resolved (separate these into two lists)
		//2: a list of all reimbursement requests (for managers) ordered by submission date
		//3: a single reimbursement record, with the ID columns linked to specific information 
		//(like with PokeAPI)

		if(portions[0].equals("reimb") && req.getMethod().equals("GET"))
		{
			if (req.getSession(false) != null && (boolean) req.getSession().getAttribute("loggedin")) {
				if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					//"/reimb/#"
					//get reimbursement if user is logged in and has access to the reimbursement

				} else if (portions.length == 1) {
					//"/reimb"
					//list reimbursements (handle reimbursement type using query parameter (reimb?status=0)
					//only list reimbursements involving the current user (get type from session attribute user)
				}
			}
		} else {
			res.setStatus(403);
			res.getWriter().println("You must be logged in to do that!");
		}


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/project1/", "");
		String[] portions = URI.split("/");
		//post will be used for 
		//1: login
		//2: adding new reimbursement requests

		//JSON content type
		res.setContentType("application/json");
		//tomcat sends a success code by default if it finds a servlet method so we need to set the status explicitly
		res.setStatus(404);
		switch(portions[0])
		{
		case "login":
			lc.login(req, res);
			break;
		case "logout":
			lc.logout(req, res);
			break;
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//put will be used to update a reimbursement's status, resolver, and resolve date
		//status will be set to either 1 (Approved) or 2 (Denied)
	}

}
