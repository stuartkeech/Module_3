package src.com;

public class PolicyMap {
	private int policy_map_id;
	private int customer_ID;
	private int policy_ID;
	private int agent_ID;
	private String start_date;
	private int payments_per_year;
	private double premium_amount;
	private String medical_details;
	
	PolicyMap(int policy_map_id, int customer_ID, int policy_ID, int agent_ID, String start_date, int payments_per_year, double premium_amount, String medical_details) {
		this.policy_map_id = policy_map_id;
		this.customer_ID = customer_ID;
		this.policy_ID = policy_ID;
		this.agent_ID = agent_ID;
		this.start_date = start_date;
		this.payments_per_year = payments_per_year;
		this.premium_amount = premium_amount;
		this.medical_details = medical_details;
	}
	
	public int getPolicyMapId() {
		return this.policy_map_id;
	}
	
	public int getCustomerId() {
		return this.customer_ID;
	}
	
	public int getPolicyId() {
		return this.policy_ID;
	}
	
	public int getAgentId() {
		return this.agent_ID;
	}
	
	public String getStartDate() {
		return this.start_date;
	}
	
	public int getPaymentsPerYear() {
		return this.payments_per_year;
	}
	
	public double getPremiumAmount() {
		return this.premium_amount;
	}
	
	public String getMedicalDetails() {
		return this.medical_details;
	}
	
	public void setPolicyMapId(int policy_map_id) {
		this.policy_map_id = policy_map_id;
	}
	
	public void setCustomerId(int customer_ID) {
		this.customer_ID = customer_ID;
	}
	
	public void setPolicyId(int policy_ID) {
		this.policy_ID = policy_ID;
	}
	
	public void setAgentId(int agent_ID) {
		this.agent_ID = agent_ID;
	}
	
	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}
	
	public void setPaymentsPerYear(int payments_per_year) {
		this.payments_per_year = payments_per_year;
	}
	
	public void setPremiumAmount(double premium_amount) {
		this.premium_amount = premium_amount;
	}
	
	public void setMedicalDetails(String medical_details) {
		this.medical_details = medical_details;
	}
}
