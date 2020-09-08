package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;
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

	//controller class for retrieving and adding reimbursements
	
	private static ReimbService rs = new ReimbService();
	private static ObjectMapper om = new ObjectMapper();

	public void getReimb(int id, HttpServletRequest req, HttpServletResponse res) throws IOException{
		Boolean success = false;
		HttpSession ses = req.getSession();
		LoginDTO l = (LoginDTO) ses.getAttribute("user");

		Reimb r = rs.getByID(id);
		ReimbDTO rDTO = new ReimbDTO();
		
		// check user type to determine whether they have access to this record
		if (l.typeID == 1)// Employee
		{
			if (r.getAuthor().getUserID() == (l.userID))
				success = true;
		} else if (l.typeID == 2)// Manager
			success = true;
		if (success) {
			//populate ReimbDTO instance for writing to json
			rDTO.amt = r.getAmt();
			rDTO.author = r.getAuthor().getUserName();
			rDTO.desc = r.getDesc();
			rDTO.reimbId = id;
			try{
				rDTO.resolver = r.getResolver().getUserName();
				rDTO.resolvedDate = r.getResolvedDate().toString();
			}catch(NullPointerException e) {
				rDTO.resolver = null;
				rDTO.resolvedDate = null;
			}
			rDTO.submittedDate = r.getSubmittedDate().toString();
			rDTO.statusId = r.getStatus().getId();
			rDTO.typeId = r.getType().getTypeID();
			String json = om.writeValueAsString(rDTO);
			res.setStatus(200);
			res.getWriter().println(json);
		}else
			res.setStatus(403); //access denied
	}
	public void listRecords(HttpServletRequest req, HttpServletResponse res) throws Exception{
		List<Reimb> result;
		List<ReimbDTO> finalResult = new ArrayList<>();
		ReimbDTO rDTO;
		HttpSession ses = req.getSession();
		LoginDTO l = (LoginDTO) ses.getAttribute("user");
		System.out.println("User type: " + l.typeID);
		switch(l.typeID)
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
			for(Reimb r : result) {
				//write ReimbDTO entries for each reimbursement returned
				rDTO = new ReimbDTO();
				rDTO.reimbId = r.getReimbID();
				rDTO.amt = r.getAmt();
				rDTO.author = r.getAuthor().getUserName();
				rDTO.desc = r.getDesc();
				try{
					rDTO.resolver = r.getResolver().getUserName();
					rDTO.resolvedDate = r.getResolvedDate().toString();
				}catch(NullPointerException e) {
					rDTO.resolver = null;
					rDTO.resolvedDate = null;
				}
				rDTO.submittedDate = r.getSubmittedDate().toString();
				rDTO.statusId = r.getStatus().getId();
				rDTO.typeId = r.getType().getTypeID();
				finalResult.add(rDTO);
			}
			//write final result as json
			String json = om.writeValueAsString(finalResult);
			System.out.println(json);
			if(finalResult.isEmpty())
				res.setStatus(204); //no content
			else {
				res.setStatus(200);
				res.getWriter().println(json);
			}
				
	}
	public boolean addReimb(String body) throws IOException{
		ReimbDTO r = om.readValue(body, ReimbDTO.class);
		return rs.addReimb(r); 
		
	}
	public boolean updateReimb(String body) throws IOException{
		ReimbDTO r = om.readValue(body, ReimbDTO.class);
		return rs.updateStatus(r);
	}
}
