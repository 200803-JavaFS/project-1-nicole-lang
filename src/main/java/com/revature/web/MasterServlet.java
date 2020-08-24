package com.revature.web;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MasterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public MasterServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/project1/", "");
		
		//JSON content type
		res.setContentType("application/json");
		//tomcat sends a success code by default if it finds a servlet method so we need to set the status explicitly
		res.setStatus(404);
		String[] portions = URI.split("/");
		System.out.println(Arrays.toString(portions));
		
		//get can retrieve one of the following:
		//1: a single user record of either an employee or a financial manager (for login)
		//2: a list of reimbursement requests submitted by the logged-in employee, 
		//either pending or resolved (separate these into two lists)
		//3: a list of all reimbursement requests (for managers) ordered by submission date
		//4: a single reimbursement record, with the ID columns linked to specific information 
		//(like with PokeAPI)

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//post will be used to add new reimbursement requests
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//put will be used to update a reimbursement's status, resolver, and resolve date
		//status will be set to either 1 (Approved) or 2 (Denied)
	}

}
