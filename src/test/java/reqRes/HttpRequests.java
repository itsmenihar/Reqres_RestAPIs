package reqRes;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class HttpRequests {
	int id;

	// getting the users from page no 2
	@Test(priority = 1)
	void getUsers() {
		given().when().get("https://reqres.in/api/users?page=2").then().statusCode(200).body("page", equalTo(2)).log()
				.all();
	}

	// creating a user
	@Test(priority = 2)
	void createUser() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "Milan");
		data.put("job", "QA");
		id = given().contentType("application/json").body(data).when().post("https://reqres.in/api/users").jsonPath()
				.getInt("id");
//		.then()
//			.statusCode(201).log().all();
	}

	// update the user whatever create in that particular id
	@Test(priority = 3, dependsOnMethods = { "createUser" })
	void upDateUser() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "Milan");
		data.put("job", "Automation Tester");
		given().contentType("application/json").body(data).when().put("https://reqres.in/api/users/" + id).then()
				.statusCode(200).log().all();
	}

	// delete the user from, which "id" is created in createUser method
	@Test(priority = 4, dependsOnMethods = { "upDateUser" })
	void deleteUser() {
		given().when().delete("https://reqres.in/api/users/" + id).then().statusCode(204).log().all();
	}

}
