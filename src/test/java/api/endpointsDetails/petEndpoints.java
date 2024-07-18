package api.endpointsDetails;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payloadDetails.PetDetails;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/*
 * performing CRUD operations here
 * 
 * */

public class petEndpoints {
	
	public static ResourceBundle getURL()
	{
		//creating a method to call URLs from properties file using ResourceBundle class
		ResourceBundle urls=ResourceBundle.getBundle("host_Urls");
		
		return urls;
	}
	
	//adding pet details to the Store
	public static Response AddPettoStore(PetDetails payload)
	{
		String post_url=getURL().getString("post_url");
		Response response =given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.body(payload)
		
		.when()
			.post(post_url);
		return response;
	}
	
	//Getting petdetails using petId
	public static Response getPetDetails(Long petId)
	{
		String get_url=getURL().getString("get_url");
		
		Response response=given()
			.pathParam("petId", petId)
		
		.when()
			.get(get_url);
		return response;
	}
	
	//updating pet details using petId as request body parameter
	public static Response updatePetDetails(PetDetails payload)
	{
		String update_url=getURL().getString("update_url");
		Response response = given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.body(payload)
		
		.when()
			.put(update_url);
		return response;
	}
	
	//deleting pet details using petId
	public static Response deletePet(long petId)
	{
		String delete_url=getURL().getString("delete_url");
		
		Response response =given()
			.pathParam("petId", petId)
		
		.when()
			.delete(delete_url);
		return response;
	}

}
