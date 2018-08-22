package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//The DAO class is responsible for retrieving data from the database. 
//In our case, we need to change sql to whatever we want to query

public class Database {
	private String databaseURL = "jdbc:oracle:thin:@10.101.1.122:1521:xe";
	private String user = "system";
	private String password = "tcs12345";
	private Connection connection;	
	public void createConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(databaseURL, user, password);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	public void destroyConnection() {
		try {
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch(NullPointerException e2) {
			e2.printStackTrace();
		}
	}

	//created by Stuart 8/20/2018 10:27
	public double getManPower(int ID) {
		PreparedStatement statement=null;
		ResultSet result=null;
		double out=-1;
		try {
			statement=connection.prepareStatement("Select maximum_claim_approval_amount from designations where designation_id="
					+ "(select designation_id from managers where manager_id=?)");
			statement.setInt(1,ID);
			result=statement.executeQuery();
			result.next();
			try {
				out=result.getDouble(1);
			}catch(NullPointerException e) {
				//do nothing out will stay -1
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(out==0)return -1;
		return out;
	}
	//created by stuart 8/16/2018 3:34
	//updated by pankti 8/17/2018 3:34
	//getPolicy gets all the policy that have the specified approval
	public String[][] getPolicies(Integer aproved){
		ArrayList<String[]> output=new ArrayList<String[]>();
		PreparedStatement statement=null;
		ResultSet result=null;
		try {
			if (aproved==null)statement=connection.prepareStatement("Select Policy_map_id from Policymap where approved is NULL");
			else statement=connection.prepareStatement("Select policy_map_id from policymap where approved="+aproved);    		
			result = statement.executeQuery();
			ArrayList<Integer> policyMapIDs=new ArrayList<Integer>();           
			while (result.next()) {
				policyMapIDs.add(result.getInt("policy_map_id"));             
			}
			for(int i:policyMapIDs) {
				statement=connection.prepareStatement("SELECT customer_ID, policy_ID from PolicyMap where policy_Map_ID=?");
				statement.setInt(1, i);
				result=statement.executeQuery();
				result.next();
				String userID=result.getInt(1)+"";
				int policyID=result.getInt(2);
				statement=connection.prepareStatement("SELECT policy_name from Policies where policy_ID=?");            	
				statement.setInt(1, policyID);
				result=statement.executeQuery();
				result.next();
				String policyName=result.getString(1);
				String[]out= {policyID+"",policyName,userID,i+""};
				output.add(out);            	
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		try {
			result.close();
			statement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		String[][] outputA = new String[output.size()][4];
		for (int i = 0; i < output.size(); i++) {
			String[] row = output.get(i);
			outputA[i] =row;
		}
		return outputA;
	}



	//created by stuart 8/16/2018 2:45
	public String[][] getClaims(Integer aproved,double sum){
		ArrayList<String[]> output=new ArrayList<String[]>();
		PreparedStatement statement=null;
		ResultSet result=null;
		try {
			if (aproved==null)statement=connection.prepareStatement("Select claim_id from claims where approved is NULL");
			else statement=connection.prepareStatement("Select claim_id from claims where approved="+aproved);
			result = statement.executeQuery();
			ArrayList<Integer> claimIDs=new ArrayList<Integer>();           
			while (result.next()) {
				claimIDs.add(result.getInt("claim_id"));             
			}
			for(int i:claimIDs) {
				statement=connection.prepareStatement("SELECT customer_ID, policy_ID, premium_amount from PolicyMap where policy_Map_ID=(Select policy_map_id from claims where claim_id=?)");
				statement.setInt(1, i);
				result=statement.executeQuery();
				result.next();
				String userID=result.getInt(1)+"";
				int policyID=result.getInt(2);
				statement=connection.prepareStatement("SELECT sum_assured from Policies where policy_ID=?");
				statement.setInt(1, policyID);
				result=statement.executeQuery();
				result.next();
				double claimAmount=result.getDouble(1);
				String[]out= {userID,policyID+"",claimAmount+"",i+""};
				if(claimAmount<sum ||sum==-1)output.add(out);            	
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		try {
			result.close();
			statement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		String[][] outputA = new String[output.size()][4];
		for (int i = 0; i < output.size(); i++) {
			String[] row = output.get(i);
			outputA[i] =row;
		}
		return outputA;
	}
	/**
	 * @author Felix
	 * @created 08/20/2018
	 * @param claim_id
	 * @return Claim
	 */
	public Claim getClaimByClaimId(int claim_id) {
		Claim claim = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			System.out.println(connection.isClosed());
			String query = "SELECT * FROM Claims WHERE claim_id = ?";
			System.out.println(claim_id);
			statement = connection.prepareStatement(query);
			statement.setInt(1, claim_id);
			result = statement.executeQuery();
			result.next();
			claim = new Claim(result.getInt("claim_id"), result.getInt("policy_map_id"), result.getString("claim_date"), result.getInt("approved"), result.getInt("manager_id"), result.getString("reason_for_claim"), result.getString("reason_for_rejection"));

		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		return claim;
	}

	/**
	 * @author Felix
	 * @created 08/17/2018
	 * @param policy_id
	 * @return Policy
	 */
	public Policy getPolicyByPolicyId(int policy_ID) {
		Policy policy = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = "SELECT * FROM Policies WHERE policy_ID = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, policy_ID);
			result = statement.executeQuery();
			result.next();
			policy = new Policy(result.getInt("policy_id"),result.getString("policy_type"),result.getString("policy_name"),result.getInt("number_nominees"),result.getDouble("tenure"), result.getDouble("sum_assured"),result.getString("pre_reqs"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		return policy;
	}

	/**
	 * @author Felix
	 * @created 08/17/2018
	 * @param policy_map_id
	 * @return PolicyMap
	 */
	public PolicyMap getPolicyMapByPolicyMapId(int policy_map_id) {
		PolicyMap policyMap = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = "SELECT * FROM PolicyMap WHERE policy_map_id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, policy_map_id);
			result = statement.executeQuery();
			result.next();
			policyMap = new PolicyMap(result.getInt("policy_map_id"), result.getInt("customer_ID"), result.getInt("policy_ID"), result.getInt("agent_ID"), result.getDate("start_date"), result.getInt("payments_per_year"), result.getDouble("premium_amount"), result.getString("medical_details"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		return policyMap;
	}

	/**
	 * @author Felix
	 * @created 08/17/2018
	 * @param customer_id
	 * @return Customer
	 */
	public Customer getCustomerByCustomerId(int customer_id) {
		Customer customer = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = "SELECT * FROM Customers WHERE customer_ID = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, customer_id);
			result = statement.executeQuery();
			result.next();
			customer = new Customer(result.getInt("customer_ID"), result.getString("firstname"), result.getString("middlename"), result.getString("lastname"), result.getString("DOB"), result.getString("gender"), result.getString("occupation"), result.getDouble("salary"), result.getString("marital_status"), result.getInt("number_children"), result.getString("SIN"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}

	/**
	 * @author Felix
	 * @created 08/20/2018
	 * @param claim
	 */
	public void confirmClaim(Claim claim) {
		PreparedStatement statement = null;

		try {
			String query = "UPDATE Claims SET approved = 1 WHERE claim_id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, claim.getClaimId());
			statement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Felix
	 * @created 08/17/2018
	 * @param policyMap
	 */
	public void confirmPolicyMap(PolicyMap policyMap) {
		PreparedStatement statement = null;

		try {
			String query = "UPDATE PolicyMap SET approved = 1 WHERE policy_map_id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, policyMap.getPolicyMapId());
			statement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Felix
	 * @created 08/20/2018
	 * @param text
	 * @param policyMapId
	 */
	public void submitClarification(String text, int policyMapId) {
		PreparedStatement statement = null;

		try {
			String query = "UPDATE PolicyMap SET reason_for_rejection = ? WHERE policy_map_id = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, text);
			statement.setInt(2, policyMapId);
			statement.executeUpdate();

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}   


	// insert rejection reaonons into table
	// created by Chin Han Chen on 2018/08/16
	public void claimRejectionReason(String rejectionReason, int claimId){
		try {
			PreparedStatement pr = null;
			pr = connection.prepareStatement("update Claims set reason_for_rejection = ? where claim_id = ?");
			pr.setString(1, rejectionReason);
			pr.setInt(2, claimId);
			pr.executeUpdate();

			pr.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// insert rejection reaonons into table
	// created by Chin Han Chen on 2018/08/16
	public void policyRejectionReason(String rejectionReason, int policyMapId){
		try {
			PreparedStatement pr = null;
			pr = connection.prepareStatement("update PolicyMap set reason_for_rejection = ? where policy_map_id = ?");
			pr.setString(1, rejectionReason);
			pr.setInt(2, policyMapId);
			pr.executeUpdate();

			pr.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	// created by Chin Han Chen on 2018/08/16
	public void claimRejectionStatus(int inp, int claimId){
		try {
			PreparedStatement pr = null;
			pr = connection.prepareStatement("update Claims set approved = ? where claim_id = ?");
			pr.setInt(1, inp);
			pr.setInt(2, claimId);
			pr.executeUpdate();

			pr.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void policyRejectionStatus(int inp, int policyMapId){
		try {
			PreparedStatement pr = null;
			pr = connection.prepareStatement("update PolicyMap set approved = ? where policy_map_id = ?");
			pr.setInt(1,inp);
			pr.setInt(2, policyMapId);
			pr.executeUpdate();

			pr.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public double[] getEligibleAmmount(int[] claimID) {
		double [] out=new double[claimID.length];
		for(int i=0;i<claimID.length;i++) {
			//System.out.println(claimID[i]);
			Claim claim=getClaimByClaimId(claimID[i]);
			PolicyMap map=getPolicyMapByPolicyMapId(claim.getPolicyMapId());
			System.out.println(claimID[i]);

			Policy policy=getPolicyByPolicyId(map.getPolicyId());
			double SA=policy.getSumAssured();
			double tenure=policy.getTenure();
			double completed=dateSub(new Date(map.getStartDate().getTime()));
			out[i]= (double)(Math.round((((SA/tenure)*completed)-(0.04*SA+2000)) * 100)) / 100.0;
		}
		return out;
	}
	private double dateSub(Date sDate) {
		Date cDate=new Date();
		int cYears=cDate.getYear();
		int cMonths=cDate.getMonth();
		int sYears=sDate.getYear();
		int sMonths=sDate.getMonth();
		return 12*(cYears-sYears)+(cMonths-sMonths);
	}


}