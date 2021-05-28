package usermanagement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

	private String companyName;

	private String fName;
	private String lName;
	private String mName;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int personId;

	public String getCompanyName() {
		return companyName;
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public String getmName() {
		return mName;
	}

	public int getPersonId() {
		return personId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

}
