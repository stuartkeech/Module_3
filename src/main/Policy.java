package main;

/**
 * Plain old Java object for Policy
 * @author Felix
 * @created 08/16/2018
 */

public class Policy {
	private int policy_id;
	private String policy_type;
	private String policy_name;
	private int number_nominees;
	private double tenure;
	private double sum_assured;
	private String pre_reqs;
	
	Policy(int policy_id, String policy_type, String policy_name, int number_nominees, double tenure, double sum_assured, String pre_reqs) {
		this.policy_id = policy_id;
		this.policy_type = policy_type;
		this.policy_name = policy_name;
		this.number_nominees = number_nominees;
		this.tenure = tenure;
		this.sum_assured = sum_assured;
		this.pre_reqs = pre_reqs;
	}
	
	public int getPolicyId() {
		return this.policy_id;
	}
	
	public String getPolicyType() {
		return this.policy_type;
	}
	
	public String getPolicyName() {
		return this.policy_name;
	}
	
	public int getNumberNominees() {
		return this.number_nominees;
	}
	
	public double getTenure() {
		return this.tenure;
	}
	
	public String getPreReqs() {
		return this.pre_reqs;
	}
	
	public void setPolicyId(int policy_id) {
		this.policy_id = policy_id;
	}
	
	public void setPolicyType(String policy_type) {
		this.policy_type = policy_type;
	}
	
	public void setPolicyName(String policy_name) {
		this.policy_name = policy_name;
	}
	
	public void setNumberNominees(int number_nominees) {
		this.number_nominees = number_nominees;
	}
	
	public void setTenure(double tenure) {
		this.tenure = tenure;
	}
	
	public void setPreReqs(String pre_reqs) {
		this.pre_reqs = pre_reqs;
	}

	public double getSumAssured() {
		return sum_assured;
	}

	public void setSumAssured(double sum_assured) {
		this.sum_assured = sum_assured;
	}
}
