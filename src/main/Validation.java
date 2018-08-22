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
import javax.activation.MimetypesFileTypeMap;

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
	
	public static boolean checkImage(InputStream inp) {
		try {
			ImageIO.read(inp).toString();
			return true;
		}catch(Exception e) {
			return false;
		}
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
		if((One >= 0 || Two >= 0 )|| inp.equals("")) {
			return false;
		}else {
			return true;
		}
		
	}
}
