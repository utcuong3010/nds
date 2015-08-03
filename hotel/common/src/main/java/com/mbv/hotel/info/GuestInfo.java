package com.mbv.hotel.info;

public class GuestInfo {
	

	private String lastName;
	private String firstName;
	private String email;
	private String mobile;
	private String nationalId;
	
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the nationalId
	 */
	public String getNationalId() {
		return nationalId;
	}
	/**
	 * @param nationalId the nationalId to set
	 */
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GuestInfo [lastName=" + lastName + ", firstName=" + firstName
				+ ", email=" + email + ", mobile=" + mobile + ", nationalId="
				+ nationalId + "]";
	}
	

	

}
