package kn_14_5.obykhvost.usermanagement;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	
	public User(String firstName, String lastName, Date now) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = now;
	}

	public User(Long id, String firstName, String lastName, Date now) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = now;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
		{
			return false;
		}
		if(this == obj)
		{
			return true;
		}
		if(this.getId() == null && ((User)obj).getId() == null)
		{
			return true;
		}
		return this.getId().equals(((User)obj).getId());
	}

	@Override
	public int hashCode() {
		if(this.getId() == null)
		{
			return 0;
		}
		return this.getId().hashCode();
	}

	public Long getId() { return id; }
	
	public void setId(Long id) { this.id = id; }
	
	public String getFirstName() { return firstName; }
	
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return lastName; }
	
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public Date getDateOfBirth() { return dateOfBirth; }
	
	public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
	
	public String getFullName() { return getLastName() + ", " + getFirstName(); }
	
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		calendar.setTime(getDateOfBirth());
		return currentYear - calendar.get(Calendar.YEAR);
	}
	
}

