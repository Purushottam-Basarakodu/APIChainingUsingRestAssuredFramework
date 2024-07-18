package api.testsDetails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpointsDetails.petEndpoints;
import api.payloadDetails.PetDetails;
import io.restassured.response.Response;

public class TestPetEndpoints {
	
	PetDetails petPayload;
	Long petId;
	
	@BeforeClass
	public void GeneratePetDetails()
	{
		petPayload= new PetDetails();//creating object of PetDetails class to set the pet details
		
		petPayload.setStatus("available");
		petPayload.setId(0);
	}
	
	@Test(priority=1)
	public void testAddPettoStore(ITestContext context)
	{
		Response response =petEndpoints.AddPettoStore(petPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//created an JSON object to store the response body and extracting created Pet Id
		JSONObject jo=new JSONObject(response.asString());
		String PetIdasString =jo.get("id").toString(); // converting petId from object to string
		long id=Long.parseLong(PetIdasString); // converting pet id from string to Long
		
		System.out.println("petId printing from POST api :" +id);
		response.then().log().body();
		context.getSuite().setAttribute("petId", id); // setting up the pet id information available for other API's using ITestXContext class
			
	}
	
	@Test(priority=2)
	public void testGetPetDetails(ITestContext context)
	{
		petId=(Long) context.getSuite().getAttribute("petId"); // fetching id from CreatAPI using ITestContext class
		System.out.println("petId printing from get API : "+petId);
		Response response = petEndpoints.getPetDetails(petId); // call get API from PetEndpoints class without creating objects as these are static methods
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3)
	public void testupdatePetDetails(ITestContext context)
	{
		petPayload.setStatus("Sold");
		petId=(Long) context.getSuite().getAttribute("petId");
		petPayload.setId(petId);
		System.out.println("petId printing from Update API : "+petId);
		Response response =petEndpoints.updatePetDetails(petPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
		response.then().log().body();
		
	}

	@Test(priority=4)
	public void testdeletePetDetails(ITestContext context)
	{
		petId=(Long) context.getSuite().getAttribute("petId");
		System.out.println("petId printing from delete API : "+petId);
		Response response = petEndpoints.deletePet(petId);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
