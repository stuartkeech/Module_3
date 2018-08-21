package main;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import main.Validation;

@MultipartConfig
@WebServlet("/Controller")
public class Controller extends HttpServlet {	
	private Database db=new Database();
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession s=request.getSession();
		if(s.getAttribute("id")==null)s.setAttribute("id",1);
		db.createConnection();
		s.setAttribute("managerPower",db.getManPower((int)s.getAttribute("id")));
		db.destroyConnection();
		String url=request.getHeader("referer");
		String ref=url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".jsp"));
		
		
		// is request comes from the initiate claim page
		if(ref.equals("initiateClaim")) {
			// The block that auto updates the sites info
			/*
			String userName = request.getParameter("policyName");
			if(userName == null || "".equals(userName)) {
				response.setContentType("text/plain");
				response.getWriter().write("");
			}else {
				response.setContentType("text/plain");
				response.getWriter().write(userName);
			}
			*/
			db.createConnection();
			int polMid = db.getPolicyMapId(request.getParameter("policyName"),s.getAttribute("id").toString());
			db.destroyConnection();
			String manid = null;
			String c_reason = request.getParameter("claimReason");
			
			// if the reason for claim is death of policy holder
			if(c_reason.equals("policyholderDeath")) {
				// success
				Part filePart = request.getPart("deathcert");
	 			db.createConnection();
	 			if(Validation.checkImage(filePart.getInputStream())) {
	 				db.inputData(polMid,new java.util.Date(),manid, c_reason, null, filePart);
	 				response.sendRedirect("Home.jsp");
	 			}else {
	 				request.getRequestDispatcher("initiateClaim.jsp").forward(request, response);
	 			}
	 			db.destroyConnection();
	 			
	 		// if the claim reason is maturing of policy
			}else if(c_reason.equals("maturedPolicy")) {
				// success
	 			db.createConnection();
	 			if(db.checkDate(Integer.toString(polMid))) {
	 				db.inputData(polMid,new java.util.Date(),manid, c_reason, null, null);
	 				response.sendRedirect("Home.jsp");
	 			}else {
	 				//request.getRequestDispatcher("/initiateClaim.jsp").forward(request, response);
	 				response.sendRedirect("initiateClaim.jsp");
	 			}
	 			db.destroyConnection();
	 			
	 		// if the claim reason is intermitten claims
			}else if(c_reason.equals("intermittentClaim")) {
				// success
	 			db.createConnection();
	 			if(Validation.checkInjection(request.getParameter("interreason"))) {
	 				db.inputData(polMid,new java.util.Date(),manid, c_reason, request.getParameter("interreason"), null);
	 				response.sendRedirect("Home.jsp");
	 			}else {
	 				// request.setAttribute("name", "value");
	 				request.getRequestDispatcher("initiateClaim.jsp").forward(request, response);
	 			}
	 			db.destroyConnection();
			}
		}else if(ref.equals("RegularClosing")) {
			if(request.getParameter("info").equals("Pending")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(null,(double)s.getAttribute("managerPower")));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			} else if(request.getParameter("info").equals("Approved")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(1,-1));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			} else if(request.getParameter("info").equals("Rejected")){
				db.createConnection();
				s.setAttribute("claims",db.getClaims(0,-1));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			}
		}else if(ref.equals("IntermitentClosing")) {
			if(request.getParameter("info").equals("Pending")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(null,(double)s.getAttribute("managerPower")));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			} else if(request.getParameter("info").equals("Approved")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(1,-1));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			} else if(request.getParameter("info").equals("Rejected")){
				db.createConnection();
				s.setAttribute("claims",db.getClaims(0,-1));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			}
		}else if(ref.equals("PolicyAproval")) {
			if(request.getParameter("info").equals("Pending")) {
				db.createConnection();
				s.setAttribute("policies",db.getPolicies(null));
				db.destroyConnection();
				response.sendRedirect("PolicyReview.jsp");
			} else if(request.getParameter("info").equals("Approved")) {
				db.createConnection();
				s.setAttribute("policies",db.getPolicies(1));
				db.destroyConnection();
				response.sendRedirect("PolicyReview.jsp");
			} else if(request.getParameter("info").equals("Rejected")){
				db.createConnection();
				s.setAttribute("policies",db.getPolicies(0));
				db.destroyConnection();
				response.sendRedirect("PolicyReview.jsp");
			}
		}else if(ref.equals("Login")) {
			String button = request.getParameter("loginButton");			
			if(button.equals("Login as Policy Holder")) {
				s.setAttribute("role", "policyHolder");
				s.setAttribute("id", 2);
				try {
					db.createConnection();
					s.setAttribute("policies", db.getPolicyId(s.getAttribute("id").toString()));
					db.destroyConnection();
				}catch(Exception e) {
					s.setAttribute("policies", null);
				}
				response.sendRedirect("Home.jsp");				
			}else if(button.equals("Login as Manager")) {
				s.setAttribute("role", "manager");
				s.setAttribute("id", 1);
				response.sendRedirect("Home.jsp");				
			}else if(button.equals("Login as Admin")) {
				//Redirect the user to AdminManagerNavigation
				s.setAttribute("role", "admin");
				s.setAttribute("id", 1);
				response.sendRedirect("Home.jsp");
			}
		}
	}
}
