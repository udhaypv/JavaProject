package fr.epita.Core.datamodel;

public class Identity {
	String DisplayName;
	String UID;
	String Email;
	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	@Override
	public String toString() {
		return "Identity [DisplayName=" + DisplayName + ", UID=" + UID + ", Email=" + Email + "]";
	}
	public Identity() {
		super();
		// TODO Auto-generated constructor stub
	}
}
