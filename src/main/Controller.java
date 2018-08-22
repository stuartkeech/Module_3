package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.Part;
@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {	
	private Database db=new Database();
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// The block that auto updates the sites info
		HttpSession s=request.getSession();
		String userName = request.getParameter("CustomerID");
		if(userName == null || !"True".equals(userName)) {
			response.setContentType("text/plain");
			response.getWriter().write("");
		}else {
			response.setContentType("application/Json");
			db.createConnection();
			response.getWriter().write(db.getInfoJson(s.getAttribute("id").toString()));
			db.destroyConnection();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST");
		HttpSession s=request.getSession();

		if(s.getAttribute("id")==null)s.setAttribute("id",1);
		db.createConnection();
		s.setAttribute("managerPower",db.getManPower((int)s.getAttribute("id")));
		db.destroyConnection();
		String url=request.getHeader("referer");
		String ref;
		if(url.contains("jsp"))ref=url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".jsp"));
		else ref="Login";
		System.out.println(ref);
		String from = request.getParameter("hidden");
		System.out.println(" from: "+from);
		
		if(ref.equals("RegularClosing")) {
			if(request.getParameter("info").equals("Pending")) {
				System.out.println(s.getAttribute("managerPower"));
				db.createConnection();
				s.setAttribute("claims",db.getClaims(null,(double)s.getAttribute("managerPower")));
				db.destroyConnection();
				response.sendRedirect("PendingClaims.jsp");
			} else if(request.getParameter("info").equals("Approved")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(1,-1));
				db.destroyConnection();
				response.sendRedirect("NonPendingClaims.jsp");
			} else if(request.getParameter("info").equals("Rejected")){
				db.createConnection();
				s.setAttribute("claims",db.getClaims(0,-1));
				db.destroyConnection();
				response.sendRedirect("NonPendingClaims.jsp");
			}
		}else if(ref.equals("IntermittentClosing")) {
			if(request.getParameter("info").equals("Pending")) {
				db.createConnection();
				s.setAttribute("claims",db.getClaims(null,(double)s.getAttribute("managerPower")));
				db.destroyConnection();
				response.sendRedirect("PendingClaims.jsp");
			} else if(request.getParameter("info").equals("Approved")) {
				db.createConnection();
				String[][] claimInfo=db.getClaims(1,-1);
				int[] claimID=new int[claimInfo.length];
				s.setAttribute("claims",claimInfo);
				for(int i=0;i<claimInfo.length;i++) {
					claimID[i]=Integer.parseInt(claimInfo[i][3]);
				}
				s.setAttribute("EA",db.getEligibleAmmount(claimID));
				db.destroyConnection();
				response.sendRedirect("IntermittentApprovedClaims.jsp");
			} else if(request.getParameter("info").equals("Rejected")){
				db.createConnection();
				s.setAttribute("claims",db.getClaims(0,-1));
				db.destroyConnection();
				response.sendRedirect("NonPendingClaims.jsp");
			}
		}else if(ref.equals("PolicyApproval")) {
			if(request.getParameter("info").equals("Pending")) {
				db.createConnection();
				s.setAttribute("policies",db.getPolicies(null));
				db.destroyConnection();
				response.sendRedirect("PendingPolicies.jsp");
			} else if(request.getParameter("info").equals("Approved")) {
				db.createConnection();
				s.setAttribute("policies",db.getPolicies(1));
				db.destroyConnection();
				response.sendRedirect("NonPendingPolicies.jsp");
			} else if(request.getParameter("info").equals("Rejected")){
				db.createConnection();
				s.setAttribute("policies",db.getPolicies(0));
				db.destroyConnection();
				response.sendRedirect("NonPendingPolicies.jsp");
			}
		}else if(ref.equals("Login")) {
			String button = request.getParameter("loginButton");			
			if(button.equals("Login as Policy Holder")) {
				s.setAttribute("role", "policyHolder");
				s.setAttribute("id", 1);
				response.sendRedirect("Home.jsp");				
			}else if(button.equals("Login as Manager")) {
				s.setAttribute("role", "manager");
				s.setAttribute("id", 3);
				response.sendRedirect("Home.jsp");				
			}else if(button.equals("Login as Admin")) {
				//Redirect the user to AdminManagerNavigation
				s.setAttribute("role", "admin");
				s.setAttribute("id", 1);
				response.sendRedirect("Home.jsp");
			}
		}else if(ref.equals("PendingPolicies")) {
			db.createConnection();
			int policyID=Integer.parseInt(request.getParameter("info"));
			s.setAttribute("specificPolicyMap", db.getPolicyMapByPolicyMapId(policyID));
			PolicyMap specificPolicyMap = db.getPolicyMapByPolicyMapId(policyID);

			s.setAttribute("specificPolicy", db.getPolicyByPolicyId(specificPolicyMap.getPolicyId()));
			s.setAttribute("specificCustomer", db.getCustomerByCustomerId(specificPolicyMap.getCustomerId()));
			db.destroyConnection();

			response.sendRedirect("Policy.jsp");
		}
		else if(from!=null &&from.equals("Policy")) {
			String inputValue = request.getParameter("policyAction");
			PolicyMap policyMap = null;

			if(inputValue.equals("Confirm")) {
				db.createConnection();
				policyMap = (PolicyMap) s.getAttribute("specificPolicyMap");

				//Checks if it is not already approved
				if(policyMap.getApproved() != 1) {
					db.confirmPolicyMap(policyMap);
					response.sendRedirect("ConfirmingPolicy.jsp");

					db.destroyConnection();
				}
			}
			else if(inputValue.equals("Reject")) {
				//Redirects to Eric's rejection page
				response.sendRedirect("PolicyRejection.jsp");
			}
			else if(inputValue.equals("Clarification")) {
				response.sendRedirect("Clarification.jsp");
			}
			else if(inputValue.equals("Go back")) {
				response.sendRedirect("PendingPolicies.jsp");
			}
		}
		else if(from!=null &&from.equals("submitClarification")) {
			String text = request.getParameter("clarification");
			PolicyMap specificPolicyMap = (PolicyMap) s.getAttribute("specificPolicyMap");
			db.createConnection();
			db.submitClarification(text, specificPolicyMap.getPolicyMapId());
			System.out.println(specificPolicyMap.getPolicyId());
			response.sendRedirect("PolicyApproval.jsp");
			db.destroyConnection();
		}
		else if(ref.equals("PendingClaims")) {
			db.createConnection();
			int claimID=Integer.parseInt(request.getParameter("info"));
			s.setAttribute("specificClaim", db.getClaimByClaimId(claimID));
			Claim specificClaim = db.getClaimByClaimId(claimID);
			s.setAttribute("specificPolicyMap", db.getPolicyMapByPolicyMapId(specificClaim.getPolicyMapId()));
			PolicyMap specificPolicyMap = db.getPolicyMapByPolicyMapId(specificClaim.getPolicyMapId());

			s.setAttribute("specificPolicy", db.getPolicyByPolicyId(specificPolicyMap.getPolicyId()));
			s.setAttribute("specificCustomer", db.getCustomerByCustomerId(specificPolicyMap.getCustomerId()));
			db.destroyConnection();

			response.sendRedirect("Claim.jsp");
		}
		else if(from!=null &&from.equals("Claim")) {
			String inputValue = request.getParameter("claimAction");
			Claim claim = null;

			if(inputValue.equals("Confirm")) {
				db.createConnection();
				claim = (Claim) s.getAttribute("specificClaim");

				//Checks if it is not already approved
				if(claim.getApproved() != 1) {
					db.confirmClaim(claim);
					response.sendRedirect("ConfirmingClaim.jsp");

					db.destroyConnection();
				}
			}
			else if(inputValue.equals("Reject")) {
				//Redirects to Eric's rejection page
				response.sendRedirect("ClaimRejection.jsp");
			}
			else if(inputValue.equals("Go back")) {
				response.sendRedirect("PendingClaims.jsp");
			}
		}
		// module for inserting reason for rejection into PolicyMap Table 
		// created by Chin Han Chen on 2018/08/16
		else if(ref.equals("ClaimRejection")) {
			if(Validation.checkInjection(request.getParameter("Rejection"))) {
				db.createConnection();
				db.claimRejectionReason(request.getParameter("Rejection"),Integer.parseInt(s.getAttribute("specificClaimId") + ""));
				db.claimRejectionStatus(0,Integer.parseInt(s.getAttribute("specificClaimId") + ""));
				db.destroyConnection();
				response.sendRedirect("RegularClosing.jsp");
			}else {
				request.setAttribute("fail", "fail");
				RequestDispatcher dis1 = request.getRequestDispatcher("/ClaimRejection.jsp");
				dis1.include(request, response);
			}
		}
		else if(ref.equals("PolicyRejection")) {
			if(Validation.checkInjection(request.getParameter("Rejection"))) {
				db.createConnection();
				db.policyRejectionReason(request.getParameter("Rejection"),Integer.parseInt(s.getAttribute("specificPolicyMapId") + ""));
				db.policyRejectionStatus(0,Integer.parseInt(s.getAttribute("specificPolicyMapId") + ""));
				db.destroyConnection();
				response.sendRedirect("PolicyApproval.jsp");
			}else {
				request.setAttribute("fail", "fail");
				RequestDispatcher dis1 = request.getRequestDispatcher("/PolicyRejection.jsp");
				dis1.include(request, response);
			}
		}else if(ref.equals("initiateClaim")) {
			int polMid = Integer.parseInt(request.getParameter("policyName"));
			String manid = null;
			String c_reason = request.getParameter("claimReason");
			// if the reason for claim is death of policy holder
			if(c_reason.equals("policyholderDeath")) {
				// success
				Part filePart = request.getPart("deathcert");
	 			db.createConnection();
	 			if(Validation.checkImage(filePart.getInputStream())) {
	 				db.inputData(polMid,new java.util.Date(),manid, null, filePart);
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
	 				db.inputData(polMid,new java.util.Date(),manid, null, null);
	 				response.sendRedirect("Home.jsp");
	 			}else {
	 				//request.getRequestDispatcher("/initiateClaim.jsp").forward(request, response);
	 				s.setAttribute("fail", "fail");
	 				response.sendRedirect("initiateClaim.jsp");
	 			}
	 			db.destroyConnection();
	 			
	 		// if the claim reason is intermitten claims
			}else if(c_reason.equals("intermittentClaim")) {
				// success
	 			db.createConnection();
	 			if(Validation.checkInjection(request.getParameter("interreason"))) {
	 				db.inputData(polMid,new java.util.Date(),manid, request.getParameter("interreason"), null);
	 				response.sendRedirect("Home.jsp");
	 			}else {
	 				// request.setAttribute("name", "value");
	 				request.getRequestDispatcher("initiateClaim.jsp").forward(request, response);
	 			}
	 			db.destroyConnection();
			}
		}
	}
}
