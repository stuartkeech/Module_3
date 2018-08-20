package main;
<<<<<<< HEAD

=======
>>>>>>> master
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 

//The DAO class is responsible for retrieving data from the database. 
//In our case, we need to change sql to whatever we want to query

public class Database {
    private String databaseURL = "jdbc:oracle:thin:@10.101.1.152:1521:xe";
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
    //created by stuart 8/16/2018 3:34
//updated by pankti 8/17/2018 3:34


//getPolicy gets all the policy that are approved


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
	        connection.close();
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
// Created by Felix on Thursday August 16, 2018
// Updated by Pankti on Friday  8/17/2018
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
			policy = new Policy(result.getInt("policy_id"),result.getString("policy_type"),result.getString("policy_name"),result.getInt("number_nominees"),result.getDouble("tenure"),result.getString("pre_reqs"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return policy;
	}



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
			policyMap = new PolicyMap(result.getInt("policy_map_id"), result.getInt("customer_ID"), result.getInt("policy_ID"), result.getInt("agent_ID"), result.getString("start_date"), result.getInt("payments_per_year"), result.getDouble("premium_amount"), result.getString("medical_details"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return policyMap;
	}
	
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
    //created by stuart 8/16/2018 2:45
    public String[][] getClaims(Integer aproved,int sum){
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
	        connection.close();
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
   
}