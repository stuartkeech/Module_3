package main;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Part;
 

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
   
 // created by Chin Han Chen on 2018/08/16
    public boolean checkDate(String polmID){
    	try {
    		Statement st = null;
        	ResultSet rs = null;
    		double temp1 = 0;
    		Date temp2 = new Date();
    		st = connection.createStatement();
    		rs = st.executeQuery("select Policies.tenure, PolicyMap.start_date "+
    		"from Policies inner join PolicyMap on Policies.policy_id = PolicyMap.policy_ID where policy_map_ID = "+polmID);
    		while(rs.next()) {
    			temp1 = rs.getDouble(1);
    			temp2 = new Date(rs.getDate(2).getTime());
    		}
    		rs.close();
    		st.close();
    		LocalDate date1 = temp2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    		return(Period.between(date1,LocalDate.now()).getYears() >= temp1);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return false;
	}
    
    // insert Claim into Claims Table
    // created by Chin Han Chen on 2018/08/16
    public void inputData(int inp2,java.util.Date inp3, String inp5, String inp6, String inp7, Part filePart){
    	try {
    		java.sql.Date sqlDate = new java.sql.Date(inp3.getTime());  
        	PreparedStatement pr = null;
    		pr = connection.prepareStatement("insert into Claims values((select NVL(max(claim_id)+1,1) from Claims),?,?,null,?,?,?,?)");
    		pr.setInt(1, inp2);
    		pr.setDate(2,sqlDate);
    		pr.setObject(3, inp5);
    		pr.setString(4, inp6);
    		pr.setString(5, inp7);
    		if(filePart != null) {
    			InputStream is = filePart.getInputStream();
    			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    			int nRead;
    			byte[] data = new byte[16384];
    			while((nRead = is.read(data,0,data.length))!=-1) {
    				buffer.write(data,0,nRead);
    			}
    			buffer.flush();
    			byte[] filecontents = buffer.toByteArray();
    			Blob b = connection.createBlob();
    			b.setBytes(1, filecontents);
    			pr.setBlob(6, b);
    		}else {
    			pr.setBlob(6, (Blob)null);
    		}
    		pr.executeUpdate();
    		pr.close();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
    
    // insert rejection reaonons into table
    // created by Chin Han Chen on 2018/08/16
    public void inputRejectionReason(String inp, String poliMid){
    	try {
    		PreparedStatement pr = null;
        	pr = connection.prepareStatement("insert into PolicyMap (reason_for_rejection) values (?) where policy_map_id = ?");
        	pr.setString(1,inp);
        	pr.setString(2, poliMid);
        	pr.executeUpdate();
        	
        	pr.close();
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    // check textarea for sql injection
    // created by Chin Han Chen on 2018/08/16
    public void inputRejectionStatus(int inp, String poliMid){
    	try {
    		PreparedStatement pr = null;
        	pr = connection.prepareStatement("update PolicyMap set approved = ? where policy_map_id = ?");
        	pr.setInt(1,inp);
        	pr.setString(2, poliMid);
        	pr.executeUpdate();
        	
        	pr.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    // check if claim person is the same as policy holder
    // created by Chin Han Chen on 2018/08/17
    public boolean checkOwner(String inp, String poliMid){
    	try {
    		Statement st = null;
        	ResultSet rs = null;
        	String temp = null;
    		st = connection.createStatement();
    		rs = st.executeQuery("select customer_ID from PolicyMap where policy_map_ID = "+poliMid);
    		while(rs.next()) {
    			temp = rs.getObject(1).toString();
    		}
    		rs.close();
    		st.close();
    		return(inp.equals(temp));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    // get managerID and PolicyMapID from database
    // created by Chin Han Chen on 2018/08/17
    public String[] getIDs(String inp){
    	try {
    		Statement st = null;
        	ResultSet rs = null;
        	String temp = null;
    		st = connection.createStatement();
    		rs = st.executeQuery("select Claims.manager_id, PolicyMap.policy_map_id "+
    				"from Claims inner join PolicyMap on Claims.policy_map_id = PolicyMap.policy_map_id where customer_ID = "+inp);
    		while(rs.next()) {
    			temp = rs.getObject(1).toString();
    			temp += " ";
    			temp += rs.getObject(2).toString();
    		}
    		rs.close();
    		st.close();
    		return(temp.split(" "));
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    // crosscheck the nominee is same as claimer after policy owner dies
    // created by Chin Han Chen on 2018/08/17
    public String[] checkNominee(String poliMid){
    	try {
    		Statement st = null;
        	ResultSet rs = null;
        	String temp = "";
    		st = connection.createStatement();
    		rs = st.executeQuery("select * from Nominees inner join NomineeMap on Nominees.nominee_id = NomineeMap.nominee_id where policy_map_ID = "+ poliMid);
    		while(rs.next()) {
    			temp = rs.getObject(1).toString();
    			temp += " ";
    		}
    		rs.close();
    		st.close();
    		return(temp.split(" "));
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    // get all policy_id using customer_ID
    // created by Chin Han Chen on 2018/08/20
    public String[] getPolicyId(String cusid){
    	try {
    		Statement st = null;
        	ResultSet rs = null;
        	String temp = "";
    		st = connection.createStatement();
    		rs = st.executeQuery("select policy_id from PolicyMap where customer_ID = "+cusid);
    		while(rs.next()) {
    			temp += rs.getObject(1).toString();
    			temp += " ";
    		}
    		rs.close();
    		st.close();
    		return(temp.split(" "));
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    // get all policy_map_id using customer_ID
    // created by Chin Han Chen on 2018/08/20
    public int getPolicyMapId(String polid, String cusid){
    	int temp=0;
    	try {
    		Statement st = null;
        	ResultSet rs = null;
    		st = connection.createStatement();
    		rs = st.executeQuery("select policy_map_id from PolicyMap where (policy_id = "+polid+" and customer_id = "+cusid+")");
    		while(rs.next()) {
    			temp = rs.getInt(1);
    		}
    		rs.close();
    		st.close();
    		return(temp);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return(temp);
    }
    
    // get policy mature date
    // created by Chin Han Chen on 2018/21/2018
    public String getMatureDate(String polid, String cusid) {
    	try {
    		int polMid = this.getPolicyMapId(polid, cusid);
    		Statement st = null;
        	ResultSet rs = null;
    		double temp1 = 0;
    		Date temp2 = new Date();
    		st = connection.createStatement();
    		rs = st.executeQuery("select Policies.tenure, PolicyMap.start_date "+
    		"from Policies inner join PolicyMap on Policies.policy_id = PolicyMap.policy_ID where policy_map_ID = "+polMid);
    		while(rs.next()) {
    			temp1 = rs.getDouble(1);
    			temp2 = new Date(rs.getDate(2).getTime());
    		}
    		rs.close();
    		st.close();
    		LocalDate date1 = temp2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    		return(date1.plusYears((long)temp1).toString());
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
 
}