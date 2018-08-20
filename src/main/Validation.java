/**
 * Validates input from initiate claim form
 * @Chin Han Chen
 * @1.0.0
 * @Validation
 * @2018/08/16
 * @Recently put checkDate into Database Class
 * @Reviewed by Chin Han Chen & pending
 */

package main;
import java.io.*;
import javax.imageio.*;
import java.util.Date;
import java.sql.*;
import java.time.*;

public class Validation {
	public static Connection con = null;
	public static Statement st = null;
	public static ResultSet rs = null;
	
	/**
	 * 
	 * @param <String><check the url for uploaded image>
	 * @return <boolean><true if upload file is image, false if not>
	 * @exception doesn't throw exception
	 * @since
	 * @see
	 */
	public static boolean checkImage(String inp) {
		try {
			ImageIO.read(new File(inp)).toString();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param <String><The policy map id to check claim maturity>
	 * @return <boolean><true if claim has matured, false if not>
	 * @throws ClassNotFoundException, SQLException
	 * @since
	 * @see
	 */
	public static boolean checkDate(String polmID) throws Exception{
		double Temp1 = 0; // the variable for tenure length
		Date Temp2 = new Date(); // Start Date for each policy
		String dr = "oracle.jdbc.driver.OracleDriver";
		Class.forName(dr);
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tcs12345");
		st = con.createStatement();
		rs = st.executeQuery("select Policies.tenure, PolicyMap.start_date "+
		"from Policies inner join PolicyMap on Policies.policy_id = PolicyMap.policy_ID");
		while(rs.next()) {
			Temp1 = rs.getDouble(1);
			Temp2 = new Date(rs.getDate(1).getTime());
		}
		rs.close();
		st.close();
		con.close();
		LocalDate Date1 = Temp2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return(Period.between(Date1,LocalDate.now()).getYears() >= Temp1);
	}
	
	/**
	 * 
	 * @param <String><the reason for rejecting policy>
	 * @return <boolean><return true if no invalid characters in input, false otherwise>
	 * @throws
	 * @since
	 * @see
	 */
	public static boolean checkInjection(String inp) {
		int One = inp.indexOf('=');
		int Two = inp.indexOf('\"');
		if(One > 0 || Two >0) {
			return false;
		}else {
			return true;
		}
		
	}
}
