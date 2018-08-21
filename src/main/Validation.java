package main;
import java.io.*;
import javax.imageio.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import java.time.*;

public class Validation {
	public static Connection con = null;
	public static Statement st = null;
	public static ResultSet rs = null;
	
	public static boolean checkImage(String inp) {
		try {
			ImageIO.read(new File(inp)).toString();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static boolean checkDate(String polmID) throws Exception{
		double temp1 = 0;
		Date temp2 = new Date();
		String dr = "oracle.jdbc.driver.OracleDriver";
		Class.forName(dr);
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tcs12345");
		st = con.createStatement();
		rs = st.executeQuery("select Policies.tenure, PolicyMap.start_date "+
		"from Policies inner join PolicyMap on Policies.policy_id = PolicyMap.policy_ID");
		while(rs.next()) {
			temp1 = rs.getDouble(1);
			temp2 = new Date(rs.getDate(1).getTime());
		}
		rs.close();
		st.close();
		con.close();
		LocalDate date1 = temp2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return(Period.between(date1,LocalDate.now()).getYears() >= temp1);
	}
	
	public static boolean checkInjection(String inp) {
		int one = inp.indexOf('=');
		int two = inp.indexOf('\"');
		if(one > 0 || two >0) {
			return false;
		}else {
			return true;
		}
		
	}
}
