package zenler;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;

public class Users {
	String xApiKey = "WAXRZCYCXUSVXLL5KF2RDUO3IMHLDCML";
	String id;

	@Test(priority = 1)
	void createUser() {

		HashMap<String, Object> userData = new HashMap<>();

		// Add the JSON data to the HashMap
		userData.put("first_name", "Jhon");
		userData.put("last_name", "Cena");
		userData.put("email", "jhoncena" + (int) (Math.random() * 1000) + "@gmail.com");
		userData.put("password", "Jhon@123");
		userData.put("commission", "10");

		// Create an array to store roles and add it to the HashMap
		int[] roles = { 7 };
		userData.put("roles", roles);

		Response response = given().contentType("application/json").headers("X-API-Key", xApiKey)
				.headers("X-Account-Name", "sdet").headers("Accept", "application/json").body(userData).when()
				.post("https://api.newzenler.com/api/v1/users");
		id = response.jsonPath().getString("data.id");
		response.then().statusCode(201).body("data.first_name", equalTo("Jhon")).body("data.last_name", equalTo("Cena"))
				.body("message", equalTo("People added successfully")).log().all();
	}

	@Test(priority = 2, dependsOnMethods = { "createUser" })
	void getUser() {
		given().when().get("https://api.newzenler.com/api/v1/users/" + id).then().statusCode(200).log().all();
	}

	@Test(priority = 3, dependsOnMethods = { "getUser" })
	void deleteUser() {
		given().contentType("application/json").headers("X-API-Key", xApiKey).headers("X-Account-Name", "sdet")
				.headers("Accept", "application/json").when().delete("https://api.newzenler.com/api/v1/users/" + id)
				.then().statusCode(200).log().all();
	}
}
