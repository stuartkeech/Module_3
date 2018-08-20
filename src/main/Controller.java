package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import main.Validation;

@WebServlet("/Controller")
public class Controller extends HttpServlet {	
	private Database db=new Database();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession s=request.getSession();
		if(s.getAttribute("id")==null)s.setAttribute("id",1);
		db.createConnection();
		s.setAttribute("managerPower",db.getManPower((int)s.getAttribute("id")));
		db.destroyConnection();
		String url=request.getHeader("referer");
		String ref=url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".jsp"));	
		if(ref.equals("initiateClaim")) {
			int polMid = Integer.parseInt(db.getPolicyMapId(request.getParameter("policyname"),s.getAttribute("id").toString()));
			int manid = -1;
			if(request.getParameter("claimReason").equals("policyholderDeath")) {
				// success
	 			db.createConnection();
	 			if(Validation.checkImage(request.getParameter("request for death certificate form name"))) {
	 				Part filePart = request.getPart("image");
	 				db.inputData(polMid,new java.util.Date(),manid, request.getParameter("claim type name"), null, filePart);
	 				// request.setAttribute("name", "value");
	 				request.getRequestDispatcher("success input webpage").forward(request, response);
	 			}else {
	 				// request.setAttribute("name", "value");
	 				request.getRequestDispatcher("failure input return to claim jsp").forward(request, response);
	 			}
	 			db.destroyConnection();
			}else if(request.getParameter("claimReason").equals("maturedPolicy")) {
				// success
	 			db.createConnection();
	 			if(db.checkDate("request for Policy Map ID ")) {
	 				db.inputData(polMid,new java.util.Date(),manid, request.getParameter("claim type name"), null, null);
	 				// request.setAttribute("name", "value");
	 				request.getRequestDispatcher("success input webpage").forward(request, response);
	 			}else {
	 				// request.setAttribute("name", "value");
	 				request.getRequestDispatcher("failure input return to claim jsp").forward(request, response);
	 			}
	 			db.destroyConnection();
			}else if(request.getParameter("claimReason").equals("intermittentClaim")) {
				// success
	 			db.createConnection();
	 			if(Validation.checkInjection("request for textArea form name")) {
	 				db.inputData(polMid,new java.util.Date(),manid, request.getParameter("claim type name"), request.getParameter("textArea name"), null);
	 				// request.setAttribute("name", "value");
	 				request.getRequestDispatcher("success input webpage").forward(request, response);
	 			}else {
	 				// request.setAttribute("name", "value");
	 				request.getRequestDispatcher("failure input return to claim jsp").forward(request, response);
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
