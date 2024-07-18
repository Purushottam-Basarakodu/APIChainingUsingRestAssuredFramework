package api.payloadDetails;

//this is payload class having required request body parameters to create a pet id

public class PetDetails {
	
	long id;
	String status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
