

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Controller")
public class Controller extends HttpServlet {	
	private Database db=new Database();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession s=request.getSession();
		s.setAttribute("managerPower",-1);
		String url=request.getHeader("referer");
		String ref=url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".jsp"));		
		if(ref.equals("RegularClosing")) {
			if(request.getParameter("info").equals("Pending")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(null,(int)s.getAttribute("managerPower")));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			} else if(request.getParameter("info").equals("Approved")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(1,(int)s.getAttribute("managerPower")));
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
				s.setAttribute("claims",db.getClaims(null,(int)s.getAttribute("managerPower")));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			} else if(request.getParameter("info").equals("Approved")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(1,(int)s.getAttribute("managerPower")));
				db.destroyConnection();
				response.sendRedirect("PolicyClosing.jsp");
			} else if(request.getParameter("info").equals("Rejected")){
				db.createConnection();
				s.setAttribute("claims",db.getClaims(0,(int)s.getAttribute("managerPower")));
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
				s.setAttribute("id", 1);
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

		// module for inserting reason for rejection into PolicyMap Table 
 		// created by Chin Han Chen on 2018/08/16
 		if(/*Condition where leads you to submit claim*/) {
 			if(Validation.checkInjection(request.getParameter("Rejection"))) {
 				DB.createConnection();
 				DB.inputRejectionReason(/*Confirm the PolicyMap ID*/,request.getParameter("Rejection"));
 				DB.inputRejectionStatus(/*Confirm the PolicyMap ID*/,request.getParameter("Rejection"));
 				DB.destroyConnection();
 			}else {
 				request.setAttribute("fail", "fail");
 				RequestDispatcher dis1 = request.getRequestDispatcher("/ClaimRejection.jsp");
 				dis1.include(request, response);
 			}
 		}
 		
 		// module for confirming claim person and policy owner are the same
 		// created by Chin Han Chen on 2018/08/16
 		if(/*Condition where leads you to confirming Claim Person and Policy Owner*/) {
 			DB.createConnection();
 			if(DB.checkOwner(request.getParameter("cusID"),/*request policy map id*/)) {
 				DB.destroyConnection();
 				/*forward to whatever place comes after successful validation...*/
 			}else {
 				DB.destroyConnection();
 				request.setAttribute("fail", "fail");
 				RequestDispatcher dis2 = request.getRequestDispatcher("/CheckClaimer.jsp");
 				dis2.include(request, response);
 			}
 		}
		
	}
}