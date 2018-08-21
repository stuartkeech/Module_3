package main;

/**
 * Plain old Java object for Claim
 * @author Felix
 * @created 08/20/2018
 */

public class Claim {

	private int claim_id;
	private int policy_map_id;
	private String claim_date;
	private int approved;
	private int manager_id;
	private String reason_for_claim;
	private String reason_for_rejection;
	//Blob
	
	public Claim(int claim_id, int policy_map_id, String claim_date, int approved, int manager_id, String reason_for_claim, String reason_for_rejection) {
		this.claim_id = claim_id;
		this.policy_map_id = policy_map_id;
		this.claim_date = claim_date;
		this.approved = approved;
		this.manager_id = manager_id;
		this.reason_for_claim = reason_for_claim;
		this.reason_for_rejection = reason_for_rejection;
	}
	
	public int getClaimId() {
		return claim_id;
	}
	public void setClaimId(int claim_id) {
		this.claim_id = claim_id;
	}
	public int getPolicyMapId() {
		return policy_map_id;
	}
	public void setPolicyMapId(int policy_map_id) {
		this.policy_map_id = policy_map_id;
	}
	public String getClaimDate() {
		return claim_date;
	}
	public void setClaimDate(String claim_date) {
		this.claim_date = claim_date;
	}
	public int getApproved() {
		return approved;
	}
	public void setApproved(int approved) {
		this.approved = approved;
	}
	public int getManagerId() {
		return manager_id;
	}
	public void setManagerId(int manager_id) {
		this.manager_id = manager_id;
	}
	public String getReasonForClaim() {
		return reason_for_claim;
	}
	public void setReasonForClaim(String reason_for_claim) {
		this.reason_for_claim = reason_for_claim;
	}
	public String getReasonForRejection() {
		return reason_for_rejection;
	}
	public void setReasonForRejection(String reason_for_rejection) {
		this.reason_for_rejection = reason_for_rejection;
	}
}