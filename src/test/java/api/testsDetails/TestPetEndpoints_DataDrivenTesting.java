package api.testsDetails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpointsDetails.petEndpoints;
import api.payloadDetails.PetDetails;
import api.utilitiesDetails.DataProviders;
import io.restassured.response.Response;

public class TestPetEndpoints_DataDrivenTesting {
	
	long petId;
	PetDetails petPayload;
	
	@Test(priority=1,dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testAddPettoStore(String id, String status)
	{
		petPayload= new PetDetails();
		petPayload.setId(Long.parseLong(id));
		petPayload.setStatus(status);
		Response response =petEndpoints.AddPettoStore(petPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//created an JSON object to store the response body and extracting created Pet Id
		JSONObject jo=new JSONObject(response.asString());
		String PetIdasString =jo.get("id").toString(); // converting petId from object to string
		long id_created=Long.parseLong(PetIdasString); // converting pet id from string to Long
		
		System.out.println("petId printing from POST api :" +id);
		response.then().log().body();
			
	}
	
	@Test(priority=2,dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testGetPetDetails(String id, String status)
	{
		petId=Long.parseLong(id); // fetching id from CreatAPI using ITestContext class
		System.out.println("petId printing from get API : "+petId);
		Response response = petEndpoints.getPetDetails(petId); // call get API from PetEndpoints class without creating objects as these are static methods
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3,dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testupdatePetDetails(String id, String status)
	{
		petPayload.setStatus(status);
		petPayload.setStatus("Sold");
		petPayload.setId(Long.parseLong(id));
		System.out.println("petId printing from Update API : "+petId);
		Response response =petEndpoints.updatePetDetails(petPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
		response.then().log().body();
		
	}
	
	
}
